import { searchSido, searchGugun, searchDong } from "@/api/apt.js";

const aptStore = {
  namespaced: true,
  state: {
    sidoList: [],
    gugunList: [],
    dongList: [],
  },
  getters: {},
  mutations: {
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
      state.sidoList = [];
    },
    CLEAR_GUGUN_LIST(state) {
      state.gunguList = [];
    },
    CLEAR_DONG_LIST(state) {
      state.dongList = [];
    },
  },
  actions: {
    searchSidoList({ commit }) {
      searchSido(
        ({ data }) => {
          commit("SEARCH_SIDO_LIST", data.sidoNameList);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    searchGugunList({ commit }, payload) {
      searchGugun(
        payload,
        ({ data }) => {
          commit("SEARCH_GUGUN_LIST", data.gugunNameList);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    searchDongList({ commit }, payload) {
      searchDong(
        payload,
        ({ data }) => {
          commit("SEARCH_DONG_LIST", data.dongNameList);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default aptStore;
