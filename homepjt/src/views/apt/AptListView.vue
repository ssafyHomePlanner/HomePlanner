<template>
  <v-container fill-height fluid class="ma-7">
    <v-row justify="center">
      <v-sheet
        height="400"
        width="1000"
        elevation="4"
        rounded="xl"
        class="pt-8"
      >
        <v-row class="ma-1">
          <v-col cols="auto" align-self="center" style="min-width: 100px">
            아파트 검색
          </v-col>
          <v-col align-self="center">
            <v-text-field
              @keyup.enter="clickSearchBtn"
              hide-details
              class="mt-0 pt-0"
              v-model="search"
            ></v-text-field>
          </v-col>
          <v-col cols="auto">
            <v-btn color="primary" @click="clickSearchBtn">검색</v-btn>
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
                v-model="sido"
                :items="sidoList"
                label="시도 선택"
                @change="makeGugunList"
              ></v-select>
            </v-col>
            <v-col cols="3">
              <v-select
                v-model="gugun"
                :items="gugunList"
                label="시군구 선택"
                @change="makeDongList"
              ></v-select>
            </v-col>
            <v-col cols="3">
              <v-select
                v-model="dong"
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
              v-model="priceRange"
              thumb-label="always"
              thumb-size="35"
              max="150"
              min="0"
              hide-details
            >
              <template v-slot:thumb-label="item">
                {{ item.value }}억
              </template>
            </v-range-slider>
          </v-col>
        </v-row>
        <v-divider></v-divider>
        <v-row class="ma-1">
          <v-col cols="auto" align-self="center" style="min-width: 100px">
            전용 면적(평)
          </v-col>
          <v-col align-self="center">
            <v-range-slider
              v-model="areaRange"
              thumb-label="always"
              thumb-size="35"
              max="130"
              min="0"
              hide-details
            >
              <template v-slot:thumb-label="item">
                {{ item.value }}평
              </template>
            </v-range-slider>
          </v-col>
        </v-row>
        <v-divider></v-divider>
        <v-row class="ma-1">
          <v-col cols="auto" align-self="center" style="min-width: 100px">
            건축년도
          </v-col>
          <v-col align-self="center">
            <v-range-slider
              v-model="buildYearRange"
              thumb-label="always"
              thumb-size="40"
              v-bind:max="currentYear"
              min="2000"
              hide-details
            >
              <template v-slot:thumb-label="item">
                {{ item.value }}년
              </template></v-range-slider
            >
          </v-col>
        </v-row>
      </v-sheet>
    </v-row>

    <v-row justify="center" class="mt-12 mb-12">
      <v-col class="ma-8 pl-8 pr-8">
        <v-data-table
          :height="600"
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
import { mapState, mapActions, mapMutations } from "vuex";

const aptStore = "aptStore";
const houseInfoStore = "houseInfoStore";

export default {
  methods: {
    clickSearchBtn() {
      const TransferPongToSquareMeter = 3.306;
      const TransferOneToMan = 10000;

      let minAreaSquareMeter = this.areaRange[0] * TransferPongToSquareMeter;
      let maxAreaSquareMeter = this.areaRange[1] * TransferPongToSquareMeter;

      let minDealAmountMan = this.priceRange[0] * TransferOneToMan;
      let maxDealAmountMan = this.priceRange[1] * TransferOneToMan;

      let serachedData = {
        aptName: this.search,
        dongName: this.dong,
        gugunName: this.gugun,
        sidoName: this.sido,
        minArea: minAreaSquareMeter,
        maxArea: maxAreaSquareMeter,
        minBuildYear: this.buildYearRange[0],
        maxBuildYear: this.buildYearRange[1],
        minDealAmount: minDealAmountMan,
        maxDealAmount: maxDealAmountMan,
        page: 1,
      };

      this.getHouseInfoDetailInfo(serachedData);
    },
    initData() {
      let today = new Date();
      this.currentYear = today.getFullYear(); // 년도

      this.sido = this.$store.state.aptStore.sidoName;
      this.gugun = this.$store.state.aptStore.gugunName;
      this.dong = this.$store.state.aptStore.dongName;
      this.search = this.$store.state.aptStore.searchedApartName;
    },
    clickRow(value) {
      this.searchHouseInfo(value);
      this.getHouseInfoDeal(value.aptCode);
      this.moveResultPage();
    },
    moveResultPage() {
      this.$router.push({ name: "aptResultView" }).catch(() => {});
    },
    makeGugunList() {
      this.CLEAR_GUGUN_LIST();
      this.CLEAR_DONG_LIST();

      this.gugun = "";
      this.dong = "";
      if (this.sido) this.searchGugunList(this.sido);
    },
    makeDongList() {
      this.CLEAR_DONG_LIST();
      this.dong = "";
      if (this.gugun != "") this.searchDongList(this.gugun);
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
      "getHouseInfoDetailInfo",
      "getHouseInfoDeal",
    ]),
    ...mapMutations(aptStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_DONG_LIST",
    ]),
  },
  computed: {
    ...mapState(aptStore, [
      "sidoList",
      "gugunList",
      "dongList",
      "sidoName",
      "gugunName",
      "dongName",
      "searchedApartName",
    ]),
    ...mapState(houseInfoStore, ["houseInfo", "houseInfoList"]),
  },
  mounted() {
    this.initData();
  },
  data() {
    return {
      sido: "",
      gugun: "",
      dong: "",
      search: "",
      currentYear: "2022",
      priceRange: [10, 50],
      areaRange: [10, 50],
      buildYearRange: [2010, 2020],
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
