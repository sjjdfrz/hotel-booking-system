import { createRouter, createWebHistory } from 'vue-router';
import { useAuth } from '@/stores/authStore';

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/LoginView.vue'),
    meta: { guestOnly: true },
  },
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/HomeView.vue'),
  },
  {
    path: '/hotels',
    name: 'Hotels',
    component: () => import('@/views/HotelsView.vue'),
  },
  {
    path: '/rooms',
    name: 'Rooms',
    component: () => import('@/views/RoomsView.vue'),
  },
  {
    path: '/reserve',
    name: 'Reserve',
    component: () => import('@/views/ReserveView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/payment-confirm',
    name: 'PaymentConfirm',
    component: () => import('@/views/PaymentConfirmView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/payment-success',
    name: 'PaymentSuccess',
    component: () => import('@/views/PaymentSuccessView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/payment-failure',
    name: 'PaymentFailure',
    component: () => import('@/views/PaymentFailView.vue'),
    meta: { requiresAuth: true },
  },
  {
    path: '/user',
    name: 'User',
    component: () => import('@/views/UserHistoryView.vue'),
    meta: { requiresAuth: true },
  },
];

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
});

router.beforeEach(async (to) => {
  const auth = useAuth();

  if (to.meta.requiresAuth && !auth.isLoggedIn) {
    return {
      name: 'Login',
      query: { redirect: to.fullPath },
    };
  }

  if (to.meta.guestOnly && auth.isLoggedIn) {
    return { name: 'home' };
  }
});

export default router;
