<script setup>
import {onMounted, ref} from "vue";
import axios from "axios";
import {message} from "ant-design-vue";

// 定义变量
const requestUrl = "/requests/application"
const queryData = ref({count: 0, dataSource: []})
const stateDisplay = ref(['待审核', '已通过', '未通过'])
const selectedData = ref(null)

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

const showDisplayPanel = ref(false)
// 方法
const check = (targetId) => {
  // 打开审查窗口，检查提交信息
  selectedData.value = queryData.value.dataSource.filter(item => item.applicationId === targetId)[0]
  showDisplayPanel.value = true
}

const refuse = (targetId) => {
  // 拒绝申请
  let translateObj = queryData.value.dataSource.filter(item => item.applicationId === targetId)[0].auditState = 2
  translateObj.certification = JSON.stringify(translateObj.certification)
  axios.post(`${requestUrl}/adminUpdateApplication`, {
    translateObj
  }).then(res => {
    message.success("修改成功")
    queryData.value.dataSource.filter(item => item.applicationId === targetId)[0].auditState = stateDisplay.value[2]
  }).catch(err => {
    message.warning("修改失败")
    console.log(err)
  })
}

const pass = (targetId) => {
  // 通过申请
  let translateObj = queryData.value.dataSource.filter(item => item.applicationId === targetId)[0]
  translateObj.auditState = 1
  translateObj.certification = JSON.stringify(translateObj.certification)
  console.log(translateObj)
  axios.post(`${requestUrl}/adminUpdateApplication`, translateObj)
      .then(res => {
        message.success("修改成功")
        queryData.value.dataSource.filter(item => item.applicationId === targetId)[0].auditState = stateDisplay.value[1]
      }).catch(err => {
    message.warning("修改失败")
    console.log(err)
  })
}

const handleClose = () => {
  showDisplayPanel.value = false
}

// 初始化数据
onMounted(() => {
  axios.get(`${requestUrl}/adminQueryApplication?page=0`)
      .then(res => {
        queryData.value.count = res.data.count
        queryData.value.dataSource = res.data.applicationsEntityList
        queryData.value.dataSource.forEach(item => {
          item.auditState = stateDisplay.value[item.auditState]
          let certifications = []
          let jsonCertification = JSON.parse(item.certification)
          for (let key in jsonCertification) {
            certifications.push({key: key, value: jsonCertification[key]})
          }

          item.certification = certifications
        })
      })
      .catch(err => {
        console.log(err)
        message.warning(err.response.data, 3)
      })

})

</script>

<template>
  <a-table :columns="columns" :data-source="queryData.dataSource" :pagination="pagination" bordered>
    <template #bodyCell="{ column, text, record }">
      <template v-if="column.dataIndex === 'operation'">
        <div class="editable-row-operations" v-if="record.auditState === '待审核'">
          <a @click="check(record.applicationId)" style="margin-right: 0.5em">查看</a>
          <span style="margin-right: 0.5em"><a-popconfirm title="确定通过?" @confirm="pass(record.applicationId)"
                                                          ok-text="确认"
                                                          cancel-text="取消">
              <a style="color: limegreen">通过</a>
            </a-popconfirm></span>
          <span><a-popconfirm title="确定拒绝?" @confirm="refuse(record.applicationId)" ok-text="确认"
                              cancel-text="取消">
              <a style="color: red">拒绝</a>
            </a-popconfirm></span>
        </div>
      </template>
    </template>
  </a-table>

  <a-modal title="查看" v-model:open="showDisplayPanel">
    <a-typography-text>申请用户编号：{{ selectedData.userId }}</a-typography-text>
    <br>
    <a-typography-text>申请名称：{{ selectedData.merchantName }}</a-typography-text>
    <br>
    <a-typography-text>申请邮箱：{{ selectedData.email }}</a-typography-text>
    <br>
    <a-typography-text>申请联系方式：{{ selectedData.phone }}</a-typography-text>
    <br>
    <a-typography-text>申请商家地址：{{ selectedData.address }}</a-typography-text>
    <br>
    <a-typography-text>申请商家介绍：{{ selectedData.introduce }}</a-typography-text>
    <br>
    <a-typography-text>验证信息：</a-typography-text>
    <br>
    <div v-for="item in selectedData.certification">
      <a-typography-text>{{ item.key }}</a-typography-text>
      <a-image :src="'/imgs/'+item.value"></a-image>
    </div>
    <template #footer>
      <a-button @click="handleClose">关闭</a-button>
      <!-- 你可以添加任何自定义按钮或布局 -->
    </template>
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