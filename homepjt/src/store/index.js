import Vue from "vue";
import Vuex from "vuex";
// import http from "@/api/http";
import createPersistedState from "vuex-persistedstate";
import aptStore from "@/store/modules/aptStore";
import boardStore from "@/store/modules/boardStore";
import bookmarkStore from "@/store/modules/bookmarkStore";
import houseInfoStore from "@/store/modules/houseInfoStore";
import memberStore from "@/store/modules/memberStore";
import plannerStore from "@/store/modules/plannerStore";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    aptStore,
    boardStore,
    bookmarkStore,
    houseInfoStore,
    memberStore,
    plannerStore,
  },
  plugins: [
    createPersistedState({
      // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
      storage: sessionStorage,
      paths: [
        "aptStore",
        "boardStore",
        "bookmarkStore",
        "houseInfoStore",
        "memberStore",
        "plannerStore",
      ],
    }),
  ],
});
