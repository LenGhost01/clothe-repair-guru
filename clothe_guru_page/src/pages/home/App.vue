<script setup>
import NavBar from "/src/components/NavBar.vue";
import CarouselMap from "/src/components/CarouselMap.vue";
import CharRoomBtn from "/src/components/ChatRoomBtn.vue";
import Mask from "/src/components/Mask.vue";
import ChatRoom from "/src/components/ChatRoom.vue";
import {onMounted} from "vue";
import axios from "axios";
import {message} from "ant-design-vue";
import store from "../../store/store.js";

onMounted(() => {
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
  }
})

</script>

<template>
<!--  <chat-room>-->
<!--&lt;!&ndash;    <Mask></Mask>&ndash;&gt;-->
<!--  </chat-room>-->
  <a-row>
    <a-col :span="4"></a-col>
    <a-col :span="16">
      <nav-bar class="margin-top-large"></nav-bar>
      <!-- 快捷选择侧边栏和推荐轮播图合并 -->
      <router-view></router-view>
<!--      <char-room-btn></char-room-btn>-->
    </a-col>
    <a-col :span="4"></a-col>
  </a-row>

</template>
