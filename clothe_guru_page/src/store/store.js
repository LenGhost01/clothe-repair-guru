import {createStore} from "vuex";

export default createStore({
    state() {
        return {
            userState: {
                isLogin: false,
                user: {}
            }
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
        }
    },
    modules: {}
})