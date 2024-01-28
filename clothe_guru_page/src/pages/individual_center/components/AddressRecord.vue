<script setup>
import {onMounted, ref, watch} from "vue";
import store from "/src/store/store.js";
import axios from "axios";
import {message} from "ant-design-vue";

const recordCount = ref(0)
const pagination = ref({
  total : recordCount,
  defaultPageSize : 6,
  hideOnSinglePage : true,
})
const columns = ref([
  {
    title: '时间',
    dataIndex: 'time',
    key: 'time',
  },
  {
    title: '变化',
    dataIndex: 'change',
    key: 'change',
  },
  {
    title: '位置',
    dataIndex: 'address',
    key: 'address',
  },
])

const data = ref([
  //数据通过token访问后台得来,一页最多分配6个数据

])

//更改用户记录展示页面
const pageChangeHandler = (e)=>{
  let page = e.current
  let loginUser = store.state.userState.user
  axios.get(`/requests/user/getUserLoginMsg?userId=${loginUser.userId}&page=${page - 1}`)
      .then(res => {
        data.value = []
        recordCount.value = res.data.count
        for (let item in res.data.userRecordVoList) {
          console.log(res.data.userRecordVoList[item])
          data.value.push({
            key: res.data.userRecordVoList[item].recordId,
            time: res.data.userRecordVoList[item].loginTime,
            change: res.data.userRecordVoList[item].loginIp,
            address: res.data.userRecordVoList[item].loginLocation,
          })
        }

      })
      .catch(err => {
        message.warning(err.response.data)
      })
}

onMounted(() => {
  // 获取到第一页的用户登录地址信息
  let loginUser = store.state.userState.user
  if (loginUser !== {}) {
    axios.get(`/requests/user/getUserLoginMsg?userId=${loginUser.userId}&page=0`)
        .then(res => {
          console.log(res.data)

          recordCount.value = res.data.count
          for (let item in res.data.userRecordVoList) {
            console.log(res.data.userRecordVoList[item])
            data.value.push({
              key: res.data.userRecordVoList[item].recordId,
              time: res.data.userRecordVoList[item].loginTime,
              change: res.data.userRecordVoList[item].loginIp,
              address: res.data.userRecordVoList[item].loginLocation,
            })
          }

        })
        .catch(err => {
          message.warning(err.response.data)
        })
  }
})
</script>

<template>
  <div class="container" style="min-height: 60vh">
    <a-typography-text strong style="color: #7F7F7F;font-size: 24px">登录记录</a-typography-text>
    <a-typography-text style="color:#AAAAAA;margin-left: 0.5rem;">您最近一周的登录情况</a-typography-text>
    <a-divider style="margin: 0.5rem"></a-divider>
    <a-typography-text>根据登录时间，IP，地理位置，若判断为异常情况，请在核实后及时</a-typography-text>
    <a-button type="link">修改密码</a-button>
    <br/>
    <div style="display: inline-block;transform: translateY(-200%)">
      <a-typography-text>说明：</a-typography-text>
    </div>
    <div style="display: inline-block">
      <a-typography-text>
        1： 移动端登录，由于运营商是级分配IP，往往存在与时机登录地不符的情况<br>
        2：若使用VPN或代理联网的，登陆地无法准确记录<br>
        3：部分网络代理的服务，存在IP地址不稳定的问题
      </a-typography-text>
    </div>

    <div class="label_container">
      <a-table :columns="columns" :data-source="data"  size="small" :pagination="pagination" @change="pageChangeHandler">
      </a-table>
    </div>
  </div>
</template>

<style scoped>
.label_container {
  margin-top: 1rem;
}
</style>