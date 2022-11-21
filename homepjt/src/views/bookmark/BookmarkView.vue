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
      </v-tabs-items>
    </v-container>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

const bookmarkStore = "bookmarkStore";
const memberStore = "memberStore";
const aptStore = "aptStore";

export default {
  data() {
    return {
      tab: null,
    };
  },
  computed: {
    ...mapState(bookmarkStore, ["bookmarkAptList"]),
    ...mapState(memberStore, ["userInfo"]),
    ...mapState(aptStore, ["sidoName", "gugunName", "dongName"]),
  },
  mounted() {
    this.CLEAR_BOOKMARK_APT_LIST();
    this.selectBookmarkAptList(this.userInfo.id);
    this.searchDongCodeInfo(this.bookmarkAptList.dongCode);
  },
  methods: {
    ...mapMutations(bookmarkStore, ["CLEAR_BOOKMARK_APT_LIST"]),
    ...mapActions(bookmarkStore, ["selectBookmarkAptList"]),
    ...mapActions(aptStore, ["searchDongCodeInfo"]),
  },
};
</script>

<style></style>
