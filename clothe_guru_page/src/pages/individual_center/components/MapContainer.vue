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
  ping: String,
})
let emits = defineEmits(["putLocation"])
watch(() => props.ping, (value, oldValue) => {
  emits("putLocation", mapData)
}, {deep: true})
onMounted(() => {
  // 指定安全密钥
  window._AMapSecurityConfig = {
    securityJsCode: 'ed605ce1fc709611c168142c381174a7',
  }
  AMapLoader.load({
    key: "322be3b0259e5f68d63531b3d896edbc", // 申请好的Web端开发者Key，首次调用 load 时必填
    version: "2.0", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
    plugins: ['AMap.PlaceSearch', 'AMap.Geocoder', 'AMap.Marker', 'AMap.Geolocation']
  }).then((AMap) => {
    const mapInstance = new AMap.Map('container', {
      viewMode: '2D',
      zoom: 13,
      resizeEnable: true,
      // layers: [new AMap.TileLayer.Satellite(), new AMap.TileLayer.RoadNet()],
    });

    mapInstance.plugin('AMap.Geolocation', function () {
      let geolocation = new AMap.Geolocation({
        enableHighAccuracy: true,//是否使用高精度定位，默认:true
        timeout: 10000,          //超过10秒后停止定位，默认：无穷大
        maximumAge: 0,           //定位结果缓存0毫秒，默认：0
        convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
        showButton: true,        //显示定位按钮，默认：true
        buttonPosition: 'LB',    //定位按钮停靠位置，默认：'LB'，左下角
        buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
        showMarker: false,        //定位成功后在定位到的位置显示点标记，默认：true
        showCircle: false,        //定位成功后用圆圈表示定位精度范围，默认：true
        panToLocation: true,     //定位成功后将定位到的位置作为地图中心点，默认：true
        zoomToAccuracy: true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
      });
      mapInstance.addControl(geolocation);
      geolocation.getCurrentPosition();
    })
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
      <div class="input-item-prepend"><span class="input-item-text">地址</span></div>
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


.btn {
  width: 6em;
}
</style>