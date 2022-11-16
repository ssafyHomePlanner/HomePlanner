import Vue from "vue";
import Vuex from "vuex";
import http from "@/api/http";
// import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    boardList: [],
    board: null,
  },
  getters: {},
  mutations: {
    SEARCH_BOARD_LIST(state, payload) {
      state.boardList = payload;
    },
  },
  actions: {
    searchBoardList({ commit }, payload) {
      http.get(`/board/${payload}`).then(({ data }) => {
        commit("SEARCH_BOARD_LIST", data.boardList);
      });
    },
  },
  modules: {},
  // plugins: [
  //   createPersistedState({
  //     // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
  //     storage: sessionStorage,
  //   }),
  // ],
});
