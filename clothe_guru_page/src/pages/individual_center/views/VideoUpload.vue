<script setup>
import {onBeforeMount, reactive, ref} from "vue";
import {InboxOutlined, MinusOutlined, PlusOutlined} from '@ant-design/icons-vue'
import {message} from "ant-design-vue";
import axios from "axios";
import zhCN from "ant-design-vue/es/locale/zh_CN.js";
import dayjs from "dayjs";

dayjs.locale("zh-cn")
const dateFormat = 'YYYY-MM-DD';

const uploadData = reactive({
  video: [],
  videoData: {
    videoName: '',
    currentEpisode: '',
    totalEpisode: '',
    starring: '',
    description: '',
    type: "",
    subType: [],
    location: '',
    publishDate: '',
  },
  image: []
})
const videoType = ref([])
const videoSubType = ref(new Array(4))
const selectedSubType = ref([])
const videoLocations = ref([])
const imgBeforeUpload = (image) => {
  uploadData.image = [...(uploadData.image || []), image]
  return false
}

const videoBeforeUpload = (video) => {
  uploadData.video = [...(uploadData.video || []), video]
  return false
}

// 图片上传页面原
function getBase64(file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = error => reject(error);
  });
}

const previewVisible = ref(false);
const previewImage = ref('');
const previewTitle = ref('');
const handleCancel = () => {
  previewVisible.value = false;
  previewTitle.value = '';
};
const handlePreview = async file => {
  if (!file.url && !file.preview) {
    file.preview = await getBase64(file.originFileObj);
  }
  previewImage.value = file.url || file.preview;
  previewVisible.value = true;
  previewTitle.value = file.name || file.url.substring(file.url.lastIndexOf('/') + 1);
};


const submit = () => {
  // 以表单格式发送数据
  let formData = new FormData();
  // formData.append('video',uploadData.video[0])


  uploadData.image.forEach(item => {
    const img = item.originFileObj
    const suffix = img.name.split('.').pop()
    const blob = new Blob([img], {type: img.type})
    formData.append('image', blob, `image_${Date.now()}.${suffix}`)
  })

  uploadData.video.forEach(item => {
    const video = item.originFileObj
    const suffix = video.name.split('.').pop()
    const blob = new Blob([video], {type: video.type})
    formData.append('video', blob, `video_${Date.now()}.${suffix}`)
  })
  formData.append("jsonData",JSON.stringify(uploadData.videoData))

  // 使用axios请求发送
  axios.post("/video-master/resource/videoUpload", formData
  ).then(res => {
    if (res.data === true) {
      message.success("上传成功")
    } else {
      message.warning('上传失败，请重试')
    }

  }).catch(err => {
    message.warning(err, 2)
  })
}

const handleChange = (e) => {
  let index = videoType.value.findIndex(item => item.value === e)
  selectedSubType.value = videoSubType.value[index + 1]
}

const popupScroll = () => {
  console.log('popupScroll');
};

onBeforeMount(async () => {
  axios.get("/video-master/resource/getVideoCategory")
      .then((res) => {
        res.data.category.forEach(item => videoType.value.push({
          value: item,
          label: item,
        }))

        for (let index in res.data.subCategory) {
          let arr = res.data.subCategory[index].map(item => {
            item = {value: item}
            return item
          })

          videoSubType.value[index] = arr
        }
      })
      .catch((err) => {
        console.log(err.response)
        if (err.response.status >= 500 && err.response.status < 600) {
          message.error("服务器出现错误，请及时联系服务器管理员")
        }
      })

  axios.get("/video-master/resource/getAllLocations")
      .then((res) => {
        console.log(res.data)
        let received = res.data;
        received = received.map(item => {
          return{
            value: item,
            label: item,
          }
        })
        videoLocations.value = received
      })
      .catch(() => {

      })
})
</script>

<template>
  <div style="position: relative" class="upload-wrapper">
    <div>
      <div>
        <a-typography-text>视频图片</a-typography-text>
        <a-upload
            v-model:file-list="uploadData.image"
            :max-count="1"
            action="/video-master/resource/imgTest"
            list-type="picture-card"
            @preview="handlePreview"
            name="image"
            :before-upload="imgBeforeUpload"
            :accept="'image/*'"
        >
          <div v-if="uploadData.image.length < 2">
            <plus-outlined/>
            <div style="margin-top: 8px">上传图片</div>
          </div>
        </a-upload>
        <a-modal :open="previewVisible" :title="previewTitle" :footer="null" @cancel="handleCancel">
          <img alt="example" style="width: 100%" :src="previewImage"/>
        </a-modal>
      </div>
      <div>
        <a-typography-text>上传视频</a-typography-text>
        <a-upload-dragger
            v-model:fileList="uploadData.video"
            name="video"
            :max-count="1"
            :multiple="false"
            action="/video-master/resource/videoTest"
            :before-upload="videoBeforeUpload"
            :accept="'video/*'"
        >
          <p class="ant-upload-drag-icon">
            <inbox-outlined></inbox-outlined>
          </p>
          <p class="ant-upload-text">单击或拖动文件到此区域进行上传
          </p>
        </a-upload-dragger>
      </div>
    </div>
    <a-row :gutter="[16,16]">
      <a-col :span="12">
        <div class="wrapper-unit">
          <a-typography-text>视频名称</a-typography-text>
          <a-input placeholder="请输入视频名称" v-model:value="uploadData.videoData.videoName"></a-input>
        </div>
        <div class="wrapper-unit">
          <a-typography-text>视频类型</a-typography-text>
          <br>
          <a-select
              v-model:value="uploadData.videoData.type"
              style="width: 120px"
              :options="videoType"
              @focus="focus"
              @change="handleChange"
          ></a-select>
          <a-select
              v-model:value="uploadData.videoData.subType"
              :options="selectedSubType"
              mode="multiple"
              placeholder="请选择视频子类型(可多选)"
              style="width: 20em"
              @popupScroll="popupScroll"
          ></a-select>
        </div>
        <div class="wrapper-unit">
          <a-typography-text>视频集数</a-typography-text>
          <br>
          <a-typography-text>当前</a-typography-text>
          <a-input-number :controls="false" v-model:value="uploadData.videoData.currentEpisode"></a-input-number>
          <a-typography-text>集</a-typography-text>
          <MinusOutlined/>
          <a-typography-text>共</a-typography-text>
          <a-input-number :controls="false" v-model:value="uploadData.videoData.totalEpisode"></a-input-number>
          <a-typography-text>集</a-typography-text>
        </div>
        <div class="wrapper-unit">
          <a-typography-text>主演</a-typography-text>
          <a-input placeholder="请输入主演姓名，不同主演之前请用‘,’连接" v-model:value="uploadData.videoData.starring"></a-input>
        </div>
      </a-col>
      <a-col :span="12">
        <div class="wrapper-unit">
          <a-typography-text>视频地址</a-typography-text>
          <br>
          <a-select
              v-model:value="uploadData.videoData.location"
              :options="videoLocations"
              @focus="focus"
              style="width: 120px">
          </a-select>
        </div>
        <div class="wrapper-unit">
          <a-typography-text>出版时间</a-typography-text>
          <br>
          <a-config-provider :locale="zhCN">
            <a-date-picker size="small" autofocus v-model:value="uploadData.videoData.publishDate"
                           :value-format="dateFormat"
                           :format="dateFormat"
                           placeholder="选择日期"/>
          </a-config-provider>
        </div>
        <div class="wrapper-unit">
          <a-typography-text>视频介绍</a-typography-text>
          <a-textarea placeholder="请输入视频介绍" v-model:value="uploadData.videoData.description"></a-textarea>
        </div>
      </a-col>
    </a-row>


    <div class="wrapper-unit">
      <a-button type="primary" @click="submit">提交</a-button>
    </div>
  </div>
</template>

<style scoped>
.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}


.wrapper-unit {
  margin-top: 0.5em;
  width: 30em;
}
</style>