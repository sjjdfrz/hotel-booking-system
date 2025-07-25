<template>
  <nav
    class="flex flex-row-reverse justify-between items-center px-6 py-3 bg-white rounded-lg shadow-sm"
    :class="{ 'mb-8': hasMargin }"
  >
    <div class="flex items-center gap-4">
      <slot name="left">
        <button
          v-if="showBackButton"
          @click="goBack"
          class="flex items-center gap-2 px-4 py-2 text-gray-600 hover:text-violet-700 hover:bg-gray-100 rounded-full transition-all duration-200"
        >
          <svg
            xmlns="http://www.w3.org/2000/svg"
            class="h-5 w-5"
            viewBox="0 0 20 20"
            fill="currentColor"
          >
            <path
              fill-rule="evenodd"
              d="M9.707 16.707a1 1 0 01-1.414 0l-6-6a1 1 0 010-1.414l6-6a1 1 0 011.414 1.414L5.414 9H17a1 1 0 110 2H5.414l4.293 4.293a1 1 0 010 1.414z"
              clip-rule="evenodd"
            />
          </svg>
          <span class="text-sm font-medium">بازگشت</span>
        </button>
      </slot>
    </div>

    <div class="flex items-center gap-2">
      <button
        @click="goToUserView"
        class="flex items-center gap-2 px-4 py-2 bg-violet-100 hover:bg-violet-200 text-violet-700 rounded-full transition-all duration-200"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="h-5 w-5"
          viewBox="0 0 20 20"
          fill="currentColor"
        >
          <path
            fill-rule="evenodd"
            d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z"
            clip-rule="evenodd"
          />
        </svg>
        <span class="text-sm font-medium">حساب کاربری</span>
      </button>

      <button
        v-if="auth.isLoggedIn"
        @click="logout"
        class="flex items-center gap-2 px-4 py-2 bg-red-100 hover:bg-red-200 text-red-700 rounded-full transition-all duration-200"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="h-5 w-5"
          viewBox="0 0 20 20"
          fill="currentColor"
        >
          <path
            fill-rule="evenodd"
            d="M3 4a1 1 0 011-1h6a1 1 0 110 2H5v10h5a1 1 0 110 2H4a1 1 0 01-1-1V4zm11.293 5.293a1 1 0 010 1.414L12.414 13H17a1 1 0 110 2h-4.586l1.879 1.879a1 1 0 11-1.414 1.414l-3.586-3.586a1 1 0 010-1.414l3.586-3.586a1 1 0 011.414 0z"
            clip-rule="evenodd"
          />
        </svg>
        <span class="text-sm font-medium">خروج</span>
      </button>
    </div>
  </nav>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { useAuth } from '@/stores/authStore';

const router = useRouter();
const auth = useAuth();

const props = defineProps({
  hasMargin: {
    type: Boolean,
    default: true,
  },
  showBackButton: {
    type: Boolean,
    default: true,
  },
});

const goBack = () => {
  router.go(-1);
};

const goToUserView = async () => {
  if (auth.isLoggedIn) {
    router.push({ name: 'User' });
  } else {
    router.push({
      name: 'Login',
      query: { redirect: '/user' },
    });
  }
};

const logout = () => {
  auth.clearAuth();
  router.push({ name: 'Home' });
};
</script>

