import Vue from "vue";
import Vuex from "vuex";
import http from "@/api/http";
// import createPersistedState from "vuex-persistedstate";

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
    boardList: [],
    board: null,
    boardComment: [],

    // 시도, 시군구, 동
    sidoList: [],
    gugunList: [],
    dongList: [],
  },
  getters: {},
  mutations: {
    SEARCH_BOARD_LIST(state, payload) {
      state.boardList = payload;
    },
    UPDATE_BOARD_DETAIL(state, payload) {
      state.board = payload;
    },
    DELETE_BOARD(state) {
      state.board = null;
    },
    SEARCH_BOARD_COMMENT(state, payload) {
      state.boardComment = payload;
    },

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
    updateBoardDetail({ commit }, payload) {
      commit("UPDATE_BOARD_DETAIL", payload);
    },
    searchBoardList({ commit }, payload) {
      http.get(`/board/${payload}`).then(({ data }) => {
        commit("SEARCH_BOARD_LIST", data.boardList);
      });
    },
    searchBoardComment({ commit }, payload) {
      http.get(`/board/comment/${payload}`).then(({ data }) => {
        commit("SEARCH_BOARD_COMMENT", data.boardCommentList);
      });
    },
    updateBoard(context, payload) {
      http
        .put("/board/update", {
          memberId: context.state.member.id,
          title: payload.title,
          content: payload.content,
          id: payload.id,
        })
        .then(({ data }) => {
          if (data === "success") {
            console.log("게시글 수정 성공");
          }
        });
    },
    writeBoard(context, payload) {
      http
        .post(`/board/write`, {
          memberId: context.state.member.id,
          title: payload.title,
          content: payload.content,
        })
        .then(({ data }) => {
          if (data === "success") {
            console.log("게시글 등록 성공");
          }
        });
    },
    deleteBoard({ commit }, payload) {
      http.delete(`/board/delete/${payload}`).then(({ data }) => {
        if (data === "success") {
          console.log("게시글 삭제 성공");
          commit("DELETE_BOARD");
        }
      });
    },

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
  modules: {},
  // plugins: [
  //   createPersistedState({
  //     // 브라우저 종료시 제거하기 위해 localStorage가 아닌 sessionStorage로 변경. (default: localStorage)
  //     storage: sessionStorage,
  //   }),
  // ],
});
