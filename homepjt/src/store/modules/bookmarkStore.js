import { searchPath, insertPath, deletePath } from "@/api/bookmark.js";

const bookmarkStore = {
  namespaced: true,
  state: {
    pathInfo: {},
    // pathInfoList: [],
    timePathList: [],
    distPathList: [],
    timeArr: [],
    distArr: [],
  },
  getters: {},
  mutations: {
    SEARCH_PATH_INFO(state, payload) {
      state.pathInfo = payload;
    },
    SEARCH_PATH_INFO_LIST(state, payload) {
      // state.pathInfoList = payload;
      state.timePathList = payload.timePointList;
      state.distPathList = payload.distPointList;
      state.timeArr = payload.timeArr;
      state.distArr = payload.distArr;
    },
    CLEAR_PATH_INFO(state) {
      state.pathInfo = null;
    },
    CLEAR_PATH_INFO_LIST(state) {
      // state.pathInfoList = [];
      state.timePathList = [];
      state.distPathList = [];
      state.timeArr = [];
      state.distArr = [];
    },
  },
  actions: {
    getPathInfoList({ commit }, payload) {
      searchPath(
        payload,
        ({ data }) => {
          console.log("data : {}", data);
          commit("SEARCH_PATH_INFO_LIST", data);
          console.log("pathList : {}", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    insertPathList(payload) {
      const pathInfo = {
        aptCode: payload.aptCode,
        aptName: payload.aptName,
        lat: payload.lat,
        lon: payload.lon,
      };
      insertPath(
        pathInfo,
        payload.memberId,
        payload.pathName,
        ({ data }) => {
          console.log("message : {}", data.message);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    deletePathList(payload) {
      deletePath(
        payload,
        ({ data }) => {
          console.log("message : {}", data.message);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default bookmarkStore;
