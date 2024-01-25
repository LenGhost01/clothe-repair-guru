<script setup>

</script>

<template>
  <div class="container" style="transform: translateY(-20%)">
    <a-result
        status="success"
        title="恭喜您，绑定xx成功！"
    >
      <template #extra>
        <a-button key="console" type="primary">回到主页</a-button>
      </template>
    </a-result>
  </div>
</template>

<style scoped>

</style>