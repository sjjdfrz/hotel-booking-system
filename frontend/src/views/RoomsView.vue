<template>
  <AppNavigation />
  <div class="p-6 max-w-5xl mx-auto bg-gray-50 min-h-screen vazir-font">
    <div class="bg-white rounded-xl shadow-lg overflow-hidden">
      <HotelHeader :hotel="hotelDetail.hotel" v-if="hotelDetail.hotel.hotelName" />

      <RoomList
        :rooms="hotelDetail.hotel.rooms"
        @reserve="reserveRoom"
        v-if="hotelDetail.hotel.rooms?.length"
      />

      <EmptyState v-else message="اتاقی برای نمایش وجود ندارد." />
    </div>
  </div>
</template>

<script setup>
import { useHotelDetail } from '@/stores/hotelDetailStore';
import { useRoute, useRouter } from 'vue-router';
import { onMounted } from 'vue';
import AppNavigation from '@/components/layout/AppNavigation.vue';
import HotelHeader from '@/components/hotels/HotelHeader.vue';
import RoomList from '@/components/rooms/RoomList.vue';
import EmptyState from '@/components/ui/EmptyState.vue';

const hotelDetail = useHotelDetail();
const route = useRoute();
const router = useRouter();

onMounted(async () => {
  const { hotelId, dateFrom, dateTo } = route.query;
  hotelId && (await hotelDetail.fetchHotelRooms(hotelId, dateFrom, dateTo));
});

const reserveRoom = async (roomId) => {
  const { hotelId, dateFrom, dateTo } = route.query;
  router.push({
    name: 'Reserve',
    query: { hotelId, roomId, dateFrom, dateTo },
  });
};
</script>

<style scoped>
.vazir-font {
  font-family: 'Vazirmatn', sans-serif;
}
</style>
