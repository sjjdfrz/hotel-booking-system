<template>
  <section
    class="relative h-[80vh] bg-cover bg-center flex items-center justify-center text-white"
    style="background-image: url('./images/hero.jpg')"
  >
    <div class="text-center">
      <HeroTextBlock />
      <HotelSearchForm @search="$emit('search', $event)" />
    </div>
  </section>
</template>

<script setup>
import HeroTextBlock from '@/components/home/HeroTextBlock.vue';
import HotelSearchForm from '@/components/home/HotelSearchForm.vue';

defineEmits(['search']);
</script>
