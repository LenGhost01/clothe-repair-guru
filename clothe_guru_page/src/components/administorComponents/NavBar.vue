<script setup>

</script>

<template>
  <div class="nav_container">
    <div class="title_container user_select_forbidden" @click="console.log('点击标题')">
      <h2>影视天地后台管理系统</h2>
    </div>
  </div>
</template>

<style scoped>
.nav_container{
  display: inline-block;
  width: 100%;
  height: 4em;
  background: #001529;
}

.title_container{
  display: inline-block;
  line-height: 4em;
  width: 20%;
  margin-left: 1em;
  color: #5FB878;

}

</style>