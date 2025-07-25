<template>
  <div class="bg-gray-100 min-h-screen py-10 px-4 vazir-font">
    <AppNavigation />

    <HotelFilters @apply-filters="handleApplyFilters" />

    <div v-if="!hotels.hotelsFound && hotels.hotels.length === 0" class="text-center text-gray-600">
      در حال بارگذاری...
    </div>

    <div v-else-if="hotels.hotelsFound && hotels.hotels.length === 0" class="text-center mt-10">
      <p class="text-lg text-red-500">هیچ هتلی یافت نشد.</p>
    </div>

    <div v-else class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 max-w-7xl mx-auto">
      <HotelCard v-for="hotel in hotels.hotels" :key="hotel.id" :hotel="hotel" />
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { useHotels } from '../stores/hotelsStore';
import AppNavigation from '@/components/layout/AppNavigation.vue';
import HotelFilters from './../components/hotels/HotelFilters.vue';
import HotelCard from './../components/hotels/HotelCard.vue';

const route = useRoute();
const hotels = useHotels();

onMounted(async () => {
  const { city, dateFrom, dateTo, priceFrom, priceTo, stars, sort } = route.query;
  hotels.setFilters({ city, dateFrom, dateTo, priceFrom, priceTo, stars, sort });
  await hotels.fetchHotelsByFilters();
});

const handleApplyFilters = (filters) => {
  hotels.setFilters({ ...filters });
  hotels.fetchHotelsByFilters();
};
</script>

<style scoped>
.vazir-font {
  font-family: 'Vazirmatn', sans-serif;
}
</style>
