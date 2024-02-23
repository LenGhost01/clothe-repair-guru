<template>
  <div class="sub-container" style="padding: 1.5em 0 0 1.5em">
    <UserAddOutlined class="icon"/>
    <span style="margin-left: 5%" class="nav-font-middle">申请 - 3</span>
    <div id="user-content" class="user-content">
      <div class="scroll-container scrollbar">
        <a-row>
          <a-col :span="6" v-for="i in 128">
            <div class="personal">
              <div class="personal-display">
                <a-avatar src="/imgs/avatar/noise_change.png?width=36"></a-avatar>
                <span style="margin-left: 5%">用户名</span>
              </div>
              <div class="apply">
                <div class="apply-button">
                  <a-button type="primary" danger size="small">移除屏蔽</a-button>
                </div>
              </div>
            </div>
          </a-col>
        </a-row>
      </div>
    </div>
  </div>

</template>

<script setup>
import {UserAddOutlined} from '@ant-design/icons-vue'
</script>

<style scoped>
.user-content {
  margin-top: 1.5em;
  display: inline-block;
  position: relative;
  height: 92%;
  border-radius: 10px;
}

.scroll-container {
  display: inline-block;
  height: 100%;
  width: 100%;
  overflow-y: scroll;
  overflow-x: hidden;
}

.personal {
  display: inline-block;
  margin: 5px;
  height: 7em;
  width: 95%;
  border-radius: 15px;
  background: #F2F2F2;
}

.apply {
  position: relative;
  height: 30%;
}

.apply-button {
  position: absolute;
  top: 50%;
  right: 7%;
  transform: translateY(-50%);
}


</style>