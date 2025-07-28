<template>
  <p>
    <strong class="text-blue-600">ستاره‌ها:</strong>
    <span class="text-yellow-400">
      <svg
        v-for="i in 5"
        :key="i"
        class="inline-block w-5 h-5 ml-1"
        :class="i <= stars ? 'text-yellow-400' : 'text-gray-300'"
        fill="currentColor"
        viewBox="0 0 20 20"
      >
        <path
          d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.286 3.96a1 1 0 00.95.69h4.178c.969 0 1.371 1.24.588 1.81l-3.39 2.46a1 1 0 00-.364 1.118l1.287 3.959c.3.92-.755 1.688-1.54 1.118L10 13.348l-3.39 2.46c-.784.57-1.838-.197-1.539-1.118l1.286-3.959a1 1 0 00-.364-1.118l-3.39-2.46c-.783-.57-.38-1.81.588-1.81h4.178a1 1 0 00.95-.69l1.287-3.96z"
        />
      </svg>
    </span>
  </p>
</template>

<script setup>
defineProps({
  stars: {
    type: Number,
    required: true,
    validator: (value) => value >= 0 && value <= 5,
  },
});
</script>
