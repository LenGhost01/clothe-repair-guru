import {createApp} from 'vue'
import MerchandiseDetail from './MerchandiseDetail.vue'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css'
import 'animate.css'
// vue-router 路由
import Router from './routers/main.js'
import '/src/style.css'
import Store from "/src/store/store.js"


createApp(MerchandiseDetail)
    .use(Antd)
    .use(Router)
    .use(Store)
    .mount('#app')
