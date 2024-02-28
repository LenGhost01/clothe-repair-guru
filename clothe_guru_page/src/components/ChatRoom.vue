<script setup>
import {markRaw, onBeforeMount, onBeforeUnmount, onMounted, reactive, ref} from "vue";
import emitter from "/src/utils/EventBus.js";
import {MessageFilled, PlusOutlined, UsergroupAddOutlined} from "@ant-design/icons-vue";
import ChatRoomPeerToPeer from "./chatroomcomp/ChatRoomPeerToPeer.vue";
import Channels from "./chatroomcomp/Channels.vue";
import AddChannel from "./chatroomcomp/AddChannel.vue";
import FoundChannel from "./chatroomcomp/FoundChannel.vue";

const change_display_page = (page_name) => {
  // router.replace(page_name)
  console.log(page_name)
}
//使用setup 语法糖时，数组直接绑组件本身而不是组件名
const chat_panels = reactive([markRaw(ChatRoomPeerToPeer), markRaw(Channels), markRaw(AddChannel), markRaw(FoundChannel)])
const visited_component = ref(chat_panels[0])
// 生命周期钩子函数
onMounted(() => {
  console.log("组件被初始化了")

})


onBeforeUnmount(() => {
  emitter.off("call_chat_room")
})
</script>

<template>
  <div class="chatroom-wrapper safe_area">
    <!-- 聊天室主体页面-->
    <!-- 显示可选项 -->
    <div class="base-content-wrapper" style="width: 5vw;text-align: center">
      <!-- 一对一聊天图标 -->
      <div>
        <a @click="visited_component=chat_panels[0]">
          <a-badge count="1">
            <a-avatar :size="48" shape="square">
              <template #icon>
                <MessageFilled :style="{
                  fontSize: 32+'px',
                }"/>
              </template>
            </a-avatar>
          </a-badge>
        </a>
      </div>

      <a-divider style="height: 2px;background: #696969;"></a-divider>
      <!-- 已有频道 -->
      <div class="channel-selector-wrapper scrollbar">
        <!-- todo 非固定数据，需要获取用户加入的频道后得出 -->
        <div v-for="i in 2" :style="{
          margin: '5% 0 15% 0',
          display: 'inline-block',
        }">
          <a @click="visited_component=chat_panels[1]">
            <a-avatar :size="48" shape="square" :style="{
              border: '2px solid #BFBFBF',
            }"
                      src="/imgs/avatar/default.jpg">
            </a-avatar>
          </a>
        </div>

        <!-- 添加新频道 -->
        <div :style="{
          margin: '5% 0 15% 0',
        }">
          <a @click="visited_component=chat_panels[2]">
            <a-avatar :size="48" shape="square">
              <template #icon>
                <PlusOutlined :style="{
                  fontSize: 32+'px',
                }"/>
              </template>
            </a-avatar>
          </a>
        </div>

        <!-- 发现频道 -->
        <div :style="{
          margin: '5% 0 15% 0',
        }">
          <a @click="visited_component=chat_panels[3]">
            <a-avatar :size="48" shape="square">
              <template #icon>
                <UsergroupAddOutlined style="font-size: 32px"/>
              </template>
            </a-avatar>
          </a>
        </div>
      </div>
    </div>
    <!-- 展示信息主体 -->
    <div class="base-content-wrapper" style="width: calc(100% - 5vw)" >
      <keep-alive>
        <component :is="visited_component">
        </component>
      </keep-alive>
    </div>
  </div>
</template>

<style scoped>
.chatroom-wrapper {
  box-sizing: border-box;
  width: 100vw;
  height: 100vh;
  padding: 1em 0.5em;
}

.base-content-wrapper {
  display: inline-block;
  box-sizing: border-box;
  vertical-align: top;
  height: 95vh;
}

.channel-selector-wrapper {
  height: 80vh;
  width: 5vw;
  overflow-y: auto;
}
</style>