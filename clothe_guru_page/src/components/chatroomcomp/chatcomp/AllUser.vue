<script setup>
import {UserOutlined} from '@ant-design/icons-vue'
import {MessageOutlined,UserDeleteOutlined} from '@ant-design/icons-vue'
</script>

<template>
  <div :style="{
    height: '100%',
    width: '100%',
    padding: '1.5em 0 0 1.5em',
  }">
    <UserOutlined style="font-size: 1em"/>
    <span style="margin-left: 5%">全部 - 5</span>
    <a-button style="margin-left: 5%" type="primary" size="small" @click="console.log('添加好友')">添加好友</a-button>
    <div ref="friendPanel" class="ps-container scrollbar">

      <!-- todo 将头像地址和状态一并填入 -->
      <a-row>
        <a-col :span="6" v-for="i in 128">
          <div class="personal">
            <div class="personal-display">
              <a-avatar src="/imgs/avatar/noise_change.png?width=36"></a-avatar>
              <div class="state_dot bg-green" :style="{
                  top: 36*1.15+'px',
                  left: 36*1.15+'px',
                }"></div>
              <span style="margin-left: 5%">用户名</span>
            </div>
            <div class="operation-container">
              <div class="operation">
                <a-row >
                  <a-col :span="12">
                    <div class="call">
                      <MessageOutlined style="font-size: 26px"/>
                    </div>
                  </a-col>
                  <a-col :span="12">
                    <div class="remove">
                      <UserDeleteOutlined style="font-size: 26px"/>
                    </div>
                  </a-col>
                </a-row>
              </div>
            </div>
          </div>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<style scoped>
.ps-container {
  display: inline-block;
  position: relative;
  margin-top: 1.5em;
  height: calc(100% - 4em);
  overflow-y: scroll;
  overflow-x: hidden;

}

.operation-container {
  position: relative;
  height: 45%;
}

.operation {
  position: absolute;
  display: inline-block;
  right: 0;
  bottom: 0;
  width: 44%;
  height: 80%;
  text-align: center;
  margin: auto;
}

.call{

}

</style>