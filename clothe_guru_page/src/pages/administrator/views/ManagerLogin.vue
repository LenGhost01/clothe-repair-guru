<script setup>
import {reactive, ref} from "vue";
import {message} from "ant-design-vue";
let visible = ref(false)
let error_msg = ref('')
const cssParams = reactive({
  width: document.body.clientWidth,
  height: document.body.clientHeight,
  ratio: window.devicePixelRatio
})

let managerLoginDataSet = reactive({
  mUsername: "",
  mPassword: "",
})
const returnToIndex = ()=>{
  window.location.href = '/'
}

const managerCheck = ()=>{
  //todo 根据结果显示提示并跳转或留在本界面
  if(managerLoginDataSet.mUsername === "admin" && managerLoginDataSet.mPassword === "123456"){
    // 登录成功后，将token存储在localStorage中，跳转到管理员主页
  }else{
    // 登录失败，显示错误提示信息
   message.error('您的账号或者密码填写错误！！！',3)
  }
}
</script>

<template>
  <div id="login_container"
       :style="{
    display: 'inline-block',
    width: cssParams.width+'px',
    height: cssParams.height+'px',
    textAlign: 'center',
  }">
    <div class="login_panel"
         :style="{
      transform: `translateY(calc(${cssParams.height/2}px - 7em))`
    }"
    >
      <a-typography-title :level="3" style="margin-top: 1em">管理员登录</a-typography-title>
      <div class="input_container">
        <a-typography-text style="width: 20%">账号：</a-typography-text>
        <a-input style="width: 80%" v-model:value.lazy="managerLoginDataSet.mUsername" autofocus placeholder="请输入管理员账号"/>
      </div>
      <div class="input_container">
        <a-typography-text style="width: 20%">密码：</a-typography-text>
        <a-input-password style="width: 80%" v-model:value.lazy="managerLoginDataSet.mPassword" autofocus placeholder="请输入管理员密码"/>
      </div>
      <div class="button_group">
        <a-button type="primary" style="margin-right: 0.5em" @click="managerCheck">登录</a-button>
        <a-button type="default" @click="returnToIndex">返回</a-button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login_panel {
  display: inline-block;
  width: 20em;
  height: 14em;
  border: #4096FF 2px solid;
  border-radius: 5px;
}

.input_container {
  display: inline-block;
  width: 80%;
  height: auto;
  margin-bottom: .5em;
}
.button_group{
  position: absolute;
  margin-top: 1em;
  right: 0;
  margin-right: 10%;
}
.error_msg{
  position: absolute;
  top: 3em;
  left: 25%;
  width: 50%;
}
</style>