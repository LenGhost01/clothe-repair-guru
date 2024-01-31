import {createRouter, createWebHashHistory, createWebHistory} from "vue-router";
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
        component: ()=> import("../views/ManagerSys.vue"),
        children: [
            // 嵌套路由
            {
                path: '/user_management',
                name: 'user_management',
                meta: {
                    requireAuth : true
                },
                component: ()=>import("../views/UserManagement.vue")
            },
            {
                path: '/category_message',
                name: 'category_message',
                meta: {
                    requireAuth : true
                },
                component: ()=>import("../views/CategoryMessage.vue")
            },
            {
                path: '/complaints_application',
                name: 'complaints_application',
                meta: {
                    requireAuth : true
                },
                component: ()=>import("../views/ComplaintsApplication.vue")
            },
            {
                path: '/material_message',
                name: 'material_message',
                meta: {
                    requireAuth : true
                },
                component: ()=>import("../views/MaterialMessage.vue")
            },
            {
                path: '/merchant_application',
                name: 'merchant_application',
                meta: {
                    requireAuth : true
                },
                component: ()=>import("../views/MerchantApplication.vue")
            },
            {
                path: '/merchant_message',
                name: 'merchant_message',
                meta: {
                    requireAuth : true
                },
                component: ()=>import("../views/MerchantMessage.vue")
            },
            {
                path: '/order_management',
                name: 'order_management',
                meta: {
                    requireAuth : true
                },
                component: ()=>import("../views/OrderManagement.vue")
            },

        ]
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

router.beforeEach( (to,from) => {
    //在进入页面前进行拦截，如果未登录，需要跳转登录界面 next函数在当前版本已经不被推荐使用
    // 需要密码的组件需要跳转登录界面
    if(to.meta.requireAuth) {
        console.log("路由跳转",to)
        //管理员已处于登录状态且目标页面不是登录界面，就跳转到指定页面
        if(sessionStorage.getItem("admin") !== null && to.name !== "manager_login"){

        }else{
            return {name : "manager_login"}
        }
    }
})

export default router