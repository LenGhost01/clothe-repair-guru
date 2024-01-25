<script setup>
const props = defineProps({
  token: String
})

let getToken = props["token"]
console.log("浏览页面" + getToken)
</script>

<template>
浏览记录测试页面
</template>

<style scoped>

</style>