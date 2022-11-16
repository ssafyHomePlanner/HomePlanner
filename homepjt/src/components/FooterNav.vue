<template>
  <v-footer dark>
    <v-card class="flex" tile>
      <v-card-text class="py-2 white--text text-center">
        <strong>@2020 made by 최규림, 최정온</strong>

        <v-btn v-for="icon in icons" :key="icon" class="mx-4" dark icon>
          <v-icon size="24px">
            {{ icon }}
          </v-icon>
        </v-btn>
      </v-card-text>
    </v-card>
  </v-footer>
</template>

<script>
export default {
  data() {
    return {
      icons: ["mdi-github"],
    };
  },
};
</script>

<style></style>
