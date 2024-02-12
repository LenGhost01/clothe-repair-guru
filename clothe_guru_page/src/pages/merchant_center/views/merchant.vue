<script setup>
import store from "/src/store/store.js";
import {onMounted, reactive, ref} from "vue";
import {MinusCircleOutlined, PlusOutlined} from "@ant-design/icons-vue";
import axios from "axios";
import {message} from "ant-design-vue";
//todo 有一个错误，产生在添加一个新证件并输入内容后再添加一个新的证件，产生原因未知

const queryData = ref({})

// 图片上传功能
const previewVisible = ref(false);
const previewImage = ref('');
const previewTitle = ref('');
const beforeUpload = () => {
  // 手动上传
  return false
}

const handlePreview = async (file) => {
  if (!file.url && !file.preview) {
    file.preview = await getBase64(file.originFileObj);
  }
  previewImage.value = file.url || file.preview;
  previewVisible.value = true;
  previewTitle.value = file.name || file.url.substring(file.url.lastIndexOf('/') + 1);
}

const handleCancel = () => {
  previewVisible.value = false;
  previewTitle.value = '';
};

function getBase64(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
  });
}

// 动态添加表单功能实现
const addedImage = reactive({image: []})
const addDomain = () => {
  console.log(addedImage.image)
  addedImage.image.push({
    key: "",
    img: [],
  })
}

const removeDomainById = (item) => {
  const index = addedImage.image.indexOf(item);
  if (index !== -1) {
    addedImage.image.splice(index, 1);
  }
}

const removeItemByKey = (item) => {
  const index = queryData.value.certification.indexOf(item);
  if (index !== -1) {
    queryData.value.certification.splice(index, 1);
  }
}

const submitModify = () => {
  // 封装图片
  let certificationImages = []
  let certificationText = []
  addedImage.image.forEach(item => {
    certificationImages.push(item.img[0])
    certificationText.push(item.key)
  })
  // 封装json对象
  queryData.value.certificationText = certificationText
  // post请求将formData发送到后端
  let formData = new FormData()
  certificationImages.forEach(item => {
    const img = item.originFileObj
    const suffix = img.name.split('.').pop()
    const blob = new Blob([img], {type: img.type})
    formData.append('images', blob, `image_${Date.now()}.${suffix}`)
  })
  formData.append("metaData", JSON.stringify(queryData.value))
  axios.post("/requests/merchant/updateMerchant", formData
  ).then(res => {
    store.dispatch("merchantStore/updateMerchantState",res.data)
    queryData.value = JSON.parse(JSON.stringify(res.data))
    // 清除添加图片数组
    addedImage.image = []
    message.success("上传成功")
  }).catch(err => {
    message.warning(err.response.data, 2)
  })
}

onMounted(() => {
  // 深拷贝
  queryData.value = JSON.parse(JSON.stringify(store.state.merchantStore.merchant))
})
</script>

<template>
  <div class="container" style="min-height: 80vh;padding: 1em">
    <a-row :gutter="[16,16]">
      <a-col :span="24">
        <a-typography-title :level="5">店铺编号: {{ queryData.merchantId }}</a-typography-title>
      </a-col>
      <a-col :span="24">
        <a-typography-title :level="5">店铺名称: {{ queryData.merchantName }}</a-typography-title>
      </a-col>
      <a-col :span="24">
        <a-typography-title :level="5">联系电话：
          <a-input v-model:value="queryData.contactMesg" style="width: 70%"></a-input>
        </a-typography-title>
      </a-col>
      <a-col :span="24">
        <a-typography-title :level="5">联系邮箱：
          <a-input v-model:value="queryData.email" style="width: 70%"></a-input>
        </a-typography-title>
      </a-col>
      <a-col :span="24">
        <a-typography-title :level="5">地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：
          <a-input v-model:value="queryData.address" style="width: 70%"></a-input>
        </a-typography-title>
      </a-col>
      <a-col :span="24">
        <a-typography-title :level="5">店铺描述：</a-typography-title>
        <a-textarea v-model:value="queryData.merchantDescription" style="width: 76%"></a-textarea>
      </a-col>
      <a-col :span="24">
        <a-typography-title :level="5">证件信息：</a-typography-title>
        <div v-for="(item,index) in queryData.certification" :key="item.key" class="uploadContainer">
          <!-- 已有内容展示 -->
          <a-typography-title :level="5">{{ item.key }}：</a-typography-title>
          <a-image :src="'/imgs/'+item.value" style="height: 15rem;width: auto"></a-image>
          <MinusCircleOutlined v-if="queryData.certification.length + addedImage.image.length > 1"
                               @click="removeItemByKey(item)"/>
        </div>
        <div v-for="(domain,index) in addedImage.image" :key="domain.key" class="uploadContainer">
          <!-- 添加内容 -->
          <a-input v-model:value.lazy="domain.key" placeholder="请输入证件信息..."
                   style="display: inline-block;width: 50%"></a-input>
          <a-upload class="uploadUnit" v-model:file-list="domain.img" name="avatar" action=""
                    list-type="picture-card"
                    @preview="handlePreview" :before-upload="beforeUpload" :max-count="1">
            <div style="display: inline-block" v-if="domain.img.length < 1">
              <plus-outlined/>
              <div style="margin-top: 8px">上传图片</div>
            </div>
          </a-upload>
          <MinusCircleOutlined v-if="queryData.certification.length + addedImage.image.length > 1"
                               @click="removeDomainById(domain)"/>
        </div>
        <a-button type="dashed" style="width: 60%" @click="addDomain">
          <PlusOutlined/>
          添加证件
        </a-button>
      </a-col>
      <a-button @click="submitModify">提交修改</a-button>
    </a-row>
  </div>
</template>

<style scoped>
.uploadContainer {
  display: inline-block;
  height: auto;
  width: 100%;
}

.uploadUnit {
  margin-left: 1em;
  width: auto;
}

.uploadUnit :deep(.ant-upload-list) {
  display: inline-block;
}
</style>

<style lang="less">
.full-modal {
  .ant-modal {
    max-width: 100%;
    top: 0;
    padding-bottom: 0;
    margin: 0;
  }

  .ant-modal-content {
    display: flex;
    flex-direction: column;
    height: calc(100vh);
  }

  .ant-modal-body {
    flex: 1;
  }
}
</style>