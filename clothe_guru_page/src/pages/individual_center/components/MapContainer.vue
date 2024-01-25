<script setup>
import AMapLoader from '@amap/amap-jsapi-loader';
import {onMounted, onUnmounted, reactive, watch} from "vue";


let map = '';
const mapData = reactive({
  map: {},
  keyword: '',
  selectedLocation: {},
  selectedAddress: '',
});
let props = defineProps({
  ping: String
})
let emits = defineEmits(["putLocation"])
watch(()=>props.ping,(value,oldValue)=>{
  emits("putLocation",mapData)
},{deep: true})
onMounted(() => {
  window._AMapSecurityConfig = {
    securityJsCode: 'ed605ce1fc709611c168142c381174a7',
  }
  AMapLoader.load({
    key: "322be3b0259e5f68d63531b3d896edbc", // 申请好的Web端开发者Key，首次调用 load 时必填
    version: "2.0", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
    plugins: ['AMap.PlaceSearch', 'AMap.Geocoder','AMap.Marker'], // 需要使用的的插件列表，如比例尺'AMap.Scale'等
  }).then((AMap) => {
    const mapInstance = new AMap.Map('container', {
      viewMode: '2D',
      zoom: 11,
      // layers: [new AMap.TileLayer.Satellite(), new AMap.TileLayer.RoadNet()],
    });

    let geocoder = new AMap.Geocoder()
    let lnglat = '';
    let marker = new AMap.Marker();
    mapInstance.on('click', (e) => {
      let lng = e.lnglat.lng
      let lat = e.lnglat.lat
      mapData.selectedLocation = [lng, lat]


      // 通过逆地理编码将经纬度转化为具体地址 需要指定安全码，否则会报错
      geocoder.getAddress(mapData.selectedLocation, (status, result) => {
        if (status === 'complete' && result.regeocode) {
          mapData.selectedAddress = result.regeocode.formattedAddress;
        } else {
          console.log("查询失败")
        }
      });
      // 给点击的位置添加一个标记
      marker.setIcon("https://a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png")
      marker.setPosition(mapData.selectedLocation)
      mapInstance.add(marker)
    });


    mapData.map = mapInstance;

  }).catch(e => {
    console.log(e)
  })

})


onUnmounted(() => {
  mapData.map.destroy();
});
</script>

<template>
  <div id="container"></div>
  <div class="input-card" style='width:28rem;'>
    <label style='color:grey'>选择的地址信息</label>
    <div class="input-item">
      <div class="input-item-prepend"><span class="input-item-text" >地址</span></div>
      <input id='address' type="text" :value="mapData.selectedAddress" disabled>
    </div>
  </div>

</template>

<style scoped>
#container {
  padding: 0px;
  margin: 0px;
  width: 100%;
  height: 100%;
}


.btn{
  width: 6em;
}
</style>