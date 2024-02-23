import {createApp} from 'vue'
import ChatRoomView from './ChatRoomView.vue'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css'
import 'animate.css'
// vue-router 路由
import Router from './routers/main.js'
import '/src/style.css'
import Store from "/src/store/store.js"
import ckeditor5 from "@ckeditor/ckeditor5-vue";


createApp(ChatRoomView)
    .use(Antd)
    .use(Router)
    .use(Store)
    .use(ckeditor5)
    .mount('#app')
