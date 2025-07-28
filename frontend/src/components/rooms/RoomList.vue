<template>
  <div class="p-8">
    <h2 class="text-3xl font-semibold text-blue-700 border-b border-blue-300 pb-3 mb-6">
      اتاق‌ها
    </h2>
    <div class="space-y-6">
      <RoomCard
        v-for="room in rooms"
        :key="room.id"
        :room="room"
        @reserve="$emit('reserve', room.id)"
      />
    </div>
  </div>
</template>

<script setup>
import RoomCard from '@/components/rooms/RoomCard.vue';

defineProps({
  rooms: {
    type: Array,
    required: true
  }
});

defineEmits(['reserve']);
</script>
