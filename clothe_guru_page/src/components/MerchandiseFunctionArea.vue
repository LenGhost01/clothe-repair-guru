<script setup>

import {computed} from "vue";
import store from "/src/store/store.js";

const merchandise = computed(()=> store.state.merchandiseDetailStore.merchandise)
</script>

<template>
  <div style="position:absolute;;">
    <div class="merchandise-function-wrapper">
      <div>
        <div class="text-container">
          <div class="text-container-sub1">{{ merchandise.merchantName }}</div>
          <div class="text-container-sub2"><a-button type="link">进入店铺页面</a-button></div>
        </div>
        <div style="width:100%;display: inline-block;margin-top: 50px;">
          <div style="background-color: #E1DEDE;width: 100%;padding: 1em;text-align: center">店内搜索</div>
          <div style="padding: 2em;border: 2px solid #E1DEDE;">
            <a-row :gutter="[0,16]">
              <a-col :span="8"><a-typography-text style="font-size: 16px">关键字：</a-typography-text></a-col>
              <a-col :span="16"><a-input placeholder="请输入搜索关键字"></a-input></a-col>
              <a-col :span="8"><a-typography-text style="font-size: 16px">价格：</a-typography-text></a-col>
              <a-col :span="16">
                <a-input style="width: 40%" placeholder="低价"></a-input>
                <a-typography-text> 到 </a-typography-text>
                <a-input style="width: 40%" placeholder="高价"></a-input>
              </a-col>
              <a-col :span="6"></a-col>
              <a-col :span="18"><a-button>搜索</a-button></a-col>
            </a-row>
          </div>
        </div>
      </div>
      <div>
        <div class="functional-group">
          <div>商品信息</div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.merchandise-function-wrapper {
  display: grid;
  grid-template-columns: 1fr 3fr;
  grid-gap: 10px 20px;
}

.text-container{
  border-radius: 2px;
  display: grid;
  grid-template-columns: repeat(2,1fr);
  grid-template-rows: repeat(2,1fr);
  grid-column-gap: 20px;
  grid-row-gap: 10px;
  place-items: center;
}
.text-container-sub1{
  text-align: center;
  width: 100%;
  background-color: #E1DEDE;
  padding: 1em;
  grid-area: 1 / 1 / 2 / 3;
}

.text-container-sub2{
  text-align: center;
  width: 100%;
  background-color: #E1DEDE;
  padding: .5em;
  grid-area: 2 / 1 / 3 / 2;
}

.functional-group{
  width: 80%;
  display: grid;
  grid-template-columns: repeat(7,1fr);
  place-items: center;
  padding: 1em;
  background-color: #E1DEDE;
}
</style>