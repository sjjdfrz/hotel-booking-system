<template>
  <p class="text-center text-gray-500 mt-10 text-xl italic">
    {{ message }}
  </p>
</template>

<script setup>
defineProps({
  message: {
    type: String,
    default: 'موردی یافت نشد',
  },
});
</script>
