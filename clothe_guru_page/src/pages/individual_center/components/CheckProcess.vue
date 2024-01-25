<script setup>
import {markRaw, ref} from 'vue';
import {message} from "ant-design-vue";
import CurrentIdentity from "./CurrentIdentity.vue";
import BindIdentity from "./BindIdentity.vue";
import BindSuccess from "./BindSuccess.vue";
const current = ref(0);
const next = () => {
  current.value++;
};
const prev = () => {
  current.value--;
};
const steps = [
  {
    title: '验证身份',
    content: markRaw(CurrentIdentity),
  },
  {
    title: '绑定邮箱',
    content: markRaw(BindIdentity),
  },
  {
    title: '绑定成功',
    content: markRaw(BindSuccess),
  },
];
const items = steps.map(item => ({
  key: item.title,
  title: item.title,
}));
</script>

<template>
  <div>
    <a-steps :current="current" :items="items"></a-steps>
    <div class="steps-content">
      <component :is="steps[current].content"></component>
    </div>
    <div class="steps-action">
      <a-button v-if="current < steps.length - 1" type="primary" @click="next">下一步</a-button>
      <a-button
          v-if="current == steps.length - 1"
          type="primary"
          @click="message.success('Processing complete!')"
      >
        完成
      </a-button>
      <a-button v-if="current > 0" style="margin-left: 8px" @click="prev">上一步</a-button>
    </div>
  </div>
</template>

<style scoped>
.steps-content {
  margin-top: 16px;
  border: 1px dashed #e9e9e9;
  border-radius: 6px;
  background-color: #fafafa;
  min-height: 200px;
  text-align: center;
  padding-top: 80px;
}

.steps-action {
  margin-top: 24px;
}

[data-theme='dark'] .steps-content {
  background-color: #2f2f2f;
  border: 1px dashed #404040;
}
</style>