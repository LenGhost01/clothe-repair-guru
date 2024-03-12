<!-- 专用做处理面对满私聊 -->
<template>
  <div class="sub-container" style="padding:1.5em 0 0 1.5em">
    <a-typography-title :level="4" style="display: inline-block">{{ targetUser.nickname }}</a-typography-title>
    <!-- v-show是display属性，如果已经设置display,则v-show不生效 -->
    <a-typography-text v-if="!is_friend" type="secondary" style="display: inline-block;margin-left: 1em">临时会话
    </a-typography-text>
    <success-button v-if="!is_friend" class="content-font-small add">添加好友</success-button>
    <div class="content">
      <message-windows :transport="transport">
      </message-windows>
    </div>
    <div class="send_message_container">
      <input-box></input-box>
    </div>
  </div>
</template>

<script setup>
import emitter from "@/utils/EventBus.js";
import successButton from "@/components/SuccessButton.vue"
import InputBox from "@/components/InputBox.vue"
import {computed, inject, onBeforeMount, onBeforeUnmount, onMounted, provide, reactive, ref} from "vue";
import MessageWindows from "@/components/MessageWindows.vue";
import {v4 as uuidv4} from 'uuid';
import axios from "axios";
import store from "@/store/store.js";

const chatWs = ref()
const chatIdentity = ref(null)
const is_friend = ref(false);
const wsPath = import.meta.env.VITE_API_WS_PATH
const request = import.meta.env.VITE_API_REQUESTS_PATH
//todo 头像资源预加载

//Vue3中父组件向子组件传值方式
const transport = reactive({
  token: "测试token",
})
const selectedUserId = inject("selectedUserId")
const selected = computed(() => selectedUserId)
provide("webSocketConnector", chatWs)
onBeforeMount(async () => {

})
const targetUser = ref('')
provide("targetUser", targetUser)

onMounted(async () => {
  is_friend.value = store.state.MembersStore.userCorrelationMember.map(item => item.targetId).indexOf(selectedUserId.value) === -1 ? false : true
  // 根据双方的用户id和唯一标识建立一个聊天室
  // 根据点击的用户id获取到对应的用户资料并显示在聊天窗口中 需要将异步操作变为同步操作保证挂载后的步骤能正常运行
  chatIdentity.value = uuidv4()
  chatWs.value = new WebSocket(`${wsPath}`)

  // 建立websocket连接后，绑定一系列的监听器
  chatWs.value?.addEventListener('open', () => {
    console.log(selectedUserId.valueOf())
    // ws与后端建立连接成功会调用这个回调函数
    chatWs.value?.send(JSON.stringify({
      type: 'init',
      sender: store.state.userState.user.userId,
      receiver: selectedUserId.value,
    }));
  })

  chatWs.value?.addEventListener("close", () => {
    console.log("连接关闭")
  })

  chatWs.value?.addEventListener("error", (err) => {
    console.log(err)
  })

  chatWs.value?.addEventListener("message", async (event) => {
    if (event.isTrusted) {
      let rec = JSON.parse(event.data)
      switch (rec.type) {
        case 'initMessage':
          // 接收到初始化信息
          await store.dispatch("messageStore/initMessage", JSON.parse(rec.content))
          console.log(store.state.messageStore.receivedMessage)
          break;
      }
    }
  })

  let res = await axios.get(`${request}/user/getUserById?uid=${selectedUserId.value}`)
  targetUser.value = res.data

})

onBeforeUnmount(() => {
  emitter.off('privateChat')
  if (chatWs.value !== undefined) {
    // 退出当前页面时断开websocket连接
    chatWs.value.close()
  }
})
</script>

<style scoped>

.add {

}

.content {
  display: inline-block;
  box-sizing: border-box;
  width: 99%;
  height: calc(94% - 10em);
}

.send_message_container {
  display: inline-block;
  box-sizing: border-box;
  height: 10em;
  width: 99%;
}
</style>