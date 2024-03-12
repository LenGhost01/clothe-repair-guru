export default {
    namespaced: true, // 使用命名空间
    state() {
        return {
            // 模块的初始状态
            senderMessage:[],
            receivedMessage:[],
        };
    },
    mutations: {
        // 状态变更函数
        ADD_SENDER_MESSAGE(status,value){
            status.senderMessage.push(value)
        },
        ADD_RECEIVED_MESSAGE(status,value){
            status.receivedMessage.push(value)
        },
        INIT_MESSAGE(status,value,receiver){
            status.receivedMessage.push(...value.filter(item=>item.receiver===receiver))
            status.senderMessage.push(...value.filter(item=>item.sender===receiver))
        }
    },
    actions: {
        // 异步操作
        addSenderMessage(context,value){
            context.commit("ADD_SENDER_MESSAGE",value)
        },
        addReceivedMessage(context,value){
            context.commit("ADD_RECEIVED_MESSAGE",value)
        },
        initMessage(context,value,receiver){
            context.commit("INIT_MESSAGE",value??[],receiver)
        }
    },
    getters: {
        // 从状态派生的值

    },
};