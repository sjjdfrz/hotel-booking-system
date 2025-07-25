<template>
  <AppNavigation />
  <div
    class="flex flex-col justify-center items-center min-h-screen text-center vazir-font px-4 py-8 relative h-[80vh] bg-cover bg-center"
    style="background-image: url('./images/background.svg')"
  >
    <h1 class="text-2xl font-bold text-white mb-6">
      {{ mode === 'register' ? 'ثبت نام' : 'ورود' }}
    </h1>

    <div class="w-full max-w-md">
      <AuthForm
        :mode="mode"
        :loading="loading"
        :error="auth.error"
        :mfaQrCodeImage="auth.mfaQrCodeImage"
        :secretKey="auth.secretKey"
        :otpSent="auth.requiresOtp"
        @submit="handleSubmit"
        @toggle-mode="toggleMode"
      />
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useAuth } from '@/stores/authStore';
import AuthForm from '@/components/auth/AuthForm.vue';
import AppNavigation from '@/components/layout/AppNavigation.vue';

const router = useRouter();
const route = useRoute();
const auth = useAuth();

const mode = ref('login');
const loading = ref(false);

const toggleMode = () => {
  mode.value = mode.value === 'login' ? 'register' : 'login';
  auth.error = null;
  auth.requiresOtp = false;
  auth.pendingEmail = null;
  auth.mfaQrCodeImage = null;
  auth.secretKey = null;
};

const handleSubmit = async (formData) => {
  loading.value = true;
  auth.error = null;

  try {
    if (mode.value === 'login' && auth.requiresOtp) {
      const success = await auth.verifyOtp({
        email: auth.pendingEmail,
        code: formData.otpCode,
      });

      if (success) {
        const redirectPath = route.query.redirect || '/';
        await router.push(redirectPath);
      }
    } else if (mode.value === 'login' && !auth.requiresOtp) {
      await auth.login(formData);
    } else if (auth.mfaQrCodeImage) {
      const success = await auth.completeMfaSetup();
      if (success) {
        mode.value = 'login';
      }
    } else {
      await auth.register(formData);
    }
  } finally {
    loading.value = false;
  }
};
</script>

