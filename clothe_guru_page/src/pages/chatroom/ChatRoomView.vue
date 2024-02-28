<script setup>
import ChatRoom from "/src/components/ChatRoom.vue";
import ChatRoomPeerToPeer from "/src/components/chatroomcomp/ChatRoomPeerToPeer.vue";
import {onMounted} from "vue";
import axios from "axios";
import store from "@/store/store.js";

onMounted(async ()=>{
  const token = localStorage.getItem("token")
  const res = await axios.get(`/requests/user/loginByToken?token=${token}`)
  await store.dispatch("updateUserState", {
    isLogin: true,
    user: res.data
  })
})
</script>

<template>
  <ChatRoom></ChatRoom>
</template>

<style scoped>

</style>
