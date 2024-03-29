import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from "path";
import ckeditor5 from "@ckeditor/vite-plugin-ckeditor5";
import { createRequire } from 'node:module'
import {fileURLToPath} from 'url'
const require = createRequire(import.meta.url)

// https://vitejs.dev/config/
export default defineConfig({
  base: '/', //nginx服务器中存放index.html的文件夹 vite使用，使用vue-cli则需要使用module.exports
  plugins: [
    vue(),
    ckeditor5({ theme: require.resolve( '@ckeditor/ckeditor5-theme-lark') }),
  ],
  server: {
    open: '/src/pages/home/index.html',
    // 配置代理服务器
    proxy: {
      '/imgs': {
        target: 'http://192.168.32.141/images',
        changeOrigin: true,
        rewrite: path => path.replace(/^\/imgs/, '')
      },
      '/statics': {
        target: 'http://192.168.32.141/statics',
        changeOrigin: true,
        rewrite: path => path.replace(/^\/statics/, '')
      },
      '/requests': {
        target: 'http://192.168.32.141/clothe-master',
        changeOrigin: true,
        rewrite: path => path.replace(/^\/requests/, '')
      },
    }
  },
  build: {
    rollupOptions: {
      input: {
        main: path.resolve(__dirname, '/src/pages/home/index.html'),
        administrator: path.resolve(__dirname, '/src/pages/administrator/index.html'),
        individual_center: path.resolve(__dirname, '/src/pages/individual_center/index.html'),
        merchant_application: path.resolve(__dirname, '/src/pages/merchant_application/index.html'),
        merchandise_detail: path.resolve(__dirname, '/src/pages/merchandise_detail/index.html'),
        chatroom_view: path.resolve(__dirname, '/src/pages/chatroom/index.html'),
      }
    }
  },
  resolve: {
    alias: {
      '@': fileURLToPath( new URL( './src', import.meta.url ) )
    }
  }
})
