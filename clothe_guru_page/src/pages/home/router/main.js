import {createRouter, createWebHashHistory, createWebHistory} from "vue-router";
import chat_room_route from "./chat_room_route.js";

const routes = [
    chat_room_route,
]

const router = createRouter({
    history: createWebHistory(),
    routes, // `routes: routes` 的缩写
})

export default router