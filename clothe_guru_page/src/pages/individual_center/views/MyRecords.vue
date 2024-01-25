<script setup>
import {markRaw, reactive, ref} from "vue";
import AddressRecord from "../components/AddressRecord.vue";
import BrowseRecord from "../components/BrowseRecord.vue";

const items = ref([
  {
    key: 'address_record',
    title: '地址记录',
    label: '地址记录',
    index: 0,
  },{
    key: 'browse_record',
    title: '浏览记录',
    label: '浏览记录',
    index: 1,
  }
])
let current = ref(['address_record'])
let compSet = reactive([
  markRaw(AddressRecord),
  markRaw(BrowseRecord)
])
let activatedComp = ref(compSet[0])

let changeComp = (index)=>{
  activatedComp = compSet[index]
}
</script>

<template>
  <div class="record_container">
    <div class="small_tip small_tip_color"></div>
    <a-typography-text class="small_tip_font_color">我的记录</a-typography-text>

    <a-menu v-model:selectedKeys="current" mode="horizontal" :items="items" @click="changeComp($event.item.index)"/>
    <component :is="activatedComp" :token="'token'"></component>
  </div>

</template>

<style scoped>
.record_container{
  padding: 2em;
}
</style>