<template>
  <div class="item">
    <div class="item-bg">
      <div class="item-header-text">Home Planner</div>
      <div class="item-header-subtext">내 집 마련, 언제까지 생각만 할껀가요?</div>
      <v-container>
        <v-row align="center" justify="center">
          <v-sheet rounded="xl" class="item-header-card" color="white" elevation="8" height="185" width="825">
            <v-container fill-height fluid>
              <v-text-field
              class="mt-5 ml-2 mr-2"
                v-model="aptName"
                solo
                label="아파트를 검색해보세요."
              ></v-text-field>
              <v-row align="center" justify="center">
                <v-col cols="3">
                  <v-select v-model="sidoName" :items="sidoList" label="시도 선택" @change="makeGugunList"></v-select>
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
                  <v-select v-model="dongName" :items="dongList" label="읍면동 선택"></v-select>
                </v-col>
              </v-row>
            </v-container>
          </v-sheet>
        </v-row>
      </v-container>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

export default {
  name: "HomeView",

  data() {
    return {
      sidoName: "",
      gugunName: "",
      dongName: "",
    };
  },

  create() {
    this.CLEAR_SIDO_LIST();
    this.CLEAR_GUGUN_LIST();
    this.CLEAR_DONG_LIST();
    // this.searchSidoList();
  },
  computed: {
    ...mapState(["sidoList", "gugunList", "dongList"]),
  },
  mounted() {
    this.searchSidoList();
  },
  methods: {
    ...mapActions(["searchSidoList", "searchGugunList", "searchDongList"]),
    ...mapMutations(["CLEAR_SIDO_LIST", "CLEAR_GUGUN_LIST", "CLEAR_DONG_LIST"]),

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
  },
};
</script>

<style>
.item-bg {
  width: 100%;
  height: 557px;
  background-image: url(../assets/backgroundImage.jpg);
  background-position: center;
}

.item-header-text {
  position: relative;
  top: 104px;
  font-size: 72px;
  font-weight: bold;
  text-align: center;
  text-shadow: 0px 2px 9px rgba(0, 0, 0, 0.8);
  color: white;
}

.item-header-subtext {
  position: relative;
  top: 108px;
  text-align: center;
  color: white;
  font-size: 20px;
  text-shadow: 0px 1px 4px rgba(0, 0, 0, 0.85);
}
.item-header-card {
  position: relative;
  top: 140px;
}
</style>
