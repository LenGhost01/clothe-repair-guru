<script setup>
import {onMounted, ref} from 'vue';
import axios from "axios";
import {message} from "ant-design-vue";
import router from "../pages/home/router/main.js";
import errPrompt from "../utils/StandardExceptioPrompt.js";

const selectedKeys = ref([]);
const openKeys = ref([]);
const items = ref([
  {
    key: '',
    label: '所有商品',
    title: '所有商品',
  },
  {
    key: 'category',
    label: '衣物类型',
    title: '衣物类型',
    children: []
  },
  {
    key: 'material',
    label: '衣物材质',
    title: '衣物材质',
    children: []
  }
]);

const handleClick = info => {
  // 根据点击的单元的key跳转到对应的页面
  if(typeof info === 'string'){
    router.push({path:'/merchandise-list'})
  }else if(typeof info === 'object'){
    router.push({path:'/merchandise-list',query:{keyword:info.parent,keyValue:info.key}})
  }
}
onMounted(async () => {
  try {
    let categoryResult = await axios.get("/requests/merchandise/getCategory?page=0")
    categoryResult.data.forEach(item => {
      items.value[1].children.push({
        key: item.categoryId,
        label: item.categoryName,
        title: item.categoryName,
      })
    })
    let materialResult = await axios.get("/requests/merchandise/getMaterial?page=0")
    materialResult.data.forEach(item => {
      items.value[2].children.push({
        key: item.materialId,
        label: item.materialName,
        title: item.materialName,
      })
    })
  } catch (error) {
    errPrompt(error)
  }

})
</script>

<template>
  <div style="width: 100%;height: 100%;border: 1px solid #F0F0F0;">
    <a-menu>
      <div v-for="item in items" :key="item.key">
        <a-menu-item v-if="item.children === undefined">
          <!-- 在子组件里放置点击按钮的时候，如果点击事件不触发，可能是事件冒泡导致的，需要停止冒泡 -->
          <a-button block class="align-left all-button" type="link" @click.stop="handleClick(item.key)">
            <span class="innerText">{{ item.title }}</span>
          </a-button>
        </a-menu-item>
        <a-sub-menu v-else trigger="hover" :title="item.title" :key="item.key">
          <div style="width: 30vw;height: auto;background: #FFFFFF">
            <a-button type="link" class="link-text" v-for="child in item.children"
                      @click="handleClick({parent:item.key,key:child.key})">
              <span class="innerText">{{ child.title }}</span>
            </a-button>
          </div>
        </a-sub-menu>
      </div>
    </a-menu>
  </div>
</template>
<style scoped>
.innerText {
  color: black;
}

.innerText:hover {
  color: #25A5F7;
}

.all-button {
  padding: 0
}
</style>
