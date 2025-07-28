<template>
  <div class="bg-white p-6 rounded-xl shadow-lg max-w-5xl mx-auto mb-10">
    <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-4">
      <div>
        <label class="block text-sm font-semibold text-gray-700 mb-1">حداقل قیمت</label>
        <input
          v-model.number="filters.priceFrom"
          type="number"
          placeholder="تومان"
          class="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>

      <div>
        <label class="block text-sm font-semibold text-gray-700 mb-1">حداکثر قیمت</label>
        <input
          v-model.number="filters.priceTo"
          type="number"
          placeholder="تومان"
          class="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
        />
      </div>

      <div>
        <label class="block text-sm font-semibold text-gray-700 mb-1">تعداد ستاره</label>
        <select
          v-model.number="filters.stars"
          class="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
        >
          <option value="0">همه</option>
          <option v-for="s in 5" :key="s" :value="s">{{ s }} ⭐</option>
        </select>
      </div>

      <div>
        <label class="block text-sm font-semibold text-gray-700 mb-1">مرتب‌سازی</label>
        <select
          v-model="filters.sort"
          class="w-full border border-gray-300 rounded-lg px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"
        >
          <option value="rate">امتیاز</option>
          <option value="price">ارزان‌ترین</option>
          <option value="price,desc">گران‌ترین</option>
        </select>
      </div>

      <div class="flex items-end">
        <button
          @click="applyFilters"
          class="w-full bg-blue-600 text-white py-2 rounded-lg hover:bg-blue-700 transition font-semibold"
        >
          اعمال فیلتر
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue';

const emit = defineEmits(['apply-filters']);

const filters = reactive({
  priceFrom: 0,
  priceTo: 0,
  stars: 0,
  sort: 'rate',
});

const applyFilters = () => {
  emit('apply-filters', filters);
};
</script>
