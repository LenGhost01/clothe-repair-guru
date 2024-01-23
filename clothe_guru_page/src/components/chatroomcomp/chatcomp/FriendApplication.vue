<template>
  <div class="comp-container">
    <div id="prompt-information" class="prompt-information">
      <div class="subprompt">
        <UserAddOutlined class="icon"/>
        <span style="margin-left: 5%" class="nav-font-middle">申请 - 3</span>
      </div>

    </div>
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
                  <success-button>
                    通过申请
                  </success-button>
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
import {UserAddOutlined,CheckOutlined} from '@ant-design/icons-vue'
import successButton from "../../SuccessButton.vue"
</script>

<style scoped>
.comp-container {
  height: 100%;
}

.prompt-information {
  position: relative;
  height: 10%;
  margin-top: 2%;
  padding-left: 1.5em;
}

.subprompt {
  position: absolute;
  width: 100%;
  top: 50%;
  transform: translateY(-50%);
  font-size: 1.2em;
}

.user-content {
  position: relative;
  height: calc(85%);
  padding-left: 1.5em;
  border-radius: 10px;

}

.scroll-container {
  display: inline-block;
  height: 100%;
  width: 100%;
  overflow-y: scroll;
  overflow-x: hidden;
}
.personal{
  display: inline-block;
  margin: 5px;
  height: 7em;
  width: 95%;
  border-radius: 15px;
  background: #F2F2F2;
}
.apply{
  position: relative;
  height: 30%;
}

.apply-button{
  position: absolute;
  top: 50%;
  right: 7%;
  transform: translateY(-50%);
}


</style>