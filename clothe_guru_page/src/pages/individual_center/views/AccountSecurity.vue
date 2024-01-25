<script setup>
import {onBeforeMount, reactive, ref} from "vue";
import router from "../routers/main.js";

let accountState = ref(0)
let accountMsg = reactive([
  {
    State: 0,
    title: '设置密码',
    content: '未设置密码',
    btn_text: ['设置密码', '更换密码'],
    pattern: 0
  },
  {
    State: 0,
    title: '绑定手机',
    content: '未绑定手机',
    btn_text: ['绑定手机', '更换手机'],
    pattern: 1
  },
  {
    State: 0,
    title: '绑定邮箱',
    content: '未绑定邮箱',
    btn_text: ['绑定邮箱', '更换邮箱'],
    pattern: 2
  },
  {
    State: 0,
    title: '密保问题',
    content: '未设置密保问题',
    btn_text: ['设置密保', '更换密保'],
    pattern: 3
  },

])

let jumpToSecCheck = (pattern) => {
  console.log(pattern)
  router.push({path:'/security_check',query:{
      pattern : pattern
    }})
}
onBeforeMount(()=>{
  //todo 在页面组件挂载前，通过token访问后台获取到相关数据
})
</script>

<template>
  <div class="security_panel_container">
    <div class="show_account_state horizontal_center" v-if="accountState === 0">
      <img src="/static/security_test1.jpg" style="width: 20%" alt="账号不安全">
      <a-typography-title :level="5" type="danger" style="margin-top: 0.5em">账号不安全</a-typography-title>
    </div>
    <a-divider></a-divider>
    <div v-for="(content,index) in accountMsg">
      <a-row>
        <a-col :span="4"><div class="dot" :style="{
        background: content.State === 0 ? '#F59A23' : '#95F204'
      }"></div>
          <a-typography-text style="margin-left: 1em">{{ content.title }}</a-typography-text></a-col>
        <a-col :span="16" style="text-align: center"><a-typography-text>{{ content.content}}</a-typography-text></a-col>
        <a-col :span="4" style="text-align: right"><a-button type="link" @click="jumpToSecCheck(content.pattern)"> {{ content.btn_text[content.State] }} </a-button></a-col>
      </a-row>
      <a-divider v-if="index < 4"></a-divider>
    </div>
  </div>
</template>

<style scoped>
.security_panel_container {
  padding: 2em;
  box-sizing: border-box;
  overflow: auto;
}

.dot{
  display: inline-block;
  width: 1em;
  height: 1em;
  border-radius: 1em;
}
</style>