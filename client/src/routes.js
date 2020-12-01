import { createWebHistory, createRouter } from 'vue-router';
import Login from './components/Login.vue';
import Panel from './components/Panel.vue';
import Notifications from './components/Notifications.vue';
import Chat from './components/Chat.vue';
import Employes from './components/Employes.vue';
import History from './components/History.vue'
import InternalChat from './components/InternalChat.vue'

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/panel',
        name: 'Panel',
        component: Panel,
        children: [
            {
                path: '/panel/activities',
                name: 'Notifications',
                component: Notifications
            },
            {
                path: '/panel/live_chats',
                name: 'Chat',
                component: Chat
            },
            {
                path: '/panel/employes',
                name: 'Employes',
                component: Employes
            },
            {
                path: '/panel/history',
                name: 'History',
                component: History
            },
            {
                path: '/panel/internalChat',
                name: 'InternalChat',
                component: InternalChat
            }
        ]
    },
    {
        path: '/', redirect: { path: '/panel/activities' },
    },
    { path: '/panel', redirect: { path: '/panel/activities' } }

];

const router = createRouter({
    history: createWebHistory(),
    routes
});

router.beforeEach((to, from, next) => {
    if (to.name !== 'Login' && !sessionStorage.usuario) { next({ path: '/login' }) }

    else if (to.name == 'Login' && sessionStorage.usuario) { next({ path: '/panel/activities' }) }

    else if (to.name == 'Panel') { next({ path: '/panel/activities' }) }
    else next();
});

export default router;