import {createRouter, createWebHashHistory, createWebHistory} from "vue-router";
const routes = [
    // 导入已经写好的路由 或者 简单的可以直接在这里写
    {
        path: "/",
        redirect: "/overall"
    },
    {
        path: "/overall",
        name: "overall",
        component: ()=>import("../views/overall.vue")
    },
    {
        path: "/merchant",
        name: "merchant",
        component: ()=>import("../views/merchant.vue")
    },
    {
        path: "/merchandiseDisplay",
        name: "merchandiseDisplay",
        component: ()=>import("../views/merchandiseDisplay.vue")
    },
    {
        path: "/orderGenerate",
        name: "orderGenerate",
        component: ()=>import("../views/orderGenerate.vue")
    },

]

const router = createRouter({
    history: createWebHashHistory(),
    routes, // `routes: routes` 的缩写
})

export default router