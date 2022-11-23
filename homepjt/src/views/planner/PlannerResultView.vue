<template>
  <v-container fill-height fluid class="ma-12">
    <v-row class="planner-result-item-header-text">
      {{ this.aptName }} 예상 구매 시기는 희망 구매 시기보다

      <h3>{{ calculatedDate }}</h3>
    </v-row>
    <v-row class="planner-result-item-middle-text">
      <strong>{{ resultDate }}</strong> 입니다.
      <v-btn class="mt-4" color="primary" @click="savePlannerInfo"
        ><v-icon>mdi-content-save</v-icon>플래너 저장</v-btn
      >
    </v-row>
    <v-row justify="start" class="mb-2">
      <v-divider></v-divider>
    </v-row>

    <!-- <v-col>
      <LineChart :chart-data="charData" />
    </v-col> -->
  </v-container>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
// import LineChart from "@/components/chart/LineChart.vue";

const plannerStore = "plannerStore";
const memberStore = "memberStore";

export default {
  components: {
    // LineChart,
  },
  data() {
    return {
      aptCode: "",
      aptName: "",
      aptAmount: 0,
      hopedDate: "",
      budget: "",
      savingPerMonth: "",
      loanAmount: "",

      calculatedDate: "",
      resultDate: "",
    };
  },
  mounted() {
    console.log(this.plannerInfo);
    this.aptCode = this.plannerInfo.aptCode;
    this.aptName = this.plannerInfo.aptName;
    this.aptAmount = parseFloat(this.plannerInfo.aptAmount.replace(",", ""));
    this.hopedDate = this.plannerInfo.hopedDate;
    this.budget = parseFloat(this.plannerInfo.budget);
    this.savingPerMonth = parseFloat(this.plannerInfo.savingPerMonth);
    this.loanAmount = parseFloat(this.plannerInfo.loanAmount);

    let myMoney = this.budget + this.loanAmount;

    if (myMoney >= this.aptAmount) {
      console.log("지금 구매 가능 : ", myMoney - this.aptAmount);
      if (this.savingPerMonth == 0) {
        this.savingPerMonth = 1;
      }
      this.calculatedDate = "현재 구매 가능";
    } else {
      console.log("지금 구매 불가능");
      let needTime = parseInt((this.aptAmount - myMoney) / this.savingPerMonth);
      // this.calculatedDate = needTime + "개월 뒤인";

      console.log("need time = ", needTime);
      let todayYear = new Date().getFullYear();
      let todayMonth = new Date().getMonth() + 1;
      let todayMonths = todayYear * 12 + todayMonth;
      let calcMonths = todayMonths + needTime;
      console.log("calcMonths = ", calcMonths);

      let hopeDate = this.hopedDate.split("-");
      let hopeDayYear = parseInt(hopeDate[0]);
      let hopeDayMonth = parseInt(hopeDate[1]);
      let hopeDayMonths = hopeDayYear * 12 + hopeDayMonth;
      console.log("hopeDayMonths = ", hopeDayMonths);

      if (calcMonths < hopeDayMonths) {
        this.calculatedDate = hopeDayMonths - calcMonths;
        this.calculatedDate += " 개월 빠른";
      } else {
        this.calculatedDate = calcMonths - hopeDayMonths;
        this.calculatedDate += " 개월 느린";
      }

      let resultYear = parseInt(calcMonths / 12);
      console.log(Math.floor(calcMonths / 12));
      console.log(parseFloat(calcMonths / 12));
      let resultMonth = parseInt(calcMonths % 12);
      if (resultMonth == 0) {
        resultYear -= 1;
        resultMonth = 12;
      }

      console.log(resultYear + "년 " + resultMonth + "월");
      this.resultDate = resultYear + "년 " + resultMonth + "월";
      // this.resultDate = year + "년 " + month + "월 입니다.";
    }
  },
  computed: {
    ...mapState(plannerStore, ["plannerInfo"]),
    ...mapState(memberStore, ["userInfo"]),

    charData() {
      let labelList = [];
      let dataList = [];

      this.$store.state.houseInfoStore.houseDealList.forEach((houseDealData) => {
        let dateTransfer =
          houseDealData.dealYear.toString() +
          "년 " +
          houseDealData.dealMonth.toString().padStart(2, "0") +
          "월 " +
          houseDealData.dealDay.toString().padStart(2, "0") +
          "일";

        labelList.push(dateTransfer);
        dataList.push(houseDealData.dealAmount.replace(",", ""));
      });

      let chartData = {
        labels: labelList,
        datasets: [
          {
            label: "거래 내역",
            backgroundColor: "#f87979",
            data: dataList,
          },
        ],
      };

      return chartData;
    },
  },
  methods: {
    ...mapMutations(plannerStore, ["SEARCH_PLANNER_INFO", "CLEAR_PLANNER_INFO"]),
    ...mapActions(plannerStore, ["createPlannerInfo"]),
    castStringToInteger(num) {
      return parseFloat(num);
    },
    savePlannerInfo() {
      const payload = {
        aptAmount: this.aptAmount,
        aptCode: this.aptCode,
        aptName: this.aptName,
        budget: this.budget,
        expectedDate: "",
        hopedDate: this.hopedDate,
        loanAmount: this.loanAmount,
        memberId: this.userInfo.id,
        savingPerMonth: this.savingPerMonth,
        writeDate: "",
      };

      console.log("payload = ", payload);
      this.createPlannerInfo(payload);
    },
  },
};
</script>

<style>
.planner-result-item-header-text {
  font-weight: bold;
  text-align: center;
  font-size: 23px;
  margin-bottom: 3px;
}
.planner-result-item-middle-text {
  text-align: center;
  font-size: 20px;
  margin-bottom: 3px;
}
</style>
