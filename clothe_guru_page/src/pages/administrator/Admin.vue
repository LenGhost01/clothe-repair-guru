<script setup>

import {useRoute} from "vue-router";
import router from "./routers/main.js";

let testsfunc = () => {
  router.push('/manager_login')
}
</script>

<template>
  <div>
    <router-view :key="useRoute().fullPath"></router-view>
  </div>
</template>

<style scoped>

</style>
