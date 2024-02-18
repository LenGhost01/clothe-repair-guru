import {createStore} from "vuex";
import merchantStore from "./modules/merchantStore.js";
import merchandiseDetailStore from "./modules/merchandiseDetailStore.js";

export default createStore({
    state() {
        return {
            userState: {
                isLogin: false,
                user: {}
            },

        }
    },
    mutations: {
        // 负责更新对象
        UPDATE_USER_STATE(status, value) {
            this.state.userState.user = value
            this.state.userState.isLogin = true


        },
        CLEAR_USER_STATE(status, value) {
            this.state.userState.user = {}
            this.state.userState.isLogin = false
        },
        UPDATE_USER_ADDRESS(status, value){
            console.log(value)
            this.state.userState.user.receiver = value
        }
    },
    actions: {
        // 一个负责执行某个行为的对象，负责执行业务逻辑或者发送ajax请求 处理完毕后交给mutation处理
        updateUserState(context, value) {
            if (value.isLogin === true) {
                context.commit('UPDATE_USER_STATE', value.user)
            }
        },
        clearUserState(context, value) {
            context.commit("CLEAR_USER_STATE")
        },
        updateUserAddress(context,value){
            context.commit("UPDATE_USER_ADDRESS",value)
        }
    },
    modules: {merchantStore,merchandiseDetailStore},

})