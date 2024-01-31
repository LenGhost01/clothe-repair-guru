<script setup>

import {useRoute} from "vue-router";
import {onBeforeUnmount, onMounted} from "vue";
</script>

<template>
  <div>
    <router-view></router-view> <!-- 谨慎使用:key，当子路由刷新的时候，如果父路由也使用了:key，那么父路由也会强制刷新 -->
  </div>
</template>

<style scoped>

</style>
