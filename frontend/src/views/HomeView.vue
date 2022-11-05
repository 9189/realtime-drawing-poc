<script setup lang="ts">
import {onMounted, onUnmounted, reactive, Ref, ref} from "vue";
import drawClient from "@/api/draw-client";
import type {Line} from "@/models/line";

const canvasRef: Ref<HTMLCanvasElement | null> = ref(null);
let context: any;
const coordinates = reactive({x: 0, y: 0});
let isDrawing = false;
let connection: WebSocket | undefined;

onMounted(() => {
  context = canvasRef.value?.getContext('2d');
  connection = drawClient.connect('joe');
  connection.onopen = () => {
    onMessageDrawLine();
  }
});

onUnmounted(() => {
  connection?.close();
})

function draw(event: MouseEvent) {
  if (!isDrawing) return;

  drawLine(coordinates.x, coordinates.y, event.offsetX, event.offsetY);
  sendLine(coordinates.x, coordinates.y, event.offsetX, event.offsetY);

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

function drawLine(previousX: number, previousY: number, x: number, y: number, color: string = 'black') {
  context.beginPath();
  context.strokeStyle = color;
  context.lineWidth = 1;
  context.moveTo(previousX, previousY);
  context.lineTo(x, y);
  context.stroke();
  context.closePath();
}

function sendLine(previousX: number, previousY: number, x: number, y: number) {
  const line: Line = {
    startingPoint: {x: previousX, y: previousY},
    endPoint: {x: x, y: y}
  };

  connection?.send(JSON.stringify(line));
}

function onMessageDrawLine() {
  if (!connection) return;

  connection.onmessage = (message: MessageEvent<string>) => {
    const line = JSON.parse(message.data);

    const start = line.startingPoint;
    const end = line.endPoint;

    console.log(message.data);
    drawLine(start.x, start.y, end.x, end.y, 'red');
  }
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
