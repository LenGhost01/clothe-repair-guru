<script setup>

import {onMounted} from "vue";
import axios from "axios";
import {message} from "ant-design-vue";
import store from "/src/store/store.js"
import MerchantCenterDisplay from "./views/merchantCenterDisplay.vue";
import router from "./routers/main.js";

onMounted(() => {
  const queryParams = new URLSearchParams(window.location.search);
  let merchantId = queryParams.get("merchantId")
  // 通过获取到的商家id向后端请求对应商家的所有信息
  axios.get(`/requests/merchant/getMerchant?merchantId=${merchantId}`)
      .then((res) => {
        store.dispatch("merchantStore/updateMerchantState", res.data)
      }).catch((err) => {
    message.warning("位置错误" + err.toString())
  })
  axios.get(`/requests/merchandise/getCategory?page=0`).then(res => {
    store.dispatch("merchantStore/updateCategory",res.data)
  }).catch((err) => {
    message.warning("获取类目信息失败")
  })
  axios.get(`/requests/merchandise/getMaterial?page=0`).then(res => {
    store.dispatch("merchantStore/updateMaterial",res.data)
  }).catch((err) => {
    message.warning("获取材质信息失败")
  })

  router.push({path: "/"})
})
</script>

<template>
  <merchant-center-display/>
</template>

<style scoped>

</style>
