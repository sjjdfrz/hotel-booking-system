import { defineStore } from 'pinia';
import axios from 'axios';

export const useHotels = defineStore('hotel', {
  state: () => ({
    hotels: [],
    hotelsFound: false,
    filters: {
      city: '',
      stars: 0,
      priceFrom: 0,
      priceTo: 0,
      dateFrom: '',
      dateTo: '',
      sort: 'rate',
    },
  }),

  getters: {
    filteredHotels: (state) => {
      return state.hotels;
    },
  },

  actions: {
    async fetchHotels({
      city,
      dateFrom,
      dateTo,
      priceFrom = 0,
      priceTo = 0,
      stars = 0,
      sort = 'rate',
    }) {
      try {
        this.hotelsFound = false;
        const params = {};

        if (city) params.city = city;
        if (dateFrom) params.dateFrom = dateFrom;
        if (dateTo) params.dateTo = dateTo;
        if (priceFrom) params.priceFrom = priceFrom;
        if (priceTo) params.priceTo = priceTo;
        if (stars !== 0) params.stars = stars;
        params.sort = sort;

        const res = await axios.get('http://localhost:8080/api/hotels', { params });

        this.hotels = res.data.map((hotel) => {
          return {
            ...hotel,
            image: hotel.imagePath,
          };
        });

        this.hotelsFound = true;
      } catch (error) {
        this.hotels = [];
        this.hotelsFound = false;
        console.error('Failed to fetch hotels:', error.message);
        throw new Error('Failed to fetch hotels');
      }
    },

    async fetchHotelsByFilters() {
      const { city, dateFrom, dateTo, priceFrom, priceTo, stars, sort } = this.filters;
      await this.fetchHotels({ city, dateFrom, dateTo, priceFrom, priceTo, stars, sort });
    },

    setFilters(filters) {
      this.filters = { ...this.filters, ...filters };
    },
  },
});
