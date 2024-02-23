<script setup>
import {animate_controller_double_stage} from "@/utils/AnimateSeletor.js";
import {markRaw, nextTick, reactive, ref} from "vue";
import OnlineUser from "./chatcomp/OnlineUser.vue";
import AllUser from "./chatcomp/AllUser.vue"
import Ban from "./chatcomp/Ban.vue"
import FriendApplication from "./chatcomp/FriendApplication.vue"
import PrivateChat from "./chatcomp/PrivateChat.vue"
import emitter from "/src/utils/EventBus.js"

const username = "测试用户名"
const chat_component = reactive([markRaw(AllUser), markRaw(OnlineUser), markRaw(FriendApplication), markRaw(Ban), markRaw(PrivateChat)])
const activated_chat_component = ref(chat_component[0])
// todo 将数据库中读取出的关于用户好友信息的数据存放在此处
const icon_path_white = (path) => {
  return path + '_white'
}
const icon_path_origin = (path) => {
  return path.slice(0, -6)
}
const friends_list = reactive([
  {
    used_icon: `team`,
    icon_alt: '所有用户',
    title: '全部',
    number: 1
  }, {
    used_icon: `user`,
    icon_alt: '所有用户',
    title: '在线',
    number: 2
  }, {
    used_icon: `add_user`,
    icon_alt: '所有用户',
    title: '请求',
    number: 3
  }, {
    used_icon: `attention_forbid`,
    icon_alt: '所有用户',
    title: '已屏蔽',
    number: 4
  },
])

const call_chat_panel = (event_obj) => {
  console.log(event_obj.target)
  emitter.emit('privateChat', 'input——message')
  nextTick(() => {
    activated_chat_component.value = chat_component[4]
  })
}
</script>

<template>
 <div class="sub-container">
     <div class="slider-wrapper">
       <a-typography-title
           :level="4"
       >{{ username }}
       </a-typography-title>
       <a-typography-text>
         好友
       </a-typography-text>
       <div style="margin-bottom: 1em">
         <div v-for="(fr,index) in friends_list" :key="fr.title">
           <div
               class="content-font-small friends-wrapper"
               @mouseenter="animate_controller_double_stage($event.target, 'mouse_enter_animation', 'mouse_leave_animation'),fr.used_icon=icon_path_white(fr.used_icon)"
               @mouseleave="animate_controller_double_stage($event.target, 'mouse_leave_animation', 'mouse_enter_animation'),fr.used_icon=icon_path_origin(fr.used_icon)"
               @click="activated_chat_component=chat_component[index]">
             <div class="vertical_center_scoped" style="padding-left: 1em" fill>
               <!--                <img class="svg-container" :src="'/imgs/icons/'+ fr.used_icon + '.svg'" :alt="fr.icon_alt">-->
               <div class="background-red finger-point"><span class="select_forbidden">{{ fr.title }}</span></div>
               <a class="select_forbidden">{{ fr.number }}</a>
             </div>
           </div>
         </div>
       </div>
       <a-typography-text>
         私信
       </a-typography-text>
       <div class="ps-container scrollbar" style="height: 63vh">
         <div class="individual_content" @click="call_chat_panel($event)" v-for="i in 32">
           <a-row>
             <a-col :span="6">
               <!-- 头像和状态展示 -->
               <div :style="{
                 display: 'inline-block',
                 textAlign: 'center'
               }">
                 <div style="margin: 0">
                   <a-avatar shape="circle" class="select_forbidden" src="/imgs/avatar/noise_change.png?width=36"
                             :size="36">
                   </a-avatar>
                   <div class="state_dot bg-green" :style="{
                       left : 36/1.25+'px',
                       top : 36/1.25+'px',
                     }"></div>
                 </div>

               </div>
             </a-col>
             <a-col :span="12">
               <!-- 用户名展示 -->
               <div :style="{
                 display: 'inline-block',
                 width: 100+'%',
                 overflow: 'hidden',      /*溢出隐藏*/
                 'white-space': 'nowrap',	/*规定文本不进行换行*/
                 'text-overflow': 'ellipsis',	/*当对象内文本溢出时显示省略标记（...）*/
               }"
                    class="select_forbidden">
                 <a-typography-text class="content-font-small left-margin-5 select_forbidden">
                   用户123411234
                 </a-typography-text>
               </div>
             </a-col>
             <a-col :span="6">
               <!-- 消息条数 -->
               <div :style="{
                 display: 'inline-block',
                 width: 20+'%',
                 textAlign: 'right',
               }"
                    class="select_forbidden">
                 <a-badge count="100"></a-badge>
               </div>
             </a-col>
           </a-row>
         </div>
       </div>
     </div>
     <div :style="{
         display:'inline-block',
         boxSizing: 'border-box',
         width: '80%',
         height: '95vh'
         }">

       <keep-alive>
         <component :is="activated_chat_component">
           <!-- 这里存放这与聊天相关组件 -->
         </component>
       </keep-alive>
     </div>
 </div>

</template>

<style scoped>
.vertical_center_scoped {
  display: inline-block;
  width: 100%;
  height: 2em;
  line-height: 2em;
}

.background-red {
  display: inline-block;
  padding-left: 1em;
  width: 80%;
}

.individual_content {
  display: inline-block;
  width: 100%;
  height: 3em;
  margin-top: 5px;
}

.individual_content div {
  line-height: 3em;
  height: 3em;
}


@keyframes background_to_gray {
  0% {
    background-color: #FFF;
    color: black;
  }
  100% {
    background-color: #BFBFBF;
    color: #FFFFFF;
  }
}

@keyframes background_to_white {
  0% {
    background-color: #BFBFBF;
    color: #FFFFFF;
  }
  100% {
    background-color: #FFF;
    color: black;
  }
}


.mouse_enter_animation {
  animation: background_to_gray;
  animation-duration: 0.2s;
  animation-fill-mode: forwards;
}

.mouse_leave_animation {
  animation: background_to_white;
  animation-duration: 0.2s;
  animation-fill-mode: forwards;
}

.svg-container {
  display: inline-block;
  width: 16px;
  height: 16px;
}

.slider-wrapper {
  box-sizing: border-box;
  display: inline-block;
  width: 20%;
  height: 100%;
  padding: 0 1em;
  border-right: 2px solid #BFBFBF;
  vertical-align: top;
}

.friends-wrapper {
  display: inline-block;
  box-sizing: border-box;
  width: 100%;
  height: 10%;
  margin-top: 0.5em;
  border: 1px solid black;
  border-radius: 5px;
}
</style>