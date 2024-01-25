<script setup>
import {computed, onBeforeUnmount, onMounted, reactive, ref} from 'vue';
import router from "./routers/main.js";
import emitter from "/src/utils/EventBus.js";
import {message} from "ant-design-vue";
import {PoweroffOutlined,LogoutOutlined} from "@ant-design/icons-vue";
import store from "./store/store.js";
import zhCN from 'ant-design-vue/es/locale/zh_CN';
import 'moment/locale/zh-cn.js'
import moment from "moment";
import {useRoute, useRouter} from "vue-router";
moment.locale("zh-cn")

const onCollapse = (collapsed, type) => {

};
const onBreakpoint = broken => {

};
const selectedKeys = ref([0]);
let pushToTargetPage = (page, ...msg) => {
  if (msg.length === 0)
    router.push({path: page})
  else {
    let sets = JSON.stringify(msg)
    router.push({path: page, query: {params: sets}})
  }
}
const animate_list = ref(['user_display_show', 'user_display_hidden'])
let selectAnimate = ref('')
let intViewportHeight = ref(document.documentElement.clientHeight);
let operationSet = reactive([
  {
    name: '首页',
    to: '/home'
  },
  {
    name: '我的信息',
    to: '/message'
  },
  {
    name: '账号安全',
    to: '/security'
  },
  {
    name: '我的记录',
    to: '/records'
  },
  {
    name: '上传作品',
    to: '/upload'
  },
  {
    name: '用户注销',
    to: '/revoked'
  },
])

emitter.on("changeSelected", (param) => {
  // 通过mitt组件实现的全局事件总线，用于实现组件之间通信
  selectedKeys.value = [param.changeTo]
})


onBeforeUnmount(() => {
  // 在页面跳转前，结束挂载事件
  emitter.off("changeSelected")
})

onMounted(() => {
  let token = localStorage.getItem("token")
  if (token) {
    // 如果token存在，则向后端发送请求，获取数据

  }
  //防止页面刷新后还留在其他页面。在页面挂载完毕后将页面调整到首页
  router.push("/home")
})

const avatarPath = computed(() => {
  return "/users/" + store.state.userState.user.img
})

const returnIndex = ()=>{
  window.location.href = '/'
}
</script>

<template>
  测试页面
<!--  <a-config-provider :locale="zhCN">-->
<!--    <div class="container">-->
<!--      <a-layout :style="{height : intViewportHeight+'px'}">-->
<!--        <a-layout-sider-->
<!--            breakpoint="lg"-->
<!--            collapsed-width="0"-->
<!--            @collapse="onCollapse"-->
<!--            @breakpoint="onBreakpoint"-->
<!--        >-->
<!--          <div class="logo user_select_forbidden">-->
<!--            <a-typography-title style="color: #5FB878" :level="3">个人中心</a-typography-title>-->
<!--          </div>-->
<!--          <a-menu v-model:selectedKeys="selectedKeys" theme="dark" mode="inline">-->
<!--            <a-menu-item v-for="(operation,index) in operationSet" :key="index" @click="pushToTargetPage(operation.to)">-->
<!--              <span class="nav-text">{{ operation.name }}</span>-->
<!--            </a-menu-item>-->
<!--          </a-menu>-->
<!--        </a-layout-sider>-->
<!--        <a-layout>-->
<!--          <a-layout-header :style="{ background: '#fff', padding: 0 , position:'relative', zIndex:4}">-->
<!--            <div class="avatar-wrapper" @mouseenter="selectAnimate=animate_list[0]"-->
<!--                 @mouseleave="selectAnimate=animate_list[1]">-->
<!--              <a-avatar :size="32" class="avatar" wrap :src="avatarPath">-->
<!--              </a-avatar>-->
<!--              <div class="operator-wrapper" :class="selectAnimate">-->
<!--                <a-button type="text" size="large" @click="" block>-->
<!--                  <span><PoweroffOutlined/> 退出登录</span>-->
<!--                </a-button>-->
<!--                <a-button type="text" size="large" @click="returnIndex" block>-->
<!--                  <span><LogoutOutlined /> 返回主页</span>-->
<!--                </a-button>-->
<!--              </div>-->
<!--            </div>-->

<!--          </a-layout-header>-->
<!--          <a-layout-content :style="{ margin: '24px 16px 0' }">-->
<!--            <div :style="{ padding: '24px', background: '#fff',-->
<!--            height: 'auto'}">-->
<!--              <router-view :key="useRoute().fullPath"></router-view>-->
<!--            </div>-->
<!--          </a-layout-content>-->
<!--          <a-layout-footer style="text-align: center">-->
<!--            Ant Design ©2018 Created by Ant UED-->
<!--          </a-layout-footer>-->
<!--        </a-layout>-->
<!--      </a-layout>-->
<!--    </div>-->
<!--  </a-config-provider>-->
</template>

<style scoped>
#components-layout-demo-responsive .logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px;
}

.site-layout-sub-header-background {
  background: #fff;
}

.site-layout-background {
  background: #fff;
}

[data-theme='dark'] .site-layout-sub-header-background {
  background: #141414;
}

.logo {
  display: inline-block;
  width: 100%;
  height: 5vh;
  line-height: 5vh;
  margin-left: 2em;
  margin-top: 1em;
}

.avatar-wrapper {
  display: inline-block;
  position: absolute;
  right: calc(7vw);
  text-align: center;
  z-index: -1;
  width: auto;
  height: auto;
}

.operator-wrapper {
  display: inline-block;
  position: absolute;
  background: white;
  height: auto;
  left: calc(-5em + 16px);
  bottom: 0%;
  border: 1px solid #B3B3B3;
  z-index: 5;
  border-radius: 5px;
  transform: translateY(100%);
  padding: 0 1ex;
  opacity: 0;
}

.avatar {
  position: relative;
  z-index: 999;
}

.user_display_show {
  animation: fadeIn 0.5s;
  animation-fill-mode: forwards;
  visibility: visible !important;
  opacity: 1;
}

.user_display_hidden {
  animation: fadeOut 0.5s;
  animation-fill-mode: forwards;
  visibility: hidden !important;
  opacity: 0;
}
.operator-wrapper :deep(.ant-btn){

}
</style>
