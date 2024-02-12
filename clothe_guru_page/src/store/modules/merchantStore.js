
export default {
    namespaced: true, // 使用命名空间
    state() {
        return {
            // 模块的初始状态
            merchant: {},
            category: [],
            material: [],
        };
    },
    mutations: {
        // 状态变更函数
        UPDATE_USER_STATE(status, value) {
            status.merchant = value
        },

        CLEAR_USER_STATE(status, value) {
            status.merchant = {}
        },
        UPDATE_CATEGORY(status, value) {
            status.category = value
        },
        UPDATE_MATERIAL(status, value) {
            status.material = value
        },

    },
    actions: {
        // 异步操作
        updateMerchantState(context,value){
            value.certification = JSON.parse(value.certification)
            context.commit("UPDATE_USER_STATE",value)
        },
        clearMerchantState(context,value){
            context.commit("CLEAR_USER_STATE")
        },
        updateCategory(context,value){
            context.commit("UPDATE_CATEGORY",value)
        },
        updateMaterial(context,value){
            context.commit("UPDATE_MATERIAL",value)
        },
    },
    getters: {
        // 从状态派生的值
    },
};
