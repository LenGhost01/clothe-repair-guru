<script setup>
import {onMounted, onUnmounted, reactive, watch} from "vue";
import AMapLoader from "@amap/amap-jsapi-loader";
import {Result} from "ant-design-vue";

// 在组件中传入参数，参数为高德地图api中申请的安全码和key
const props = defineProps({
  ping: String,
  aMapEssential: {
    securityCode: String,
    key: String
  }
})

const mapData = reactive({
  selectedLocation: '',
  selectedAddress: '',
});

let map = null;

onMounted(() => {
  // 指定安全码
  window._AMapSecurityConfig = {
    securityJsCode: props.aMapEssential.securityCode,
  }

  AMapLoader.load({
    key: props.aMapEssential.key, // 申请好的Web端开发者Key，首次调用 load 时必填
    version: "2.0", // 指定要加载的 JSAPI 的版本，缺省时默认为 1.4.15
    plugins: ['AMap.PlaceSearch', 'AMap.Geocoder', 'AMap.Marker', 'AMap.Geolocation'], // 需要使用的的插件列表
  })
      .then((AMap) => {
        // 初始化map容器
        map = new AMap.Map("container", {
          // 设置地图容器id
          viewMode: "3D", // 是否为3D地图模式
          zoom: 13, // 初始化地图级别
          center: [116.397428, 39.90923], // 初始化地图中心点位置
          resizeEnable: true,
        });

        //自动定位
        map.plugin("AMap.Geolocation", () => {
          let geolocation = new AMap.Geolocation({
            enableHighAccuracy: true,//是否使用高精度定位，默认:true
            timeout: 10000,          //超过10秒后停止定位，默认：无穷大
            maximumAge: 0,           //定位结果缓存0毫秒，默认：0
            convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
            showButton: true,        //显示定位按钮，默认：true
            position: 'LB',    //定位按钮停靠位置，默认：'LB'，左下角
            offset: [30, 30],//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
            showMarker: false,        //定位成功后在定位到的位置显示点标记，默认：true
            showCircle: false,        //定位成功后用圆圈表示定位精度范围，默认：true
            panToLocation: true,     //定位成功后将定位到的位置作为地图中心点，默认：true
            zoomToAccuracy: true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
          });
          map.addControl(geolocation);
          geolocation.getCurrentPosition(function (status, result) {
            if (status == 'complete') {
              console.log(result)
            } else {
              console.error(result)
            }
          });
        })

        // 点击添加marker并获取坐标
        let geocoder = new AMap.Geocoder()
        let lnglat = "";
        let marker = new AMap.Marker();
        map.on("click", e => {
          let lng = e.lnglat.lng
          let lat = e.lnglat.lat
          let lnglat = [lng,lat]
          // 添加一个新标记
          marker.setIcon("https://a.amap.com/jsapi_demos/static/demo-center/icons/poi-marker-default.png")
          marker.setPosition(lnglat)
          map.add(marker)

          mapData.selectedAddress = geocoder.getAddress(lnglat, (status,result)=>{
            // 通过逆地理编址得到经纬度对应的位置信息
            if (status === 'complete' && result.info === 'OK') {
              mapData.selectedLocation = lnglat
              mapData.selectedAddress = result.regeocode.formattedAddress
            }
          })
        })

      })
      .catch((e) => {
        console.log(e);
      });
});
const emit = defineEmits(["putLocation"])
// 监听ping命令的变化，如有ping命令，则向父组件发送选中地址信息
watch(()=>props.ping,(value)=>{
  emit("putLocation",mapData)
},{deep:true})

onUnmounted(() => {
  map?.destroy();
});
</script>

<template>
  <div id="container"></div>
  <div class="input-card" style='width:40rem;'>
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

</style>