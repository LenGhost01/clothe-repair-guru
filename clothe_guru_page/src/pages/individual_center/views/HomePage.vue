<script setup>
import axios from "axios";
import {computed, reactive} from "vue";
import router from "../routers/main.js";
import emitter from "/src/utils/EventBus.js";
import store from "/src/store/store.js";
import {message} from "ant-design-vue";

let jumpToSecurityDetailPage = ()=>{
  emitter.emit("changeSelected", {
    changeTo: 2
  })
  router.push("/security")
}

let jumpToChangeMsg = ()=>{
  emitter.emit("changeSelected", {
    changeTo: 1
  })
  router.push("/message")
}

let loginUser = computed(()=>{
  if (store.state.userState.user){
    return JSON.parse(JSON.stringify(store.state.userState.user))
  }
  else {
    message.error("你没登录就进来了呢，真厉害！！")
  }
})

let avatarPath = computed(()=>{
  if (loginUser){
    return '/users/' + loginUser.value.img
  }else {

  }
})
</script>

<template>
  <div class="home_container">
    <div>
      <div class="avatar_container">
        <a-avatar :size="{ xs: 16, sm: 24, md: 32, lg: 52, xl: 64, xxl: 80 }" :src="avatarPath">
        </a-avatar>
      </div>
      <div class="user_text">
        <a-typography-title :level="4">{{ loginUser.nickname }}</a-typography-title>
        <a-space :size="[0, 'small']" wrap>
          <a-tag :bordered="false">Tag 1</a-tag>
          <a-tag :bordered="false">Tag 2</a-tag>
          <a-tag :bordered="false">Tag 3</a-tag>
        </a-space>
      </div>
      <div class="user_opr_container">
        <a-button type="primary" size="small" style="margin-right: 1em" @click="jumpToChangeMsg">修改资料</a-button>
        <a-button type="default" size="small">个人空间</a-button>
      </div>
    </div>
    <a-divider></a-divider>
    <div class="security_container">
      <div class="sec_header">
        <a-typography-title :level="5" style="display: inline-block">账号安全</a-typography-title>
        <a-button style="display: inline-block;position: absolute;right: 5%" type="default" size="small" @click="jumpToSecurityDetailPage">
          更多账号安全
        </a-button>
      </div>
      <div class="sec_body">
        <a-row :gutter="[8,64]" style="margin-top: 1em">
          <a-col :span="12">
            <div class="text_container">
              <a-typography-text>我的邮箱</a-typography-text>
            </div>
            <div class="text_container">
              <a-typography-text style="font-size: 12px">{{ loginUser.email === null?'绑定后即可使用邮箱登录':loginUser.email }}</a-typography-text>
            </div>
            <a-button size="small"> {{ loginUser.email === null?'绑定邮箱':'更换邮箱' }}</a-button>
          </a-col>
          <a-col :span="12">
            <div class="text_container">
              <a-typography-text>我的手机</a-typography-text>
            </div>
            <div class="text_container">
              <a-typography-text style="font-size: 12px">{{ loginUser.phone === null?'绑定后即可使用手机登录':loginUser.phone }}</a-typography-text>
            </div>
            <a-button size="small">{{ loginUser.phone === null?"绑定手机":"更换绑定" }}</a-button>
          </a-col>
          <a-col :span="12">
            <div class="text_container">
              <a-typography-text>我的密保</a-typography-text>
            </div>
            <div class="text_container">
              <a-typography-text style="font-size: 12px">{{loginUser.safeQuestion===null?'设置密保，账号更安全':'已设置密保'}}</a-typography-text>
            </div>
            <a-button size="small">{{loginUser.safeQuestion===null?"设置密保":"更换密保"}}</a-button>
          </a-col>
          <a-col :span="12">
            <div class="text_container">
              <a-typography-text>修改密码</a-typography-text>
            </div>
            <div class="text_container">
              <a-typography-text style="font-size: 12px">定期修改密码，账号更安全</a-typography-text>
            </div>
            <a-button size="small">修改密码</a-button>
          </a-col>
        </a-row>
      </div>
    </div>
  </div>
</template>

<style scoped>
.home_container {
  padding: 2em;
}

.avatar_container {
  display: inline-block;
}

.avatar_css {
  width: 64px;
  height: 64px;
}

.user_text {
  display: inline-block;
  margin-left: 2em;
  transform: translateY(30%);
}

.user_opr_container {
  display: inline-block;
  position: absolute;
  right: 5%;
  margin-top: 4em;
}

.security_container {

}

.sec_header {

}
.text_container{
  margin-bottom: 5px;
}
</style>