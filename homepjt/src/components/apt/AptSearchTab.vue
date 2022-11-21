<template>
  <div>
    <v-container class="mt-5">
      <v-row>
        <v-tabs v-model="tab">
          <v-tab>찜한 아파트</v-tab>
          <v-tab>아파트 검색</v-tab>
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
                      <v-list-item-subtitle> {{ item.dong }} ({{ item.roadName }}) </v-list-item-subtitle>
                    </v-list-item-content>
                  </v-list-item>
                  <v-divider></v-divider>
                </template>
              </v-virtual-scroll>
            </v-container>
          </v-tab-item>

          <v-tab-item>
            <v-container style="width: 500px; height: 280px"> 아파트 검색 화면 </v-container>
          </v-tab-item>
        </v-tabs-items>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

const bookmarkStore = "bookmarkStore";
const memberStore = "memberStore";

export default {
  data() {
    return {
      tab: null,
    };
  },
  computed: {
    ...mapState(bookmarkStore, ["bookmarkAptList"]),
    ...mapState(memberStore, ["userInfo"]),
  },
  mounted() {
    this.selectBookmarkAptList(this.userInfo.id);
  },
  methods: {
    clickLikeApartment() {
      // 해당 아파트 상세 페이지로 이동해야함
    },

    ...mapActions(bookmarkStore, ["selectBookmarkAptList"]),
  },
};
</script>

<style></style>
