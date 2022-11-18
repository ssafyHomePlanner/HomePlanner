<template>
  <v-container fill-height fluid class="ma-8">
    <v-container>
      <v-row justify="space-evenly">
        <v-col cols="auto" class="mr-5">
          <v-row justify="start">
            <v-col cols="auto">
              <v-row class="item-middle-box-text mt-4" justify="start">
                <div class="path-item-middle-text">출발지를 입력하세요</div>
              </v-row>
            </v-col>
            <v-col cols="auto" class="pa-0 mt-3">
              <v-icon align-self="start">mdi-help-circle-outline</v-icon>
            </v-col>
          </v-row>
          <v-row class="mt-6">
            <v-sheet
              color="white"
              elevation="1"
              height="45"
              width="270"
            ></v-sheet>
          </v-row>
        </v-col>
        <v-col cols="auto" class="mr-5 mt-8">
          <v-icon size="90" color="darken-2"> mdi-arrow-right </v-icon>
        </v-col>
        <v-col cols="auto" class="mr-5">
          <v-row justify="start">
            <v-col cols="auto">
              <v-row class="item-middle-box-text mt-4" justify="start">
                <div class="path-item-middle-text">도착지를 입력하세요</div>
              </v-row>
            </v-col>
            <v-col cols="auto" class="pa-0 mt-3">
              <v-icon align-self="start">mdi-help-circle-outline</v-icon>
            </v-col>
          </v-row>
          <v-row class="mt-6">
            <v-sheet
              color="white"
              elevation="1"
              height="45"
              width="270"
            ></v-sheet>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
    <v-container class="mt-12">
      <v-row>
        <v-col cols="auto" class="mr-5">
          <v-row justify="start">
            <v-col cols="auto">
              <v-row class="item-middle-box-text mt-4" justify="start">
                <div class="path-item-middle-text">아파트 경유지를 추가하세요</div>
              </v-row>
            </v-col>
            <v-col cols="auto" class="pa-0 mt-3">
              <v-icon align-self="start">mdi-help-circle-outline</v-icon>
            </v-col>
          </v-row>
        </v-col>
      </v-row>

      <v-row>
        
      </v-row>
    </v-container>
  </v-container>
</template>

<script>
export default {};
</script>

<style>
.path-item-middle-text {
  font-weight: bold;
  text-align: center;
  font-size: 22px;
}
</style>
