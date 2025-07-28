<template>
  <div
    class="bg-gradient-to-r from-white to-blue-50 rounded-xl p-6 shadow-md flex flex-col md:flex-row md:justify-between md:items-center hover:shadow-lg transition-shadow duration-300"
  >
    <div class="text-right space-y-1">
      <h3 class="text-2xl font-bold text-blue-800">{{ room.title }}</h3>
      <p class="text-gray-700">
        ظرفیت: <span class="font-medium">{{ room.capacity }} نفر</span>
      </p>
      <p class="text-gray-700">
        تعداد شب‌ها: <span class="font-medium">{{ room.nightCount }}</span>
      </p>
      <p class="text-green-700 font-extrabold text-lg">
        قیمت کل: <span>{{ formatPrice(room.price) }} تومان</span>
      </p>
    </div>

    <button
      @click="$emit('reserve')"
      class="mt-4 md:mt-0 bg-green-600 hover:bg-green-700 focus:ring-4 focus:ring-green-400 text-white px-6 py-3 rounded-full font-semibold shadow-md transition duration-300"
    >
      رزرو
    </button>
  </div>
</template>

<script setup>
const formatPrice = (num) => {
  return num.toLocaleString('fa-IR');
};

defineProps({
  room: {
    type: Object,
    required: true,
  },
});

defineEmits(['reserve']);
</script>
