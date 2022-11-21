import { searchSido, searchGugun, searchDong, searchDongCode } from "@/api/apt.js";

const aptStore = {
  namespaced: true,
  state: {
    sidoList: [],
    gugunList: [],
    dongList: [],
    sidoName: "",
    gugunName: "",
    dongName: "",
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

    SET_DONG_CODE_INFO(state, payload) {
      state.sidoName = payload.sidoName;
      state.gugunName = payload.gugunName;
      state.dongName = payload.dongName;
    },
    CLEAR_DONG_CODE_INFO(state) {
      state.sidoName = "";
      state.gugunName = "";
      state.dongName = "";
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

    searchDongCodeInfo({ commit }, dongCode) {
      searchDongCode(
        dongCode,
        ({ data }) => {
          commit("CLEAR_DONG_CODE_INFO");
          commit("SET_DONG_CODE_INFO", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    setAddressInfo({ commit }, payload){
      commit("CLEAR_DONG_CODE_INFO");
      commit("SET_DONG_CODE_INFO", payload);
    }
  },
};

export default aptStore;
