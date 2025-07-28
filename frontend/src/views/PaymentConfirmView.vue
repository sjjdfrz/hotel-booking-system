<template>
  <AppNavigation />

  <div
    class="flex flex-col justify-center items-center text-center vazir-font px-4 relative h-[80vh] bg-cover bg-center"
    style="background-image: url('./images/background.svg')"
  >
    <h1 class="text-2xl font-bold text-white mb-4">رسید رزرو</h1>

    <div
      class="shadow-[0_20px_60px_rgba(128,0,255,0.3)] hover:shadow-[0_25px_75px_rgba(128,0,255,0.4)] transition duration-300 bg-white rounded-xl w-full max-w-md p-6 text-right"
    >
      <p><strong>نام:</strong> {{ booking.firstName }} {{ booking.lastName }}</p>
      <p><strong>هتل:</strong> {{ booking.hotelTitle }} ({{ booking.hotelStars }} ستاره)</p>
      <p><strong>اتاق:</strong> {{ booking.roomTitle }} (ظرفیت: {{ booking.roomCapacity }} نفر)</p>
      <p><strong>تاریخ ورود:</strong> {{ formatDate(booking.checkInDate) }}</p>
      <p><strong>تاریخ خروج:</strong> {{ formatDate(booking.checkOutDate) }}</p>
      <p><strong>تعداد شب:</strong> {{ booking.nightCount }}</p>
      <p><strong>مبلغ:</strong> {{ formatPrice(booking.price) }} تومان</p>
    </div>

    <button
      class="mt-6 bg-blue-600 text-white py-2 px-6 rounded-lg hover:bg-blue-700 transition"
      @click="pay"
    >
      پرداخت
    </button>

    <p v-if="loading" class="text-gray-500 mt-4">در حال اتصال به درگاه...</p>
    <p v-if="error" class="text-red-500 mt-4">{{ error }}</p>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import axios from 'axios';
import { useAuth } from '@/stores/authStore';
import AppNavigation from '@/components/layout/AppNavigation.vue';
import router from '@/router';
const auth = useAuth();
const booking = history.state.booking;
const loading = ref(false);
const error = ref('');

const formatDate = (dateStr) => {
  return new Date(dateStr).toLocaleDateString('fa-IR');
};

const formatPrice = (amount) => {
  return amount.toLocaleString('fa-IR');
};

const pay = async () => {
  loading.value = true;
  error.value = '';
  try {
    const response = await axios.get(`http://localhost:8080/api/payments/${booking.paymentCode}`, {
      headers: {
        Authorization: 'Bearer ' + auth.token,
      },
    });
    const redirectUrl = response.data.data.redirectUrl;
    if (redirectUrl) {
      localStorage.setItem('booking', JSON.stringify(booking));
      window.location.href = redirectUrl;
    } else {
      error.value = 'آدرس درگاه دریافت نشد.';
      loading.value = false;
    }
  } catch (err) {
    const status = err.response?.status;
    if (status === 401 || status === 403) {
      auth.clearAuth();
      router.push({
        path: '/login',
        query: { redirect: router.currentRoute.value.fullPath }
      });
    } else {
      error.value = 'خطا در برقراری ارتباط با درگاه پرداخت.';
    }
    loading.value = false;
  }
};
</script>

<style scoped>
.vazir-font {
  font-family: 'Vazirmatn', sans-serif;
}
</style>
