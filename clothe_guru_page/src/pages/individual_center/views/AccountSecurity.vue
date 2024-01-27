<script setup>
import {onBeforeMount, onMounted, reactive, ref} from "vue";
import router from "../routers/main.js";
import store from "/src/store/store.js";

let accountState = ref(1)
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
  router.push({
    path: '/security_check', query: {
      pattern: pattern
    }
  })
}
onBeforeMount(() => {
  //todo 在页面组件挂载前，通过验证公共环境变量找到对应的状态
  accountMsg[0].State = !store.state.userState.user.passwordExists ? 0 : 1;
  accountMsg[1].State = (store.state.userState.user.phone === "" || store.state.userState.user.phone === null) ? 0 : 1;
  accountMsg[2].State = (store.state.userState.user.email === "" || store.state.userState.user.email === null) ? 0 : 1;
  accountMsg[3].State = (store.state.userState.user.safetyQuestion === "" || store.state.userState.user.safetyQuestion === null) ? 0 : 1;

  accountMsg[0].content = store.state.userState.user.passwordExists ? "已设置密码" : "未设置密码";
  accountMsg[1].content = !(store.state.userState.user.phone === "" || store.state.userState.user.phone === null) ? store.state.userState.user.phone : "未设置手机号";
  accountMsg[2].content = !(store.state.userState.user.email === "" || store.state.userState.user.email === null) ? store.state.userState.user.email : "未设置邮箱";
  accountMsg[3].content = !(store.state.userState.user.safetyQuestion === "" || store.state.userState.user.safetyQuestion === null) ? "已设置密保" : "未设置密保";
})

onMounted(() => {
  // todo 统计账户信息中已经填写的账户信息，评估账户状态
  let count = 0;
  for (let i =0;i<accountMsg.length;i++) {
    if (accountMsg[i].State === 1) {
      count++
    }
  }
  console.log(count)
  if (count <= 2) {
    accountState.value = 0
  } else if (count === 3) {
    accountState.value = 1
  } else if (count > 3) {
    accountState.value = 2
  }
})
</script>

<template>
  <div class="security_panel_container">
    <div class="show_account_state horizontal_center" v-if="accountState === 0">
      <img :src="`/statics/icons/security_unsafe.png`" style="height: 200px;width: auto" alt="账号安全程度低">
      <a-typography-title :level="5" type="danger" style="margin-top: 0.5em">账号安全程度低</a-typography-title>
    </div>
    <div class="show_account_state horizontal_center" v-if="accountState === 1">
      <img :src="`/statics/icons/security_warning.png`" style="height: 200px;width: auto" alt="账号安全程度中等">
      <a-typography-title :level="5" type="warning" style="margin-top: 0.5em">账号安全程度中等</a-typography-title>
    </div>
    <div class="show_account_state horizontal_center" v-if="accountState === 2">
      <img :src="`/statics/icons/security_safe.png`" style="height: 200px;width: auto" alt="账号安全程度高">
      <a-typography-title :level="5" type="success" style="margin-top: 0.5em">账号安全程度高</a-typography-title>
    </div>
    <a-divider></a-divider>
    <div v-for="(content,index) in accountMsg">
      <a-row>
        <a-col :span="4">
          <div class="dot" :style="{
        background: content.State === 0 ? '#F59A23' : '#95F204'
      }"></div>
          <a-typography-text style="margin-left: 1em">{{ content.title }}</a-typography-text>
        </a-col>
        <a-col :span="16" style="text-align: center">
          <a-typography-text>{{ content.content }}</a-typography-text>
        </a-col>
        <a-col :span="4" style="text-align: right">
          <a-button type="link" @click="jumpToSecCheck(content.pattern)"> {{
              content.btn_text[content.State]
            }}
          </a-button>
        </a-col>
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

.dot {
  display: inline-block;
  width: 1em;
  height: 1em;
  border-radius: 1em;
}
</style>