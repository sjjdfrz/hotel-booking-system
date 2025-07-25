<template>
  <label class="block w-full">
    <span class="text-gray-700 font-medium mb-1 block">{{ label }}</span>
    <input
      :type="type"
      :value="modelValue"
      @input="$emit('update:modelValue', $event.target.value)"
      :required="required"
      class="block w-full rounded-lg border border-gray-400 focus:border-blue-500 focus:ring-2 focus:ring-blue-300 focus:outline-none px-4 py-2 text-gray-800 placeholder-gray-400 transition"
      :class="inputClass"
    />
  </label>
</template>

<script setup>
defineProps({
  label: String,
  modelValue: [String, Number],
  type: {
    type: String,
    default: 'text',
  },
  required: Boolean,
  inputClass: String,
});

defineEmits(['update:modelValue']);
</script>
