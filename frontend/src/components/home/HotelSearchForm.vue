<template>
  <div
    class="bg-white/10 backdrop-blur-xl rounded-2xl shadow-xl p-6 flex flex-col gap-5 items-stretch w-full max-w-md mx-auto border border-white/20"
  >
    <div class="flex flex-col gap-2 text-right">
      <label class="text-white text-sm font-medium">تاریخ ورود</label>
      <PersianDatePicker
        v-model="checkin"
        format="YYYY/MM/DD"
        display-format="jYYYY/jMM/jDD"
        placeholder="تاریخ ورود"
        input-class="bg-white/60 text-gray-800 placeholder:text-gray-600 text-center rounded-lg border border-gray-300 px-4 py-2 focus:outline-none focus:ring-2 focus:ring-violet-500"
      />
    </div>

    <div class="flex flex-col gap-2 text-right">
      <label class="text-white text-sm font-medium">تاریخ خروج</label>
      <PersianDatePicker
        v-model="checkout"
        format="YYYY/MM/DD"
        display-format="jYYYY/jMM/jDD"
        placeholder="تاریخ خروج"
        input-class="bg-white/60 text-gray-800 placeholder:text-gray-600 text-center rounded-lg border border-gray-300 px-4 py-2 focus:outline-none focus:ring-2 focus:ring-violet-500"
      />
    </div>

    <div class="flex flex-col gap-2 text-right">
      <label for="city" class="text-white text-sm font-medium">شهر مقصد</label>
      <select
        id="city"
        v-model="city"
        class="bg-white/60 text-gray-800 border border-gray-300 rounded-lg px-4 py-2 text-center focus:outline-none focus:ring-2 focus:ring-violet-500"
      >
        <option disabled value="">انتخاب شهر مقصد</option>
        <option v-for="c in cities" :key="c" :value="c">{{ c }}</option>
      </select>
    </div>

    <div v-if="error" class="text-red-300 text-sm text-center mt-1">
      {{ error }}
    </div>

    <button
      @click="handleSubmit"
      class="bg-violet-600 hover:bg-violet-700 transition-all duration-150 text-white px-6 py-2 rounded-lg shadow-md"
    >
      مشاهده هتل‌ها
    </button>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import PersianDatePicker from 'vue3-persian-datetime-picker';

const emit = defineEmits(['search']);

const city = ref('');
const checkin = ref('');
const checkout = ref('');
const error = ref('');
const cities = ['تهران', 'مشهد'];

const isFutureDate = (dateStr) => {
  const today = new Date();
  const date = new Date(dateStr);
  return date > today;
};

const handleSubmit = () => {
  error.value = '';

  if (!checkin.value || !checkout.value || !city.value) {
    error.value = 'لطفاً همه فیلدها را پر کنید.';
    return;
  }

  if (!isFutureDate(checkin.value) || !isFutureDate(checkout.value)) {
    error.value = 'تاریخ‌ها باید در آینده باشند.';
    return;
  }

  if (new Date(checkout.value) <= new Date(checkin.value)) {
    error.value = 'تاریخ خروج باید بعد از تاریخ ورود باشد.';
    return;
  }

  emit('search', {
    city: city.value,
    checkin: checkin.value,
    checkout: checkout.value,
  });
};
</script>
