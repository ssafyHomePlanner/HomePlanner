import Vue from "vue";
import Vuex from "vuex";
// import http from "@/api/http";
// import createPersistedState from "vuex-persistedstate";
import boardStore from "@/store/modules/boardStore";
import aptStore from "@/store/modules/aptStore";
import memberStore from "@/store/modules/memberStore";

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
    boardStore,
    aptStore,
    memberStore,
  },
});
