import {createApp} from 'vue'
import '/src/style.css'
import App from './App.vue'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css'
import 'animate.css'
// 轮播图插件
import 'swiper/css/bundle'
// vue-router 路由
import Router from './router/main.js'



createApp(App)
    .use(Antd)
    .use(Router)
    .mount('#app')
