<template>
  <div>
    <v-container class="bv-example-row mt-10">
      <v-row class="path-item-middle-text mb-7" justify="start">
        <h2>아파트 즐겨찾기 목록</h2>
      </v-row>

      <v-row justify="start">
        <v-sheet
          v-for="(aptInfo, index) in bookmarkAptList"
          :key="index"
          :aptInfo="aptInfo"
          height="120"
          width="200"
          elevation="4"
          class="pl-0 pt-0 mr-5"
          rounded="xl"
        >
          <v-row class="mt-8" justify="center">
            <v-col cols="auto">
              <v-row justify="end"> </v-row>
              <v-row justify="center" class="item-middle-box-text mt-4"
                >{{ aptInfo.apartmentName }}
              </v-row>
              <v-row justify="center" class="item-middle-box-subtext mt-3">
                {{ aptInfo.dong }} ({{ aptInfo.roadName }})
              </v-row>
            </v-col>
          </v-row>
        </v-sheet>
      </v-row>
      <div class="text-center mt-8"></div>
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
    return {};
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
