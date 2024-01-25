<script setup>
import {inject, reactive, ref, watch} from "vue";
import axios from "axios";
import {MailOutlined, SafetyOutlined} from "@ant-design/icons-vue";

let loginDataSet = reactive({
  type: "email",
  mail_msg: {
    email: "",
    captcha: "",
  }
})

let isLoading = ref(false)
let CaptchaContent = ref('验证码')
let disableSendButton = ref(false)
let emits = defineEmits(['putUser'])
// 依赖注入传入的就是一个ref对象
let callLogin = inject("callLogin")
let clearSignal = inject("clear")
watch(() => callLogin.value, () => {
  emits('putUser', JSON.stringify(loginDataSet))
})

watch(() => clearSignal.value, () => {
  loginDataSet.email = ""
  loginDataSet.code = ""
})


let sendCheckCode = (mail) => {
  isLoading = true;
  axios.get(`/requests/user/getCaptcha?email=${loginDataSet.mail_msg.email}`)
      .then((res) => {
        isLoading = false;
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
      }).catch(() => {
    // todo 发往错误提示页面
  })
}
</script>

<template>
  <div>
    <div>
      <h3 class="align-center">邮箱登录</h3>
      <div>
        <a-input v-model:value="loginDataSet.mail_msg.email" placeholder="请输入邮箱...">
          <template #prefix>
            <MailOutlined/>
          </template>
        </a-input>
      </div>
      <div class="margin-top-medium">
        <a-input-group compact>
          <a-input v-model:value.lazy="loginDataSet.mail_msg.captcha" placeholder="请输入验证码..."
                   style="width: calc(100% - 6rem)">
            <template #prefix>
              <SafetyOutlined/>
            </template>
          </a-input>
          <a-button type="primary" style="width: 6rem;" :loading="isLoading" :disabled="disableSendButton"
                    @click="sendCheckCode(loginDataSet.mail_msg.email)">{{ CaptchaContent }}
          </a-button>
        </a-input-group>

      </div>
    </div>
  </div>
</template>

<style scoped>

</style>