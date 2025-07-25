<template>
  <div
    class="bg-white rounded-2xl shadow-md overflow-hidden hover:shadow-lg transition transform hover:scale-[1.01] flex flex-col"
  >
    <img
      :src="hotel.image || 'https://via.placeholder.com/400x200?text=Hotel'"
      alt="Hotel Image"
      class="w-full h-48 object-cover"
    />
    <div
      class="flex flex-col justify-between flex-1 px-5 pt-5 pb-4 space-y-3 text-right leading-relaxed"
    >
      <div class="space-y-2">
        <h3 class="text-xl font-bold text-blue-600">{{ hotel.name }}</h3>
        <p><span class="font-semibold">شهر:</span> {{ hotel.city }}</p>
        <p><span class="font-semibold">آدرس:</span> {{ hotel.address }}</p>
        <StarRating :stars="hotel.stars" />
        <p><span class="font-semibold">امتیاز:</span> {{ hotel.rate }} / 5</p>
        <p><span class="font-semibold">قیمت:</span> {{ formatPrice(hotel.price) }} تومان</p>
        <p><span class="font-semibold">تعداد شب‌ها:</span> {{ hotel.nightCount }}</p>
      </div>

      <button
        @click="goToHotelDetails(hotel.id)"
        class="w-full bg-green-600 text-white py-2.5 rounded-lg hover:bg-green-700 transition font-semibold"
      >
        مشاهده و رزرو
      </button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
import { useHotels } from './../../stores/hotelsStore';
import StarRating from '../ui/StarRating.vue';

const router = useRouter();
const hotelsStore = useHotels();
const props = defineProps({
  hotel: {
    type: Object,
    required: true,
  },
});

const formatPrice = (num) => num.toLocaleString('fa-IR');

const goToHotelDetails = (hotelId) => {
  const { dateFrom, dateTo } = hotelsStore.filters;

  router.push({
    name: 'Rooms',
    query: {
      hotelId,
      dateFrom,
      dateTo,
    },
  });
};
</script>
