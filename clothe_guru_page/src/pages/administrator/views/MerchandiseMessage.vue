<script setup>

import {onMounted, ref} from "vue";
import axios from "axios";
import {message} from "ant-design-vue";

const queryUrl = "/requests/merchandise/getMerchandiseView"
const queryData = ref({
  count: 0,
  dataSource: []
})

const columns = ref([
  {
    title: '商品编号',
    dataIndex: 'merchandiseId',
    sorter: true,
    width: '20%',
    key: 'merchandiseId',
  },
  {
    title: '商品名称',
    dataIndex: 'merchandiseName',
    sorter: true,
    key: 'merchandiseName',
  },
  {
    title: '发布时间',
    dataIndex: 'publishTime',
    sorter: true,
    key: 'publishTime',
  },
  {
    title: "操作",
    key: "operation"
  }
])

const showDisplayPanel = ref(false)
const openModel = ()=>{
  showDisplayPanel.value = true
}


onMounted(() => {
  axios.post(`${queryUrl}`,{
    page: 0
  })
      .then(res => {
        console.log("获取到数据")
      })
      .catch(err => {
        message.warning(err.response.data,3)
      })
})
</script>

<template>
  <!-- 展示表单 -->
  <a-table :columns="columns" :data-source="queryData.dataSource">
    <template #headerCell="{ column }">
    </template>
    <template #bodyCell="{ column }">
      <template v-if="column.key === 'operation'"><a-button type="link" @click="openModel">查看所有</a-button></template>
    </template>
  </a-table>

  <a-modal v-model:open="showDisplayPanel" title="查看所有用户信息" cancel-text="返回">
    <p>Some contents...</p>
    <p>Some contents...</p>
    <p>Some contents...</p>
  </a-modal>
</template>

<style scoped>

</style>