<template>
    <div class="sub-container" style="padding:1.5em 0 0 1.5em">
      <a-typography-title :level="4" style="display: inline-block">选中用户</a-typography-title>
      <!-- v-show是display属性，如果已经设置display,则v-show不生效 -->
      <a-typography-text v-if="is_temporary" type="secondary" style="display: inline-block;margin-left: 1em">临时会话
      </a-typography-text>
      <success-button v-if="is_friend" class="content-font-small add">添加好友</success-button>
      <div class="content">
        <message-windows :transport="transport">

        </message-windows>
      </div>
      <div class="send_message_container">
        <input-box style="height: 10em"></input-box>
      </div>
    </div>
</template>

<script setup>
import emitter from "@/utils/EventBus.js";
import successButton from "@/components/SuccessButton.vue"
import InputBox from "@/components/InputBox.vue"
import {reactive, ref} from "vue";
import MessageWindows from "@/components/MessageWindows.vue";

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