import {createApp} from 'vue'
import Individual from './Individual.vue'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css'
import 'animate.css'
// vue-router 路由
import Router from './routers/main.js'
import '/src/style.css'
import './demo-center.css'

//vuex仓库
import store from "/src/store/store.js"

createApp(Individual)
    .use(Antd)
    .use(Router)
    .use(store)
    .mount('#app')
