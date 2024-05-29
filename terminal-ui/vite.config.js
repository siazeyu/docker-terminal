import { defineConfig } from 'vite'
import uni from '@dcloudio/vite-plugin-uni'

export default defineConfig({
    plugins: [
        uni()
    ],
    server: {
        port: 3000,
        proxy: {
            '/api': {
                target: 'http://localhost:8090', // 目标服务  // target: 'http://182.43.88.52:8081', // 目标服务
                changeOrigin: false,
                rewrite: path => path.replace(/^\/api/, ''),
            }
        }
    }
})
