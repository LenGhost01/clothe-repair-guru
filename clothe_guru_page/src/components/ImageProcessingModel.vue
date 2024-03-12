<script setup>
import {markRaw, onMounted, provide, reactive, ref} from "vue";
import {FontColor} from "@ckeditor/ckeditor5-font";
import ClotheChangeColor from "@/components/imageProcessing/ClotheChangeColor.vue";
import ClotheRepair from "@/components/imageProcessing/ClotheRepair.vue";
import TagTranslation from "@/components/imageProcessing/TagTranslation.vue";

//使用环境文件的常量
const wsPath = import.meta.env.VITE_API_IMAGE_PROCESSING_WS_PATH
const navData = reactive([
  {
    id:1,
    title: "衣物改色",
    bindComponent: markRaw(ClotheChangeColor),
  },
  {
    id:2,
    title:"衣物修复",
    bindComponent: markRaw(ClotheRepair),
  },
  {
    id:3,
    title:"标签翻译",
    bindComponent: markRaw(TagTranslation),
  }
])
const activateComponent = ref(navData[0].bindComponent)
const navItemSelected = ref(1)
const changeComponent = (obj)=>{
  navItemSelected.value = obj.id
  activateComponent.value = obj.bindComponent
}
const ping  =ref(new Date().getTime())
const imageProcessingWsConnect = ref()
// 通过vue的依赖注入向子组件传递通用信息
provide("ping",ping)
provide("imageWsConnect",imageProcessingWsConnect)

onMounted( ()=>{
  imageProcessingWsConnect.value = new WebSocket(`${wsPath}`)
  imageProcessingWsConnect.value.addEventListener("open",function (){
    console.log("组件成功连接到socket服务器")
  })
})
</script>

<template>
  <!-- 头部：切换导航栏 -->
  <div class="head_container">
    <a-breadcrumb separator="|">
      <a-breadcrumb-item v-for="unit in navData" :key="unit.id">
        <a-typography-link :style="{color:navItemSelected === unit.id?'black':'#8C8C8C'}" @click="changeComponent(unit)">{{ unit.title }}</a-typography-link>
      </a-breadcrumb-item>
    </a-breadcrumb>
  </div>
  <!-- 信息展示 -->
  <component :is="activateComponent"></component>
</template>

<style scoped>
.head_container{
  display: grid;
  place-items: center;
}
</style>