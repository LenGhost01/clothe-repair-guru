const chat_room_route =
    {
        path: '/contact',
        redirect: '/char_room',
        name: 'contact',
        children: [
            {
                path: '/chat_room',
                name: 'chat_room',
                components: {
                    chatRoomPage: () => import('/src/components/chatroomcomp/ChatRoomPeerToPeer.vue')
                }
            },
            // {
            //     path: 'channel/:channelId',
            //     name: 'channel',
            //     components: {
            //         chatRoomPage: () => import('../pages/ChatRoomChannel.vue')
            //     }
            // },
            // {
            //     path: 'add_channel',
            //     name: 'add_channel',
            //     components: {
            //         chatRoomPage: () => import('../pages/ChatRoomAddChannel.vue')
            //     }
            // },
            // {
            //     path: 'find_channel',
            //     name: 'find_channel',
            //     components: {
            //         chatRoomPage: () => import('../pages/ChatRoomFindChannel.vue')
            //     }
            // }
        ]
    }

export default chat_room_route