<template>
<button class="success-button">
  <slot />
</button>
</template>

<script setup>

</script>

<style scoped>
.success-button{
  color: white;
  background: #9ed900;
  border: none;
  padding: 5px 10px;
  margin: 5px;
  border-radius: 5px;
  cursor: pointer;
}

.success-button:hover{
  background: #afdd22;
}
</style>