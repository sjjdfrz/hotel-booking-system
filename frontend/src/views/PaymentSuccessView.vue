<template>
  <div class="flex flex-col justify-center items-center h-screen text-center vazir-font">
    <h1 class="text-3xl font-bold text-green-600 mb-4">پرداخت موفق بود!</h1>
    <p v-if="booking" class="text-gray-700 mb-2">
      کد پیگیری: <strong>{{ booking.paymentCode }}</strong>
    </p>
    <p v-if="booking" class="text-sm text-gray-500 mb-6">
      از رزرو شما متشکریم، {{ booking.firstName }} {{ booking.lastName }}
    </p>
    <p v-else class="text-red-500">اطلاعات رزرو پیدا نشد.</p>
    <button @click="goHome" class="bg-blue-600 text-white py-2 px-4 rounded hover:bg-blue-700">
      بازگشت به خانه
    </button>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const booking = ref(null);

onMounted(() => {
  const stored = localStorage.getItem('booking');
  console.log(stored);
  if (stored) {
    booking.value = JSON.parse(stored);
    localStorage.removeItem('booking');
  }
});

const goHome = () => {
  router.push('/');
};
</script>

<style scoped>
.vazir-font {
  font-family: 'Vazirmatn', sans-serif;
}
</style>
