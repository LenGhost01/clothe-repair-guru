<template>
  <div class="container">
    <div class="header">
      <a-typography-title :level="4" style="display: inline-block">选中用户</a-typography-title>
      <!-- v-show是display属性，如果已经设置display,则v-show不生效 -->
      <a-typography-text v-if="is_temporary" type="secondary" style="display: inline-block;margin-left: 1em">临时会话
      </a-typography-text>
      <success-button v-if="is_friend" class="content-font-small add">添加好友</success-button>
    </div>
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
import emitter from "../../../utils/EventBus.js";
import successButton from "../../SuccessButton.vue"
import InputBox from "../../InputBox.vue"
import {reactive, ref} from "vue";
import MessageWindows from "../../MessageWindows.vue";

const is_friend = ref(false);
const is_temporary = ref(true);
//Vue3中父组件向子组件传值方式
const transport = reactive({
  token: "测试token",
})
emitter.on('privateChat', (received) => {
  console.log(received)
})
</script>

<style scoped>

.header {
  position: relative;
  display: inline-block;
  margin-top: 2.5%;
  margin-left: 1.5em;
  padding-top: 1em;
  height: 10%;
  width: 90%;
}

.add {
  position: absolute;
  right: 5%;
}

.content {
  position: relative;
  display: inline-block;
  height: 73%;
  width: 90%;
  margin-left: 1.5em;
  /*background: #4096FF;*/
  border: dimgrey solid 1px;
  border-radius: 5px;
}

.send_message_container {
  display: inline-block;
  margin-top: 1em;
  margin-left: 1.5em;
  height: 7%;
  width: 90%;
}
</style>