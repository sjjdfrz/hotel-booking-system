<template>
  <AppNavigation />

  <div class="p-6 max-w-5xl mx-auto font-vazir">
    <div v-if="bookingStore.loading" class="text-center text-gray-500">در حال بارگذاری...</div>
    <div v-else>
      <section class="mb-12">
        <h2 class="text-3xl font-bold text-indigo-700 mb-6 border-b border-indigo-200 pb-2">
          رزروهای فعلی
        </h2>
        <div v-if="bookingStore.currentBookings.length === 0" class="text-gray-400 text-lg">
          رزروی وجود ندارد.
        </div>
        <div class="grid gap-8 md:grid-cols-2 lg:grid-cols-3">
          <div
            v-for="booking in bookingStore.currentBookings"
            :key="booking.id"
            class="bg-white border border-gray-100 shadow-[0_8px_24px_rgba(128,0,255,0.15)] hover:shadow-[0_12px_32px_rgba(128,0,255,0.2)] transition-all duration-300 rounded-2xl p-6 space-y-2"
          >
            <h3 class="text-xl font-semibold text-indigo-600">{{ booking.hotelTitle }}</h3>
            <p class="text-gray-700">{{ booking.roomTitle }}</p>
            <div class="text-sm text-gray-500 leading-relaxed">
              <p>ورود: {{ new Date(booking.checkInDate).toLocaleDateString('fa-IR') }}</p>
              <p>خروج: {{ new Date(booking.checkOutDate).toLocaleDateString('fa-IR') }}</p>
            </div>
            <p class="mt-3 font-bold text-purple-700 text-lg">
              قیمت: {{ booking.price.toLocaleString('fa-IR') }} تومان
            </p>
          </div>
        </div>
      </section>

      <section class="mb-12">
        <h2 class="text-3xl font-bold text-gray-800 mb-6 border-b border-gray-200 pb-2">
          رزروهای گذشته
        </h2>
        <div v-if="bookingStore.pastBookings.length === 0" class="text-gray-400 text-lg">
          رزرو گذشته‌ای وجود ندارد.
        </div>
        <div class="grid gap-8 md:grid-cols-2 lg:grid-cols-3">
          <div
            v-for="booking in bookingStore.pastBookings"
            :key="booking.id"
            class="bg-white border border-gray-100 shadow-[0_6px_18px_rgba(0,0,0,0.08)] hover:shadow-[0_10px_30px_rgba(0,0,0,0.1)] transition-all duration-300 rounded-2xl p-6 space-y-2"
          >
            <h3 class="text-xl font-semibold text-gray-700">{{ booking.hotelTitle }}</h3>
            <p class="text-gray-600">{{ booking.roomTitle }}</p>
            <div class="text-sm text-gray-500 leading-relaxed">
              <p>ورود: {{ new Date(booking.checkInDate).toLocaleDateString('fa-IR') }}</p>
              <p>خروج: {{ new Date(booking.checkOutDate).toLocaleDateString('fa-IR') }}</p>
            </div>
            <p class="mt-3 font-bold text-gray-700 text-lg">
              قیمت: {{ booking.price.toLocaleString('fa-IR') }} تومان
            </p>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import AppNavigation from '@/components/layout/AppNavigation.vue';
import { useBookingStore } from '@/stores/bookingStore';
import { onMounted } from 'vue';

const bookingStore = useBookingStore();

onMounted(() => {
  bookingStore.fetchBookings();
});
</script>

<style scoped>
@import url('https://cdn.jsdelivr.net/gh/rastikerdar/vazir-font@v30.1.0/dist/font-face.css');

.font-vazir {
  font-family: 'Vazirmatn', sans-serif;
}
</style>
