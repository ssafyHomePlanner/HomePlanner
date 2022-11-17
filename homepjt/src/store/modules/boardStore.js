import {
  listArticle,
  writeArticle,
  modifyArticle,
  deleteArticle,
  getArticleCommentList,
} from "@/api/board.js";

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
    INSERT_BOARD_ONE(state, payload) {
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
    insertBoardOne({ commit }, payload) {
      commit("INSERT_BOARD_ONE", payload);
    },
    searchBoardList({ commit }, payload) {
      listArticle(
        payload,
        ({ data }) => {
          commit("SEARCH_BOARD_LIST", data.boardList);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    searchBoardComment({ commit }, payload) {
      getArticleCommentList(
        payload,
        ({ data }) => {
          commit("SEARCH_BOARD_COMMENT", data.boardCommentList);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    updateBoard(context, payload) {
      let article = {
        memberId: context.state.member.id,
        title: payload.title,
        content: payload.content,
        id: payload.id,
      };

      modifyArticle(
        article,
        ({ data }) => {
          if (data === "success") {
            console.log("게시글 수정 성공");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    writeBoard(context, payload) {
      let article = {
        memberId: context.state.member.id,
        title: payload.title,
        content: payload.content,
      };

      writeArticle(
        article,
        ({ data }) => {
          if (data === "success") {
            console.log("게시글 등록 성공");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    deleteBoard({ commit }, payload) {
      deleteArticle(
        payload,
        () => {
          commit("DELETE_BOARD");
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default boardStore;
