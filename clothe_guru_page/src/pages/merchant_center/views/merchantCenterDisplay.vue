<script setup>
import {reactive, ref} from 'vue';
import {useRoute} from "vue-router";
import router from "../routers/main.js";

const selectedKeys = ref(['overall']);

const asideSelection = reactive([
  {
    title: "总览",
    to: "/overall",
    key: "overall",
  },
  {
    title: "商家信息",
    to: "/merchant",
    key: "merchant",
  },
  {
    title: "商品概览",
    to: '/merchandiseDisplay',
    key: "merchandiseDisplay",
  },
  {
    title: "订单查询",
    to: "/orderQuery",
    key: "orderQuery"
  }
])

const jumpTo = (to) => {
  router.push({path: to})
}
</script>

<template>
  <a-layout has-sider>
    <a-layout-sider
        :style="{ overflow: 'auto', height: '100vh', position: 'fixed', left: 0, top: 0, bottom: 0,background:'white'
        ,borderRight:'1px solid #F0F0F0'}"
    >
      <div class="logo"/>
      <a-menu v-model:selectedKeys="selectedKeys" theme="light" mode="inline">
        <a-menu-item v-for="item in asideSelection" :key="item.key" @click="jumpTo(item.to)">
          <span class="nav-text">{{ item.title }}</span>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout :style="{ marginLeft: '200px' }">
      <a-layout-header :style="{ background: '#fff', padding: 0 }"/>
      <a-layout-content :style="{ margin: '24px 16px 0', overflow: 'initial',background: 'white' }">
        <router-view :key="useRoute().fullPath"></router-view>
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