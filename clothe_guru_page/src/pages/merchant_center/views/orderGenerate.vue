<script setup>
import {h, reactive} from "vue";
import {v4 as uuidv4} from 'uuid';
import {Input, Modal,Typography} from "ant-design-vue";
import {EyeFilled} from "@ant-design/icons-vue";

const [modal, contextHolder] = Modal.useModal();
// 使用ref创建响应式数据
const inputValue = reactive({
  contactMethod: "",
  receiverAddress: "",
});
const merchantId = new URLSearchParams(window.location.search).get("merchantID")

// 订单在聊天信息中的基本格式 content传输的内容体，context是传输所需的上下文
const chat = reactive({
  chatType: 'order',
  content: "",
  context: "",
  id: uuidv4()
})

const generateOrder = () => {
  // 生成预览窗口 通过上下文 自动生成部分信息
  // 当编写此订单的目标为商家时，允许一部分内容编写，编写此订单的目标是用户时，允许另一部分的内容编写
  modal.confirm({
    title: "预览窗口",
    icon: h(EyeFilled, {style: 'color:gray;'}),
    content: h('div', {}, [
      h(Typography.Title,{level:5},"这是一段测试标题")
      ,
      h(Input, {
        disabled: merchantId === new URLSearchParams(window.location.search).get("merchantID"),
        modelValue: inputValue.value, // 绑定值
        onInput: (e) => {
          inputValue.value = e.target.value; // 更新值
        },
        placeholder: '请输入联系方式' // 其他a-input属性
      }),
      h(Input, {
        disabled: merchantId === new URLSearchParams(window.location.search).get("merchantID"),
        modelValue: inputValue.value, // 绑定值
        onInput: (e) => {
          inputValue.value = e.target.value; // 更新值
        },
        placeholder: '请输入收货地址' // 其他a-input属性
      })
    ]),
    onOk() {
      console.log(inputValue.value)
    }
  })

}
</script>

<template>
  <!-- todo 填写订单信息页面  订单生成器页面暂时废弃，生成器页面应放在聊天室页面中-->
  <div class="container">
    <div class="order-refinement"></div>
    <!-- todo 点击生成页面之后，会弹出一个对话框，其中包含生成订单的预览和链接的复制 -->
    <a-button type="primary" @click="generateOrder">生成</a-button>
    <contextHolder/>
  </div>


</template>

<style scoped>

</style>