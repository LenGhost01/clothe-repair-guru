import {createApp} from 'vue'
import Admin from './Admin.vue'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css'
import 'animate.css'
// vue-router 路由
import Router from './routers/main.js'
import '/src/style.css'


createApp(Admin)
    .use(Antd)
    .use(Router)
    .mount('#app')
