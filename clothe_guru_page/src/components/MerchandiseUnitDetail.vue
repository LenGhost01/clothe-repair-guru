<script setup>
import {computed, onMounted, ref} from "vue";
import store from "/src/store/store.js";
import zhCN from "ant-design-vue/es/locale/zh_CN";

const visible = ref(false);
const selectedImage = ref('')
const imageFrame = ref(null)
const imageFrameSize = ref('')
const optionalImageSize = ref('')
const optionalImageSelected = ref(0)
const dispatchAddress = ref('')

// 计算属性
const merchandiseItem = computed(() => {
  return store.state.merchandiseDetailStore.merchandise
})

const optionalImages = computed(() => {
  let array = new Array(store.state.merchandiseDetailStore.merchandise.mainImg ?? '');
  array.push(...store.state.merchandiseDetailStore.merchandise.subImg ?? [])
  return array
})

const dispatchAddressValue = computed({
  get: () => {
    return store.state.userState.user.receiver
  },
  set: (val) => {
    store.dispatch("updateUserAddress", val)
  }
})

const categoryMap = computed(()=> store.state.merchandiseDetailStore.category)
const materialMap = computed(()=> store.state.merchandiseDetailStore.material)

// 函数
const changeOptionalImageSelected = (index, item) => {
  optionalImageSelected.value = index
}

onMounted(() => {
  // 通过获取图片容器的ref对象得到当前宽度
  imageFrameSize.value = window.getComputedStyle(imageFrame.value).width
  optionalImageSize.value = Number(imageFrameSize.value.substring(0,imageFrameSize.value.length-2))/7 +'px'
})
</script>

<template>
  <div class="margin-top-giant">
    <a-typography-title :level="5">商品展示</a-typography-title>
    <div class="merchandise-content-wrapper">
      <div class="content-wrapper" ref="imageFrame">
        <a-config-provider :locale="zhCN">
          <div
              :style="{
                display: 'grid',
                height: imageFrameSize,
                width: imageFrameSize,
                gridTemplateColumns: '1fr',
                placeItems: 'center',
                backgroundColor: 'rgba(217,213,213,0.78)',
                border: '5px solid #f2f2f2'
              }">
            <a-image
                :preview="true"
                :src="'/imgs/'+optionalImages[optionalImageSelected]"
                @click="visible = true"
                :placeholder="true"
            />
          </div>
          <!-- 可选图片展示 -->
          <div class="optional-image-wrapper">
            <div
                v-for="(item,index) in optionalImages"
                class="optional-image-item" :style="{
                width:  optionalImageSize,
                height: optionalImageSize,
                }"
                :class="optionalImageSelected === index?'optional-image-item-selected':''"
                @click="changeOptionalImageSelected(index,item)">
              <div>
                <a-image :preview="false" :src="'/imgs/'+item"></a-image>
              </div>
            </div>
          </div>
        </a-config-provider>
      </div>
      <div class="content-wrapper">
        <!-- 商品信息展示 -->
        <div style="padding:2em">
          <a-typography-title class="custom-title-style"  :level="4">商品名：{{ merchandiseItem.merchandiseName }}</a-typography-title>
          <a-typography-title class="custom-title-style" v-if="merchandiseItem.lowPrice !== merchandiseItem.highPrice" :level="4">
            价格：￥{{ merchandiseItem.lowPrice }} - ￥{{ merchandiseItem.highPrice }}
          </a-typography-title>
          <a-typography-title class="custom-title-style" :level="4" v-else>￥{{ merchandiseItem.lowPrice }}</a-typography-title>
          <a-typography-title class="custom-title-style" :level="4">
            支持版型：
            <a-tag v-for="tag in merchandiseItem.material" color="#f50" class="custom-tag-style">
              {{ categoryMap.get(tag)?.categoryName }}
            </a-tag>
          </a-typography-title>
          <a-typography-title class="custom-title-style" :level="4">
            支持材质：
            <a-tag v-for="tag in merchandiseItem.category" color="#f50" class="custom-tag-style">
              <a-tooltip>
                <template #title>{{ materialMap.get(tag)?.materialDescription }} 又名：{{materialMap.get(tag)?.alias}}</template>
                {{ materialMap.get(tag)?.materialName }}
              </a-tooltip>
            </a-tag>
          </a-typography-title>
          <a-typography-title class="custom-title-style" :level="4">
            送货地址：
            <a-input placeholder="请输入或选择送货地址..." :style="{width:'60%'}"
                     v-model:value.lazy="dispatchAddressValue"></a-input>
            <a-button type="link">定位</a-button>
          </a-typography-title>
          <a-button type="primary" size="large">联系商家</a-button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.merchandise-content-wrapper {
  display: grid;
  grid-template-columns: 3fr 5fr;
}

.content-wrapper {
  display: inline-block;
  width: 100%;
}

.optional-image-wrapper {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  place-items: center;
}

.optional-image-item {
  display: grid;
  background-color: #fff;
  grid-template-columns: 1fr;
  place-items: center;
  border: 2px solid #f2f2f2;
}

.optional-image-item:hover {
  border: 2px solid #02A7F0;
}

.optional-image-item-selected {
  border: 2px solid #02A7F0;
}

.custom-tag-style {
  font-size: 1rem;
  padding: 4px 8px;
}

.custom-title-style{
  padding: 1rem 0.5rem
}
</style>