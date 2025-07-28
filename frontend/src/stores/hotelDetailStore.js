import { defineStore } from 'pinia';
import axios from 'axios';

export const useHotelDetail = defineStore('hotelDetail', {
  state: () => ({
    hotel: {
      hotelName: '',
      stars: 0,
      address: '',
      description: '',
      rooms: [],
    },
    loading: false,
    error: null,
    currentRoom: null,
  }),

  actions: {
    async fetchHotelRooms(hotelId, dateFrom, dateTo) {
      this.loading = true;
      this.error = null;
      try {
        const res = await axios.get('http://localhost:8080/api/rooms', {
          params: { hotelId, dateFrom, dateTo },
        });

        const data = res.data.data;

        this.hotel = {
          ...data,
          image: data.imagePath,
       };
      } catch (err) {
        this.error = err.response?.data?.message || 'خطا در دریافت اطلاعات هتل';
      } finally {
        this.loading = false;
      }
    },
    fetchHotelRoom(roomId, dateFrom, dateTo) {
      if (!this.hotel.rooms || this.hotel.rooms.length === 0) {
        this.error = 'هیچ اتاقی بارگذاری نشده است';
        return;
      }
      const room = this.hotel.rooms.find((r) => r.id === Number(roomId));

      if (!room) {
        this.error = 'اتاق مورد نظر پیدا نشد';
        return;
      }

      this.currentRoom = {
        ...room,
        dateFrom,
        dateTo,
      };
    },
  },
});
