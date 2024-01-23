<script setup>
import {MessageOutlined} from "@ant-design/icons-vue";
import {ref} from "vue";
import emitter from "../utils/EventBus.js";

let message_count = ref(5)
const float_button = ref(null)
const call_chat_room = () => {
//todo 通过mitt发送事件，唤醒掩膜和聊天室组件
  emitter.emit("call_chat_room")
}
</script>

<template>
  <div class="z-index-infinity">
    <!--  todo 浮动标签可以在鼠标按下后移动  -->
    <a-float-button
        ref="float_button"
        type="default"
        :style="{
            width: 60+'px',
            height: 60+'px',
            bottom: 5+'%',
            right: 5+'%',
          }"
        @click="call_chat_room"
    >
      <template #icon>
        <a-badge :count="message_count" :style="{
          position: 'absolute',
          transform: 'translate(-55%, -10%)',
          zIndex: 1,
        }"></a-badge>
        <message-outlined :style="{
          position: 'absolute',
          transform: 'translate(-60%, -20%)',
        }"/>
      </template>
    </a-float-button>
  </div>
</template>

<style scoped>

</style>