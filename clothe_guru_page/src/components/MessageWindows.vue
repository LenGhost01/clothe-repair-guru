<template>
  <div class="message_dis scrollbar">
    <div class="msg_display" v-for="item in store.state.messageStore.receivedMessage">
      <div v-if="item.receiverId === store.state.userState.user.userId">
        <div class="msg_head">
          <div style="display: inline-block;width: 36px;height: 36px;position: relative">
            <a-avatar shape="circle" class="select_forbidden" :src="img + '/'+target.avatar"
                      :size="36">
            </a-avatar>
            <div class="state_dot bg-green" :style="{
                    left : 36/1.45+'px',
                    top : 36/1.45+'px',
                  }"></div>
          </div>
          <div :style="{display: 'inline-block',marginLeft: '1em'}"
               class="select_forbidden">
            <a-typography-text class="content-font-middle left-margin-5 select_forbidden">
              {{ target.nickname }}
            </a-typography-text>
            <a-typography-text type="secondary" class="content-font-middle left-margin-5 select_forbidden">
              {{ item.createTime }} <!-- 填入数据库中存放的消息发送时间 -->
            </a-typography-text>
          </div>
        </div>
        <div class="msg_content">
          <div v-html="item.content" class="ck-content"></div>
        </div>
      </div>
      <div v-if="item.senderId === store.state.userState.user.userId" class="align-right">
        <!-- 发送者的聊天记录位于右边 -->
        <div class="msg_head">
          <div style="display: inline-block;width: 36px;height: 36px;position: relative">
            <a-avatar shape="circle" class="select_forbidden" :src="img + '/'+target.avatar"
                      :size="36">
            </a-avatar>
            <div class="state_dot bg-green" :style="{
                    left : 36/1.45+'px',
                    top : 36/1.45+'px',
                  }"></div>
          </div>
          <div :style="{display: 'inline-block',marginLeft: '1em'}"
               class="select_forbidden">
            <a-typography-text class="content-font-middle left-margin-5 select_forbidden">
              {{ store.state.userState.user.nickname }}
            </a-typography-text>
            <a-typography-text type="secondary" class="content-font-middle left-margin-5 select_forbidden">
              {{ item.createTime }} <!-- 填入数据库中存放的消息发送时间 -->
            </a-typography-text>
          </div>
        </div>
        <div class="msg_content">
          <div v-html="item.content" class="ck-content"></div>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import {inject, nextTick, onMounted, ref, watch} from "vue";
import store from "@/store/store.js";

function cleanImageAttributes(html) {
  const div = document.createElement('div');
  div.innerHTML = html;

  const images = div.getElementsByTagName('img');
  for (let img of images) {
    img.removeAttribute('width');
    img.removeAttribute('height');
    // 设置CSS类以应用自定义样式
    img.className = 'image_resized';
  }

  return div.innerHTML;
}


// 使用依赖注入获取到父组件绑定的ws对象，并监听message
const ws = inject("webSocketConnector")
const target = inject("targetUser")
const img = import.meta.env.VITE_API_IMAGES_PATH
const rec = defineProps({
  transport: Object
})
const showMsg = ref('')
// todo 编写监听事件，监听rec的变化，如有改动，更新当前聊天信息，并将页面滑动置底部
watch(
    () => rec.transport.token,
    (newValue) => {
      nextTick(() => {
        //todo Token更新后，使用scrollIntoView()方法将元素滚动到视口位置

      })
    }
)

onMounted(() => {
  // ws.value?.addEventListener('message', (message) => {
  //   // ws接收到服务器发送的信息会调用这个回调 根据接收到的信息的type判断要执行怎样的业务逻辑
  // })
})

</script>

<style scoped>
.message_dis {
  position: relative;
  display: inline-block;
  width: 100%;
  height: 100%;
  overflow-y: scroll;
  overflow-x: hidden;
}

.msg_display {
  padding-left: 2em;
  width: 95%;
}


.msg_head {
  margin-top: 1em;

}

.msg_content {
  margin-top: 1em;
  display: inline-block;
}

.rich-text img {

}

</style>