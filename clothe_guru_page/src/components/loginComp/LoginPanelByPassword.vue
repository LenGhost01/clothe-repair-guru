<script setup>
import {inject, reactive, watch} from "vue";
import {KeyOutlined,UserOutlined} from "@ant-design/icons-vue";

let loginDataSet = reactive({
  type: "pass",
  pass_msg:{
    username: "",
    password: "",
  }


})

let emits = defineEmits(['putUser'])
let callLogin = inject("callLogin")
let clearSignal = inject("clear")
watch(() => callLogin.value, () => {
  emits('putUser', JSON.stringify(loginDataSet))
})

watch(() => clearSignal.value, () => {
  loginDataSet.username = ""
  loginDataSet.password = ""
})
</script>

<template>
  <div>
    <div>
      <h3 class="align-center">普通登录</h3>
      <div>
        <a-input v-model:value="loginDataSet.pass_msg.username" placeholder="请输入用户名..." >
          <template #prefix>
            <user-outlined/>
          </template>
        </a-input>
      </div>
      <div class="margin-top-medium">
        <a-input-password v-model:value="loginDataSet.pass_msg.password" placeholder="请输入密码...">
          <template #prefix>
            <KeyOutlined/>
          </template>
        </a-input-password>
      </div>
    </div>
  </div>
</template>

<style scoped>

.margin-top-medium {
  margin-top: 10px;
}
</style>