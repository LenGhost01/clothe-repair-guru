<script setup>
import {onMounted, reactive, ref} from 'vue';
import router from "../routers/main.js";
import {useRoute} from "vue-router";

const selectedKeys = ref(['user_management']);
const openKeys = ref(['']);

const menuItem = reactive([
  {
    title: "用户管理",
    key: "user_management",
    to: "/user_management",
  },
  {
    title: "商品管理",
    key: "cargo_management",
    to: "",
    subMenuItem: [
      {
        title: "商品信息",
        key: "cargo_message",
        to: "/merchant_message",
      },
      {
        title: "分类信息",
        key: "category_message",
        to: "/category_message",
      },
      {
        title: "材质信息",
        key: "material_message",
        to: "/material_message",
      }
    ]
  }, {
    title: "订单管理",
    key: "order_management",
    to: "/order_management",
  }, {
    title: "请求管理",
    key: "application_management",
    to: "",
    subMenuItem: [
      {
        title: "商家请求",
        key: "merchant_application",
        to: "/merchant_application",
      },
      {
        title: "投诉请求",
        key: "complaints_application",
        to: "/complaints_application",
      },
    ]
  }
])

const jumpToPage = (pagePath,...params) => {
  if (params.length > 1) {
    const queryParams = Object.assign({}, ...params.map(param => ({[param.key]: param.value})));
    router.push({path: pagePath, query: queryParams})
  } else {
    router.push({path: pagePath})
  }
}


onMounted(() => {

  // 初始化完毕后，跳转到默认页面
  jumpToPage("/user_management")
})
</script>

<template>
  <a-layout has-sider>
    <a-layout-sider
        :style="{ overflow: 'auto', height: '100vh', position: 'fixed', left: 0, top: 0, bottom: 0 }"
    >
      <div class="logo">12341234</div>
      <a-menu v-model:selectedKeys="selectedKeys" v-model:openKeys="openKeys" theme="dark" mode="inline">
        <div v-for="item in menuItem">
          <a-sub-menu v-if="item.subMenuItem !== undefined" :key="item.key">
            <template #title>
              {{ item.title }}
            </template>
            <div v-for="subItem in item.subMenuItem">
              <a-menu-item :key="subItem.key"  @click="jumpToPage(subItem.to)"><span>{{ subItem.title }}</span></a-menu-item>
            </div>
          </a-sub-menu>
          <a-menu-item v-if="item.subMenuItem === undefined" :key="item.key" @click="jumpToPage(item.to)">
            <span> {{ item.title }} </span>
          </a-menu-item>
        </div>
      </a-menu>
    </a-layout-sider>
    <a-layout :style="{ marginLeft: '200px' }">
      <a-layout-header :style="{ background: '#fff', padding: 0 }"/>
      <a-layout-content :style="{ margin: '24px 16px 0', overflow: 'initial' }">
        <div :style="{ padding: '24px', background: '#fff' }">
          <router-view :key="useRoute().fullPath"></router-view>
        </div>
      </a-layout-content>
      <a-layout-footer :style="{ textAlign: 'center' }">
        Ant Design ©2018 Created by Ant UED
      </a-layout-footer>
    </a-layout>
  </a-layout>
</template>
<style scoped>
#components-layout-demo-fixed-sider .logo {
  height: 32px;
  background: rgba(255, 255, 255, 0.2);
  margin: 16px;
}

.site-layout .site-layout-background {
  background: #fff;
}

[data-theme='dark'] .site-layout .site-layout-background {
  background: #141414;
}
</style>