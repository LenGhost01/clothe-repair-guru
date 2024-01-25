<script setup>
import NavBar from "/src/components/NavBar.vue";
import CarouselMap from "/src/components/CarouselMap.vue";
import CategoryOutline from "/src/components/CategoryOutline.vue";
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
          store.dispatch("updateUserState",{
            isLogin: true,
            user: res.data
          })
        })
        .catch(err => {
          message.error(err.message)
        })
  }
})

</script>

<template>
  <chat-room>
    <Mask></Mask>
  </chat-room>
  <a-row>
    <a-col :span="4"></a-col>
    <a-col :span="16">
      <nav-bar class="margin-top-large"></nav-bar>
      <carousel-map class="margin-top-large"></carousel-map>
      <category-outline class="margin-top-giant"></category-outline>
      <char-room-btn></char-room-btn>
    </a-col>
    <a-col :span="4"></a-col>
  </a-row>

</template>
