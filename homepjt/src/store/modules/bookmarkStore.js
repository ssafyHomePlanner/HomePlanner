import { searchPath, insertPath, deletePath } from "@/api/bookmark.js";

const bookmarkStore = {
  namespaced: true,
  state: {
    pathInfo: {},
    pathInfoList: [],
  },
  getters: {},
  mutations: {
    SEARCH_PATH_INFO(state, payload) {
      state.pathInfo = payload;
    },
    SEARCH_PATH_INFO_LIST(state, payload) {
      state.pathInfoList = payload;
    },
    CLEAR_PATH_INFO(state) {
      state.pathInfo = null;
    },
    CLEAR_PATH_INFO_LIST(state) {
      state.pathInfoList = [];
    },
  },
  actions: {
    getPathInfoList({ commit }, payload) {
      searchPath(
        payload,
        ({ data }) => {
          console.log("data : {}", data);
          commit("SEARCH_PATH_INFO_LIST", data.bookmarkPathRequestDtoList);
          console.log("pathList : {}", data.bookmarkPathRequestDtoList);
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

export default bookmarkInfoStore;
