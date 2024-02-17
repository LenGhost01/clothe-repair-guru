import {createRouter, createWebHashHistory, createWebHistory} from "vue-router";
const routes = [
]

const router = createRouter({
    history: createWebHashHistory(),
    routes, // `routes: routes` 的缩写
})

export default router