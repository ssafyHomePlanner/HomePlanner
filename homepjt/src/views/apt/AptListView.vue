<template>
  <v-container fill-height fluid class="ma-5">
    <v-row justify="center">
      <v-sheet
        height="360"
        width="1000"
        elevation="4"
        rounded="xl"
        class="pt-3"
      >
        <v-row class="ma-1">
          <v-col cols="auto" align-self="center" style="min-width: 100px">
            아파트 검색
          </v-col>
          <v-col align-self="center">
            <v-text-field hide-details class="mt-0 pt-0"></v-text-field>
          </v-col>
        </v-row>
        <v-divider></v-divider>
        <v-row class="ma-1">
          <v-col cols="auto" align-self="center" style="min-width: 100px">
            지역 선택
          </v-col>
          <v-row justify="start" class="ml-3">
            <v-col cols="3">
              <v-select
                v-model="sidoName"
                :items="sidoList"
                label="시도 선택"
                @change="makeGugunList"
              ></v-select>
            </v-col>
            <v-col cols="3">
              <v-select
                v-model="gugunName"
                :items="gugunList"
                label="시군구 선택"
                @change="makeDongList"
              ></v-select>
            </v-col>
            <v-col cols="3">
              <v-select
                v-model="dongName"
                :items="dongList"
                label="읍면동 선택"
              ></v-select>
            </v-col>
          </v-row>
        </v-row>
        <v-divider></v-divider>
        <v-row class="ma-1">
          <v-col cols="auto" align-self="center" style="min-width: 100px">
            가격
          </v-col>
          <v-col align-self="center">
            <v-range-slider
              hint="Im a hint"
              max="50"
              min="-50"
              step="20"
              ticks="always"
              tick-size="4"
              hide-details
            ></v-range-slider>
          </v-col>
        </v-row>
        <v-divider></v-divider>
        <v-row class="ma-1">
          <v-col cols="auto" align-self="center" style="min-width: 100px">
            전용 면적
          </v-col>
          <v-col align-self="center">
            <v-range-slider
              hint="Im a hint"
              max="50"
              min="-50"
              step="20"
              ticks="always"
              tick-size="4"
              hide-details
            ></v-range-slider>
          </v-col>
        </v-row>
        <v-divider></v-divider>
        <v-row class="ma-1">
          <v-col cols="auto" align-self="center" style="min-width: 100px">
            건축년도
          </v-col>
          <v-col align-self="center">
            <v-range-slider
              hint="Im a hint"
              max="50"
              min="-50"
              step="20"
              ticks="always"
              tick-size="4"
              hide-details
            ></v-range-slider>
          </v-col>
        </v-row>
      </v-sheet>
    </v-row>
    <v-row>
      <v-btn @click="moveResultPage" color="primary">아파트 검색 결과</v-btn>
    </v-row>
    <v-row justify="center" class="mt-12">
      <v-col class="ml-8 mr-8 pl-8 pr-8">
        <v-data-table
          :headers="headers"
          :items="desserts"
          :items-per-page="5"
          class="elevation-1"
        ></v-data-table>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  methods: {
    moveResultPage() {
      this.$router.push({ name: "aptResultView" });
    },
  },
  data() {
    return {
      headers: [
        {
          text: "Dessert (100g serving)",
          align: "start",
          sortable: false,
          value: "name",
        },
        { text: "Calories", value: "calories" },
        { text: "Fat (g)", value: "fat" },
        { text: "Carbs (g)", value: "carbs" },
        { text: "Protein (g)", value: "protein" },
        { text: "Iron (%)", value: "iron" },
      ],
      desserts: [
        {
          name: "Frozen Yogurt",
          calories: 159,
          fat: 6.0,
          carbs: 24,
          protein: 4.0,
          iron: "1%",
        },
        {
          name: "Ice cream sandwich",
          calories: 237,
          fat: 9.0,
          carbs: 37,
          protein: 4.3,
          iron: "1%",
        },
        {
          name: "Eclair",
          calories: 262,
          fat: 16.0,
          carbs: 23,
          protein: 6.0,
          iron: "7%",
        },
        {
          name: "Cupcake",
          calories: 305,
          fat: 3.7,
          carbs: 67,
          protein: 4.3,
          iron: "8%",
        },
        {
          name: "Gingerbread",
          calories: 356,
          fat: 16.0,
          carbs: 49,
          protein: 3.9,
          iron: "16%",
        },
        {
          name: "Jelly bean",
          calories: 375,
          fat: 0.0,
          carbs: 94,
          protein: 0.0,
          iron: "0%",
        },
        {
          name: "Lollipop",
          calories: 392,
          fat: 0.2,
          carbs: 98,
          protein: 0,
          iron: "2%",
        },
        {
          name: "Honeycomb",
          calories: 408,
          fat: 3.2,
          carbs: 87,
          protein: 6.5,
          iron: "45%",
        },
        {
          name: "Donut",
          calories: 452,
          fat: 25.0,
          carbs: 51,
          protein: 4.9,
          iron: "22%",
        },
        {
          name: "KitKat",
          calories: 518,
          fat: 26.0,
          carbs: 65,
          protein: 7,
          iron: "6%",
        },
      ],
    };
  },
};
</script>

<style></style>
