<template>
  <div class="item">
    <div class="item-bg">
      <div class="item-header-text">Home Planner</div>
      <div class="item-header-subtext">
        내 집 마련, 언제까지 생각만 할껀가요?
      </div>
      <v-container>
        <v-row align="center" justify="center">
          <v-sheet
            rounded="xl"
            class="item-header-card"
            color="white"
            elevation="8"
            height="185"
            width="825"
          >
            <v-container fill-height fluid>
              <v-text-field
                class="mt-5 ml-8 mr-8"
                v-model="aptName"
                solo
                label="아파트를 검색해보세요."
              ></v-text-field>
              <v-row align="center" justify="center">
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
            </v-container>
          </v-sheet>
        </v-row>
      </v-container>
    </div>
    <v-container class="mt-5 mb-10 text-center" >
      <div class="item-middle-text">아파트 구매 계획 세우기</div>
      <div class="item-middle-subtext mt-2">원하는 아파트를 찾으셨나요?</div>
      <v-container class="mt-8 mb-8">
        <v-row justify="space-between">
            <v-sheet height="278" width="214" elevation="4" class="pt-7">
              <v-row justify="center" class="mt-5">
                <v-icon size="109" color="green darken-2"> mdi-bank </v-icon>
              </v-row>
              <v-row class="item-middle-box-text mt-4" justify="center">
                저축 계획 세우기
              </v-row>
              <v-row justify="center" class="item-middle-box-subtext mt-4">
                내가 찜한 아파트를 목표로 <br />
                저축 계획 세우기
              </v-row>
            </v-sheet>
            <v-sheet height="278" width="214" elevation="4" class="pt-7">
              <v-row justify="center" class="mt-5">
                <v-icon size="109" color="red darken-2"> mdi-check </v-icon>
              </v-row>
              <v-row class="item-middle-box-text mt-4" justify="center">
                계획 점검하기
              </v-row>
              <v-row justify="center" class="item-middle-box-subtext mt-4">
                내가 목표로 한 저축 계획이 <br />
                실천할 수 있는 계획인지 체크
              </v-row>
            </v-sheet>
            <v-sheet height="278" width="214" elevation="4" class="pt-7">
              <v-row justify="center" class="mt-5">
                <v-icon size="109" color="yellow darken-2"> mdi-clock-outline </v-icon>
              </v-row>
              <v-row class="item-middle-box-text mt-4" justify="center">
                계획 실천하기
              </v-row>
              <v-row justify="center" class="item-middle-box-subtext mt-4">
                내 계획을 잘 지키고 있는지 <br />
                지속적으로 체크하기
              </v-row>
            </v-sheet>
            <v-sheet height="278" width="214" elevation="4" class="pt-7">
              <v-row justify="center" class="mt-5">
                <v-icon size="109" color="blue darken-2"> mdi-home </v-icon>
              </v-row>
              <v-row class="item-middle-box-text mt-4" justify="center">
                내 집 마련 성공!!
              </v-row>
              <v-row justify="center" class="item-middle-box-subtext mt-4">
                드디어 내가 원하던 집을 <br />
                구매했다!
              </v-row>
            </v-sheet>
        </v-row>
      </v-container>
      <v-container fluid style="max-width: 600px;">
        <v-btn outlined block class="mt-8">구매 계획 세우러 가기</v-btn>
      </v-container>
    </v-container>
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
  font-size: 80px;
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
  font-size: 25px;
  text-shadow: 0px 1px 4px rgba(0, 0, 0, 0.85);
}
.item-header-card {
  position: relative;
  top: 140px;
}

.item-middle-text {
  font-weight: bold;
  text-align: center;
  font-size: 27px;
}
.item-middle-subtext {
  font-weight: bold;
  text-align: center;
  font-size: 16px;
  color: #9f9999;
}

.item-middle-box-text {
  font-weight: bold;
  text-align: center;
  font-size: 16px;
}
.item-middle-box-subtext {
  text-align: center;
  font-size: 14px;
  color: #958E8E;
}
</style>
