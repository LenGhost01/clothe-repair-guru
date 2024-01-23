<script setup>

import {ref} from "vue";

const page_transform = (page_name) => {
  console.log(page_name)
}
const img_width = ref(1200 / 6 - 20)
</script>

<template>
  <div>
    <div class="clothe_category margin-top-large">
      <a-row>
        <a-col :span="12">
          <a-typography-title :level="4" class=" left-margin-20 select_forbidden">衣物种类</a-typography-title>
        </a-col>
        <a-col :span="12" class="align-right">
          <span class=" right-margin-20">
            <a-button size="middle" type="primary"
                      @click="page_transform('转到商品展示页面，附加种类')">所有衣物种类</a-button>
          </span>
        </a-col>
      </a-row>
    </div>
    <div class="clothe_category_detail margin-top-large">
      <a-row class="left-margin-10 right-margin-10" :style="{
        height: 10+'%',
      }">
        <!-- todo 使用v-for批量展示图片-->
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 100%; border-radius: 10px;"
                  @click="page_transform('转到商品页面，指定衣物种类')">
            <template #cover>
              <img alt="skirt" :src="'/imgs/skirt.png?width='+img_width"/>
            </template>
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 180px; border-radius: 10px;">
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 180px; border-radius: 10px;">
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 180px; border-radius: 10px;">
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 180px; border-radius: 10px;">
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 180px; border-radius: 10px;">
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
      </a-row>
    </div>
    <div class="cloth_category margin-top-large">
      <a-row>
        <a-col :span="12">
          <a-typography-title :level="4" class=" left-margin-20 select_forbidden">布料材质</a-typography-title>
        </a-col>
        <a-col :span="12" class="align-right">
          <span class=" right-margin-20">
            <a-button size="middle" type="primary"
                      @click="page_transform('转到商品展示页面，附加种类')">所有布料材质</a-button>
          </span>
        </a-col>
      </a-row>
    </div>
    <div class="cloth_category_detail margin-top-large">
      <a-row class="left-margin-10 right-margin-10" :style="{
        height: 10+'%',
      }">
        <!-- todo 使用v-for批量展示图片-->
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 100%; border-radius: 10px;"
                  @click="page_transform('转到商品页面，指定衣物种类')">
            <template #cover>
              <img alt="linen" :src="'/imgs/linen.jpg?width='+img_width"/>
            </template>
            <a-card-meta title="亚麻"></a-card-meta>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 180px; border-radius: 10px;">
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 180px; border-radius: 10px;">
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 180px; border-radius: 10px;">
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 180px; border-radius: 10px;">
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 180px; border-radius: 10px;">
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
      </a-row>
    </div>
    <div class="service_category margin-top-large">
      <a-row>
        <a-col :span="12">
          <a-typography-title :level="4" class=" left-margin-20 select_forbidden">衣物种类</a-typography-title>
        </a-col>
        <a-col :span="12" class="align-right">
          <span class=" right-margin-20">
            <a-button size="middle" type="primary"
                      @click="page_transform('转到商品展示页面，附加种类')">所有衣物种类</a-button>
          </span>
        </a-col>
      </a-row>
    </div>
    <div class="service_category_detail margin-top-large">
      <a-row class="left-margin-10 right-margin-10" :style="{
        height: 10+'%',
      }">
        <!-- todo 使用v-for批量展示图片-->
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 100%; border-radius: 10px;"
                  @click="page_transform('转到商品页面，指定衣物种类')">
            <template #cover>
              <img alt="skirt" :src="'/imgs/skirt.png?width='+img_width"/>
            </template>
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 180px; border-radius: 10px;">
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 180px; border-radius: 10px;">
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 180px; border-radius: 10px;">
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 180px; border-radius: 10px;">
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
        <a-col :span="4">
          <a-card hoverable style="width: 90%; height: 180px; border-radius: 10px;">
            <a-card-meta title="衬衫"></a-card-meta>
          </a-card>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<style scoped>


</style>