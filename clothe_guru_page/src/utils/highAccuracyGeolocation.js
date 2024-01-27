import AMap from "AMap"
const getLocation = () => {
    // eslint-disable-next-line no-undef
    AMap.plugin('AMap.Geolocation', function () {
        var geolocation = new AMap.Geolocation({
            // 是否使用高精度定位，默认：true
            enableHighAccuracy: true,
            // 设置定位超时时间，默认：无穷大
            timeout: 10000
        })
        //进行IP城市查询
        geolocation.getCityInfo(function (status, result) {
            if (status === 'complete') {
                console.log(result, 'Ip获取的')
            } else {
                onError(result)
            }
        })
        //获取用户当前的精确位置信息(经纬度)
        geolocation.getCurrentPosition(function (status, result) {
            if (status === 'complete') {
                console.log(result)
            } else {
                onError(result)
            }
        })
    })

    //定位错误，使用另一方式定位
    function onError (_data) {
        console.log('🚀 ~ file: AboutView.vue ~ line 72 ~ onError ~ _data', _data)
        // getLngLatLocation()
    }
}

export default