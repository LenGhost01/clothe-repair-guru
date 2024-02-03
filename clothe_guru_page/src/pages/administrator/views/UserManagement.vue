<script setup>
import {computed, onMounted, reactive, ref} from 'vue';
import { cloneDeep } from 'lodash-es';
import axios from "axios";
import {message} from "ant-design-vue";

const queryUrl = "/requests/user/getAllUser"
const queryData = ref({count: 0,dataSource: []})
// 展示表单
// 分页
const pagination = ref({
  total : queryData.value.count,
  defaultPageSize : 20,
  hideOnSinglePage : true,
})
// 列名数据
const columns = [
  {
    title: '用户编号',
    dataIndex: 'userId',
    sorter: true,
    width: '20%',
    key: 'userId',
  },
  {
    title: '用户名',
    dataIndex: 'username',
    sorter: true,
    key: 'username',
  },
  {
    title: '邮箱',
    dataIndex: 'email',
    key: 'email',
  },
  {
    title: "操作",
    key: "operation"
  }
]

const showDisplayPanel = ref(false)
const openModel = ()=>{
  showDisplayPanel.value = true
}
// 初始化数据
onMounted(()=>{
  axios.get(`${queryUrl}?page=0`)
      .then(res=>{
        queryData.value.count = res.data.count
        queryData.value.dataSource = res.data.userVoList
      })
      .catch(err=>{
        message.warning(err.response.data)
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

<style lang="less" scoped>
.editable-cell {
  position: relative;
  .editable-cell-input-wrapper,
  .editable-cell-text-wrapper {
    padding-right: 24px;
  }

  .editable-cell-text-wrapper {
    padding: 5px 24px 5px 5px;
  }

  .editable-cell-icon,
  .editable-cell-icon-check {
    position: absolute;
    right: 0;
    width: 20px;
    cursor: pointer;
  }

  .editable-cell-icon {
    margin-top: 4px;
    display: none;
  }

  .editable-cell-icon-check {
    line-height: 28px;
  }

  .editable-cell-icon:hover,
  .editable-cell-icon-check:hover {
    color: #108ee9;
  }

  .editable-add-btn {
    margin-bottom: 8px;
  }
}
.editable-cell:hover .editable-cell-icon {
  display: inline-block;
}
</style>