<script setup>
import {cloneDeep} from 'lodash-es';
import {onMounted, reactive, ref} from 'vue';
import axios from "axios";
import {message} from "ant-design-vue";

// 变量
const queryUrl = "/requests/merchandise/getCategory"
const deleteUrl = "/requests/merchandise/deleteCategory"
const updateUrl = "/requests/merchandise/updateCategory"
const addDataUrl = "/requests/merchandise/addCategory"
const columns = [
  {
    title: '类别编号',
    dataIndex: 'categoryId',
    width: '25%',
  },
  {
    title: '类别名',
    dataIndex: 'categoryName',
    width: '15%',
  },
  {
    title: '类别同义词',
    dataIndex: 'alias',
    width: '15%',
  },
  {
    title: '操作',
    dataIndex: 'operation',
  },
];
const editableData = reactive({});

onMounted(() => {
  axios.get(`${queryUrl}?page=0`)
      .then(res => {
        queryData.value.count = res.data.length
        queryData.value.dataSource = res.data
      })
      .catch(err => {
        message.warning(err.response.data)
      })
})

// 函数
const edit = key => {
  console.log(key)
  editableData[key] = cloneDeep(queryData.value.dataSource.filter(item => key === item.categoryId)[0]);
  console.log(editableData)
};
const save = key => {
  // todo 将编辑信息发送给后台修改，成功后，将前台数据改变
  console.log(editableData[key]);
  axios.get(`${updateUrl}?cid=${editableData[key].categoryId}&cName=${editableData[key].categoryName}&alias=${editableData[key].alias}`)
      .then(res => {
        // 成功修改后，将修改后的文本放到当前页面中
        Object.assign(queryData.value.dataSource.filter(item => key === item.categoryId)[0], editableData[key]);
        delete editableData[key];
      })
      .catch(err => {
        message.warning("修改失败")
        console.log(err);
      })

};
const cancel = key => {
  delete editableData[key];
};

const deleteById = key => {
  console.log(key);
  // 确认后台数据库删除掉对应Id的类目数据项后，删除前台数据
  axios.get(`${deleteUrl}?cid=${key}`).then(res => {
    queryData.value.dataSource = queryData.value.dataSource.filter(item => {
      return item.categoryId !== key
    })
    queryData.value.count--
  }).catch(err => {
    message.warning("删除失败")
    console.log(err);
  })

};
const queryData = ref({
  dataSource: [],
  count: 0,
})

const pagination = ref({
  total: queryData.value.count,
  defaultPageSize: 20,
  hideOnSinglePage: true,
})

const addData = reactive({
  categoryName: "",
  alias: ""
})

const handleAdd = () => {
  // 通过类别名和同义词向数据库传递数据
  axios.get(`${addDataUrl}?cname=${addData.categoryName}&alias=${addData.alias}`)
      .then((res) => {
        let rec = {
          categoryId : res.data,
          categoryName: addData.categoryName,
          alias: addData.alias
        }

        queryData.value.dataSource.push(rec)
        queryData.value.count++

        addData.categoryName = ""
        addData.alias = ""
      })
      .catch((err) => {
        message.warning(err.response.data)
        console.log(err)
      })
}
</script>

<template>
  <div style="text-align: right">
    <a-typography-text>类别名</a-typography-text>
    <a-input style="width: 20rem;margin-right: 1em" v-model:value.lazy="addData.categoryName"></a-input>
    <a-typography-text>别名</a-typography-text>
    <a-input style="width: 20rem;margin-right: 1em" v-model:value.lazy="addData.alias"></a-input>
    <a-button class="editable-add-btn" style="margin-bottom: 8px" @click="handleAdd">添加</a-button>
  </div>
  <a-table :columns="columns" :data-source="queryData.dataSource" :pagination="pagination" bordered>
    <template #bodyCell="{ column, text, record }">
      <template v-if="['categoryName','alias'].includes(column.dataIndex)">
        <div>
          <a-input
              v-if="editableData[record.categoryId]"
              v-model:value="editableData[record.categoryId][column.dataIndex]"
              style="margin: -5px 0"
          />
          <template v-else>
            {{ text }}
          </template>
        </div>
      </template>
      <template v-else-if="column.dataIndex === 'operation'">
        <div class="editable-row-operations">
          <span v-if="editableData[record.categoryId]">
            <a-typography-link @click="save(record.categoryId)">保存</a-typography-link>
            <a-popconfirm title="确定取消?" @confirm="cancel(record.categoryId)" ok-text="确认" cancel-text="取消">
              <a>取消</a>
            </a-popconfirm>
          </span>
          <span v-else>
            <a @click="edit(record.categoryId)">编辑</a>
          </span>
          <span><a-popconfirm title="确定删除?" @confirm="deleteById(record.categoryId)" ok-text="确认"
                              cancel-text="取消">
              <a style="color: red">删除</a>
            </a-popconfirm></span>
        </div>
      </template>
    </template>
  </a-table>
</template>

<style scoped>

.editable-row-operations a {
  margin-right: 8px;
}
</style>