<template>
  <v-container fill-height fluid class="ma-12">
    <v-row class="planner-item-middle-text" justify="start">
      목표 아파트를 설정하세요
    </v-row>
    <v-row>
      <v-text-field solo style="max-width: 300px" label="아파트 검색" hint="아파트명을 검색해보세요."></v-text-field>
    </v-row>
    <v-row class="mb-1">
      <h3>
        목표 아파트:
      </h3>
    </v-row>
    <v-row>
      <h3>
        현재 시세(최근 실거래가 기준):
      </h3>
    </v-row>
    <v-row>
      <v-text-field solo style="max-width: 300px"></v-text-field>
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
              v-model="date"
              label="구매 예정 날짜"
              prepend-icon="mdi-calendar"
              readonly
              v-bind="attrs"
              v-on="on"
            ></v-text-field>
          </template>
          <v-date-picker
            v-model="date"
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
      <v-text-field solo style="max-width: 300px" label="주택 구매 예산" hint="없으면 0을 적어주세요 (단위, 만원)"></v-text-field>
    </v-row>
    <v-row class="planner-item-middle-text" justify="start">
      현재 주택 구매를 위한 저축(투자 포함)은 월에 얼마씩 하고 있나요?
    </v-row>
    <v-row>
      <v-text-field solo style="max-width: 300px" label="월 저축금액" hint="없으면 0을 적어주세요 (단위, 만원)"></v-text-field>
    </v-row>
    <v-row class="planner-item-middle-text" justify="start">
      대출이 가능하신가요?
    </v-row>
    <v-row>
      <v-text-field solo style="max-width: 300px" label="대출 가능금액" hint="없으면 0을 적어주세요 (단위, 만원)"></v-text-field>
    </v-row>
    <v-row style="max-width: 500px" >
      <v-btn  color="primary" block @click="moveResult">검색</v-btn>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    activePicker: null,
    date: null,
    menu: false,
  }),
  watch: {
    menu(val) {
      val && setTimeout(() => (this.activePicker = "YEAR"));
    },
  },
  methods: {
    moveResult() {
      this.$router.push({ name: "plannerResult" });
    },
    save(date) {
      this.$refs.menu.save(date);
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
