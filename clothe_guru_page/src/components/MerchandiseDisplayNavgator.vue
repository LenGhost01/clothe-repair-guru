<script setup>
import {nextTick, onBeforeMount, onMounted, reactive, ref, watch} from "vue";
import {CaretDownOutlined, CaretUpOutlined, CommentOutlined, DownOutlined} from "@ant-design/icons-vue"
import errPrompt from "/src/utils/StandardExceptioPrompt.js";
import axios from "axios";
import zhCN from "ant-design-vue/es/locale/zh_CN";
import {useRoute} from "vue-router";
import {message} from "ant-design-vue";
import store from "/src/store/store.js";
import emitter from "/src/utils/EventBus.js";

const queryData = reactive({
  dataSource: [],
  count: 0
})
const items = reactive([
  {
    title: "衣物类型",
    key: "category",
    content: [{id: -1, key: '', name: '全部', alias: '', selected: true}],
  },
  {
    title: "衣物材质",
    key: 'material',
    content: [{id: -1, key: '', name: '全部', alias: '', selected: true}],
  }
])
const merchandiseGetterParams = reactive({
  // 模糊查询参数
  keyWord: '',
  keyWordLabel: '',
  // 排序参数,
  orderLabel: '',
  orderRule: '',
  // 按价格筛选,
  lowPrice: '',
  highPrice: '',
  // 筛选标签,
  filterParam: items.map(item => item.key),
  filterValue: new Array(items.length).fill(''),
  // 获取页数,
  page: 0,
})
const outerWrapper = ref(null)
const subDropdownWrapperWidth = ref('100%')
const selectedCategory = ref(-1)
const selectedMaterial = ref(-1)
const ellipsis = ref(true);
const filterOrderitems = reactive([
  {
    key: -1,
    title: "综合",
    keyword: '',
    selected: true
  },
  {
    key: 1,
    title: "销量",
    keyword: 'SALES',
    orderByAsc: '',
    selected: false
  },
  {
    key: 2,
    title: "新品",
    orderByAsc: '',
    keyword: 'PUBLISH_TIME',
    selected: false
  },
  {
    key: 3,
    title: "价格",
    orderByAsc: '',
    keyword: 'PRICE',
    selected: false
  },
  {
    key: 4,
    title: "距离",
    orderByAsc: '',
    keyword: 'ADDRESS',
    selected: false
  }
])
const selectedFilterOrderItem = ref(-1)
const linkSpanClickHandler = (id, key, parent) => {
  if (parent.key === "category") {
    selectedCategory.value = id
  } else if (parent.key === "material") {
    selectedMaterial.value = id
  }
  merchandiseGetterParams.filterValue[items.map(item => item.key).indexOf(parent.key)] = key
  let getter = merchandiseGetter();
  getter.then(res => {
    // 得到获取的对象数组
    queryData.dataSource = res.merchandiseVoList
    queryData.count = res.count
  })
}
const merchandiseGetter = async () => {
  try {
    let result = await axios.post("/requests/merchandise/getMerchandiseView", merchandiseGetterParams)
    return result.data
  } catch (error) {
    errPrompt(error)
  }
}

const selectOrder = (key, keyword) => {
  if (key === selectedFilterOrderItem.value) {
    filterOrderitems.forEach(item => {
      if (item.key === key && (item.keyword === 'PRICE' || item.keyword === 'ADDRESS')) {
        item.orderByAsc = !item.orderByAsc
      }
    })
  }
  selectedFilterOrderItem.value = key

  nextTick(() => {
    // 确保在数据修改完成后执行后面的代码
    merchandiseGetterParams.orderLabel = keyword
    merchandiseGetterParams.orderRule = filterOrderitems.filter(item => item.key === key)[0].orderByAsc

    let merchandiseArray = merchandiseGetter();
    merchandiseArray.then(res => {
      queryData.count = res.count
      queryData.dataSource = res.merchandiseVoList
    })
  })
}

const merchandiseBetweenLowHigh = () => {
  if (merchandiseGetterParams.highPrice !== '' && merchandiseGetterParams.lowPrice !== '' && (merchandiseGetterParams.highPrice < merchandiseGetterParams.lowPrice)) {
    message.warning("输入的价格高价位不能低于低价位", 2)
    return
  }
  merchandiseGetter().then(res => {
    queryData.dataSource = res.merchandiseVoList
    queryData.count = res.count
  })
}

const callMerchant = (merchantId, userId) => {
  // todo 通过商家id和用户id跳转到聊天界面
  if(userId === undefined){
    emitter.emit("emitTest")
    return
  }

}

const jumpToMerchandiseDetail = (id) => {
  window.open(`${window.location.origin}/src/pages/merchandise_detail/index.html?merchandiseId=${id}`)
}

watch(selectedCategory, (newValue, oldValue) => {
  items[0].content.forEach(item => {
    if (item.id === oldValue) item.selected = false
    if (item.id === newValue) item.selected = true
  })
})
watch(selectedMaterial, (newValue, oldValue) => {
  items[1].content.forEach(item => {
    if (item.id === oldValue) item.selected = false
    if (item.id === newValue) item.selected = true
  })
})
watch(selectedFilterOrderItem, (newValue, oldValue) => {
  filterOrderitems.forEach(item => {
    if (item.key === oldValue) {
      item.selected = false, item.orderByAsc = ''
    }
    if (item.key === newValue) {
      item.selected = true
      if (item.key > 0) item.orderByAsc = false
    }
  })
})
onMounted(async () => {
  let translatedParams = useRoute().query
  if (outerWrapper.value[0]) {
    // 如果这个容器没有值的话视作初始化失败
    const style = window.getComputedStyle(outerWrapper.value[0]);
    // 这里可以根据需要访问更多CSS属性
    subDropdownWrapperWidth.value = style.width
    // ?? 是逻辑空合运算符，在检测到式子左边的值为undefined或null时会赋予式子右边的值
    // console.log(translatedParams)
    merchandiseGetterParams.filterValue[items.map(item => item.key).indexOf(translatedParams.keyword)] = translatedParams.keyValue ?? ''
    console.log(merchandiseGetterParams.filterValue)
    merchandiseGetterParams.keyWord = translatedParams.queryKeyword ?? ''
    merchandiseGetterParams.keyWordLabel = translatedParams.labelIndex ?? ''
    try {
      // 获取页面显示必须的资源
      let categoryResult = await axios.get("/requests/merchandise/getCategory?page=0")
      let materialResult = await axios.get("/requests/merchandise/getMaterial?page=0")
      let merchandiseResult = await merchandiseGetter()
      if (materialResult && categoryResult && merchandiseResult) {
        items[0].content.push(...categoryResult.data.map(item => {
          return {
            id: item.categoryId,
            key: item.categoryId,
            name: item.categoryName,
            alias: item.alias
          }
        }))
        items[1].content.push(...materialResult.data.map(item => {
          return {
            id: item.materialId,
            key: item.materialId,
            name: item.materialName,
            alias: item.alias
          }
        }))
        queryData.count = merchandiseResult.count
        queryData.dataSource = merchandiseResult.merchandiseVoList
        // 在数据初始化完成后才选择选中对应的项
        if (translatedParams.keyValue !== undefined) {
          if(translatedParams.keyword === 'category')
            selectedCategory.value = Number(translatedParams.keyValue)
          else if(translatedParams.keyword === 'material')
            selectedMaterial.value = Number(translatedParams.keyValue)
        }
      }
    } catch (err) {
      // 调用标准请求错误处理函数来做出提示
      errPrompt(err)
    }
  }
})
onBeforeMount(()=>{
  emitter.emit("childEvent")
})
</script>

<template>
  <div class="container width-full margin-top-giant">
    <div v-for="item in items">
      <div style="border: 1px solid  #F2F2F2;border-radius: 5px;">
        <div class="aside">{{ item.title }}</div>
        <div class="operation-area" ref="outerWrapper">
          <div class="operationGridWrapper">
            <!-- 部分显示，超出的用下拉菜单显示 -->
            <div class="grid-item" v-for="unit in item.content.slice(0,9)" :key="unit.id">
              <span class="linkSpan" :class="unit.selected === true?'selected':''"
                    @click="linkSpanClickHandler(unit.id,unit.key,item)">{{ unit.name }}</span>
            </div>
            <a-dropdown class="grid-item" v-if="item.content.length > 9">
              <a class="ant-dropdown-link" @click.prevent>
                更多
                <DownOutlined/>
              </a>
              <template #overlay>
                <div style="position: relative">
                  <div :style="{
                  backgroundColor: 'white',
                  width: subDropdownWrapperWidth,
                  border: '1px solid #F2F2F2',
                  borderRadius: '5px',
                  transform: 'translateX(1rem)',
                  padding: '1em'}" class="operationGridWrapper">
                    <div class="grid-item" v-for="unit in item.content.slice(9,item.content.length)" :key="unit.title">
                      <span class="linkSpan" :class="unit.selected === true?'selected':''"
                            @click="linkSpanClickHandler(unit.id,unit.key,item)">{{ unit.name }}</span>
                    </div>
                  </div>
                </div>
              </template>
            </a-dropdown>
          </div>
        </div>
      </div>
    </div>

    <!-- 过滤筛选框 -->
    <div style="margin-top: 2em" class="filter-order-wrapper">
      <div class="filter-order-item-wrapper" v-for="unit in filterOrderitems" :key="unit.key"
           @click="selectOrder(unit.key,unit.keyword)"
           :class="selectedFilterOrderItem === unit.key?'filter-order-selected':''">
        {{ unit.title }}
        <CaretUpOutlined v-if="unit.orderByAsc === true"/>
        <CaretDownOutlined v-if="unit.orderByAsc === false"/>
      </div>
      <div class="left-margin-20">
        <a-input-number :precision="2" v-model:value="merchandiseGetterParams.lowPrice" style="width: 40%" prefix="￥"
                        placeholder="低价" @focusout="merchandiseBetweenLowHigh"></a-input-number>
        -
        <a-input-number :precision="2" v-model:value="merchandiseGetterParams.highPrice" style="width: 40%" prefix="￥"
                        placeholder="高价" @focusout="merchandiseBetweenLowHigh"></a-input-number>
      </div>
      <div>共{{ queryData.count }}件商品</div>
    </div>
    <!-- 展示资源 -->
    <a-config-provider :locale="zhCN">
      <div class="margin-top-medium merchandiseListWrapper">
        <a-empty v-if="queryData.count === 0" class="margin-top-giant"></a-empty>
        <div v-else class="merchandiseGridWrapper">
          <a-card v-for="item in queryData.dataSource" class="custom-card-style"
                  @click="jumpToMerchandiseDetail(item.merchandiseId)">
            <template #cover>
              <img :alt="item.merchandiseName" :src="'/imgs/'+item.mainImg">
            </template>
            <a-typography-text>{{ item.merchandiseName }}</a-typography-text>
            <br>
            <a-typography-text>￥
              <span v-if="item.lowPrice === item.highPrice">{{ item.lowPrice }}</span>
              <span v-else>{{ item.lowPrice }} - {{ item.highPrice }}</span>
            </a-typography-text>
            <br>
            <a-typography-text :style="ellipsis?{width: 'calc(80%)'}:{}"
                               :ellipsis="ellipsis ? { tooltip: `商家名：${item.merchantName}` } : false"
                               :content="item.merchantName">

            </a-typography-text>
            <!-- 需要阻止点击事件向上冒泡 -->
            <a-typography-link style="width: calc(20%)"
                               @click.stop="callMerchant(item.belongs,store.state.userState.user.userId)">
              <a-tooltip placement="right">
                <template #title>
                  <span>联系商家</span>
                </template>
                <CommentOutlined/>
              </a-tooltip>
            </a-typography-link>
          </a-card>
        </div>
      </div>
    </a-config-provider>

    <!-- 展示分页 -->
    <div v-if="queryData.count > 0">

    </div>
  </div>
</template>

<style scoped>
.aside {
  display: inline-block;
  width: 15%;
  background-color: #F2F2F2;
  padding: 1em;
}

.width-full {
  width: 80%;
  margin-left: 10%;
}

.operation-area {
  display: inline-block;
  padding: 1em;
  width: calc(85%);
  position: relative;
}

.operationGridWrapper {
  display: grid;
  grid-template-columns: repeat(10, 1fr); /* 创建10列，每列占据可用空间的1份 */
  grid-gap: 10px;
}

.grid-item {
  text-align: center; /* 文本居中 */
}

.linkSpan {
  cursor: pointer;
  color: #2f2f2f;
}

.linkSpan:hover {
  color: #25A5F7;
}

.selected {
  color: #25A5F7 !important;
}

.merchandiseGridWrapper {
  display: grid;
  grid-template-columns: repeat(5, 1fr);
  grid-gap: 1em;
  place-items: center;
}

.filter-order-wrapper {
  width: 100%;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 1fr 4fr 1fr;
  place-items: center;
  background-color: #F2F2F2;
}

.filter-order-item-wrapper {
  padding: 5px;
  background-color: #FFFFFF;
  border: 1px solid #797979;
  border-radius: 2px;
  width: 100%;
  text-align: center;
}

.filter-order-item-wrapper:hover {
  cursor: pointer;
  background-color: #f2f2f2;
  color: #25A5F7;
}

.filter-order-selected {
  background-color: #f2f2f2;
  color: #25A5F7;
}

.merchandiseListWrapper {
  padding-top: 1em;
}

.custom-card-style:deep(.ant-card-cover) {
  padding: 0.5rem;
}

.custom-card-style:deep(.ant-card-body) {
  padding: 0.5rem;
}


</style>