<script setup>
import {ClassicEditor} from '@ckeditor/ckeditor5-editor-classic';
// import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import {Autoformat} from '@ckeditor/ckeditor5-autoformat';
import {Bold, Italic} from '@ckeditor/ckeditor5-basic-styles';
import {BlockQuote} from '@ckeditor/ckeditor5-block-quote';
import {CKBox} from '@ckeditor/ckeditor5-ckbox';
import {CloudServices} from '@ckeditor/ckeditor5-cloud-services';
import {Essentials} from '@ckeditor/ckeditor5-essentials';
import {FindAndReplace} from '@ckeditor/ckeditor5-find-and-replace';
import {FontBackgroundColor, FontColor, FontFamily, FontSize} from '@ckeditor/ckeditor5-font';
import {Heading} from '@ckeditor/ckeditor5-heading';
import {Highlight} from '@ckeditor/ckeditor5-highlight';
import {
  Image,
  ImageCaption,
  ImageInsert,
  ImageResize,
  ImageStyle,
  ImageToolbar,
  PictureEditing
} from '@ckeditor/ckeditor5-image';
import {Indent} from '@ckeditor/ckeditor5-indent';
import {Link} from '@ckeditor/ckeditor5-link';
import {List} from '@ckeditor/ckeditor5-list';
import {Paragraph} from '@ckeditor/ckeditor5-paragraph';
import {PasteFromOffice} from '@ckeditor/ckeditor5-paste-from-office';
import {Table, TableToolbar} from '@ckeditor/ckeditor5-table';
import {TextTransformation} from '@ckeditor/ckeditor5-typing';
import {Undo} from '@ckeditor/ckeditor5-undo';
import '@ckeditor/ckeditor5-build-classic/build/translations/zh-cn';
import MyUploadAdapter from "/src/utils/CustomUploadAdapter.js";
import {inject, onMounted, ref} from "vue";
import store from "@/store/store.js";

const customUploadAdapter = function (editor) {
  // 自定义文件上传逻辑
  editor.plugins.get('FileRepository').createUploadAdapter = (loader) => {
    // 配置并返回上传适配器的实例
    return new MyUploadAdapter(loader);
  };
}
const editor = ClassicEditor
const container = ref(null)
const editorWrapper = ref(null)
const editorData = ref('')
const editorConfig = ref({
  plugins: [
    Autoformat,
    BlockQuote,
    Bold,
    CKBox,
    CloudServices,
    Essentials,
    FindAndReplace,
    FontBackgroundColor,
    FontColor,
    FontFamily,
    FontSize,
    Heading,
    Highlight,
    Image,
    ImageCaption,
    ImageInsert,
    ImageResize,
    ImageStyle,
    ImageToolbar,
    Indent,
    Italic,
    Link,
    List,
    Paragraph,
    PasteFromOffice,
    PictureEditing,
    Table,
    TableToolbar,
    TextTransformation,
    Undo
  ], toolbar: {
    items: [
      'heading',
      '|',
      'bold',
      'italic',
      'link',
      'bulletedList',
      'numberedList',
      '|',
      'outdent',
      'indent',
      '|',
      'blockQuote',
      'insertTable',
      'undo',
      'redo',
      'fontBackgroundColor',
      'findAndReplace',
      'fontColor',
      'fontSize',
      'fontFamily',
      'highlight',
      'imageInsert'
    ],
  },
  language: 'zh-cn',
  image: {
    resizeUnit: '%', // 调整大小的单位，默认为 'px'
    resizeOptions: [ // 定义调整大小的选项
      {
        name: 'imageResize:original',
        value: null,
        label: '原大小',
        icon: 'original'
      },
      {
        name: 'imageResize:25%',
        value: '25',
        label: '25%',
        icon: 'small'
      },
      {
        name: 'imageResize:50',
        value: '50%',
        label: '50',
        icon: 'medium'
      },
      {
        name: 'imageResize:75',
        value: '75',
        label: '75%',
        icon: 'large'
      },

    ],
    toolbar: [
      'imageStyle:inline', 'imageStyle:wrapText', 'imageStyle:breakText', '|',
      'toggleImageCaption', 'imageTextAlternative', '|', 'imageResize'
    ],
  },
  table: {
    contentToolbar: [
      'tableColumn',
      'tableRow',
      'mergeTableCells'
    ]
  },
  extraPlugins: [
    customUploadAdapter
  ]
})
const containerHeight = ref('')
const emitData = defineEmits(["inputData"])
const logoutContent = (event,editor) => {
  // 每次失去焦点，都向父组件发送当前输入框内的内容信息
  emitData("inputData",editor.getData())
}

const onEditorReady = () => {
  // 当组件准备好时，获取组件的实例并修改其中的视口css
  let editorUI = editorWrapper.value?.instance.ui
  if (editorUI) {
    let editorEditable = editorUI.view
  } else {
    return
  }
}

onMounted(() => {

})
</script>

<template>
  <div class="editor-style" ref="container">
    <ckeditor ref="editorWrapper"
              :editor="editor" v-model="editorData" :config="editorConfig" @ready="onEditorReady" @blur="logoutContent">
    </ckeditor>
  </div>
</template>

<style scoped>

</style>
<style>
.editor-style {
  height: 100%;
}
.editor-style .ck-editor{
  height: calc(100% - 2.4em);
}
.ck-editor .ck-editor__main{
  height: 100%;
}
.ck-editor .ck-editor__editable {
  overflow-y: auto;
  height: 100%;
}
</style>