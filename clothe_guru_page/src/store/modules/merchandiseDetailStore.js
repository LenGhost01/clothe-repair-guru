export default {
    namespaced: true, // 使用命名空间
    state() {
        return {
            // 模块的初始状态
            merchandise: {},
            category: new Map(),
            material: new Map(),
        };
    },
    mutations: {
        // 状态变更函数
        UPDATE_MERCHANDISE_STATE(status, value) {
            status.merchandise = value
        },
        UPDATE_CATEGORY(status, value) {
            value.forEach(item=>{
                status.category.set(item.categoryId,item)
            })
            console.log(status.category)
        },
        UPDATE_MATERIAL(status, value) {
            value.forEach(item=>{
                status.material.set(item.materialId,item)
            })
            console.log(status.material)
        },
    },
    actions: {
        // 异步操作
        updateMerchandiseState(context, value) {
            value.subImg = JSON.parse(value.subImg)
            context.commit("UPDATE_MERCHANDISE_STATE", value)
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