<script setup>
import NavBar from "/src/components/NavBar.vue";
import {onMounted} from "vue";
import axios from "axios";
import store from "/src/store/store.js";
import {message} from "ant-design-vue";

onMounted(() => {
  // MPA切换后，vuex公共环境会重置，需要重新获取资源
  if (localStorage.getItem("token")) {
    let token = localStorage.getItem("token")
    axios.get(`/requests/user/loginByToken?token=${token}`)
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

    // 通过点击传输的id获取到对应的单个商品信息
    let merchandiseId = new URLSearchParams(window.location.search).get("merchandiseId");
    console.log(merchandiseId)

  }
})
</script>

<template class="safe_area">
  <div class="grid-container">
    <div></div>
    <div>
      <nav-bar></nav-bar>
      <!-- 商品详情展示页面 -->

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
