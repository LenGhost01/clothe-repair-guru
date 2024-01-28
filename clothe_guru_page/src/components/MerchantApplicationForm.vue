<script setup>
/*
* 商家名称
* 注册地址
* 联系电话
* 电子邮件地址
* 营业执照号码（或相应的商业注册证明） 可提供多个
* 店铺或公司简介
* */
import {reactive, ref} from "vue";
import MapContainer from "/src/pages/individual_center/components/MapContainer.vue";


const open = ref(false);
const ping = ref("")
const showModal = () => {
  open.value = true
};
const handleOk = e => {
  ping.value = new Date().toString()
};
const putLocationHandler = (e) => {
  console.log(e)
  open.value = false
}

const data = reactive({
  merchantName: "",
  address: "",
  phone: "",
  email: "",
  certification: [],
  introduce: "",
})
</script>

<template>
  <div class="container">
    <a-row :gutter="[16,16]">
      <div style="width: 100%">
        <!-- 商家名称输入 -->
        <a-col :span="12">
          <a-typography-text>商家名称</a-typography-text>
          <a-input v-model:value="data.merchantName" placeholder="请输入商家名称..." />
        </a-col>
        <a-col :span="12">
          <a-typography-text>注册地址</a-typography-text>
          <a-input v-model:value="data.address" placeholder="请输入商家地址或定位..." />
          <a-button type="link" @click="showModal">定位</a-button>
        </a-col>
      </div>
    </a-row>

    <!-- 选择地址模态窗口 -->
    <a-modal
        v-model:open="open"
        title="定位"
        width="100%"
        wrap-class-name="full-modal"
        @ok="handleOk"
        ok-text="确认"
        cancel-text="取消"
    >
      <map-container :ping="ping" @putLocation="putLocationHandler"></map-container>
    </a-modal>
  </div>
</template>

<style scoped>
.container {
  display: inline-block;
  width: 100%;
  height: auto;
  padding: 2em;
}
</style>