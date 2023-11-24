import { createRouter, createWebHistory } from 'vue-router';
import Index from '@/views/index/index.vue';
import App from "@/App.vue";

const routes = [
    {
        path: '/terminal',
        name: 'Terminal',
        component: Index,
    },
    {
        path: '/',
        name: 'App',
        component: App,
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes: routes,
});

export default router;