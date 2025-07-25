<template>
  <div>
    <img
      v-if="hotel.image"
      :src="hotel.image"
      alt="Hotel image"
      class="w-full h-72 sm:h-96 object-cover transition-transform duration-500 hover:scale-105"
    />
    <div class="p-8 text-right">
      <h1 class="text-4xl font-extrabold text-blue-700 mb-4 tracking-wide">
        {{ hotel.hotelName }}
      </h1>

      <div class="space-y-3 text-gray-800 text-lg leading-relaxed">
        <StarRating :stars="hotel.stars" />

        <p>
          <strong class="text-blue-600">آدرس:</strong>
          {{ hotel.address }}
        </p>

        <div>
          <strong class="text-blue-600">توضیحات:</strong>
          <p class="whitespace-pre-line mt-1 text-gray-600">
            {{ hotel.description }}
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import StarRating from '@/components/ui/StarRating.vue';

defineProps({
  hotel: {
    type: Object,
    required: true,
  },
});
</script>
