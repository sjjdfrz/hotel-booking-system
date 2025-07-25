<template>
  <div class="flex flex-col justify-center items-center h-screen text-center vazir-font">
    <h1 class="text-3xl font-bold text-red-600 mb-4">پرداخت ناموفق بود</h1>

    <template v-if="booking">
      <p class="text-gray-700 mb-2">
        کد رزرو: <strong>{{ booking.id }}</strong>
      </p>
      <p class="text-sm text-gray-500 mb-6">
        در صورت کسر وجه، کد پیگیری: {{ booking.paymentCode }}
      </p>
      <div class="flex gap-4">
        <button @click="goHome" class="bg-gray-600 text-white py-2 px-4 rounded hover:bg-gray-700">
          بازگشت به خانه
        </button>
      </div>
    </template>

    <p v-else class="text-red-500">اطلاعات رزرو پیدا نشد.</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const booking = ref(null);

onMounted(() => {
  const stored = localStorage.getItem('booking');
  if (stored) {
    booking.value = JSON.parse(stored);
    localStorage.removeItem('booking');
  }
});

const goHome = () => {
  router.push({ name: 'Home' });
};
</script>

<style scoped>
.vazir-font {
  font-family: 'Vazirmatn', sans-serif;
}
</style>
