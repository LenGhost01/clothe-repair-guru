<script setup>

import {inject, ref} from "vue";
import RichTextInputBox from "@/components/RichTextInputBox.vue";
import store from "@/store/store.js";
import ImageProcessingModel from "@/components/ImageProcessingModel.vue";
import exp from "constants";

const ws = inject("webSocketConnector")
const senderText = ref('')
const selectedUserId = inject("selectedUserId")
const experimentalModelDisplay = ref(false)
const generateOrderModelDisplay = ref(false)
const inputDataHandler = (e) => {
  console.log(e)
  senderText.value = e
}

const experimentalModelDisplayHidden = ()=>{
  experimentalModelDisplay.value = false
}
const showExperimentalModel = (e)=>{
  experimentalModelDisplay.value = true
}


const sendMessage = () => {
  // todo 点击发送按钮后，将消息通过ws发送到ws服务器
  ws.value.send(JSON.stringify({
    type: "message",
    sender: store.state.userState.user.userId,
    receiver: selectedUserId.value,
    sendTime: new Date().toLocaleString(),
    content: senderText.value,
  }))
}

</script>

<template>
  <div class="input_box">
    <div class="input_zone">
      <rich-text-input-box @inputData="inputDataHandler"></rich-text-input-box>
    </div>
    <div class="ability_zone">
      <!-- 输入框功能区 -->
      <a-button @click="sendMessage">发送</a-button>
      <a-button @click="showExperimentalModel">实验性功能</a-button>
      <a-button @click="" v-if="store.state.userState.user.merchantId">订单生成</a-button>
    </div>
  </div>
  <a-modal v-model:open="experimentalModelDisplay" style="width: 50vw" title="图片处理">
    <!-- 图片处理模块前端页面 -->
    <ImageProcessingModel></ImageProcessingModel>
    <template #footer>
      <a-space>
        <a-button type="primary" @click="experimentalModelDisplayHidden">确定</a-button>
      </a-space>
    </template>
  </a-modal>
</template>


<style scoped>
.input_box {
  position: relative;
  display: inline-block;
  width: 100%;
  height: 100%;
  border: #696969 1px solid;
  border-radius: 5px;
}

.input_zone {
  display: inline-block;
  box-sizing: border-box;
  vertical-align: top;
  width: 90%;
  height: 100%;

}

.ability_zone {
  position: relative;
  display: inline-grid;
  box-sizing: border-box;
  place-items: center;
  vertical-align: top;
  width: 10%;
  height: 100%;
}
</style>