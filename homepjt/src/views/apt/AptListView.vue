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
            <v-text-field hide-details class="mt-0 pt-0" v-model="searchedApartName"></v-text-field>
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

    <v-row justify="center" class="mt-12 mb-12">
      <v-col class="ml-8 mr-8 pl-8 pr-8">
        <v-data-table
          :headers="headers"
          :items="houseInfoList"
          :items-per-page="10"
          @click:row="clickRow"
          class="elevation-1"
        ></v-data-table>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";

const aptStore = "aptStore";
const houseInfoStore = "houseInfoStore";

export default {
  methods: {
    clickRow(value) {
      console.log(value);
      this.searchHouseInfo(value);
      console.log("houseInfo", this.houseInfo);
      this.moveResultPage();
    },
    moveResultPage() {
      this.$router.push({ name: "aptResultView" }).catch(()=>{});
    },
    makeGugunList() {
      this.CLEAR_GUGUN_LIST();
      this.gugunName = "";
      if (this.sidoName) this.searchGugunList(this.sidoName);
    },
    makeDongList() {
      this.CLEAR_DONG_LIST();
      this.dongName = "";
      if (this.gugunName != "") this.searchDongList(this.gugunName);
      console.log("sidoName : ", this.sidoName);
      console.log("gugunName : ", this.gugunName);
    },
    ...mapActions(aptStore, [
      "searchSidoList",
      "searchGugunList",
      "searchDongList",
    ]),
    ...mapActions(houseInfoStore, [
      "getHouseInfoList",
      "getHouseInfoListAuto",
      "searchHouseInfo",
    ]),
  },
  computed: {
    ...mapState(aptStore, ["sidoList", "gugunList", "dongList", "sidoName", "gugunName", "dongName", "searchedApartName"]),
    ...mapState(houseInfoStore, ["houseInfo", "houseInfoList"]),
  },
  data() {
    return {
      headers: [
        { text: "건축년도", value: "buildYear" },
        { text: "아파트 이름", value: "apartmentName" },
        { text: "동이름", value: "dong" },
        { text: "조회수", value: "viewCnt" },
        { text: "좋아요", value: "likeCnt" },
      ],
    };
  },
};
</script>

<style></style>
