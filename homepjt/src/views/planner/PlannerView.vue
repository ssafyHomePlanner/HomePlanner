<template>
  <v-container fill-height fluid class="ma-12">
    <v-col>
      <v-row class="planner-item-middle-text" justify="start"> 목표 아파트를 설정하세요 </v-row>
      <v-row>
        <v-text-field
          solo
          style="max-width: 300px"
          label="아파트 검색"
          hint="아파트명을 검색해보세요."
          v-model="aptName"
        ></v-text-field>
      </v-row>
      <v-row class="mb-1">
        <h3>목표 아파트:</h3>
      </v-row>
      <v-row>
        <h3>현재 시세(최근 실거래가 기준):</h3>
      </v-row>
      <v-row>
        <v-text-field solo style="max-width: 300px" v-model="aptAmount"></v-text-field>
      </v-row>
      <v-row class="planner-item-middle-text" justify="start">
        언제쯤 내 집 마련을 하고 싶나요?
      </v-row>
      <v-row class="mb-5">
        <div>
          <v-menu
            ref="menu"
            v-model="menu"
            :close-on-content-click="false"
            transition="scale-transition"
            offset-y
            min-width="auto"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                v-model="hopedDate"
                label="구매 예정 날짜"
                prepend-icon="mdi-calendar"
                readonly
                v-bind="attrs"
                v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker
              v-model="hopedDate"
              :active-picker.sync="activePicker"
              :max="
                new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
                  .toISOString()
                  .substr(0, 10)
              "
              min="1950-01-01"
              @change="save"
            ></v-date-picker>
          </v-menu>
        </div>
      </v-row>
      <v-row class="planner-item-middle-text" justify="start">
        현재 주택 구매 예산은 얼마나 있나요?
      </v-row>
      <v-row>
        <v-text-field
          solo
          style="max-width: 300px"
          label="주택 구매 예산"
          hint="없으면 0을 적어주세요 (단위, 만원)"
          v-model="budget"
        ></v-text-field>
      </v-row>
      <v-row class="planner-item-middle-text" justify="start">
        현재 주택 구매를 위한 저축(투자 포함)은 월에 얼마씩 하고 있나요?
      </v-row>
      <v-row>
        <v-text-field
          solo
          style="max-width: 300px"
          label="월 저축금액"
          hint="없으면 0을 적어주세요 (단위, 만원)"
          v-model="savingPerMonth"
        ></v-text-field>
      </v-row>
      <v-row class="planner-item-middle-text" justify="start"> 대출이 가능하신가요? </v-row>
      <v-row>
        <v-text-field
          solo
          style="max-width: 300px"
          label="대출 가능금액"
          hint="없으면 0을 적어주세요 (단위, 만원)"
          v-model="loan"
        ></v-text-field>
      </v-row>
      <v-row style="max-width: 500px">
        <v-btn color="primary" block @click="moveResult">검색</v-btn>
      </v-row>
    </v-col>

    <v-col class="">
      <AptSearchTab
        v-on:clickLikeApartment="clickLikeApartment"
        v-on:enterApartment="enterApartment"
      />
    </v-col>
  </v-container>
</template>

<script>
import AptSearchTab from "@/components/apt/AptSearchTab.vue";
import { mapState, mapActions } from "vuex";

const houseInfoStore = "houseInfoStore";

export default {
  components: {
    AptSearchTab,
  },

  data: () => ({
    activePicker: null,
    date: null,
    menu: false,

    aptName: "",
    aptAmount: 0,
    hopedDate: "",
    budget: "",
    savingPerMonth: "",
    loan: "",
  }),
  computed: {
    ...mapState(houseInfoStore, ["houseInfoStore", "houseDealList"]),
  },

  watch: {
    menu(val) {
      val && setTimeout(() => (this.activePicker = "YEAR"));
    },
  },
  methods: {
    ...mapActions(houseInfoStore, ["getHouseInfoDeal"]),

    moveResult() {
      this.$router.push({ name: "plannerResult" }).catch(() => {});
    },
    save(date) {
      this.$refs.menu.save(date);
    },
    async clickLikeApartment(location) {
      console.log("location : ", location);
      this.aptName = location.apartmentName;
      console.log("before = ", this.houseDealList);
      await this.changeAptAmount(location.aptCode);
      console.log("after = ", this.houseDealList);

      this.aptAmount = this.houseDealList[0].dealAmount;
    },

    changeAptAmount(aptCode) {
      this.getHouseInfoDeal(aptCode);
      console.log("after2 = ", this.houseDealList);
    },

    enterApartment(aptObject) {
      this.aptName = aptObject.apartmentName;
    },
  },
};
</script>

<style>
.planner-item-middle-text {
  font-weight: bold;
  text-align: center;
  font-size: 22px;
  margin-bottom: 3px;
}
</style>
