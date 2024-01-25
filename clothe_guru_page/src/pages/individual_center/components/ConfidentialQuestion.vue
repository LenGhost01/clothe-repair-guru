<script setup>
import {reactive, ref} from 'vue';
import {MinusCircleOutlined, PlusOutlined} from "@ant-design/icons-vue";

// 表单对象
const formRef = ref();
// 存放动态组件的数组
const dynamicValidateForm = reactive({
  multiConfidential: [{
    question: '',
    answer: '',
    id: Date.now(),
  }],
});
const removeConfidential = item => {
  const index = dynamicValidateForm.multiConfidential.indexOf(item);
  if (index !== -1) {
    dynamicValidateForm.multiConfidential.splice(index, 1);
  }
};
const addConfidential = () => {
  dynamicValidateForm.multiConfidential.push({
    question: '',
    answer: '',
    id: Date.now(),
  });
};

let questions = ref([
  {
    label: "您目前的姓名是？",
    value: "您目前的姓名是？"
  },
  {
    label: "您配偶的生日是？",
    value: "您配偶的生日是？"
  },
  {
    label: "您的学号（或工号）是？",
    value: "您的学号（或工号）是？",
  },
  {
    label: "您母亲的生日是？",
    value: "您母亲的生日是？",
  },
  {
    label: "您高中班主任的名字是？",
    value: "您高中班主任的名字是？",
  },
  {
    label: "您父亲的姓名是？",
    value: "您父亲的姓名是？",
  },
  {
    label: "您小学班主任的名字是？",
    value: "您小学班主任的名字是？",
  },
  {
    label: "您父亲的生日是？",
    value: "您父亲的生日是？",
  },
  {
    label: "您配偶的姓名是？",
    value: "您配偶的姓名是？",
  },
  {
    label: "您初中班主任的名字是？",
    value: "您初中班主任的名字是？",
  },
  {
    label: "您最熟悉的童年好友名字是？",
    value: "您最熟悉的童年好友名字是？",
  },
  {
    label: "您最熟悉的学校宿舍舍友名字是？",
    value: "您最熟悉的学校宿舍舍友名字是？",
  },
  {
    label: "对您影响最大的人名字是？",
    value: "对您影响最大的人名字是？",
  }


])
const onFinish = values => {
  // 提交验证通过后，将转化后的json传到后端，成功后，返回到安全设置界面
  // console.log('Received values of form:', values.multiConfidential);
  let str = JSON.stringify( values.multiConfidential)
  console.log("转化成的json：" + str)
};
</script>

<template>
  <a-form
      ref="formRef"
      name="dynamic_form_nest_item"
      :model="dynamicValidateForm"
      @finish="onFinish"
  >
    <a-space
        v-for="(confidential, index) in dynamicValidateForm.multiConfidential"
        :key="confidential.id"
        style="display: flex; margin-bottom: 8px"
        align="baseline"
    >
      <a-form-item
          :name="['multiConfidential', index, 'question']"
          :rules="{
          required: true,
          message: '必须要选择问题',
        }"
      >
        <a-select class="input_style" v-model:value="confidential.question" :options="questions"/>
      </a-form-item>
      <a-form-item
          :name="['multiConfidential', index, 'answer']"
          :rules="{
          required: true,
          message: '问题答案不能为空',
        }"
      >
        <a-input class="input_style" v-model:value="confidential.answer" placeholder="请输入问题答案"/>
      </a-form-item>
      <MinusCircleOutlined @click="removeConfidential(confidential)"/>
    </a-space>
    <a-form-item>
      <a-button type="dashed" class="button_location" @click="addConfidential">
        <PlusOutlined/>
      </a-button>
    </a-form-item>
    <a-form-item>
      <a-button type="primary" html-type="submit">提交</a-button>
    </a-form-item>
  </a-form>
</template>

<style scoped>
.button_location {
  position: absolute;
  right: 2em;
}

.input_style {
  width: 20em;
}
</style>