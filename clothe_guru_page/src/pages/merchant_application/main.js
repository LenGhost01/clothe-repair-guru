import {createApp} from 'vue'
import '/src/style.css'
import App from './App.vue'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/reset.css'
import '/src/demo-center.css'



createApp(App)
    .use(Antd)
    .mount('#app')
