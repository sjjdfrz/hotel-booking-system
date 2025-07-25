<template>
  <AppNavigation />
  <div
    class="flex flex-col justify-center items-center min-h-screen text-center vazir-font px-4 py-8 relative h-[80vh] bg-cover bg-center"
    style="background-image: url('./images/background.svg')"
  >
    <div v-if="!loaded" class="text-center mt-10 text-gray-600">در حال بارگذاری اطلاعات...</div>

    <div
      v-else
      class="shadow-[0_20px_60px_rgba(128,0,255,0.3)] hover:shadow-[0_25px_75px_rgba(128,0,255,0.4)] transition duration-300 max-w-md mx-auto p-8 bg-white rounded-2xl ring-1 ring-gray-200 vazir-font"
    >
      <div class="mb-6 p-4 rounded-xl border bg-gray-50">
        <h2 class="text-xl font-bold text-right text-blue-800 mb-2">
          {{ hotelDetail.hotel.hotelName }}
        </h2>
        <p class="text-right text-gray-600 mb-1">آدرس: {{ hotelDetail.hotel.address }}</p>
        <p class="text-right text-gray-600 mb-1">اتاق: {{ hotelDetail.currentRoom.title }}</p>
        <p class="text-right text-gray-600 mb-1">
          ظرفیت: {{ hotelDetail.currentRoom.capacity }} نفر
        </p>
        <p class="text-right text-gray-600 mb-1">
          قیمت هر شب: {{ hotelDetail.currentRoom.price.toLocaleString('fa-IR') }} تومان
        </p>
        <p class="text-right text-gray-600">تعداد شب: {{ hotelDetail.currentRoom.nightCount }}</p>
      </div>

      <form @submit.prevent="submitBooking" class="space-y-6 text-right">
        <button
          type="submit"
          :disabled="loading"
          class="w-full bg-blue-600 text-white rounded-full py-3 font-semibold text-lg hover:bg-blue-700 disabled:bg-blue-400 disabled:cursor-not-allowed transition"
        >
          {{ loading ? 'در حال ارسال...' : 'رزرو' }}
        </button>

        <p v-if="error" class="mt-4 text-center text-red-600 font-medium select-none" role="alert">
          {{ error }}
        </p>
        <p
          v-if="success"
          class="mt-4 text-center text-green-600 font-semibold select-none"
          role="alert"
        >
          رزرو با موفقیت ثبت شد!
        </p>
      </form>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios';

import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useAuth } from '@/stores/authStore';
import AppNavigation from '@/components/layout/AppNavigation.vue';
import { useHotelDetail } from '@/stores/hotelDetailStore';

const route = useRoute();
const router = useRouter();
const hotelDetail = useHotelDetail();

const auth = useAuth();

const form = ref({
  roomId: 0,
  dateFrom: '',
  dateTo: '',
});

const loading = ref(false);
const error = ref('');
const success = ref(false);
const loaded = ref(false);

onMounted(async () => {
  const { hotelId, roomId, dateFrom, dateTo } = route.query;

  if (!roomId || !dateFrom || !dateTo) {
    error.value = 'پارامترهای رزرو ناقص است';
    return;
  }

  try {
    await hotelDetail.fetchHotelRooms(hotelId, dateFrom, dateTo);
    hotelDetail.fetchHotelRoom(roomId, dateFrom, dateTo);
    if (!hotelDetail.currentRoom) {
      router.push({
        name: 'Home',
      });
    }

    form.value.roomId = Number(roomId);
    form.value.dateFrom = dateFrom;
    form.value.dateTo = dateTo;
  } catch (err) {
    error.value = 'خطا در بارگیری اطلاعات هتل';
  } finally {
    loaded.value = true;
  }
});

const submitBooking = async () => {
  loading.value = true;
  error.value = '';
  success.value = false;

  try {
    const res = await axios.post('http://localhost:8080/api/bookings', form.value, {
      headers: {
        Authorization: 'Bearer ' + auth.token,
      },
    });

    const data = res.data.data;

    router.push({
      name: 'PaymentConfirm',
      state: { booking: data },
    });
  } catch (err) {
    const status = err.response?.status;

    if (status === 401 || status === 403) {
      auth.clearAuth();
      router.push({
        path: '/login',
        query: { redirect: router.currentRoute.value.fullPath }
      });
    } else {
      error.value = err.response?.data?.message || 'خطا در ثبت رزرو';
    }
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.vazir-font {
  font-family: 'Vazirmatn', sans-serif;
}
</style>
