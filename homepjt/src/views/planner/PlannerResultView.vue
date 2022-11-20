<template>
  <v-container fill-height fluid class="ma-12">
    <v-row class="planner-result-item-header-text">
      압구정 현대아파트 예상 구매 시기는 (내가 예상한 시기보다 1년 3개월 늦은)
    </v-row>
    <v-row class="planner-result-item-middle-text">
      '2030년 10월'입니다.
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
          <th class="text-left">
            연평균수익률
          </th>
          <th class="text-left">
            1%
          </th>
        </tr>
      </thead>
      <tbody>
        <tr
          v-for="item in desserts"
          :key="item.name"
        >
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
export default {};
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
