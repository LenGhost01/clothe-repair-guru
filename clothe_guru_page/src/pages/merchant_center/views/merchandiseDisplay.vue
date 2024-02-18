<script setup>
import {h, onMounted, reactive, ref, toRaw} from "vue";
import {MinusOutlined, UploadOutlined} from "@ant-design/icons-vue"
import store from "/src/store/store.js";
import {message, Modal} from 'ant-design-vue';
import getBase64 from "/src/utils/getBase64.js";
import axios from "axios";
import ReadFile from "/src/utils/ReadFile.js";
import {v4 as uuidv4} from 'uuid'

const defaultGetMerchandisePath = "/requests/merchandise/getMerchandiseViewById"
const getMerchandiseParam = ref({
  page: 0,
  keyword: "",
  merchantId: new URLSearchParams(window.location.search).get("merchantId").toString()
})
const info = () => {
  Modal.info({
    title: '查看图片',
    okText: "确认",
    width: 'auto',
    content: h('div', {}, [
      h('img', {src: displayImageSrc.value, style: 'max-width:80vw;height:auto;'})
    ]),
    onOk() {
    },
  });
};
// 变量
const keyWord = ref("")
const queryData = ref({
  dataSource: [],
  count: 0
})
const pagination = ref({
  hideOnSinglePage: true,
  showSizeChanger: false,
  defaultPageSize: 20,
  total: 0
})
// 商品主图，商品编号，商品名称，价格(高位和低位放在一个框中)，评分，操作
const columns = ref([
  {
    title: "商品图片",
    dataIndex: "mainImg",
    key: "mainImg"
  },
  {
    title: "商品编号",
    dataIndex: "merchandiseId",
    key: "merchandiseId"
  },
  {
    title: "商品价格",
    key: "merchandisePrice"
  },
  {
    title: "好评率",
    dataIndex: "satisfaction",
    key: "satisfaction"
  },
  {
    title: "售出",
    dataIndex: "sales",
    key: "sales"
  },
  {
    title: "操作",
    key: "operation"
  }
])

const modalManage = reactive({
  isOpen: false,
  title: "",
  okText: "",
  cancelText: "",
  mode: ""
})
const currentImages = reactive({
  mainImg: "",
  subImg: [],
})
const selectedCategory = ref([])
const selectedMaterial = ref([])
const mainImg = ref([])
const subImg = ref([])
const displayImageSrc = ref("")
const merchandiseData = reactive({
  merchandiseName: "",
  lowPrice: 0.00,
  highPrice: 0.00,
  merchandiseDescription: "",
})
// 自定义函数
const searchHandle = () => {
  getMerchandiseParam.value.keyword = keyWord.value
  getMerchandiseParam.value.page = 0
  getMerchandise()
}

const addHandle = () => {
  modalManage.title = "添加商品"
  modalManage.okText = "提交"
  modalManage.cancelText = "取消"
  modalManage.mode = "insert"
  modalManage.isOpen = true
  modalManage.zIndex = 100
}

const cancelHandle = () => {
  // 清除所有数据
  clearData()
}

const okHandle = async (mode) => {
  if (mode === "insert") {

    let mainImage = mainImg.value[0]
    let subImages = subImg.value
    let formData = new FormData()
    // 封装主图片
    const img = mainImage.originFileObj
    const suffix = img.name.split('.').pop()
    const blob = new Blob([img], {type: img.type})
    formData.append('mainImage', blob, `IMG_${uuidv4()}.${suffix}`)

    // 封装子图片
    subImages.forEach(item => {
      item = toRaw(item)
      const img = item.originFileObj
      const suffix = img.name.split('.').pop()
      const blob = new Blob([img], {type: img.type})
      formData.append('subImages', blob, `IMG_${uuidv4()}.${suffix}`)
    })

    //封装其他json数据
    const requestData = {
      merchantId: new URLSearchParams(window.location.search).get("merchantId"),
      merchandiseName: merchandiseData.merchandiseName,
      merchandiseDescription: merchandiseData.merchandiseDescription,
      lowPrice: merchandiseData.lowPrice.toString(),
      highPrice: merchandiseData.highPrice.toString(),
      category: selectedCategory.value,
      material: selectedMaterial.value
    }
    formData.append("metaData", JSON.stringify(requestData))
    await axios.post("/requests/merchandise/addMerchandise", formData)
        .then(res => {
          message.success("上传成功", 2)
          // 清理数据
          clearData()
        })
        .catch(err => [
          message.warning("发生错误" + err.response.data, 2)
        ])
    // 再次获取对应的数据
    await getMerchandise()

    modalManage.isOpen = false
  } else if (mode === "update") {
    // 对上传的图片进行处理，区分老图和新图，主图不需要进行额外处理，副图需要区分老图数组和上传的新图文件数组
    // 过滤出老图数组
    const fileNameSet = new Set(subImg.value.map(item => item.name))
    let oldSubImg = currentImages.subImg.filter(item=> fileNameSet.has(item.split('/').pop()))
    let uploadSubImg = subImg.value.filter(item => item.uid > 0)

    // 使用formData传输数据
    let formData = new FormData
    if(mainImg.value[0].uid > 0){
      // 主图的uid 不为负数代表是新图，需要传输
      const img = mainImg.value[0].originFileObj
      const suffix = img.name.split('.').pop()
      const blob = new Blob([img], {type: img.type})
      formData.append('mainImage', blob, `image_${uuidv4()}.${suffix}`)
    }
    if(uploadSubImg.length > 0){
      uploadSubImg.forEach(item => {
        item = toRaw(item)
        const img = item.originFileObj
        const suffix = img.name.split('.').pop()
        const blob = new Blob([img], {type: img.type})
        formData.append('subImages', blob, `image_${uuidv4()}.${suffix}`)
      })
    }
    // 封装json数据
    const requestData = {
      merchandiseName: merchandiseData.merchandiseName,
      merchandiseDescription: merchandiseData.merchandiseDescription,
      lowPrice: merchandiseData.lowPrice.toString(),
      highPrice: merchandiseData.highPrice.toString(),
      category: selectedCategory.value,
      material: selectedMaterial.value,
      rating: merchandiseData.rating.toString(),
      merchandiseId : merchandiseData.merchandiseId,
      oldSubImg: oldSubImg
    }
    formData.append("metaData", JSON.stringify(requestData))

    await axios.post("/requests/merchandise/updateMerchandise",formData).then(res=>{
      message.success("修改成功")
      // 清理数据
      clearData()
    }).catch(err=>{
      console.error(err)
    })
    // 再次获取对应的数据
    await getMerchandise()

    modalManage.isOpen = false
  }
}
const changePage = (e) => {

}
const popupScroll = () => {

}

const beforeUpload = () => {
  return false
}

const clearData = ()=>{
  selectedCategory.value = []
  selectedMaterial.value = []
  mainImg.value = []
  subImg.value = []
  displayImageSrc.value = ""
  merchandiseData.highPrice = 0.00
  merchandiseData.lowPrice = 0.00
  merchandiseData.merchandiseDescription = ""
  merchandiseData.merchandiseName = ""
}
// 预览图片
const imgPreview = async (e) => {
  await handlePreview(e)
  info()
}

const handlePreview = async (file) => {
  if (!file.url && !file.preview) {
    file.preview = await getBase64(file.originFileObj);
  }
  displayImageSrc.value = file.preview || file.url
}

// 查看当前行
const displayCurrentColumn = (curColumn) => {
  // 初始化模态窗口数据
  modalManage.title = curColumn.merchandiseName
  modalManage.okText = "修改"
  modalManage.cancelText = "取消"
  modalManage.mode = "update"

  selectedCategory.value = curColumn.category
  selectedMaterial.value = curColumn.material

  currentImages.mainImg = curColumn.mainImg
  currentImages.subImg = JSON.parse(curColumn.subImg)

  merchandiseData.merchandiseName = curColumn.merchandiseName
  merchandiseData.merchandiseDescription = curColumn.merchandiseDescription
  merchandiseData.lowPrice = curColumn.lowPrice
  merchandiseData.highPrice = curColumn.highPrice
  merchandiseData.rating = curColumn.rating
  merchandiseData.sales = curColumn.sales
  merchandiseData.satisfactionRate = curColumn.satisfactionRate
  merchandiseData.merchandiseId = curColumn.merchandiseId

  // 将读取的图片转化为文件供upload组件读取,文件uid为-1，防止与正常提交的文件混淆
  ReadFile(`/imgs/${curColumn.mainImg}`).then(res => {
    mainImg.value.push(toRaw(res))
  })
  for (const item of JSON.parse(curColumn.subImg)) {
    ReadFile(`/imgs/${item}`).then(res => {
      subImg.value.push(toRaw(res))
    })
  }

  modalManage.isOpen = true
  modalManage.zIndex = 100

}


// 钩子函数
onMounted( async () => {
  await getMerchandise()
})

const getMerchandise = async ()=>{
  await axios.get(`${defaultGetMerchandisePath}?page=${getMerchandiseParam.value.page}&keyword=${getMerchandiseParam.value.keyword}` +
      `&merchantId=${getMerchandiseParam.value.merchantId}`)
      .then(res => {
        // 获取到对应的数据后，将数据的总个数和当前数据的vo存放到queryData中
        queryData.value.dataSource = res.data.merchandiseVoList
        queryData.value.count = res.data.count
        pagination.value.total = queryData.value.count
      }).catch(err => {
        message.warning("获取信息失败:" + err.response.data.toString())
      })
}
</script>

<template>
  <div class="container" style="padding: 2em;min-height: 80vh">
    <!-- 头部，包括关键字搜索，添加新商品部分 -->
    <div style="text-align: right">
      <a-input v-model:value.lazy="keyWord" placeholder="输入关键字搜索" style="width: 30%"
               class="right-margin-10"></a-input>
      <a-button @click="searchHandle" class="right-margin-10">搜索</a-button>
      <a-button @click="addHandle">添加新商品</a-button>
    </div>

    <!-- 表格展示部分，展示概览字段分别为商品主图，商品编号，商品名称，价格(高位和低位放在一个框中)，评分，操作 -->
    <a-table :columns="columns" :data-source="queryData.dataSource" class="margin-top-medium" :pagination="pagination"
             @change="changePage" bordered>
      <template #headerCell="{columns}"></template>
      <template #bodyCell="{ column, record, text }">
        <template v-if="column.key === 'mainImg'">
          <a-image :src='"/imgs/"+record.mainImg' style="height: 100px;width: auto"></a-image>
        </template>
        <template v-if="column.key === 'merchandisePrice'">
          {{ record.lowPrice }} - {{ record.highPrice }}
        </template>
        <template v-if="column.key === 'satisfaction'">
          <a-typography-text>{{ record.satisfactionRate * 100 }}%</a-typography-text>
        </template>
        <template v-if="column.key === 'sales'">
          <a-typography-text v-if="record.sales === null">无</a-typography-text>
          <a-typography-text v-else>{{ record.sales }}</a-typography-text>
        </template>
        <template v-if="column.key === 'operation'">
          <a-button type="link" @click="displayCurrentColumn(record)">查看所有</a-button>
          <a-button type="link" style="color: red">删除</a-button>
        </template>
      </template>
    </a-table>

    <a-modal v-model:open="modalManage.isOpen" :title="modalManage.title" :ok-text="modalManage.okText"
             :cancel-text="modalManage.cancelText" @cancel="cancelHandle" @ok="okHandle(modalManage.mode)" width="50vw"
             :z-index="modalManage.zIndex" style="top: 2rem">
      <a-row :gutter="[16,16]">
        <a-col :span="24">
          <a-typography-title :level="5" class="title_width">商品名称：</a-typography-title>
          <a-input class="input_width" v-model:value.lazy="merchandiseData.merchandiseName"
                   placeholder="请输入商品名称..."></a-input>
        </a-col>
        <a-col :span="24">
          <a-typography-title :level="5" class="title_width">商品价格：</a-typography-title>
          <a-input-number :controls="false" v-model:value.lazy="merchandiseData.lowPrice" :precision="2" prefix="￥"
                          style="width: 14em" placeholder="低价"></a-input-number>
          <MinusOutlined/>
          <a-input-number :controls="false" v-model:value.lazy="merchandiseData.highPrice" :precision="2" prefix="￥"
                          style="width: 14em" placeholder="高价"></a-input-number>
        </a-col>
        <a-col :span="24" v-if="modalManage.mode === 'update'">
          <a-typography-title :level="5" class="title_width">商品优惠：</a-typography-title>
          <a-input-number v-model:value.lazy="merchandiseData.rating" :min="0.00" :max="1.00" :step="0.05" :precision="2"></a-input-number>
        </a-col>
        <a-col :span="24" v-if="modalManage.mode === 'update'">
          <a-typography-title :level="5" class="title_width">售卖数量：{{ merchandiseData.sales }}</a-typography-title>
        </a-col>
        <a-col :span="24">
          <a-typography-title :level="5">商品描述：</a-typography-title>
          <a-textarea v-model:value="merchandiseData.merchandiseDescription" style="height: 8rem"
                      placeholder="请输入商品描述..." show-count :maxlength="500"></a-textarea>
        </a-col>
        <a-col :span="24">
          <a-typography-title :level="5">类别：</a-typography-title>
          <a-select
              v-model:value="selectedCategory"
              :options="store.state.merchantStore.category.map(item=>({value:item.categoryId,label:item.categoryName}))"
              mode="multiple"
              size="middle"
              placeholder="请选择类别..."
              style="width: 50%"
              @popupScroll="popupScroll"
          ></a-select>
        </a-col>
        <a-col :span="24">
          <a-typography-title :level="5">材质：</a-typography-title>
          <a-select
              v-model:value="selectedMaterial"
              :options="store.state.merchantStore.material.map(item=>({value:item.materialId,label:item.materialName}))"
              mode="multiple"
              size="middle"
              placeholder="请选择材质..."
              style="width: 50%"
              @popupScroll="popupScroll"
          ></a-select>
        </a-col>
        <a-col :span="24">
          <a-typography-title :level="5">主要图片：</a-typography-title>
          <a-upload
              v-model:file-list="mainImg"
              :max-count="1"
              :before-upload="beforeUpload"
              list-type="picture"
              class="upload-list-inline"
              @preview="imgPreview"
          >
            <a-button>
              <upload-outlined></upload-outlined>
              上传主图
            </a-button>
          </a-upload>
        </a-col>
        <a-col :span="24">
          <a-typography-title :level="5">其他图片：</a-typography-title>
          <a-upload
              v-model:file-list="subImg"
              :max-count="4"
              :before-upload="beforeUpload"
              list-type="picture"
              class="upload-list-inline"
              @preview="imgPreview"
          >
            <a-button>
              <upload-outlined></upload-outlined>
              上传副图
            </a-button>
          </a-upload>
        </a-col>
      </a-row>
    </a-modal>
  </div>
</template>

<style scoped>
.title_width {
  display: inline-block;
  width: 6em;
}

.input_width {
  display: inline-block;
  width: calc(100% - 7em);
}
</style>