<template>
  <div class="input_box">
    <div class="input_zone">
      <a-input v-model:value.lazy="inputValue" autofocus placeholder="请输入要发送给对方的信息..." size="large"
               :bordered="false"></a-input>
    </div>
    <div class="ability_zone">
      <a-row class="vertical_center">
        <a-col :span="8" class="operate_container">
          <a-avatar :size="{ xs: 20, sm: 24, md: 28, lg: 32, xl: 36, xxl: 32 }">
            <template #icon>
              <AntDesignOutlined />
            </template>
          </a-avatar>
        </a-col>
        <a-col :span="8" class="operate_container">
          <a-avatar :size="{ xs: 20, sm: 24, md: 28, lg: 32, xl: 36, xxl: 32 }">
            <template #icon>
              <AntDesignOutlined />
            </template>
          </a-avatar>
        </a-col>
        <a-col :span="8" class="operate_container">
        <a-avatar :size="{ xs: 20, sm: 24, md: 28, lg: 32, xl: 36, xxl: 32 }">
          <template #icon>
            <AntDesignOutlined />
          </template>
        </a-avatar>
      </a-col>
      </a-row>
    </div>
  </div>
</template>

<script setup>

import {ref, watch} from "vue";
import { AntDesignOutlined } from '@ant-design/icons-vue'

const inputValue = ref('')
watch(inputValue, () => {
  //todo 当文本发生改动是，获取到改变后的新值
      console.log(inputValue.value);
    }
)
</script>

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
  vertical-align: top;
  width: 80%;
  height: 100%;
}

.ability_zone{
  position: relative;
  display: inline-block;
  vertical-align: top;
  width: 20%;
  height:100%;
  text-align: center;
}
</style>