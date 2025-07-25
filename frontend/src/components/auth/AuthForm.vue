<template>
  <div
    class="bg-white rounded-xl p-6 text-right shadow-[0_20px_60px_rgba(128,0,255,0.3)] hover:shadow-[0_25px_75px_rgba(128,0,255,0.4)] transition duration-300"
    dir="rtl"
  >
    <div v-if="mode === 'register' && !mfaQrCodeImage">
      <h2 class="text-xl font-semibold mb-4">ایجاد حساب کاربری</h2>
      <form @submit.prevent="$emit('submit', formData)">
        <div class="space-y-4">
          <FormInput label="نام" v-model="formData.firstName" required />
          <FormInput label="نام خانوادگی" v-model="formData.lastName" required />
          <FormInput dir="ltr" label="ایمیل" type="email" v-model="formData.email" required />
          <FormInput
            dir="ltr"
            label="رمز عبور"
            type="password"
            v-model="formData.password"
            required
          />
          <FormInput dir="ltr" label="تلفن همراه" v-model="formData.phoneNumber" required />

          <SubmitButton :loading="loading"> ثبت نام </SubmitButton>
        </div>
      </form>
    </div>

    <div v-if="mode === 'register' && mfaQrCodeImage" class="text-center">
      <h2 class="text-xl font-semibold mb-4">تنظیم تأیید دو مرحله‌ای</h2>
      <p class="mb-4 text-gray-700">لطفاً کد QR را با برنامه Google Authenticator اسکن کنید:</p>
      <img
        :src="mfaQrCodeImage"
        alt="کد QR دو مرحله‌ای"
        class="mx-auto w-48 h-48 rounded-md shadow-md"
      />
      <p class="mt-4 text-sm text-gray-500 break-all">کلید مخفی: {{ secretKey }}</p>

      <div class="mt-6">
        <SubmitButton @click="$emit('submit', { completeMfa: true })" :loading="loading">
          ادامه
        </SubmitButton>
      </div>
    </div>

    <div v-if="mode === 'login' && !otpSent">
      <h2 class="text-xl font-semibold mb-4">ورود به حساب کاربری</h2>
      <form @submit.prevent="$emit('submit', formData)">
        <div class="space-y-4">
          <FormInput dir="ltr" label="ایمیل" type="email" v-model="formData.email" required />
          <FormInput
            dir="ltr"
            label="رمز عبور"
            type="password"
            v-model="formData.password"
            required
          />

          <SubmitButton :loading="loading"> ورود </SubmitButton>
        </div>
      </form>
    </div>

    <div v-if="mode === 'login' && otpSent">
      <h2 class="text-xl font-semibold mb-4">تأیید دو مرحله‌ای</h2>
      <p class="mb-4 text-gray-700">کد تأیید ارسال شده به برنامه احراز هویت خود را وارد کنید:</p>
      <form @submit.prevent="$emit('submit', { ...formData, otpCode })">
        <FormInput
          dir="ltr"
          label="کد تأیید"
          v-model="otpCode"
          required
          maxlength="6"
          class="text-center text-lg tracking-widest"
        />

        <SubmitButton :loading="loading"> تأیید </SubmitButton>
      </form>
    </div>

    <p v-if="error" class="mt-4 text-red-600 text-center">{{ error }}</p>

    <div class="mt-6 pt-4 border-t border-gray-200">
      <button
        @click="$emit('toggle-mode')"
        class="text-blue-600 hover:text-blue-800 transition-colors"
      >
        {{ mode === 'login' ? 'حساب کاربری ندارید؟ ثبت نام' : 'حساب کاربری دارید؟ ورود' }}
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, watch } from 'vue';
import FormInput from '@/components/ui/FormInput.vue';
import SubmitButton from '@/components/ui/SubmitButton.vue';

const props = defineProps({
  mode: String,
  loading: Boolean,
  error: String,
  mfaQrCodeImage: String,
  secretKey: String,
  otpSent: Boolean,
});

const emit = defineEmits(['submit', 'toggle-mode']);

const formData = reactive({
  firstName: '',
  lastName: '',
  email: '',
  password: '',
  phoneNumber: '',
});

const otpCode = ref('');

watch(
  () => props.mode,
  () => {
    Object.keys(formData).forEach((key) => (formData[key] = ''));
    otpCode.value = '';
  },
);
</script>
