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
                <v-row class="mt-1" justify="center">
                  <v-col cols="auto">
                    <v-row justify="end">
                      <v-btn icon>
                        <v-icon @click.stop="deleteApartment(aptInfo)">mdi-close</v-icon>
                      </v-btn>
                    </v-row>
                    <v-row justify="center" class="item-middle-box-text mt-4">{{ aptInfo.apartmentName }} </v-row>
                    <v-row justify="center" class="item-middle-box-subtext mt-3">
                      {{ aptInfo.dong }} ({{ aptInfo.roadName }})
                    </v-row>
                  </v-col>
                </v-row>
                <v-row> </v-row>
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
                <v-row class="mt-0" justify="center">
                  <v-col cols="auto">
                    <v-row justify="end">
                      <v-btn icon>
                        <v-icon @click.stop="deletePath(pathInfo)">mdi-close</v-icon>
                      </v-btn>
                    </v-row>
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
                <v-row class="mt-1" justify="center">
                  <v-col cols="auto">
                    <v-row justify="end">
                      <v-btn icon>
                        <v-icon @click.stop="deletePlanner(plannerInfo)">mdi-close</v-icon>
                      </v-btn>
                    </v-row>
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
    ...mapState(bookmarkStore, ["bookmarkAptList", "bookmarkPathList", "plannerInfoList", "bookmarkPathDetailList"]),
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
    ...mapActions(bookmarkStore, [
      "selectBookmarkAptList",
      "searchBookmarkPathInfo",
      "searchBookmarkPathDetailInfo",
      "getPathInfoList",
      "deletePathList",
      "deleteBookmarkAptInfo",
    ]),
    ...mapActions(plannerStore, ["selectPlannerInfo", "selectPlannerInfoList", "deletePlannerInfo"]),
    ...mapActions(houseInfoStore, ["searchHouseInfo", "getHouseInfoDeal"]),

    clickAptInfo(value) {
      // console.log(value);
      this.searchHouseInfo(value);
      this.getHouseInfoDeal(value.aptCode);
      this.$router.push({ name: "aptResultView" }).catch(() => {});
    },
    async clickPathInfo(value) {
      console.log("경로 즐겨찾기 item 클릭");

      console.log(value);
      // 경유지 목록 가져오기
      this.searchBookmarkPathDetailInfo(value.id);

      console.log(this.bookmarkPathDetailList);

      let requestList = [];

      let start = {
        aptCode: value.startAptCode,
        aptName: value.startAptName,
        lat: value.startLat,
        lon: value.startLon,
      };

      let end = {
        aptCode: value.endAptCode,
        aptName: value.endAptName,
        lat: value.endLat,
        lon: value.endLon,
      };

      requestList.push(start);

      this.bookmarkPathDetailList.forEach((element) => {
        let temp = {
          aptCode: element.aptCode,
          aptName: element.aptName,
          lat: element.lat,
          lon: element.lon,
        };
        requestList.push(temp);
      });

      requestList.push(end);
      // 경로 탐색하기
      await this.getPathInfoList(requestList);

      // 이동하기
      this.$router.push({ name: "pathResult" }).catch(() => {});
    },
    clickPlannerInfo(value) {
      console.log(value);
      this.selectPlannerInfo(value);
      this.getHouseInfoDeal(value.aptCode);
      this.$router.push({ name: "plannerView" }).catch(() => {});
    },

    deleteApartment(aptInfo) {
      console.log("userInfo id = ", this.userInfo.id);
      console.log("aptList = ", this.bookmarkAptList);
      console.log("aptInfo = ", aptInfo);

      const payload = {
        memberId: this.userInfo.id,
        aptCode: aptInfo.aptCode,
      };

      console.log(payload);
      if (confirm("정말로 삭제하시겠습니까?")) {
        this.bookmarkAptList.forEach((item, index) => {
          if (item.aptCode === aptInfo.aptCode) {
            this.bookmarkAptList.splice(index, 1);
          }
        });
        console.log("삭제 ㄱㄱ");
        this.deleteBookmarkAptInfo(payload);
      }
    },
    deletePath(pathInfo) {
      console.log("pathInfo = ", pathInfo);
      if (confirm("정말로 삭제하시겠습니까?")) {
        this.bookmarkPathList.forEach((item, index) => {
          if (item.id === pathInfo.id) {
            this.bookmarkPathList.splice(index, 1);
          }
        });
        this.deletePathList(pathInfo.id);
      }
    },
    deletePlanner(plannerInfo) {
      console.log("plannerInfo = ", plannerInfo);
      if (confirm("정말로 삭제하시겠습니까?")) {
        this.plannerInfoList.forEach((item, index) => {
          if (item.id === plannerInfo.id) {
            this.plannerInfoList.splice(index, 1);
          }
        });
        this.deletePlannerInfo(plannerInfo.id);
      }
    },
  },
};
</script>

<style></style>
