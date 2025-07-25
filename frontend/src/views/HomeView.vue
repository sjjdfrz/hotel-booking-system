<template>
  <div
    class="min-h-screen bg-gradient-to-tr from-violet-300 via-violet-400 to-violet-500 vazir-font"
  >
    <nav
      class="flex flex-row-reverse justify-between items-center px-8 py-4 bg-white/20 backdrop-blur-md shadow-lg"
    >
      <div class="text-2xl font-extrabold text-white tracking-widest" dir="ltr">رزروینو</div>
      <button
        @click="goToUserView"
        class="p-2 text-white hover:bg-white/20 rounded-full transition"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="h-6 w-6"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
          />
        </svg>
      </button>
    </nav>
    <HeroSection @search="handleSearch" />
  </div>
</template>

<script setup>
import moment from 'jalali-moment';
import { useRouter } from 'vue-router';

import HeroSection from '@/components/home/HeroSection.vue';

const router = useRouter();

const handleSearch = ({ city, checkin, checkout }) => {
  const formatToDateTime = (jalaliStr) => {
    return moment.from(jalaliStr, 'en', 'YYYY/MM/DD').locale('en').format('YYYY-MM-DDT10:00:00');
  };
  router.push({
    name: 'Hotels',
    query: {
      city: city,
      dateFrom: formatToDateTime(checkin),
      dateTo: formatToDateTime(checkout),
    },
  });
};

const goToUserView = () => {
  router.push({ name: 'User' });
};
</script>

<style>
.vazir-font {
  font-family: 'Vazirmatn', sans-serif;
}

.vpd-content {
  background-color: #f3e8ff;
  color: #1f2937;
  border-radius: 1rem;
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.15);
  font-family: 'Vazirmatn', sans-serif;
}

.vpd-day {
  background-color: transparent;
  color: #1f2937;
}

.vpd-day:hover,
.vpd-day.vpd-selected {
  background-color: #8b5cf6;
  color: white;
  border-radius: 50%;
}

.vpd-header {
  background-color: #ddd6fe;
  color: #1f2937;
  border-bottom: 1px solid #c4b5fd;
}
</style>
