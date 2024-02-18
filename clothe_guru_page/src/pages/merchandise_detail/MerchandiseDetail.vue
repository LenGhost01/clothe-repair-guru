<script setup>
import NavBar from "/src/components/NavBar.vue";
import {onMounted} from "vue";
import axios from "axios";
import store from "/src/store/store.js";
import {message} from "ant-design-vue";
import errPrompt from "/src/utils/StandardExceptioPrompt.js";
import MerchandiseUnitDetail from "/src/components/MerchandiseUnitDetail.vue";
import MerchandiseFunctionArea from "/src/components/MerchandiseFunctionArea.vue";

onMounted(async () => {
  // MPA切换后，vuex公共环境会重置，需要重新获取资源
  if (localStorage.getItem("token")) {
    let token = localStorage.getItem("token")
    await axios.get(`/requests/user/loginByToken?token=${token}`)
        .then(res => {
          store.dispatch("updateUserState", {
            isLogin: true,
            user: res.data
          })
        })
        .catch(err => {
          message.error(err.response.data)
          localStorage.removeItem("token")
        })
  }
  // 通过点击传输的id获取到对应的单个商品信息
  let merchandiseId = new URLSearchParams(window.location.search).get("merchandiseId")
  await axios.get(`/requests/merchandise/getMerchandiseUnitById?id=${merchandiseId}`)
      .then((res) => {
        store.dispatch("merchandiseDetailStore/updateMerchandiseState", res.data)
      })
      .catch(err => {
        errPrompt(err)
      })
  let material = await axios.get(`/requests/merchandise/getMaterial?page=0`)
  await store.dispatch("merchandiseDetailStore/updateMaterial", material.data)
  let category = await axios.get(`/requests/merchandise/getCategory?page=0`)
  await store.dispatch("merchandiseDetailStore/updateCategory", category.data)
})
</script>

<template>
  <div class="grid-container">
    <div></div>
    <div>
      <nav-bar></nav-bar>
      <!-- 商品详情展示页面 -->
      <merchandise-unit-detail/>
      <!-- 从此界面进入店铺页面以及商品介绍  -->
      <merchandise-function-area />
    </div>
    <div></div>
  </div>
</template>

<style scoped>
.grid-container {
  display: grid;
  grid-template-columns: 1fr 5fr 1fr;
  padding: 1em;
}
</style>
