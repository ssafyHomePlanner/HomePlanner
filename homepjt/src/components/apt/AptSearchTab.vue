<template>
  <div>
    <v-container class="mt-5">
      <v-row>
        <v-tabs v-model="tab">
          <v-tab>찜한 아파트</v-tab>
          <!-- <v-tab>아파트 검색</v-tab> -->
        </v-tabs>

        <v-tabs-items v-model="tab">
          <v-tab-item>
            <v-container style="width: 500px; height: 280px">
              <v-virtual-scroll bench="5" :items="bookmarkAptList" height="270" item-height="64">
                <template v-slot:default="{ item }">
                  <v-list-item :key="item.name" @click="clickLikeApartment(item)">
                    <v-list-item-content>
                      <v-list-item-title>
                        <strong>{{ item.apartmentName }}</strong>
                      </v-list-item-title>
                      <v-list-item-subtitle>
                        {{ item.dong }} ({{ item.roadName }})
                      </v-list-item-subtitle>
                    </v-list-item-content>
                  </v-list-item>
                  <v-divider></v-divider>
                </template>
              </v-virtual-scroll>
            </v-container>
          </v-tab-item>

          <!-- <v-tab-item>
            <v-container fill-height fluid>
              <v-autocomplete
                :items="houseInfoList"
                :search-input.sync="search"
                class="mt-5 ml-8 mr-8"
                solo
                label="아파트를 검색해보세요."
                @input="inputChanged"
                ref="autoinput"
                v-model="aptObject"
                clearable
                item-text="apartmentName"
                item-value="apartmentName"
                return-object
              >
              </v-autocomplete>
              <button @click="clickButton">검색</button>
            </v-container>
          </v-tab-item> -->
        </v-tabs-items>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

const bookmarkStore = "bookmarkStore";
const memberStore = "memberStore";
const houseInfoStore = "houseInfoStore";

export default {
  data() {
    return {
      tab: null,
      aptObject: {},
      page: 1,
      search: "",
      benched: 0,
    };
  },
  watch: {
    search(val) {
      if (!val) {
        this.aptObject.apartmentName = "";
        this.CLEAR_HOUSE_INFO_LIST();
        return;
      }
      // this.aptObject.apartmentName = val;
      this.makeHouseInfoListAuto(val);
    },
  },
  computed: {
    ...mapState(bookmarkStore, ["bookmarkAptList"]),
    ...mapState(memberStore, ["userInfo", "recentDataList"]),
    ...mapState(houseInfoStore, ["houseInfoList"]),
  },
  mounted() {
    this.selectBookmarkAptList(this.userInfo.id);
    this.selectRecentDataInfo(this.$store.state.memberStore.userInfo.id);
  },
  methods: {
    clickLikeApartment(item) {
      // 해당 아파트 상세 페이지로 이동해야함
      console.log("send", item.aptCode + " " + item.apartmentName);
      this.$emit("clickLikeApartment", item);
      // console.log(item);
      this.apartmentName = item.apartmentName;
    },
    inputChanged() {
      //↓ For clear v-menu slot
      this.$refs.autoinput.blur();
      setTimeout(() => {
        this.$refs.autoinput.focus();
      }, 500);
    },
    makeHouseInfoListAuto(value) {
      // console.log("makeHouseInfoListAuto", value);
      // cancel pending call
      clearTimeout(this._timerId);

      this._timerId = setTimeout(() => {
        this.getHouseInfoListAuto(value);
      }, 500);

      // this.$emit("enterApartment", this.aptObject);
    },
    clickButton() {
      console.log("clicked button = ", this.aptObject);
      // this.$emit("enterApartment", this.aptObject);
      this.getHouseInfoDeal(this.aptObject.aptCode);
      this.$emit("enterApartment", this.aptObject);
    },
    changeAptAmount(aptCode) {
      this.getHouseInfoDeal(aptCode);
    },
    deleteAllRecentSearch() {
      if (this.$store.state.memberStore.userInfo.id.length >= 1) {
        this.deleteRecentDataInfoAll(this.$store.state.memberStore.userInfo.id);
      }
    },
    deleteRecentSearch(recentId) {
      // console.log("recentId", recentId);
      let payload = {
        memberId: this.$store.state.memberStore.userInfo.id,
        recentId: recentId,
      };

      this.deleteRecentDataInfo(payload);
    },

    ...mapActions(bookmarkStore, ["selectBookmarkAptList"]),
    ...mapActions(houseInfoStore, ["getHouseInfoList", "getHouseInfoListAuto", "getHouseInfoDeal"]),
    ...mapActions(memberStore, [
      "selectRecentDataInfo",
      "insertRecentDataInfo",
      "deleteRecentDataInfoAll",
      "deleteRecentDataInfo",
    ]),
    ...mapMutations(houseInfoStore, [
      "SEARCH_HOUSE_INFO_LIST",
      "SEARCH_HOUSE_INFO",
      "CLEAR_HOUSE_INFO_LIST",
      "CLEAR_HOUSE_INFO",
    ]),
  },
};
</script>

<style></style>
