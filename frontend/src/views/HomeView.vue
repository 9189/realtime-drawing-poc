<script setup lang="ts">
import {onMounted, reactive, Ref, ref} from "vue";

const canvasRef: Ref<HTMLCanvasElement | null> = ref(null);
let context: any;
const coordinates = reactive({x: 0, y: 0});
let isDrawing = false;

onMounted(() => {
  context = canvasRef.value?.getContext('2d');
});

function draw(event: MouseEvent) {
  if (!isDrawing) return;

  drawLine(coordinates.x, coordinates.y, event.offsetX, event.offsetY);
  coordinates.x = event.offsetX;
  coordinates.y = event.offsetY;
}

function beginDrawing(event: MouseEvent) {
  coordinates.x = event.offsetX;
  coordinates.y = event.offsetY;
  isDrawing = true;
}

function stopDrawing(event: MouseEvent) {
  if (!isDrawing) return;

  drawLine(coordinates.x, coordinates.y, event.offsetX, event.offsetY);
  coordinates.x = 0;
  coordinates.y = 0;
  isDrawing = false;
}

function drawLine(previousX: number, previousY: number, x: number, y: number) {
  context.beginPath();
  context.strokeStyle = 'black';
  context.lineWidth = 1;
  context.moveTo(previousX, previousY);
  context.lineTo(x, y);
  context.stroke();
  context.closePath();
}

</script>

<template>
  <main>
    <canvas @mousemove="draw($event)" @mousedown="beginDrawing($event)" @mouseup="stopDrawing($event)"
            ref="canvasRef"
            width="1920" height="1080"></canvas>
  </main>
</template>

<style lang="scss">
</style>
