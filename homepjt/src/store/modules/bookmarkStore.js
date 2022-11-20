import {
  searchPath,
  insertPath,
  deletePath,
  deleteBookmarkApt,
  selectBookmarkApt,
  insertBookmarkApt,
  updateBoardLikeMember,
  checkBoardLikeMember,
} from "@/api/bookmark.js";

const bookmarkStore = {
  namespaced: true,
  state: {
    pathInfo: {},
    // pathInfoList: [],
    timePathList: [],
    distPathList: [],
    timeArr: [],
    distArr: [],

    bookmarkAptList: [],
    bookmarkApt: {},

    isClicked: false,
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

    SEARCH_BOOKMARK_APT_LIST(state, payload) {
      state.bookmarkAptList = payload;
    },
    SEARCH_BOOKMARK_APT(state, payload) {
      state.bookmarkApt = payload;
    },
    CLEAR_BOOKMARK_APT_LIST(state) {
      state.bookmarkAptList = [];
    },
    CLEAR_BOOKMARK_APT(state) {
      state.bookmarkApt = null;
    },

    SET_BOARD_LIKE_CLICK(state, payload) {
      state.isClicked = payload;
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
    insertPathList(context, payload) {
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
    deletePathList(context, payload) {
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

    // 관심 상품 삭제
    deleteBookmarkAptInfo(context, bookmarkAptId) {
      deleteBookmarkApt(
        bookmarkAptId,
        ({ data }) => {
          if (data.message === "success") {
            console.log("관심 상품 삭제 성공");
          } else {
            console.log("관심 상품 삭제 실패");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    // 관심 상품 조회
    selectBookmarkAptList({ commit }, memberId) {
      selectBookmarkApt(
        memberId,
        ({ data }) => {
          if (data.message === "success") {
            commit("SEARCH_BOOKMARK_APT_LIST", data.houseInfoList);
            console.log("관심 상품 조회 성공");
          } else {
            console.log("관심 상품 조회 실패");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    // 관심 상품 등록
    insertBookmarkAptInfo(context, payload) {
      insertBookmarkApt(
        payload.memberId,
        payload.aptCode,
        ({ data }) => {
          if (data.message === "success") {
            console.log("관심 상품 등록 성공");
          } else {
            console.log("관심 상품 등록 실패");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    // 게시글 좋아요 회원 등록
    updateBoardLikeMemberInfo(context, payload) {
      updateBoardLikeMember(
        payload.boardId,
        payload.memberId,
        ({ data }) => {
          if (data.message === "success") {
            console.log("게시글 좋아요 회원 등록 성공");
          } else {
            console.log("게시글 좋아요 회원 등록 실패");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    // 게시글 좋아요 클릭 여부 확인
    checkBoardLikeMemberInfo({ commit }, payload) {
      checkBoardLikeMember(
        payload.boardId,
        payload.memberId,
        ({ data }) => {
          if (data.message === "success") {
            console.log("게시글 좋아요 클릭 여부 확인 성공");

            if (data.clickCount == 1) {
              commit("SET_BOARD_LIKE_CLICK", true);
              console.log("좋아요 클릭 되어 있음");
            } else {
              commit("SET_BOARD_LIKE_CLICK", false);
              console.log("좋아요 클릭 되어 있지 않음");
            }
          } else {
            console.log("게시글 좋아요 클릭 여부 확인 실패");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default bookmarkStore;
