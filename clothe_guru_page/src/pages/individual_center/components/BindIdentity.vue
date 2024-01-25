<script setup>
import {reactive} from "vue";

let inputMsg = reactive({
  // 得到的信息
  mail : '',
  code : '',
})
</script>

<template>
  <div class="horizontal_center">
    <a-input style="width: 30%" v-model:value="inputMsg.mail" addon-before="绑定邮箱"/><br>
    <div style="margin-top: 1em">
      <a-input style="width: 22%;" v-model:value="inputMsg.mail" addon-before="验证码"/>
      <a-button style="margin-left: 1%;">发送验证码</a-button>
    </div>
  </div>
</template>

<style scoped>

</style>