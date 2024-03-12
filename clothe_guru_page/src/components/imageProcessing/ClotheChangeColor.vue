<script setup>
import {inject, nextTick, onBeforeUnmount, onMounted, ref} from "vue";
import {ArrowRightOutlined, PlusOutlined} from "@ant-design/icons-vue"
import getBase64 from "@/utils/getBase64.js";
import {v4 as uuidv4} from "uuid"
import store from "@/store/store.js";
import MessageDTO from "@/dto/MessageDTO.js";


const ping = inject("ping")
const wsConnect = inject("imageWsConnect")
const blocking = ref(false)
const requestPath = import.meta.env.VITE_API_REQUESTS_PATH
const selectedColor = ref([0, 0, 0])
const color = (event) => {
  let hex = event.target.value.replace(/^#/, "")
  let r = parseInt(hex.substring(0, 2), 16);
  let g = parseInt(hex.substring(2, 4), 16);
  let b = parseInt(hex.substring(4, 6), 16);
  selectedColor.value = [r, g, b]
}

// 上传图片处理部分
const files = ref([])
const imageUrl = ref()
const previewVisible = ref(false)
const beforeUpload = () => {
  return false;
}
const handleChange = async (e) => {
  blocking.value = true
  imageUrl.value = await getBase64(e.file)
  blocking.value = false
}
const handleCancel = async (e) => {
}

const handlePreview = async (file) => {

  if (!file.url && !file.preview) {
    file.preview = await getBase64(file.originFileObj);
  }
  imageUrl.value = file.url || file.preview;
  previewVisible.value = true;

}
// 图片发送到后台并生成结果图
const generateResultAndDisplay = async () => {
  let readImage = files.value[0].originFileObj;
  // 重命名并封装图片
  let imageBase64 = imageUrl.value
  let suffix = readImage.name.split(".").pop()
  let fileName = `IMG_${uuidv4()}`
  // 上传到后台服务器 并在处理期间禁止用户操作
  const payload = JSON.stringify({
    fileName: `${fileName}.${suffix}`,
    targetColor: selectedColor.value.join("-"),
    image: imageBase64
  })
  const message = new MessageDTO("changeColor", payload,store.state.userState.user.userId,store.state.userState.user.userId, new Date().toISOString())

  wsConnect.value?.send(JSON.stringify(message))
  blocking.value = true
}

const handleMessage = function (event) {
  // 处理完需显示的数据后，清除用户界面中请稍后提示信息
  console.log("处理完毕")
  blocking.value = false
}
onMounted(()=>{
  nextTick(()=>{
    wsConnect.value?.addEventListener("message", handleMessage)
  })
})

onBeforeUnmount(()=>{
  wsConnect.value?.removeEventListener("message",handleMessage)
})

</script>

<template>
  <a-spin :spinning="blocking">
    <div class="change-color-container">
      <!-- 图片上传组件 -->
      <div>
        <a-upload
            v-model:file-list="files"
            list-type="picture-card"
            class="avatar-uploader"
            :show-upload-list="false"
            :before-upload="beforeUpload"
            @change="handleChange"
            @preview="handlePreview"
            :max-count="1"
        >
          <div v-if="imageUrl" style="width: 100%;height: 100%">
            <img :src="imageUrl" style="max-height: 100%;max-width: 100%;object-fit: contain">
          </div>
          <div v-else>
            <plus-outlined></plus-outlined>
            <div class="ant-upload-text">上传图片</div>
          </div>
        </a-upload>
      </div>
      <!-- 颜色选择组件 和 生成按钮 -->
      <div>
        <div class="middle-container">
          <div>
            <input type="color" @change="color($event)"/>
          </div>
          <div>
            <ArrowRightOutlined/>
          </div>
          <div>
            <a-button @click="generateResultAndDisplay">生成结果图片</a-button>
          </div>
        </div>
      </div>
      <!-- 结果展示组件 -->
      <div style="width: 10vw;height: auto">
        图片占位
      </div>
    </div>
  </a-spin>
</template>

<style scoped>
.change-color-container {
  display: grid;
  grid-template-columns: 2fr 1fr 2fr;
  grid-gap: 10px 20px;
  place-items: center;
  box-sizing: border-box;
  padding: 1em;
}

.middle-container {
  display: grid;
  grid-template-rows: 1fr 1fr 1fr;
  grid-gap: 2px;
  place-items: center;
}

.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #888;
}

.avatar-uploader {
  width: 18vw;
  height: 40vh;
}

.avatar-uploader:deep(.ant-upload-select) {
  width: 100% !important;
  height: 100% !important;
}

.avatar-uploader:deep(.ant-upload) {
  display: inline-block;
  box-sizing: border-box;
  width: 100%;
  height: 100%;
}
</style>