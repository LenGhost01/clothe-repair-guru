import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from "path";

// https://vitejs.dev/config/
export default defineConfig({
  base: '/', //nginx服务器中存放index.html的文件夹 vite使用，使用vue-cli则需要使用module.exports
  plugins: [
    vue(),
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
        target: 'http://localhost:8090/clothe-master',
        changeOrigin: true,
        rewrite: path => path.replace(/^\/requests/, '')
      }
    }
  },
  build: {
    rollupOptions: {
      input: {
        main: path.resolve(__dirname, '/src/pages/home/index.html'),
        administrator: path.resolve(__dirname, '/src/pages/administrator/index.html'),
        individual_center: path.resolve(__dirname, '/src/pages/individual_center/index.html'),
        merchant_application: path.resolve(__dirname, '/src/pages/merchant_application/index.html'),
      }
    }
  }
})
