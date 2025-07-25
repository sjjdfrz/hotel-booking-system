import axios from 'axios';
import { defineStore } from 'pinia';

import router from '@/router';

import { useAuth } from './authStore';

export const useBookingStore = defineStore('booking', {
  state: () => ({
    error: null,
    loading: false,
    pastBookings: [],
    currentBookings: [],
  }),

  actions: {
    async fetchBookings() {
      const auth = useAuth();

      this.error = null;
      this.loading = true;

      try {
        const config = {
          headers: {
            'Content-Type': 'application/json',
            Authorization: 'Bearer ' + auth.token,
          },
        };

        const [currentRes, pastRes] = await Promise.all([
          axios.get('http://localhost:8080/api/users/me/current-bookings', config),
          axios.get('http://localhost:8080/api/users/me/past-bookings', config),
        ]);

        this.currentBookings = currentRes.data.data;
        this.pastBookings = pastRes.data.data;
      } catch (error) {
        const status = error.response?.status;

        if (status === 403 || status === 401) { // CHECK 401, 403
          auth.clearAuth();
          router.push({
            path: '/login',
            query: { redirect: router.currentRoute.value.fullPath }
          });
        } else {
          this.error = error.response?.data?.message || error.message || 'خطا در دریافت رزروها'; // CHECK CODE
        }
      } finally {
        this.loading = false;
      }
    },
  },
});
