<script setup>
import {onMounted, onUnmounted, ref} from 'vue';
// 测试用数据，正式使用时需要读取数据库中的数据
const totalItems = Array.from(new Array(1000).keys())
const items = ref([])
const isLoading = ref(false)
const itemsPerPage = 24
let currentIndex = 0
const ellipsis = ref(true);

function loadMoreItems() {
  if (isLoading.value || currentIndex >= totalItems.length) {
    return;
  }
  isLoading.value = true;
  setTimeout(() => {
    const nextItems = totalItems.slice(currentIndex, currentIndex + itemsPerPage);
    items.value = [...items.value, ...nextItems];
    currentIndex += itemsPerPage;
    isLoading.value = false;
  }, 500); // 模拟异步加载
}

function checkBottom() {
  const scrollableHeight = document.documentElement.scrollHeight;
  const scrollTop = document.documentElement.scrollTop;
  const clientHeight = document.documentElement.clientHeight;
  if (scrollableHeight <= scrollTop + clientHeight + 5) { // 触底判定阈值
    loadMoreItems();
  }
}

onMounted(() => {
  window.addEventListener('scroll', checkBottom);
  loadMoreItems(); // 初始加载
});

onUnmounted(() => {
  window.removeEventListener('scroll', checkBottom);
});
</script>

<template>
  <div class="margin-top-giant">
    <div class="align-center">
      <a-typography-text style="font-size: 1.5em;font-family: 宋体;" strong> 商品推荐</a-typography-text>
    </div>

    <!-- 商品推荐窗口 无限滚动的实现 -->
    <div class="margin-top-large scroll-container">
      <a-row :gutter="[16,16]">
        <a-col :span="6" v-for="item in items" class="item">
          <a-card hoverable>
            <template #cover>
              <img style="width: calc(100%);height: 14em" alt="example"
                   src="https://os.alipayobjects.com/rmsportal/QBnOOoLaAfKPirc.png"/>
            </template>
            <p>商品名</p>
            <p>￥ {{ item + 1 }}</p>
            <a-typography-paragraph
                :ellipsis="ellipsis ? { rows:2 } : false"
                :content="'商品介绍：超过截断超过截断超过截断超过截断超过截断超过截断超过截断超过截断超过截断超过截断超过截断超过截断'+item"
            />
            <a-typography-text>商家名</a-typography-text><a-button type="link">联系</a-button>
          </a-card>
        </a-col>
      </a-row>
      <div v-if="isLoading">加载中...</div>
    </div>
  </div>
</template>

<style>
.scroll-container {
  padding: 20px;
}

.item {
  margin-bottom: 10px;
  padding: 10px;
}


</style>