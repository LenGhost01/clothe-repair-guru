<script setup>
import {markRaw, onBeforeUnmount, reactive, ref} from "vue";
import emitter from "../utils/EventBus.js";
import {CloseOutlined, MessageFilled, PlusOutlined, UsergroupAddOutlined} from "@ant-design/icons-vue";
import ChatRoomPeerToPeer from "./chatroomcomp/ChatRoomPeerToPeer.vue";
import Channels from "./chatroomcomp/Channels.vue";
import AddChannel from "./chatroomcomp/AddChannel.vue";
import FoundChannel from "./chatroomcomp/FoundChannel.vue";


const show_chat_room = ref(false)
emitter.on("call_chat_room", () => {
  show_chat_room.value = true
})

emitter.on("close_chat_room", () => {
  show_chat_room.value = false
})

const close_chat = () => {
  emitter.emit("close_chat_room")
}

onBeforeUnmount(() => {
  emitter.off("call_chat_room")
})

const change_display_page = (page_name) => {
  // router.replace(page_name)
  console.log(page_name)
}
//使用setup 语法糖时，数组直接绑组件本省而不是组件名
const chat_panels = reactive([markRaw(ChatRoomPeerToPeer), markRaw(Channels), markRaw(AddChannel), markRaw(FoundChannel)])
const visited_component = ref(chat_panels[0])

</script>


<template>
  <!-- 遮罩层插槽 -->
  <div>
    <!--    <slot/>-->
    <!--     聊天室主体页面-->
    <Transition name="chat_room">
      <div v-show="show_chat_room" :style="{
    display: 'inline-block',
    position: 'fixed',
    top: 50+'%',
    left: 50+'%',
    transform: 'translate(-50%, -50%)',
    width: 60+'%',
    height: 80+'%',
    backgroundColor: 'white',
    zIndex: 1001,
    // 电脑支持的最小分辨率，手机需要另外设置
    minWidth: 800+'px',
    minHeight: 600+'px',
    borderRadius: 20+'px',
  }">
        <!-- 显示可选项 -->
        <div class="align-center" :style="{
        display: 'inline-block',
        position: 'absolute',
        left: 0,
        bottom: 0,
        width: 7+'%',
        height: 90+'%',
      }">
          <!-- 一对一聊天 -->
          <div :style="{
          display: 'inline-block',
          margin: '5% 0 5% 0',
        }">
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
          <!-- 非固定数据，需要获取用户与频道的关系后输出 -->
          <div :style="{
          margin: '5% 0 15% 0',
          display: 'inline-block',
        }">
            <a @click="visited_component=chat_panels[1]">
              <a-avatar :size="48" shape="square" :style="{
              border: '2px solid #BFBFBF',
            }"
                        src="/imgs/display.png?width=48">
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

        <!-- 展示信息主体 -->
        <div :style="{
      display: 'inline-block',
      position: 'absolute',
      borderRadius: 20+'px',
      border: '1px solid black',
      width: 93+'%',
      height: 90+'%',
      bottom: 0,
      right: 0,
    }">
          <keep-alive>
            <component :is="visited_component">
            </component>
          </keep-alive>
        </div>

        <!-- 关闭按钮 -->
        <a-button type="link" size="24" danger @click='close_chat' :style="{
       position: 'absolute',
        top: 10+'px',
        right: 0,
      }">
          <CloseOutlined :style="{
        fontSize: 24+'px',
    }"/>
        </a-button>
      </div>
    </Transition>
  </div>
</template>

<style scoped>
.chat_room-enter-active {
  animation: fadeIn 0.5s;

}

.chat_room-leave-active {
  animation: fadeIn 0.5s reverse;
}
</style>