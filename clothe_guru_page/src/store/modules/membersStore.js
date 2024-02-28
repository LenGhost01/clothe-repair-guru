export default {
    namespaced: true, // 使用命名空间
    state() {
        return {
            // 模块的初始状态
            privateChatMember: [],
            userCorrelationMember: [],
        };
    },
    mutations: {
        ADD_PRIVATE_CHAT_MEMBER(status,value){
            status.privateChatMember.push(...value)
        },
        ADD_USER_CORRELATION_MEMBER(status,value){
            status.userCorrelationMember.push(...value)
        },
        UPDATE_MEMBER_STATE(status,value){
            status.privateChatMember.map(item=>{
                if(item.userId === value.userId){
                    item.state = value.state
                }
            })
            status.userCorrelationMember.map(item=>{
                if(item.userId === value.userId){
                    item.state = value.state
                }
            })
        }
    },
    actions: {
        // 更新成员登录状态
        updateMembersState(context,value){
            context.commit("UPDATE_MEMBER_STATE",value)
        },
        // 更新成员
        updatePrivateChatMember(context,value){
            if(Array.isArray(value) && value.length>0){
                context.commit("ADD_PRIVATE_CHAT_MEMBER",value)
            }
        },
        updateUserCorrelationMember(context,value){
            if(Array.isArray(value) && value.length>0){
                context.commit("ADD_USER_CORRELATION_MEMBER",value)
            }

        }
    },
    getters: {
        // 从状态派生的值
    },
}