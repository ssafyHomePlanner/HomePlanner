import {
  listArticle,
  writeArticle,
  modifyArticle,
  deleteArticle,
  getArticleCommentList,
  writeArticleComment,
  deleteArticleComment,
  addArticleView,
  addArticleLike,
  checkArticleLike,
} from "@/api/board.js";

const boardStore = {
  namespaced: true,
  state: {
    boardList: [],
    board: {},
    boardComment: [],

    likeFlag: 0,

    startPage: 0,
    currPage: 0,
    totalPage: 0,
    endPage: 0,
  },
  getters: {},
  mutations: {
    SEARCH_BOARD_LIST(state, payload) {
      state.boardList = payload.boardList;
      state.startPage = payload.startPage;
      state.currPage = payload.currPage;
      state.totalPage = payload.totalPage;
      state.endPage = payload.endPage;
    },
    INSERT_BOARD_ONE(state, payload) {
      state.board = payload;
    },
    DELETE_BOARD(state) {
      state.board = [];
    },
    SEARCH_BOARD_COMMENT(state, payload) {
      state.boardComment = payload;
      console.log(state.boardComment);
    },
    ADD_BOARD_VIEW(state) {
      state.board.viewCnt += 1;
    },
    ADD_BOARD_LIKE(state) {
      state.board.likeCnt += 1;
    },
    DELETE_BOARD_LIKE(state) {
      state.board.likeCnt -= 1;
    },
    CHECK_BOARD_LIKE(state, payload) {
      state.likeFlag = payload;
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
          commit("SEARCH_BOARD_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    updateBoard(context, payload) {
      modifyArticle(
        payload,
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
        memberId: payload.memberId,
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
    addBoardView({ commit }, payload) {
      addArticleView(
        payload,
        () => {
          commit("ADD_BOARD_VIEW");
        },
        (error) => {
          console.log(error);
        }
      );
    },
    addBoardLike({ commit }, payload) {
      addArticleLike(
        payload.boardId,
        payload.flag,
        () => {
          if (payload.flag) {
            commit("ADD_BOARD_LIKE");
          } else {
            commit("DELETE_BOARD_LIKE");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    checkBoardLike({ commit }, payload) {
      checkArticleLike(
        payload.boardId,
        payload.memberId,
        ({ data }) => {
          commit("CHECK_BOARD_LIKE", data.likeCnt);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    searchBoardComment({ commit }, payload) {
      console.log("searchBoardComment");
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
    writeBoardComment({ commit }, payload) {
      let comment = {
        memberId: payload.memberId,
        content: payload.content,
        boardId: payload.boardId,
      };

      writeArticleComment(
        comment,
        ({ data }) => {
          if (data === "success") {
            console.log("댓글 등록 성공");
          }
        },
        (error) => {
          console.log(error);
        }
      ).then(() => {
        getArticleCommentList(
          comment.boardId,
          ({ data }) => {
            commit("SEARCH_BOARD_COMMENT", data.boardCommentList);
          },
          (error) => {
            console.log(error);
          }
        );
      });
    },

    deleteBoardComment({ commit }, payload) {
      deleteArticleComment(
        payload.id,
        ({ data }) => {
          if (data === "success") {
            console.log("댓글 삭제 성공");
          }
        },
        (error) => {
          console.log(error);
        }
      ).then(() => {
        getArticleCommentList(
          payload.boardId,
          ({ data }) => {
            commit("SEARCH_BOARD_COMMENT", data.boardCommentList);
          },
          (error) => {
            console.log(error);
          }
        );
      });
    },
  },
};

export default boardStore;
