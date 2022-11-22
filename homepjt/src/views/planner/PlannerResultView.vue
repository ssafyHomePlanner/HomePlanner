<template>
  <v-container fill-height fluid class="ma-12">
    <v-row class="planner-result-item-header-text">
      압구정 현대아파트 예상 구매 시기는

      <h3>{{ calculatedDate }}</h3>
    </v-row>
    <v-row class="planner-result-item-middle-text">
      <strong>{{ resultDate }}</strong> 입니다.
    </v-row>
    <v-row justify="start" class="mb-2">
      <v-divider></v-divider>
    </v-row>
    <v-row class="mb-2"> 아파트 구매 시기를 앞당기고 싶나요? </v-row>
    <v-row class="mb-2" justify="start">
      <v-col cols="auto" class="mt-4"> 저축 금액 수정하기 </v-col>
      <v-col align-self="center">
        <v-text-field outlined style="max-width: 180px"></v-text-field>
      </v-col>
    </v-row>
    <v-row>
      <v-simple-table>
        <template v-slot:default>
          <thead>
            <tr>
              <th class="text-left">연평균수익률</th>
              <th class="text-left">1%</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in desserts" :key="item.name">
              <td>{{ item.name }}</td>
              <td>{{ item.calories }}</td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </v-row>
  </v-container>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

const plannerStore = "plannerStore";

export default {
  data() {
    return {
      aptName: "",
      aptAmount: 0,
      hopedDate: "",
      budget: "",
      savingPerMonth: "",
      loan: "",

      calculatedDate: "",
      resultDate: "",
    };
  },
  mounted() {
    // console.log(this.plannerInfo);
    this.aptName = this.plannerInfo.aptName;
    this.aptAmount = parseFloat(this.plannerInfo.aptAmount) * 1000;
    this.hopedDate = this.plannerInfo.hopedDate;
    this.budget = parseFloat(this.plannerInfo.budget);
    this.savingPerMonth = parseFloat(this.plannerInfo.savingPerMonth);
    this.loan = parseFloat(this.plannerInfo.loan);

    let myMoney = this.budget + this.loan;

    if (myMoney >= this.aptAmount) {
      console.log("지금 구매 가능 : ", myMoney - this.aptAmount);
      if (this.savingPerMonth == 0) {
        this.savingPerMonth = 1;
      }
      // let needTime = parseInt((myMoney - this.aptAmount) / this.savingPerMonth);
      // this.calculatedDate = needTime + "개월 만큼 빠른";

      // let date = this.hopedDate.split("-");
      // let year = parseFloat(date[0]);
      // let month = parseFloat(date[1]);
      // console.log("hope date = ", this.hopedDate);
      // console.log("year = ", year);
      // console.log("month = ", month);

      // let totalMonth = year * 12 + month - needTime;
      // let newYear = parseInt(totalMonth / 12);
      // let newMonth = totalMonth - newYear * 12;
      // console.log("after ");
      // console.log("year = ", newYear);
      // console.log("month", newMonth);
      this.calculatedDate = "현재 구매 가능";
    } else {
      console.log("지금 구매 불가능");
      let needTime = parseInt((this.aptAmount - myMoney) / this.savingPerMonth);
      // this.calculatedDate = needTime + "개월 뒤인";

      let todayYear = new Date().getFullYear();
      let todayMonth = new Date().getMonth() + 1;
      let todayMonths = todayYear * 12 + todayMonth;
      console.log(todayMonths);
      let calcMonths = todayMonths + needTime;

      let hopeDayYear = new Date().getFullYear();
      let hopeDayMonth = new Date().getMonth() + 1;
      let hopeDayMonths = hopeDayYear * 12 + hopeDayMonth;

      if (calcMonths < hopeDayMonths) {
        this.calculatedDate = hopeDayMonths - calcMonths;
        this.calculatedDate += " 보다 개월 빠른";
      } else {
        this.calculatedDate = calcMonths - hopeDayMonths;
        this.calculatedDate += " 보다 개월 느린";
      }

      // this.resultDate = year + "년 " + month + "월 입니다.";
    }
  },
  computed: {
    ...mapState(plannerStore, ["plannerInfo"]),
  },
  methods: {
    ...mapMutations(plannerStore, ["SEARCH_PLANNER_INFO", "CLEAR_PLANNER_INFO"]),
    ...mapActions(plannerStore, ["createPlannerInfo"]),
    castStringToInteger(num) {
      return parseFloat(num);
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
