import Vue from "vue";
import Vuex from "vuex";
import http from "@/api/http";
// import createPersistedState from "vuex-persistedstate";
import boardStore from "@/store/modules/boardStore";

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
    // 시도, 시군구, 동
    sidoList: [],
    gugunList: [],
    dongList: [],
  },
  getters: {},
  mutations: {
    // 시도, 시군구, 동
    SEARCH_SIDO_LIST(state, payload) {
      state.sidoList = payload;
    },
    SEARCH_GUGUN_LIST(state, payload) {
      state.gugunList = payload;
    },
    SEARCH_DONG_LIST(state, payload) {
      state.dongList = payload;
    },
    CLEAR_SIDO_LIST(state) {
      state.sidoList = null;
    },
    CLEAR_GUGUN_LIST(state) {
      state.gunguList = null;
    },
    CLEAR_DONG_LIST(state) {
      state.dongList = null;
    },
  },
  actions: {
    // 시도, 시군구, 동
    searchSidoList({ commit }) {
      http.get(`/dongcode`).then(({ data }) => {
        commit("SEARCH_SIDO_LIST", data.sidoNameList);
      });
    },
    searchGugunList({ commit }, payload) {
      http.get(`/dongcode/gugun/${payload}`).then(({ data }) => {
        commit("SEARCH_GUGUN_LIST", data.gugunNameList);
      });
    },
    searchDongList({ commit }, payload) {
      http.get(`/dongcode/dong/${payload}`).then(({ data }) => {
        commit("SEARCH_DONG_LIST", data.dongNameList);
      });
    },
  },
  modules: {
    boardStore,
  },
  // plugins: [
  //   createPersistedState({
  //     // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
  //     storage: sessionStorage,
  //   }),
  // ],
});
