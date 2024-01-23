<script setup>
import {animate_controller, animate_controller_double_stage} from "../utils/AnimateSeletor.js";
import {onMounted, reactive, ref} from "vue";
import {
  AppstoreAddOutlined,
  FieldTimeOutlined,
  NotificationOutlined,
  PoweroffOutlined,
  RightOutlined,
  SisternodeOutlined,
  StarOutlined,
  UserOutlined
} from "@ant-design/icons-vue";

onMounted(() => {
  user_display_size.width = avatar.value.size * 9
  user_display_size.height = avatar.value.size * 12
})
const avatar = ref(null)
const user_display_size = reactive({
  width: 0,
  height: 0
})
const font_color_list = reactive({
  selected: '#1677FF',
  unselect: '#000000'
})
const title_img_click = () => {
  console.log('title_img_click')
}
const items = reactive([
  {
    title: '衣物',
    key: 'clothes',
  },
  {
    title: '店铺',
    key: 'outlets',
  },
  {
    title: '服务',
    key: 'services',
  }
])
const handleMenuSelector = (item) => {
  bind_item.value = item.title
}
const change_font_color = (event, color) => {
  event.target.style.color = color
}
let bind_item = ref(items[0].title)
let find = reactive({
  key_word: 'clothes',
  find_content: ''
})
let onSearch = () => {
  console.log('search')
}
let avatar_animate = ref('')
const animate_list = ref(['avatar_magnify', 'avatar_shrink', 'user_display_show', 'user_display_hidden'])
let header_image = ref('')
let is_login = ref(false)
let show_user_display = ref(false)
let login = () => {
  alert('login')
}
let register = () => {
  alert('register')
}
let display_user_panel = ref('')
let font_color = ref(font_color_list.unselect)
let display_sub_user_panel = ref('')

</script>

<template>
  <div class="z-index-4">
    <a-row>
      <a-col :span="3">
        <img width="50" src="" @click="title_img_click">
      </a-col>
      <a-col :span="14">
        <div class="vertical_center">
          <a-input v-model="find.find_content">
            <template #addonBefore>
              <a-select v-model:value="bind_item" size="large">
                <a-select-option v-for="item in items" :key="item.key" @click="handleMenuSelector(item)">
                  {{ item.title }}
                </a-select-option>
              </a-select>
            </template>
            <template #suffix>
              <a-button size="middle" type="primary" @click="onSearch">搜索</a-button>
            </template>
          </a-input>
        </div>
      </a-col>
      <a-col :span="3">
        <a-row class="vertical_center align-center">
          <a-col :span="24">
            <div class="inline_block"
                 @mouseenter="is_login===true?(display_user_panel=animate_list[2],avatar_animate=animate_list[0]):display_user_panel=animate_list[2]"
                 @mouseleave="is_login===true?(display_user_panel=animate_list[3],avatar_animate=animate_list[1]):display_user_panel=animate_list[3]"
            >
              <!-- 非登录状态下展示框-->
              <div
                  class="user_display"
                  :class="display_user_panel"
                  v-if="!is_login"
                  :style="
                {
                'width':user_display_size.width+'px',
                'height':user_display_size.height/1.6+'px',
                'left':-user_display_size.width/4+'px',
                }">
                <div class="content">
                  <p class="nav-font-middle select_forbidden"><span>登陆后你可以:</span></p>
                  <a-row class="align-center nav-font-middle select_forbidden" style="margin: 10% 0">
                    <a-col :span="12"><span>待定内容1</span></a-col>
                    <a-col :span="12"><span>待定内容2</span></a-col>
                  </a-row>
                  <a-row class="align-center nav-font-middle select_forbidden" style="margin: 10% 0">
                    <a-col :span="12"><span>待定内容3</span></a-col>
                    <a-col :span="12"><span>待定内容4</span></a-col>
                  </a-row>
                  <a-button type="primary" size="large" block @click="login">立即登录</a-button>
                  <a-row class="align-center nav-font-middle" style="margin: 5% 0">
                    <a-col :span="24">
                      <span class="select_forbidden">首次使用？</span>
                      <span style="color: #4096FF" @click="register">点击注册</span>
                    </a-col>
                  </a-row>
                </div>
              </div>
              <!-- 登录状态下展示框-->
              <div class="user_display"
                   :class="display_user_panel"
                   v-if="is_login"
                   :style="
               {
                'width':user_display_size.width+'px',
                'height':user_display_size.height+'px',
                'left':-user_display_size.width/4+'px',
                'margin-top':25+'%'
                }">
                <div class="content" style="padding-top: 10%">
                  <a-divider/>
                  <a-row class="align-center">
                    <!--todo 填入关注/粉丝/动态的数目 改造成v-for形式-->
                    <a-col :span="8" class="cursor_pointer">
                      <div
                          @mouseenter="change_font_color($event,font_color_list.selected)"
                          @mouseleave="change_font_color($event,font_color_list.unselect)"
                          @click=""
                          :style="{
                               color: font_color,
                             }"
                      >
                        <p class="select_forbidden">35</p>
                        <p class="select_forbidden">关注</p>
                      </div>

                    </a-col>
                    <a-col :span="8" class="cursor_pointer">
                      <div
                          @mouseenter="change_font_color($event,font_color_list.selected)"
                          @mouseleave="change_font_color($event,font_color_list.unselect)"
                          @click=""
                          :style="{
                               color: font_color,
                             }"
                      >
                        <p>127</p>
                        <p>粉丝</p>
                      </div>
                    </a-col>
                    <a-col :span="8" class="cursor_pointer">
                      <div
                          @mouseenter="change_font_color($event,font_color_list.selected)"
                          @mouseleave="change_font_color($event,font_color_list.unselect)"
                          @click=""
                          :style="{
                               color: font_color,
                             }"
                      >
                        <p>523</p>
                        <p>动态</p>
                      </div>
                    </a-col>
                  </a-row>
                  <a-button @mouseenter=""
                            @mouseleave=""
                            class="align-left" type="text" size="large" block>
                    <span><UserOutlined/> 个人中心</span>

                  </a-button>
                  <a-button @mouseenter=""
                            @mouseleave=""
                            class="align-left" type="text" size="large" block>
                    <span><AppstoreAddOutlined/> 商品管理</span>
                  </a-button>
                  <a-button @mouseenter="display_sub_user_panel=animate_list[2]"
                            @mouseleave="display_sub_user_panel=animate_list[3]"
                            class="align-left" type="text" size="large" block>
                              <span>
                                <SisternodeOutlined/>
                                更多服务
                                <span :style="{
                                 'margin-left': 80+'%',
                                }"><RightOutlined/></span>
                              </span>
                    <div class="user_display"
                         :class="display_sub_user_panel"
                         :style="{
                                'width': 100 + '%',
                                'height': 380 + '%',
                                'top':0,
                                'left':90+'%',
                           }">
                      <div :style="{margin:10+'px'}">
                        <a-button @mouseenter=""
                                  @mouseleave=""
                                  class="align-left" type="text" size="large" block>
                          <span><UserOutlined/> 快递查询</span>
                        </a-button>
                        <br>
                        <a-button @mouseenter=""
                                  @mouseleave=""
                                  class="align-left" type="text" size="large" block>
                          <span><UserOutlined/> 订单中心</span>

                        </a-button>
                        <br>
                        <a-button @mouseenter=""
                                  @mouseleave=""
                                  class="align-left" type="text" size="large" block>
                          <span><UserOutlined/> 还没想好</span>

                        </a-button>
                      </div>
                    </div>
                  </a-button>
                  <a-divider/>
                  <a-button @mouseenter=""
                            @mouseleave=""
                            class="align-left" type="text" size="large" block>
                    <span><PoweroffOutlined/> 退出登录</span>
                  </a-button>
                </div>
              </div>
              <!-- 头像展示框-->
              <div
                  :class="avatar_animate"
                  :style="{
                  margin: 30+'%',
                  transform: 'translateX(-30%)',
                    }">
                <a-avatar ref="avatar" :size="32">
                  <template #icon>
                    <img :src="header_image">
                  </template>
                </a-avatar>
              </div>
            </div>
          </a-col>
        </a-row>
      </a-col>
      <!--动态-->
      <a-col :span="1"
             @mouseenter=
                 "animate_controller_double_stage($event.target.childNodes[1].childNodes[3],animate_list[2],animate_list[3])+
                    animate_controller($event.target.childNodes[1].childNodes[0],'icon_bounce_animate',1)"
             @mouseleave=
                 "animate_controller_double_stage($event.target.childNodes[1].childNodes[3],animate_list[3],animate_list[2])+
                    animate_controller($event.target.childNodes[1].childNodes[0],'icon_bounce_animate',0)"
      >
        <div class="vertical_center align-center">
          <NotificationOutlined style="font-size: 125%"/>
          <br>
          <span class="select_forbidden">动态</span>
          <div class="user_display"
               :style="{
              'width':user_display_size.width+'px',
              'height':user_display_size.height+'px',
              'left' : -125+'%',
              'margin-top':25+'%'
            }">
            <!-- todo 动态页面中获取个人动态并展示 -->
          </div>
        </div>
      </a-col>
      <!--收藏-->
      <a-col :span="1"
             @mouseenter=
                 "animate_controller_double_stage($event.target.childNodes[1].childNodes[3],animate_list[2],animate_list[3])+
                    animate_controller($event.target.childNodes[1].childNodes[0],'icon_bounce_animate',1)"
             @mouseleave=
                 "animate_controller_double_stage($event.target.childNodes[1].childNodes[3],animate_list[3],animate_list[2])+
                    animate_controller($event.target.childNodes[1].childNodes[0],'icon_bounce_animate',0)"
      >
        <div class="vertical_center align-center">
          <StarOutlined style="font-size: 125%"/>
          <br>
          <span class="select_forbidden">收藏</span>
          <div class="user_display"
               :style="{
              'width':user_display_size.width+'px',
              'height':user_display_size.height+'px',
              'left' : -250+'%',
              'margin-top':25+'%'
            }">
            <!-- todo 收藏页面中显示用户收藏信息并展示 -->
          </div>
        </div>
      </a-col>
      <!--历史-->
      <a-col :span="1"
             @mouseenter=
                 "animate_controller_double_stage($event.target.childNodes[1].childNodes[3],animate_list[2],animate_list[3])+
                    animate_controller($event.target.childNodes[1].childNodes[0],'icon_bounce_animate',1)"
             @mouseleave=
                 "animate_controller_double_stage($event.target.childNodes[1].childNodes[3],animate_list[3],animate_list[2])+
                    animate_controller($event.target.childNodes[1].childNodes[0],'icon_bounce_animate',0)"
      >
        <div class="vertical_center align-center">
          <FieldTimeOutlined style="font-size: 125%"/>
          <br>
          <span class="select_forbidden">历史</span>
          <div class="user_display"
               :style="{
              'width':user_display_size.width+'px',
              'height':user_display_size.height+'px',
              'left' : -325+'%',
              'margin-top':25+'%'
            }">
            <!-- todo 动态页面中获取个人访问历史并展示 -->
          </div>
        </div>
      </a-col>

    </a-row>
  </div>

</template>

<style scoped>
.inline_block {
  display: inline-block;
}

@keyframes avatar_animate_magnify {
  from {
    transform: scale(1);
  }
  to {
    transform: scale(4) translate(-50%, 40%);
    margin-bottom: 1px;

  }
}

@keyframes avatar_animate_shrink {
  from {
    transform: scale(4) translate(-50%, 40%);
    margin-bottom: 1px;
  }
  to {
    transform: scale(1);
  }
}

@keyframes icon_animate_bounce {
  50% {
    transform: translateY(-50%);
  }
}

.avatar_magnify {
  animation: avatar_animate_magnify 0.5s;
  animation-fill-mode: forwards;
}

.avatar_shrink {
  animation: avatar_animate_shrink 0.5s;
  animation-fill-mode: forwards;
}

.user_display_show {
  animation: fadeIn 0.5s;
  animation-fill-mode: forwards;
  visibility: visible !important;
  opacity: 1;
}

.user_display_hidden {
  animation: fadeOut 0.5s;
  animation-fill-mode: forwards;
  visibility: hidden !important;
  opacity: 0;
}

.icon_bounce_animate {
  animation: icon_animate_bounce 0.3s;

}

.user_display {
  display: inline-block;
  position: absolute;
  visibility: hidden;
  opacity: 0;
  filter: Alpha(opacity=0);
  border: #f2f2f2 solid 2px;
  background: white;
  border-radius: 5%;
  top: 100%;
}

.user_display .content {
  margin: 10%;
}

.cursor_pointer {
  cursor: pointer;
}


</style>