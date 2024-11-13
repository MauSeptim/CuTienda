<template>
  <div class="custom-radio-group">
    <div
      v-for="option in options"
      :key="option.id"
      class="custom-radio-item"
      :class="{ selected: modelValue === option.value }"
    >
      <input
        class="radio-input"
        type="radio"
        :name="name"
        :value="option.value"
        :checked="modelValue === option.value"
        @change="updateValue(option.value)"
        :id="option.id"
      />
      <label
        class="radio-label"
        :for="option.id"
      >
        {{ option.label }}
      </label>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CustomRadio',
  props: {
    options: {
      type: Array,
      required: true
    },
    name: {
      type: String,
      required: true
    },
    modelValue: {
      type: String,
      required: true
    }
  },
  methods: {
    updateValue(value) {
      this.$emit('update:modelValue', value);  // Emitimos el evento para actualizar el modelo
    }
  }
};
</script>

<style scoped>
.custom-radio-group {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 1.5rem;
}

.custom-radio-item {
  position: relative;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 0.8em;
  cursor: pointer;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.custom-radio-item.selected .radio-label {
  background-color: #4189e0;
  color: #ffffff;
  transform: scale(1.05);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.radio-input {
  display: none;
}

.radio-label {
  position: relative;
  color: #4189e0;
  font-family: "Poppins", sans-serif;
  cursor: pointer;
  padding: 1em 2em;
  border: 3px solid #4189e0;
  border-radius: 50em;
  display: inline-flex;
  align-items: center;
  gap: 0.8em;
  transition: all 0.3s ease;
  font-weight: bold;
  text-transform: capitalize;
}

.radio-label:before {
  content: "";
  height: 1.2em;
  width: 1.2em;
  border-radius: 50%;
  border: 3px solid #4189e0;
  background-color: transparent;
  transition: all 0.3s ease;
}

.radio-input:checked + .radio-label:before {
  background-color: #4189e0;
  border-color: #4189e0;
}

.radio-label:hover {
  background-color: rgba(65, 137, 224, 0.1);
  transform: scale(1.03);
}

.radio-label:focus-within {
  outline: 2px solid #4189e0;
}

.radio-label:hover {
  background-color: rgba(65, 137, 224, 0.2);
}

</style>
