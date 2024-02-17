import {createRouter, createWebHashHistory, createWebHistory} from "vue-router";
import chat_room_route from "./chat_room_route.js";

const routes = [
    chat_room_route,
    {
        path: "/",
        redirect: "/home"
    },
    {
        path: "/home",
        name: "home",
        component: ()=>import("../views/HomePage.vue")
    },
    {
        path: '/merchandise-list',
        name: "merchandiseList",
        component: ()=> import("../views/MerchandiseList.vue")
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes, // `routes: routes` 的缩写
})

export default router