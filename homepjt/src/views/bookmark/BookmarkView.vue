<template>
  <div>
    <v-container class="bv-example-row mt-10">
      <h2>즐겨찾기 목록</h2>

      <v-tabs v-model="tab">
        <v-tab>아파트 매물</v-tab>
        <v-tab>경로</v-tab>
        <v-tab>플래너</v-tab>
      </v-tabs>

      <v-tabs-items v-model="tab">
        <v-tab-item>
          <v-container style="width: 1000px, height 500px">
            <v-row justify="start">
              <v-sheet
                v-for="(aptInfo, index) in bookmarkAptList"
                :key="index"
                :aptInfo="aptInfo"
                @click="clickAptInfo(aptInfo)"
                height="120"
                width="200"
                elevation="4"
                class="pl-0 pt-0 ma-10"
                rounded="xl"
              >
                <v-row class="mt-8" justify="center">
                  <v-col cols="auto">
                    <v-row justify="end"> </v-row>
                    <v-row justify="center" class="item-middle-box-text mt-4">{{ aptInfo.apartmentName }} </v-row>
                    <v-row justify="center" class="item-middle-box-subtext mt-3">
                      {{ aptInfo.dong }} ({{ aptInfo.roadName }})
                    </v-row>
                  </v-col>
                </v-row>
              </v-sheet>
            </v-row>
          </v-container>
        </v-tab-item>

        <v-tab-item>
          <v-container style="width: 1000px, height 500px">
            <v-row justify="start">
              <v-sheet
                v-for="(pathInfo, index) in bookmarkPathList"
                :key="index"
                :pathInfo="pathInfo"
                @click="clickPathInfo(pathInfo)"
                height="120"
                width="200"
                elevation="4"
                class="pl-0 pt-0 ma-10"
                rounded="xl"
              >
                <v-row class="mt-8" justify="center">
                  <v-col cols="auto">
                    <v-row justify="end"> </v-row>
                    <v-row justify="center" class="item-middle-box-text mt-4">{{ pathInfo.pathName }} </v-row>
                    <v-row justify="center" class="item-middle-box-subtext mt-3">
                      {{ pathInfo.startAptName }} -> {{ pathInfo.endAptName }}
                    </v-row>
                  </v-col>
                </v-row>
              </v-sheet>
            </v-row>
          </v-container>
        </v-tab-item>

        <v-tab-item>
          <v-container style="width: 1000px, height 500px">
            <v-row justify="start">
              <v-sheet
                v-for="(plannerInfo, index) in plannerInfoList"
                :key="index"
                :plannerInfo="plannerInfo"
                @click="clickPlannerInfo(plannerInfo)"
                height="120"
                width="200"
                elevation="4"
                class="pl-0 pt-0 ma-10"
                rounded="xl"
              >
                <v-row class="mt-8" justify="center">
                  <v-col cols="auto">
                    <v-row justify="end"> </v-row>
                    <v-row justify="center" class="item-middle-box-text mt-4">{{ plannerInfo.aptName }} </v-row>
                    <v-row justify="center" class="item-middle-box-subtext mt-3">
                      {{ plannerInfo.writeDate }}
                    </v-row>
                  </v-col>
                </v-row>
              </v-sheet>
            </v-row>
          </v-container>
        </v-tab-item>
      </v-tabs-items>
    </v-container>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

const bookmarkStore = "bookmarkStore";
const memberStore = "memberStore";
const plannerStore = "plannerStore";
const houseInfoStore = "houseInfoStore";

export default {
  data() {
    return {
      tab: null,
    };
  },
  computed: {
    ...mapState(bookmarkStore, ["bookmarkAptList", "bookmarkPathList", "plannerInfoList"]),
    ...mapState(plannerStore, ["plannerInfoList", "plannerInfo"]),
    ...mapState(memberStore, ["userInfo"]),
  },
  mounted() {
    this.CLEAR_BOOKMARK_APT_LIST();
    this.selectBookmarkAptList(this.userInfo.id);
    this.searchBookmarkPathInfo(this.userInfo.id);
    this.selectPlannerInfoList(this.userInfo.id);
  },
  methods: {
    ...mapMutations(bookmarkStore, ["CLEAR_BOOKMARK_APT_LIST"]),
    ...mapActions(bookmarkStore, ["selectBookmarkAptList", "searchBookmarkPathInfo"]),
    ...mapActions(plannerStore, ["selectPlannerInfo", "selectPlannerInfoList"]),
    ...mapActions(houseInfoStore, ["searchHouseInfo", "getHouseInfoDeal"]),

    clickAptInfo(value) {
      // console.log(value);
      this.searchHouseInfo(value);
      this.getHouseInfoDeal(value.aptCode);
      this.$router.push({ name: "aptResultView" }).catch(() => {});
    },
    clickPathInfo(value) {
      console.log("경로 즐겨찾기 item 클릭");

      console.log(value);
    },
    clickPlannerInfo(value) {
      console.log("플래너 즐겨찾기 item 클릭");
      console.log(value);
      this.selectPlannerInfo(value);
      console.log("ASD");
      this.getHouseInfoDeal(value.aptCode);
      console.log("QWE");
      this.$router.push({ name: "plannerView" }).catch(() => {});
    },
  },
};
</script>

<style></style>
