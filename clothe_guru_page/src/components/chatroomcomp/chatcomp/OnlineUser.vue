<script setup>
import {MessageOutlined, UserDeleteOutlined, UserOutlined} from '@ant-design/icons-vue'
import {computed} from "vue";
import store from "@/store/store.js";

const request = import.meta.env.VITE_API_REQUESTS_PATH
const imagePath = import.meta.env.VITE_API_IMAGES_PATH

const onlineFriend = computed(() => store.state.MembersStore.userCorrelationMember
    .filter(item => item.state === "online"))
</script>

<template>
  <div class="sub-container" style="padding: 1.5em 0 0 1.5em">
    <div>
      <UserOutlined style="font-size: 1em"/>
      <span style="margin-left: 5%">在线 - {{ onlineFriend.length }}</span>
      <a-button style="margin-left: 5%" type="primary" size="small" @click="console.log('添加好友')">添加好友</a-button>
      <div ref="friendPanel" class="ps-container scrollbar">
      </div>
      <div class="ps-container scrollbar" style="width: 100%">
        <!-- todo 将头像地址和状态一并填入 -->
        <a-row>
          <a-col :span="6" v-for="item in onlineFriend" :key="item.id">
            <div class="personal">
              <div class="personal-display">
                <a-avatar :src="imagePath+'/'+item.avatar"></a-avatar>
                <div class="state_dot bg-green" :style="{
                  top: 36*1.15+'px',
                  left: 36*1.15+'px',
                }"></div>
                <span style="margin-left: 5%">{{ item.nickname }}</span>
              </div>
              <div class="operation-container">
                <div class="operation">
                  <a-row>
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
  </div>
</template>

<style scoped>

.call {

}

</style>