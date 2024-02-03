<script setup>
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import {message} from "ant-design-vue";

const queryUrl = "/requests/merchandise/getMaterial"
const deleteUrl = "/requests/merchandise/deleteMaterial"
const updateUrl = "/requests/merchandise/updateMaterial"
const addDataUrl = "/requests/merchandise/addMaterial"

const columns = [
  {
    title: '材质编号',
    dataIndex: 'materialId',
    width: '25%',
  },
  {
    title: '材质名',
    dataIndex: 'materialName',
    width: '15%',
  },
  {
    title: '别名',
    dataIndex: 'alias',
    width: '15%',
  },
  {
    title: '操作',
    dataIndex: 'operation',
  },
];
const queryData = ref({
  count: 0,
  dataSource: []
})

const pagination = ref({
  total: queryData.value.count,
  defaultPageSize: 20,
  hideOnSinglePage: true,
})

const handleAdd = ()=>{

}

const edit = ()=>{

}

const deleteById = (id)=>{

}
onMounted(() => {
  axios.get(`${queryUrl}?page=0`)
      .then(res => {
        console.log(res.data)
        queryData.value.count = res.data.length
        queryData.value.dataSource = res.data
      })
      .catch(err => {
        message.warning(err.response.data)
      })
})
</script>

<template>
  <div style="text-align: right">
    <a-button class="editable-add-btn" style="margin-bottom: 8px" @click="handleAdd">添加</a-button>
  </div>
  <a-table :columns="columns" :data-source="queryData.dataSource" :pagination="pagination" bordered>
    <template #bodyCell="{ column, text, record }">
      <template v-if="column.dataIndex === 'operation'">
        <div class="editable-row-operations">
          <a @click="edit" style="margin-right: 0.5em">编辑</a>
          <span><a-popconfirm title="确定删除?" @confirm="deleteById(record.materialId)" ok-text="确认"
                              cancel-text="取消">
              <a style="color: red">删除</a>
            </a-popconfirm></span>
        </div>
      </template>
    </template>
  </a-table>

  <a-modal>
    <!-- 编辑用模态窗口 -->
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