<script setup>
import {computed, onMounted, ref} from "vue";
import dayjs from "dayjs";
import MapContainer from "../components/MapContainer.vue";
import store from "../store/store.js";
import {message} from "ant-design-vue";
import {UploadOutlined} from "@ant-design/icons-vue";
import AvatarUploader from "../components/AvatarUploader.vue";
import axios from "axios";
import zhCN from 'ant-design-vue/es/locale/zh_CN';
import 'moment/locale/zh-cn.js'
import moment from "moment";

moment.locale("zh-cn")
let userObj = ref('')
let ping = ref(new Date().toString())

onMounted(() => {
  loadUser()
  dayjs.locale("zh-cn")
})

let loadUser = async () => {
  if (store.state.userState.user) {
    userObj.value = JSON.parse(JSON.stringify(store.state.userState.user))
  } else {
    message.error("你没登录就进来了呢，真厉害！！")
  }
}

const gender = ref("man")
const dateFormat = 'YYYY-MM-DD';
let birth = ref(dayjs(Date.toLocaleString(), dateFormat))
const options = [
  {
    value: 'zhejiang',
    label: 'Zhejiang',
    children: [
      {
        value: 'hangzhou',
        label: 'Hangzhou',
        children: [
          {
            value: 'xihu',
            label: 'West Lake',
          },
        ],
      },
    ],
  },
  {
    value: 'jiangsu',
    label: 'Jiangsu',
    children: [
      {
        value: 'nanjing',
        label: 'Nanjing',
        children: [
          {
            value: 'zhonghuamen',
            label: 'Zhong Hua Men',
          },
        ],
      },
    ],
  },
];
const value = ref([]);

const open = ref(false);
const uploadAvatarOpen = ref(false)
const showModal = () => {
  open.value = true
};
const handleOk = e => {
  ping.value = new Date().toString()
};
const avaterHandleOk = e => {
  pingAvatar.value = new Date().toString()
}
const putLocationHandler = (e) => {
  userObj.value.location = e.selectedAddress
  open.value = false
}
const showUploadAvatarModal = () => {
  uploadAvatarOpen.value = true
}
const pingAvatar = ref("")

let emitAvatarHandler = (e) => {
  userObj.value.img = e.value
  uploadAvatarOpen.value = false
}

let availableImg = computed(() => {
  if (userObj.value.img === '' || userObj.value === '') {
    return '/static/unlogin.png'
  } else if (userObj.value.img.indexOf('data:image') === -1) {
    return '/users/' + userObj.value.img
  } else {
    return userObj.value.img
  }
})

const userModify = () => {
  console.log(userObj.value.id)
  // 将base64转码成安全的格式
  if (userObj.value.img.indexOf("data:image") !== -1) {
    userObj.value.img = userObj.value.img.replace("+", "-").replace("/", "_");
  }
  axios.post("/video-master/users/update_user", userObj.value)
      .then((res) => {
        if (res.data !== null) {
          userObj.value = res.data
          console.log(res.data)
          // 更新仓库中的数据
          store.dispatch("updateUserState", {
            isLogin: true,
            user: res.data
          })
          message.success("更新用户信息成功")
        }
      })
      .catch((err) => {
        if (err.response.status >= 500 && err.response.status < 600) {
          message.error("服务器出现问题，请及时联系管理员")
        } else if (err.response.status >= 400 && err.response.status < 500) {
          message.error(`错误信息：${err.response.data}`)
        } else {
          message.warning(`其他错误：${err}请及时将错误信息告知管理员`)
        }

      })
}
</script>

<template>
  <div class="message_container">
    <div class="small_tip small_tip_color"></div>
    <a-typography-text class="small_tip_font_color">我的信息</a-typography-text>
    <a-divider></a-divider>
    <div class="user_form">
      <a-row :gutter="[8,16]">
        <a-col :span="4">
          <a-typography-text>头像</a-typography-text>
        </a-col>
        <a-col :span="20" style="position:relative;">
          <a-avatar :size="{ xs: 16, sm: 24, md: 32, lg: 52, xl: 64, xxl: 80 }" :src="availableImg">
          </a-avatar>
          <!-- 图片上传部分 -->
          <div class="upload-wrapper">
            <a-button type="default" class="upload-btn" @click="showUploadAvatarModal">
              <template #icon>
                <UploadOutlined/>
                上传头像
              </template>
            </a-button>
          </div>

        </a-col>
        <a-col :span="4">
          <a-typography-text>昵称</a-typography-text>
        </a-col>
        <a-col :span="20">
          <a-input size="small" v-model:value="userObj.nickname"></a-input>
        </a-col>

        <a-col :span="4">
          <a-typography-text>用户名</a-typography-text>
        </a-col>
        <a-col :span="20">
          <a-typography-text>{{ userObj.username }}</a-typography-text>
        </a-col>

        <a-col :span="4">性别</a-col>
        <a-col :span="20">
          <a-radio-group v-model:value="userObj.sex" button-style="solid" @change="console.log(gender)" size="small">
            <a-radio-button value="男">男</a-radio-button>
            <a-radio-button value="女">女</a-radio-button>
            <a-radio-button value="其他">其他</a-radio-button>
          </a-radio-group>
        </a-col>

        <a-col :span="4">出生日期</a-col>
        <a-col :span="20">
          <a-config-provider :locale="zhCN">
            <a-date-picker size="small" autofocus v-model:value="userObj.birthday" :value-format="dateFormat"
                           :format="dateFormat"
                           placeholder="选择日期"/>
          </a-config-provider>
        </a-col>


        <a-col :span="4">绑定手机</a-col>
        <a-col :span="20">
          <a-typography-text style="min-width: 16em">{{
              userObj.phone === null ? "未绑定手机" : userObj.phone
            }}
          </a-typography-text>
          <a-button size="small" style="margin-left: 3em">{{
              userObj.phone === null ? "添加手机" : "修改手机"
            }}
          </a-button>
        </a-col>

        <a-col :span="4">绑定邮箱</a-col>
        <a-col :span="20">
          <a-typography-text style="min-width: 16em">{{
              userObj.email === null ? "未绑定邮箱" : userObj.phone
            }}
          </a-typography-text>
          <a-button size="small" style="margin-left: 3em">{{
              userObj.phone === null ? "添加邮箱" : "修改邮箱"
            }}
          </a-button>
        </a-col>

        <a-col :span="4">位置:</a-col>
        <a-col :span="18">
          <!-- todo 省市县联动，需要从后台获取数据或者调用接口 -->
          <!--          <a-cascader size="small" v-model:value="value" :options="options" style="width: 40%"-->
          <!--                      placeholder="请选择省市县"/>-->
          <a-input style="height: 24px;width: 100%;" placeholder="填写位置信息"
                   v-model:value="userObj.location"></a-input>
        </a-col>
        <a-col :span="2">
          <a-button type="link" size="small" @click="showModal">定位</a-button>
        </a-col>
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
      <!-- 上传头像模态窗口 -->
      <a-modal
          v-model:open="uploadAvatarOpen"
          title="上传头像"
          @ok="avaterHandleOk"
          ok-text="确认头像"
          cancel-text="取消"
      >
        <avatar-uploader :ping="pingAvatar" @emitAvatar="emitAvatarHandler"></avatar-uploader>
      </a-modal>
    </div>

    <a-button type="primary" style="margin-top: 1em" @click="userModify">
      修改资料
    </a-button>
  </div>


</template>

<style scoped>
.message_container {
  padding: 2em;
}

.user_form {
  width: 33%;
}

.upload-wrapper {
  display: inline-block;
  position: relative;
  width: auto;
  height: auto;
}

.upload-btn {
  display: inline-block;
  position: absolute;
  left: 2em;
  padding: 5px;
  width: auto;
  transform: translateY(-50%);
}
</style>
<style lang="less">
.full-modal {
  .ant-modal {
    max-width: 100%;
    top: 0;
    padding-bottom: 0;
    margin: 0;
  }

  .ant-modal-content {
    display: flex;
    flex-direction: column;
    height: calc(100vh);
  }

  .ant-modal-body {
    flex: 1;
  }
}

.avatar-uploader > .ant-upload {
  width: inherit;
  height: inherit;
}

.ant-upload-select-picture-card i {
  font-size: 32px;
  color: #999;
}

.ant-upload-select-picture-card .ant-upload-text {
  margin-top: 8px;
  color: #666;
}
</style>