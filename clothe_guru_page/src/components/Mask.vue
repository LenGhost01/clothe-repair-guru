<script setup>
import {ref} from "vue";
import emitter from "../utils/EventBus.js";

// 记录页面滚动位置
const pageLocation = ref(null);

// 禁止滚动-在显示遮罩层的时候调用
function stop() {
  let scrollTop = window.scrollY;//滚动的高度；
  pageLocation.value = scrollTop;
  document.body.style.position = 'fixed';
  document.body.style.top = '-' + scrollTop + 'px';
};

// 取消滑动限制-在关闭遮罩层的时候调用
function move() {
  document.body.style.position = 'static';
  window.scrollTo(0, pageLocation.value);
}

const show_modal = ref(false)
emitter.on("call_chat_room", () => {
  stop()
  show_modal.value = true
})

emitter.on("close_chat_room", () => {
  move()
  show_modal.value = false
})


</script>

<template>
  <Transition name="mask_classes" >
    <div class="z-index-mask mask" v-show="show_modal">
    </div>
  </Transition>


</template>

<style scoped>
.mask {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,0.5);

}

.mask_classes-enter-active{
  animation: fadeIn 0.5s;

}

.mask_classes-leave-active {
  animation: fadeIn 0.5s reverse;

}

</style>