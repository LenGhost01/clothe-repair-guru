<script setup>
import {ref} from "vue";

const requestUrl = "/requests/"

const queryData = ref({count: 0, dataSource: []})

// 分页
const pagination = ref({
  total: queryData.value.count,
  defaultPageSize: 20,
  hideOnSinglePage: true,
})
// 列名数据
const columns = [
  {
    title: "请求编号",
    dataIndex: 'applicationId',
    sorter: true,
    key: 'applicationId',
  },
  {
    title: '请求用户',
    dataIndex: 'userId',
    sorter: true,
    key: 'userId',
  },
  {
    title: '店铺名',
    dataIndex: 'merchantName',
    key: 'merchantName',
  }, {
    title: '状态',
    dataIndex: 'auditState',
    key: 'auditState',
  },
  {
    title: "操作",
    dataIndex: "operation",
    key: "operation"
  }
]
</script>

<template>

</template>

<style scoped>

</style>