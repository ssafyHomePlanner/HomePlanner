import {
  searchHouseInfo,
  searchHouseInfoAuto,
  // searchHouseInfoDetail
} from "@/api/houseInfo.js";

const houseInfoStore = {
  namespaced: true,
  state: {
    houseInfo: {},
    houseInfoList: [],
    houseDealList: [],
  },
  getters: {},
  mutations: {
    SEARCH_HOUSE_INFO_LIST(state, payload) {
      state.houseInfoList = payload;
    },
    SEARCH_HOUSE_INFO(state, payload) {
      state.houseInfo = payload;
    },
    SEARCH_HOUSE_DEAL(state, payload) {
      state.houseDealList = payload;
    },
    CLEAR_HOUSE_INFO_LIST(state) {
      state.houseInfoList = [];
    },
    CLEAR_HOUSE_INFO(state) {
      state.houseInfo = null;
    },
    CLEAR_HOUSE_DEAL_LIST(state) {
      state.houseDealList = [];
    },
  },
  actions: {
    getHouseInfoList({ commit }, payload) {
      let houseInfo = {
        aptName: payload.aptName,
        dongName: payload.dongName,
        gugunName: payload.gugunName,
        sidoName: payload.sidoName,
      };

      console.log("houseInfo : ", houseInfo);
      console.log("page : ", payload.page);
      searchHouseInfo(
        houseInfo,
        payload.page,
        ({ data }) => {
          console.log(data);
          commit("SEARCH_HOUSE_INFO_LIST", data.houseInfoList);
          console.log(data.houseInfoList);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    getHouseInfoListAuto({ commit }, aptName) {
      const params = { aptName: aptName };
      searchHouseInfoAuto(
        params,
        ({ data }) => {
          console.log(data);
          commit("SEARCH_HOUSE_INFO_LIST", data.houseInfoList);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default houseInfoStore;
