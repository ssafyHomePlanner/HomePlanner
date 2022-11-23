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
          readonly
        ></v-text-field>
      </v-row>

      <v-row>
        <h3>현재 시세(최근 실거래가 기준):</h3>
      </v-row>
      <v-row>
        <v-text-field solo style="max-width: 300px" v-model="maxHouseDeal" readonly></v-text-field>
      </v-row>
      <v-row class="planner-item-middle-text" justify="start"> 언제쯤 내 집 마련을 하고 싶나요? </v-row>
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
              type="month"
              locale="kr"
              v-model="hopedDate"
              :active-picker.sync="activePicker"
              :max="getEndDate"
              :min="new Date(Date.now() - new Date().getTimezoneOffset() * 60000).toISOString().substr(0, 10)"
              :allowed-dates="disablePastDates"
              @change="save"
            ></v-date-picker>
          </v-menu>
        </div>
      </v-row>
      <v-row class="planner-item-middle-text" justify="start"> 현재 주택 구매 예산은 얼마나 있나요? </v-row>
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
          v-model="loanAmount"
        ></v-text-field>
      </v-row>
      <v-row style="max-width: 500px">
        <v-btn color="primary" block @click="moveResult">검색</v-btn>
      </v-row>
    </v-col>

    <v-col class="">
      <AptSearchTab v-on:clickLikeApartment="clickLikeApartment" v-on:enterApartment="enterApartment" />
    </v-col>
  </v-container>
</template>

<script>
import AptSearchTab from "@/components/apt/AptSearchTab.vue";
import { mapState, mapActions, mapMutations } from "vuex";

const houseInfoStore = "houseInfoStore";
const plannerStore = "plannerStore";

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
    loanAmount: "",
  }),
  computed: {
    ...mapState(houseInfoStore, ["houseInfoStore"]),
    ...mapState(plannerStore, ["plannerInfo"]),
    houseDealList() {
      // console.log("computed house dealList", this.$store.state.houseInfoStore.houseDealList);
      return 0;
    },
    maxHouseDeal() {
      if (this.aptName == "") {
        return 0;
      }
      return this.$store.state.houseInfoStore.houseDealList[0].dealAmount;
    },
    getEndDate() {
      var endDate = new Date(new Date().getFullYear(), new Date().getMonth() + 100, 10);
      return endDate.toISOString().slice(0, 10);
    },
  },
  mounted() {
    if (this.plannerInfo != null) {
      // this.getHouseInfoDeal(plannerInfo.aptCode);
      this.aptName = this.plannerInfo.aptName;
      this.aptAmount = this.$store.state.houseInfoStore.houseDealList[0].dealAmount;
      this.hopedDate = this.plannerInfo.hopedDate;
      this.budget = this.plannerInfo.budget;
      this.savingPerMonth = this.plannerInfo.savingPerMonth;
      this.loanAmount = this.plannerInfo.loanAmount;
      this.CLEAR_PLANNER_INFO();
    }
  },

  watch: {
    houseDealList(val) {
      console.log("watch", val);
    },
    menu(val) {
      val && setTimeout(() => (this.activePicker = "YEAR"));
    },
  },
  methods: {
    ...mapActions(houseInfoStore, ["getHouseInfoDeal"]),
    ...mapMutations(plannerStore, ["SEARCH_PLANNER_INFO", "CLEAR_PLANNER_INFO"]),

    moveResult() {
      this.CLEAR_PLANNER_INFO();

      const plannerInfo = {
        aptName: this.aptName,
        aptAmount: this.$store.state.houseInfoStore.houseDealList[0].dealAmount,
        hopedDate: this.hopedDate,
        budget: this.budget,
        savingPerMonth: this.savingPerMonth,
        loanAmount: this.loanAmount,
      };

      this.SEARCH_PLANNER_INFO(plannerInfo);

      console.log("plannerInfo : ", this.plannerInfo);
      this.$router.push({ name: "plannerResult" }).catch(() => {});
    },
    save(date) {
      this.$refs.menu.save(date);
    },
    async clickLikeApartment(location) {
      this.aptName = location.apartmentName;
      await this.changeAptAmount(location.aptCode);
    },

    changeAptAmount(aptCode) {
      this.getHouseInfoDeal(aptCode);
    },

    async enterApartment(aptObject) {
      console.log("enterApartment = ", aptObject);
      this.aptName = aptObject.apartmentName;
      await this.changeAptAmount(aptObject.aptCode);
    },
    disablePastDates(val) {
      return val >= new Date().toISOString().substr(0, 10);
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
