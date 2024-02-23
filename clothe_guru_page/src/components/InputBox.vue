<script setup>

import {ref, watch} from "vue";
import {AntDesignOutlined} from '@ant-design/icons-vue'
import RichTextInputBox from "@/components/RichTextInputBox.vue";

const inputValue = ref('')
watch(inputValue, () => {
      //todo 富文本插件失去焦点后，获取到富文本插件中的信息，显示在页面中
      console.log(inputValue.value);
    }
)
</script>

<template>
  <div class="input_box">
    <div class="input_zone">
      <rich-text-input-box></rich-text-input-box>
    </div>
    <div class="ability_zone">

    </div>
  </div>
</template>



<style scoped>
.input_box {
  position: relative;
  display: inline-block;
  width: 100%;
  height: 100%;
  border: #696969 1px solid;
  border-radius: 5px;
}

.input_zone {
  display: inline-block;
  box-sizing: border-box;
  vertical-align: top;
  width: 90%;
  height: 100%;

}

.ability_zone {
  position: relative;
  display: inline-block;
  box-sizing: border-box;
  vertical-align: top;
  width: 10%;
  height: 100%;
  text-align: center;
  padding: 0 0.5em 0 0;
}
</style>