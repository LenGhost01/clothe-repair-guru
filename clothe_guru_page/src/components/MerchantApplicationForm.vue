<script setup>
/*
* 商家名称
* 注册地址
* 联系电话
* 电子邮件地址
* 营业执照号码（或相应的商业注册证明） 可提供多个
* 店铺或公司简介
* */
import {onMounted, reactive, ref} from "vue";
import AMapContainer from "./AMapContainer.vue";
import {MinusCircleOutlined, PlusOutlined} from "@ant-design/icons-vue"
import axios from "axios";
import {message} from "ant-design-vue";

const userId = ref("")
const AMapEssential = reactive({
  securityCode: "10578ac76ed4165d4ca8c9ef89a6f216",
  key: "8249a85c9fbccce1e4d8b6ae3f2bd9f1",
})
const open = ref(false);
const ping = ref("")
const showModal = () => {
  open.value = true
};
const handleOk = e => {
  ping.value = new Date().toString()
};
const putLocationHandler = (e) => {
  data.address = e.selectedAddress
  open.value = false
}

const data = reactive({
  merchantName: "",
  address: "",
  phone: "",
  email: "",
  certification: [],
  introduce: "",
})

// 动态添加表单功能实现
const addDomain = () => {
  data.certification.push({
    text: '',
    image: [],
    id: new Date() //采用时间戳作为唯一id
  })
}

const removeDomainById = (item) => {
  const index = data.certification.indexOf(item);
  if (index !== -1) {
    data.certification.splice(index, 1);
  }
}

// 文件上传功能
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

// 提交数据集
const submitDataSet = () => {
  // 使用formData封装，发送到java端
  // 封装数据信息
  let certificationImages = []
  let certificationText = []
  data.certification.forEach(item => {
    certificationImages.push(item.image[0])
    certificationText.push(item.text)
  })
  let dataFormatterJson = {
    userId: userId.value,
    merchantName: data.merchantName,
    address: data.address,
    phone: data.phone,
    email: data.email,
    certificationText: certificationText,
    introduce: data.introduce,
  }


  // 使用formData封装image数据
  let formData = new FormData()
  certificationImages.forEach(item => {
    const img = item.originFileObj
    const suffix = img.name.split('.').pop()
    const blob = new Blob([img], {type: img.type})
    formData.append('images', blob, `image_${Date.now()}.${suffix}`)
  })
  formData.append("regMesg", JSON.stringify(dataFormatterJson))
  axios.post("/requests/application/sendApplication", formData
  ).then(res => {
    message.success("上传成功")
  }).catch(err => {
    message.warning(err.response.data, 2)
  })

}

// 从url中获取到userid的值
onMounted(() => {
  const queryParams = new URLSearchParams(window.location.search);
  userId.value = queryParams.get("userId")
})
</script>

<template>
  <div class="container">
    <div style="width: 100%">
      <!-- 商家名称输入 -->
      <a-row :gutter="[16,16]">
        <a-col :span="12">
          <a-typography-text>商家名称</a-typography-text>
          <br>
          <a-input v-model:value.lazy="data.merchantName" placeholder="请输入商家名称..."/>
        </a-col>
        <a-col :span="12">
          <a-typography-text>注册地址</a-typography-text>
          <br>
          <a-input v-model:value.lazy="data.address" style="width: calc(100% - 4em)"
                   placeholder="请输入商家地址或定位..."/>
          <a-button type="link" size="small" @click="showModal" style="width: 4em">定位</a-button>
        </a-col>
        <a-col :span="12">
          <a-typography-text>联系电话</a-typography-text>
          <br>
          <a-input v-model:value.lazy="data.phone" placeholder="请输入联系电话..."/>
        </a-col>
        <a-col :span="12">
          <a-typography-text>联系邮箱</a-typography-text>
          <br>
          <a-input v-model:value.lazy="data.email" placeholder="请输入联系邮箱..."/>
        </a-col>
        <a-col :span="24">
          <a-typography-text>店铺简介</a-typography-text>
          <br>
          <a-textarea v-model:value="data.introduce" :auto-size="{ minRows: 5, maxRows: 10 }" :maxlength="500"
                      placeholder="请输入店铺简介..."/>
        </a-col>
        <!-- 可添加多个文件组，分别为图片名和图片本身，表现为动态增减表单 -->
        <a-col :span="24">
          <a-typography-text>证件信息</a-typography-text>
          <br>
          <div v-for="(domain,index) in data.certification" :key="domain.id" class="uploadContainer">
            <a-input v-model:value.lazy="domain.text" placeholder="请输入证件信息..."
                     style="display: inline-block;width: 50%"></a-input>
            <a-upload class="uploadUnit" v-model:file-list="domain.image" name="avatar" action=""
                      list-type="picture-card"
                      @preview="handlePreview" :before-upload="beforeUpload" :max-count="1">
              <div style="display: inline-block" v-if="domain.image.length < 1">
                <plus-outlined/>
                <div style="margin-top: 8px">上传图片</div>
              </div>
            </a-upload>
            <MinusCircleOutlined v-if="data.certification.length > 1" @click="removeDomainById(domain)"/>
          </div>
          <a-button type="dashed" style="width: 60%" @click="addDomain">
            <PlusOutlined/>
            添加信息
          </a-button>
        </a-col>
        <a-button type="primary" @click="submitDataSet">提交请求</a-button>
      </a-row>
    </div>


    <!-- 选择地址模态窗口 -->
    <a-modal
        v-model:open="open"
        title="定位"
        width="100%"
        wrap-class-name="full-modal"
        @ok="handleOk"
        ok-text="确认"
        cancel-text="取消"
    >
      <a-map-container :ping="ping" :a-map-essential="AMapEssential"
                       @putLocation="putLocationHandler"></a-map-container>
    </a-modal>
    <!--  上传图像预览模态窗口  -->
    <a-modal :open="previewVisible" :title="previewTitle" :footer="null" @cancel="handleCancel">
      <img alt="example" style="width: 100%" :src="previewImage"/>
    </a-modal>
  </div>
</template>

<style scoped>
.container {
  display: inline-block;
  width: 100%;
  height: auto;
  padding: 2em;
}

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