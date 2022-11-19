import Vue from "vue";
import Vuex from "vuex";
// import http from "@/api/http";
// import createPersistedState from "vuex-persistedstate";
import aptStore from "@/store/modules/aptStore";
import boardStore from "@/store/modules/boardStore";
import bookmarkStore from "@/store/modules/bookmarkStore";
import houseInfoStore from "@/store/modules/houseInfoStore";
import memberStore from "@/store/modules/memberStore";
import plannerStore from "@/store/modules/plannerStore";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    member: {
      id: "ssafy",
      pw: "1234",
      name: "싸피",
      email: "'ssafy@gmail.com'",
      age: "27",
      phone: "010",
      gender: "m",
      joinDate: "2022-11-16 09:44:18",
    },
  },
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
});
