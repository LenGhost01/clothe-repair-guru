<script setup>
import {useRoute} from "vue-router";
import {markRaw, onBeforeMount, onBeforeUnmount, reactive, ref} from "vue";
import ChechComp from "../components/ChechComp.vue";
import ConfidentialQuestion from "../components/ConfidentialQuestion.vue";
import CheckProcess from "../components/CheckProcess.vue";
import emitter from "../../../utils/EventBus.js";
import FindAccount from "../components/FindAccount.vue";

let {query} = useRoute()
let activatedComp = query.pattern
let pageMsgSet = reactive([
  {
    title: "密码设置"
  },
  {
    title: "手机设置"
  },
  {
    title: "邮箱设置"
  },
  {
    title: "密保设置"
  }
])
let compSet = reactive([
    markRaw(ChechComp),
    markRaw(ConfidentialQuestion),
    markRaw(CheckProcess),
    markRaw(FindAccount)
])
let activated = ref(compSet[0])
// 当页面匹配参数小于3时，跳转到验证界面，等于3时，跳转到密保设置页面。

let changeToCheck = (stateCode) => {
  activated.value = compSet[stateCode]
}
emitter.on("callCheckProcess",(stateCode)=>{
  changeToCheck(stateCode)
})

emitter.on("callFindAccount",(stateCode)=>{
  changeToCheck(stateCode)
})
onBeforeMount(()=>{
  // 不能直接将proxy对象直接赋值，会取消响应式的效果，需要修改value
  activated.value = Number(activatedComp)<3?compSet[0]:compSet[1]
})

onBeforeUnmount(()=>{
  emitter.off("callFindAccount")
  emitter.off("callCheckProcess")
})
</script>

<template>
  <div class="container">
    <a-typography-text>
      <a-typography-title :level="5" style="display: inline-block">账号安全</a-typography-title>
      <svg style="transform: translateY(25%)" xmlns="http://www.w3.org/2000/svg" width="16" height="16"
           fill="currentColor" class="bi bi-chevron-right" viewBox="0 0 16 16">
        <path fill-rule="evenodd"
              d="M4.646 1.646a.5.5 0 0 1 .708 0l6 6a.5.5 0 0 1 0 .708l-6 6a.5.5 0 0 1-.708-.708L10.293 8 4.646 2.354a.5.5 0 0 1 0-.708z"/>
      </svg>
      {{ pageMsgSet[activatedComp].title }}
    </a-typography-text>

    <component :is="activated"></component>
  </div>
</template>

<style scoped>

</style>