const boardStore = {
  namespaced: true,
  state: {
    boardList: [],
    board: null,
    boardComment: [],
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
  },
};

export default boardStore;
