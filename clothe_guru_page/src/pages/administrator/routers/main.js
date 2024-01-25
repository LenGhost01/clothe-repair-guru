import {createRouter, createWebHashHistory, createWebHistory} from "vue-router";
let auth = false
const routes = [
    // 导入已经写好的路由 或者 简单的可以直接在这里写
    {
        path :'/',
        redirect: '/manager',
    },
    {
        path :'/manager',
        name: 'manager',
        meta: {
          requireAuth : true
        },
        component: ()=> import("../views/ManagerSys.vue")
    },
    {
        path: '/manager_login',
        name: 'manager_login',
        meta: {
            requireAuth : false
        },
        component: ()=>import("../views/ManagerLogin.vue")
    }
]

const router = createRouter({
    history: createWebHashHistory(),
    routes, // `routes: routes` 的缩写
})

router.beforeEach( (to,from,next) => {
    //在进入页面前进行拦截，如果未登录，需要跳转登录界面
    if(to.meta.requireAuth){
        //todo 根据当前的登录账号的token判断是否为管理员账号或是否为登录状态 auth为测试用
        if(auth === true){
            next();
        }else{
            next({name:'manager_login'})
        }
    }else{
        next()
    }
})

export default router