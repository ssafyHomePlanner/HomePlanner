import {
  searchPath,
  insertPath,
  deletePath,
  deleteBookmarkApt,
  selectBookmarkApt,
  insertBookmarkApt,
  updateBoardLikeMember,
  checkBoardLikeMember,
  searchBookmarkPath,
  searchBookmarkPathDetail,
} from "@/api/bookmark.js";

const bookmarkStore = {
  namespaced: true,
  state: {
    // pathInfo: {},
    pathInfoList: [],
    timePathList: [],
    distPathList: [],
    timeArr: [],
    distArr: [],
    requestList: [],

    bookmarkAptList: [],
    bookmarkApt: {},

    bookmarkPathList: [],
    bookmarkPathDetailList: [],

    isClicked: false,
  },
  getters: {},
  mutations: {
    SET_REQUEST_LIST(state, payload) {
      state.requestList = [];
      state.requestList = payload;
    },
    SEARCH_PATH_INFO(state, payload) {
      state.pathInfo = payload;
    },
    SEARCH_PATH_INFO_LIST(state, payload) {
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

    SEARCH_BOOKMARK_PATH_LIST(state, payload) {
      state.bookmarkPathList = payload;
    },
    CLEAR_BOOKMARK_PATH_LIST(state) {
      state.bookmarkPathList = [];
    },

    SEARCH_BOOKMARK_PATH_DETAIL_LIST(state, payload) {
      state.bookmarkPathDetailList = payload;
    },
    CLEAR_BOOKMARK_PATH_DETAIL_LIST(state) {
      state.bookmarkPathDetailList = [];
    },

    SET_BOARD_LIKE_CLICK(state, payload) {
      state.isClicked = payload;
    },
  },
  actions: {
    async getPathInfoList({ commit }, payload) {
      await searchPath(
        payload,
        ({ data }) => {
          console.log("data : {}", data);
          commit("CLEAR_PATH_INFO_LIST");
          commit("SET_REQUEST_LIST", payload);
          commit("SEARCH_PATH_INFO_LIST", data);
          console.log("pathList : {}", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    searchBookmarkPathInfo({ commit }, memberId) {
      searchBookmarkPath(
        memberId,
        ({ data }) => {
          commit("CLEAR_BOOKMARK_PATH_LIST");
          commit("SEARCH_BOOKMARK_PATH_LIST", data.bookmarkPathList);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    searchBookmarkPathDetailInfo({ commit }, bookmarkPathId) {
      searchBookmarkPathDetail(
        bookmarkPathId,
        ({ data }) => {
          commit("CLEAR_BOOKMARK_PATH_DETAIL_LIST");
          commit("SEARCH_BOOKMARK_PATH_DETAIL_LIST", data.bookmarkPathDetailList);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    insertPathList(context, payload) {
      insertPath(
        context.state.requestList,
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
    deleteBookmarkAptInfo(context, payload) {
      deleteBookmarkApt(
        payload.memberId,
        payload.aptCode,
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
            commit("CLEAR_BOOKMARK_APT_LIST");
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
