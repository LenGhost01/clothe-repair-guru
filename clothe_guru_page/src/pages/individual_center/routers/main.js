import {createRouter, createWebHashHistory, createWebHistory} from "vue-router";
let auth = false
const routes = [
    {
        path: '/',
        redirect: '/home'
    },
    {
        path: '/home',
        name: 'home',
        component: ()=>import("../views/HomePage.vue")
    },
    {
        path:'/message',
        name:'message',
        component: ()=>import("../views/MyMessage.vue")
    },
    {
        path:'/security',
        name:'security',
        component: ()=>import("../views/AccountSecurity.vue")
    },
    {
        path:'/records',
        name:'records',
        component: ()=>import("../views/MyRecords.vue")
    },
    {
        path:'/revoked',
        name:'revoked',
        component: ()=>import("../views/AccountRevoked.vue")
    },
    {
        path:'/security_check',
        name:'security_check',
        component: ()=>import("../views/SecurityCheck.vue"),
    },
    {
        path:'/upload',
        name:'/upload',
        component: ()=>import("../views/VideoUpload.vue")
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes, // `routes: routes` 的缩写
})

export default router