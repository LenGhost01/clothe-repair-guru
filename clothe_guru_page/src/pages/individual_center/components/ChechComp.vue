<script setup>
import emitter from "../../../utils/EventBus.js";

let callCheckProcess = ()=>{
  emitter.emit("callCheckProcess",2)
}

let callFindAccount = ()=>{
  emitter.emit("callFindAccount",3)
}
</script>

<template>
  <div>
    <div class="horizontal_center" style="margin: 2em 0">
      <a-typography-title :level="4">选择验证方式</a-typography-title>
    </div>
    <div class="content_container">
      <div class="cont_unit">
        <div class="svg_container">
          <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" fill="currentColor" class="bi bi-tablet"
               viewBox="0 0 16 16">
            <path
                d="M12 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h8zM4 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H4z"/>
            <path d="M8 14a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
          </svg>
        </div>
        <div class="inline_block func_description">
          <a-typography-title :level="5">通过手机验证</a-typography-title>
          <a-typography-text>如果您的绑定手机可用，则可以通过绑定的手机发送验证码修改</a-typography-text>
        </div>
        <a-button class="inline_block button_location" type="primary" size="large" @click="callCheckProcess">验证手机</a-button>
      </div>
      <a-divider></a-divider>
      <div class="cont_unit">
        <div class="svg_container">
          <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" fill="currentColor" class="bi bi-mailbox" viewBox="0 0 16 16">
            <path d="M4 4a3 3 0 0 0-3 3v6h6V7a3 3 0 0 0-3-3m0-1h8a4 4 0 0 1 4 4v6a1 1 0 0 1-1 1H1a1 1 0 0 1-1-1V7a4 4 0 0 1 4-4m2.646 1A3.99 3.99 0 0 1 8 7v6h7V7a3 3 0 0 0-3-3z"/>
            <path d="M11.793 8.5H9v-1h5a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5h-1a.5.5 0 0 1-.354-.146l-.853-.854zM5 7c0 .552-.448 0-1 0s-1 .552-1 0a1 1 0 0 1 2 0"/>
          </svg>
        </div>
        <div class="inline_block func_description">
          <a-typography-title :level="5">通过邮箱验证</a-typography-title>
          <a-typography-text>如果您的绑定邮箱可用，则可以通过绑定的邮箱发送验证码修改</a-typography-text>
        </div>
        <a-button class="inline_block button_location" type="primary" size="large" @click="callCheckProcess">验证邮箱</a-button>
      </div>
      <a-divider></a-divider>
      <div class="cont_unit">
        <div class="svg_container">
          <svg xmlns="http://www.w3.org/2000/svg" width="64" height="64" fill="currentColor" class="bi bi-x-lg" viewBox="0 0 16 16">
            <path d="M2.146 2.854a.5.5 0 1 1 .708-.708L8 7.293l5.146-5.147a.5.5 0 0 1 .708.708L8.707 8l5.147 5.146a.5.5 0 0 1-.708.708L8 8.707l-5.146 5.147a.5.5 0 0 1-.708-.708L7.293 8z"/>
          </svg>
        </div>
        <div class="inline_block func_description">
          <a-typography-title :level="5">以上方式都不能使用</a-typography-title>
          <a-typography-text>如果您的邮箱有手机号都无法使用，可以尝试账号找回</a-typography-text>
        </div>
        <a-button class="inline_block button_location" type="link" size="middle" @click="callFindAccount">账号找回</a-button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.content_container {

  padding: 2em;
  margin: 0 3em;
  border: 1px solid;
}

.cont_unit {
  position: relative;
  height: 5em;
}

.svg_container {
  display: inline-block;
  margin-left: 1em;
  height: 4em;
  width: 4em;
  position: absolute;
}

.func_description {
  position: absolute;
  left: 8em;
}

.button_location {
  position: absolute;
  right: 2em;
  top: 1em;
  padding: 0 2em;
}
</style>