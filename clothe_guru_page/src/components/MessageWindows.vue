<template>
  <div class="message_dis scrollbar">
    <div class="msg_display" v-for="i in 32">
      <div class="msg_head">
        <div style="display: inline-block;width: 36px;height: 36px;position: relative">
          <a-avatar shape="circle" class="select_forbidden" src="/imgs/avatar/noise_change.png?width=36"
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
            用户123411234
          </a-typography-text>
          <a-typography-text type="secondary" class="content-font-middle left-margin-5 select_forbidden">
            2023年9月21日21:10:00 <!-- 填入数据库中存放的消息发送时间 -->
          </a-typography-text>
        </div>
      </div>
      <div class="msg_content">
        <a-typography-paragraph :content="transport.token" :ref="showMsg">

        </a-typography-paragraph>
      </div>
    </div>
  </div>
</template>

<script setup>
import {inject, nextTick, onMounted, onUpdated, ref, watch} from "vue";

// 使用依赖注入获取到父组件绑定的ws对象，并监听message
const ws = inject("webSocketConnector")

const rec = defineProps({
  transport: Object
})
const showMsg = ref('')
// todo 编写监听事件，监听rec的变化，如有改动，更新当前聊天信息，并将页面滑动置底部
watch(
    ()=>rec.transport.token,
    (newValue)=>{
      nextTick(()=>{
        //todo Token更新后，使用scrollIntoView()方法将元素滚动到视口位置

      })
    }
)

onMounted(()=>{
  ws.value?.addEventListener('message', (message) => {
    // ws接收到服务器发送的信息会调用这个回调 根据接收到的信息的type判断要执行怎样的业务逻辑
  })
})

</script>

<style scoped>
.message_dis{
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
}
</style>