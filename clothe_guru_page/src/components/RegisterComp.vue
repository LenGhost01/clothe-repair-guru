<script setup>
import {inject, reactive, ref, watch} from "vue";
import {
  InfoCircleOutlined,
  KeyOutlined,
  MailOutlined,
  SafetyOutlined,
  SmileOutlined,
  UserOutlined
} from '@ant-design/icons-vue';
import axios from "axios";
import {message} from "ant-design-vue";
import InitJsonObject from "../utils/InitJsonObject.js";

// 变量区
const formState = reactive({
  username: '',
  nickname: '',
  email: '',
  password: '',
  repeat: '',
  captcha: '',
});

const username_state = ref("")
const password_state = ref("")
const repeat_state = ref("")
const nickname_state = ref("")
const email_state = ref("")
const captcha_state = ref("")

const username_error_value = ref("")
const password_error_value = ref("")
const repeat_error_value = ref("")
const nickname_error_value = ref("")
const email_error_value = ref("")
const captcha_error_value = ref("")

const username_regex = new RegExp("^[a-zA-Z][a-zA-Z0-9_]{4,15}$")
const password_regex = new RegExp("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$")
const email_regex = new RegExp("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+")
const nickname_regex = new RegExp("^[\u4E00-\u9FA5A-Za-z0-9]{2,20}$")

const disableSendButton = ref(false)
const CaptchaContent = ref("发送验证码")

const emits = defineEmits(["registerBack"])
const props = defineProps({
  callRegister: String
})
let clearSignal = inject("clear");

// 函数区
const checkUsername = () => {
  // 用户名不可重复，在验证完格式后需要验证数据库有有无重复的用户名
  if (username_regex.test(formState.username) === false) {
    username_state.value = "error"
    username_error_value.value = "用户名不符合要求，必须使用大小写的英文字符或_-字符组成的4-16位的用户名。"
  } else {
    axios.get(`/requests/user/getUserByUsername?username=${formState.username}`)
        .then(res => {
          //能返回状态码200表示验证通过
          username_state.value = ""
          username_error_value.value = "恭喜，用户名可用"
        }).catch(err => {
      if (err.response.status === 400) {
        //返回值为400表示验证不通过
        username_state.value = "error"
        username_error_value.value = "用户名已被占用"
      }
    })
  }
}
const clearUsernameError = () => {
  username_state.value = ""
  username_error_value.value = ""
}

const checkPassword = () => {
  if (password_regex.test(formState.password) === false) {
    password_state.value = "error"
    password_error_value.value = "密码强度不符合要求，要求最少8位，必须包含大小写字母和数字的组合。"
  } else {
    password_state.value = ""
    password_error_value.value = "密码验证通过"
  }
}
const clearPasswordError = () => {
  password_state.value = ""
  password_error_value.value = ""
}

const checkRepeat = () => {
  if (formState.password !== formState.repeat) {
    repeat_state.value = "error"
    repeat_error_value.value = "重复密码与原密码不一致"
  } else if (formState.repeat === '') {
    repeat_state.value = "error"
    repeat_error_value.value = "重复密码为空"
  } else {
    repeat_state.value = ""
    repeat_error_value.value = "重复密码验证通过"
  }
}
const clearRepeatError = () => {
  repeat_state.value = ""
  repeat_error_value.value = ""
}

const checkNickname = () => {
  if (nickname_regex.test(formState.nickname) === false) {
    nickname_state.value = "error"
    nickname_error_value.value = "昵称输入非法,存在非法字符或输入字符不在2个字符到23个字符之间"
  } else {
    nickname_state.value = ""
    nickname_error_value.value = "昵称验证通过"
  }
}
const clearNicknameError = () => {
  nickname_state.value = ""
  nickname_error_value.value = ""
}

const checkEmail = () => {
  let emailExist = true


  if (email_regex.test(formState.email) === false) {
    email_state.value = "error"
    email_error_value.value = "邮箱格式不符合要求，请检查。"
  } else {
    axios.get(`/requests/user/getUserByMail?email=${formState.email}`)
        .then(res => {
          email_state.value = ""
          email_error_value.value = "恭喜，该邮箱可以使用"
        })
        .catch(err => {
          email_state.value = "error"
          email_error_value.value = "很抱歉，该邮箱已被占用"
        })

  }
}
const clearEmailError = () => {
  email_state.value = ""
  email_error_value.value = ""
}

const checkCaptcha = () => {
  if (formState.captcha === "") {
    captcha_state.value = "error"
    captcha_error_value.value = "验证码输入不得为空"
  }
}
const clearCaptchaError = () => {
  captcha_state.value = ""
  captcha_error_value.value = ""
}
const sendCaptcha = () => {
  if (email_state.value === '') {
    // 邮箱验证成功
    axios.get(`/requests/user/getCaptcha?email=${formState.email}`).then(res => {
      message.success("发送成功")
      // 设置发送验证码冷却时间
      disableSendButton.value = true
      let time = 60;
      let intervalId = setInterval(() => {
        time--
        CaptchaContent.value = `${time}秒`
        if (time === 0) {
          disableSendButton.value = false
          CaptchaContent.value = "发送验证码"
          clearInterval(intervalId)
        }
      }, 1000)
    })
  }
}

//接搜到清理信号后，清除当前表单的值
watch(() => clearSignal.value, (value, oldValue) => {
  InitJsonObject.init(formState, "")
  clearEmailError()
  clearNicknameError()
  clearCaptchaError()
  clearRepeatError()
  clearPasswordError()
  clearUsernameError()
}, {deep: true})

watch(() => props, (value, oldValue) => {
  console.log(value, oldValue)
  emits("registerBack", JSON.stringify(formState))
}, {deep: true})

</script>

<template>
  <a-row :gutter="[12,12]">
    <a-col :span="24">
      <a-input v-model:value="formState.username" placeholder="请输入用户名..." @focusout="checkUsername"
               @focusin="clearUsernameError" :status="username_state">
        <template #prefix>
          <user-outlined/>
        </template>
        <template #suffix>
          <a-tooltip title="用户名输入要求字母开头，允许4-16字节，允许字母数字下划线">
            <info-circle-outlined style="color: rgba(0, 0, 0, 0.45)"/>
          </a-tooltip>
        </template>
      </a-input>
      <div class="error_text" :style="{color: username_state === ''?'green':'red'}">
        {{ username_error_value }}
      </div>
    </a-col>
    <a-col :span="24">
      <a-input-password v-model:value="formState.password" placeholder="请输入密码..." @focusout="checkPassword"
                        @focusin="clearPasswordError" :status="password_state">
        <template #prefix>
          <KeyOutlined/>
        </template>
      </a-input-password>
      <div class="error_text" :style="{color: password_state === ''?'green':'red'}">{{ password_error_value }}</div>
    </a-col>
    <a-col :span="24">
      <a-input-password v-model:value="formState.repeat" placeholder="请重复密码..." @focusout="checkRepeat"
                        @focusin="clearRepeatError" :status="repeat_state">
        <template #prefix>
          <KeyOutlined/>
        </template>
      </a-input-password>
      <div class="error_text" :style="{color: repeat_state === ''?'green':'red'}">{{ repeat_error_value }}</div>
    </a-col>
    <a-col :span="24">
      <a-input v-model:value="formState.nickname" placeholder="请输入昵称..." @focusout="checkNickname"
               @focusin="clearNicknameError" :status="nickname_state">
        <template #prefix>
          <SmileOutlined/>
        </template>
        <template #suffix>
          <a-tooltip title="昵称可包含中文但必须限制在2-24个字符之间">
            <info-circle-outlined style="color: rgba(0, 0, 0, 0.45)"/>
          </a-tooltip>
        </template>
      </a-input>
      <div class="error_text" :style="{color: nickname_state === ''?'green':'red'}">{{ nickname_error_value }}</div>
    </a-col>
    <a-col :span="24">
      <div>
        <a-input v-model:value="formState.email" placeholder="请输入邮箱..." @focusout="checkEmail"
                 @focusin="clearEmailError" :status="email_state">
          <template #prefix>
            <MailOutlined/>
          </template>
        </a-input>
      </div>
      <div class="error_text" :style="{color: email_state === ''?'green':'red'}">{{ email_error_value }}</div>
    </a-col>
    <a-col :span="24">
      <div>
        <a-input-group compact>
          <a-input v-model:value="formState.captcha" placeholder="请输入验证码..." @focusout="checkCaptcha"
                   @focusin="clearCaptchaError" style="width: calc(100% - 7rem)" :status="captcha_state">
            <template #prefix>
              <SafetyOutlined/>
            </template>
          </a-input>
          <a-button :disabled="disableSendButton" type="primary" style="width: 7rem" @click="sendCaptcha">
            {{ CaptchaContent }}
          </a-button>
        </a-input-group>
        <div class="error_text" :style="{color: captcha_state === ''?'green':'red'}">{{ captcha_error_value }}</div>
      </div>
    </a-col>
  </a-row>
</template>

<style scoped>
.error_text {
  font-size: 12px;
  height: 1rem;
}
</style>