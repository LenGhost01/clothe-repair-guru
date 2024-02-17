<script setup>

import NavAside from "/src/components/NavAside.vue";
import CarouselMap from "/src/components/CarouselMap.vue";
import MerchandiseRecommend from "/src/components/merchandiseRecommend.vue";
</script>

<template>
  <a-row :gutter="[32,32]" style="height: 60vh;width: auto" class="margin-top-large">
    <a-col :span="6">
      <!-- 侧边栏 以目前的情况可以分为全部商品，衣物材质，衣物版型三种类型-->
      <NavAside></NavAside>
    </a-col>
    <a-col :span="18">
      <carousel-map class="margin-top-large"></carousel-map>
    </a-col>
  </a-row>
  <!-- 推荐商品页面 -->
  <merchandise-recommend></merchandise-recommend>
</template>

<style scoped>

</style>