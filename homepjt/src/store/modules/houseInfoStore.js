import {
  searchHouseInfo,
  searchHouseInfoAuto,
  writeHouseComment,
  updateHouseComment,
  selectHouseComment,
  deleteHouseComment,
  searchHouseInfoDetail,
  searchHouseDeal,
  addHouseInfoViewCnt,
  updateAptLikeInfo,
  checkAptLikeInfo,
} from "@/api/houseInfo.js";

const houseInfoStore = {
  namespaced: true,
  state: {
    houseInfo: {},
    houseInfoList: [],
    houseDeal: {},
    houseDealList: [],

    likeFlag: false,

    comment: {},
    commentList: [],
  },
  getters: {},
  mutations: {
    SEARCH_HOUSE_INFO_LIST(state, payload) {
      state.houseInfoList = payload;
    },
    SEARCH_HOUSE_INFO(state, payload) {
      state.houseInfo = payload;
    },
    SEARCH_HOUSE_DEAL_LIST(state, payload) {
      state.houseDealList = payload;
    },
    SEARCH_HOUSE_DEAL(state, payload) {
      state.houseDeal = payload;
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
    CLEAR_HOUSE_DEAL(state) {
      state.houseDeal = null;
    },

    SEARCH_COMMENT_LIST(state, payload) {
      state.commentList = payload;
    },
    CLEAR_COMMENT_LIST(state) {
      state.commentList = [];
    },

    ADD_APT_VIEW(state) {
      state.houseInfo.viewCnt += 1;
    },

    ADD_APT_LIKE(state) {
      state.houseInfo.likeCnt += 1;
    },
    DELETE_APT_LIKE(state) {
      state.houseInfo.likeCnt -= 1;
    },
    CHECK_APT_LIKE(state, payload) {
      state.likeFlag = payload;
    },
  },
  actions: {
    searchHouseInfo({ commit }, payload) {
      commit("SEARCH_HOUSE_INFO", payload);
    },
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
    getHouseInfoDetailInfo({ commit }, payload) {
      const houseInfo = {
        aptName: payload.aptName,
        dongName: payload.dongName,
        gugunName: payload.gugunName,
        sidoName: payload.sidoName,
        maxArea: payload.maxArea,
        minArea: payload.minArea,
        maxBuildYear: payload.maxBuildYear,
        minBuildYear: payload.minBuildYear,
        maxDealAmount: payload.maxDealAmount,
        minDealAmount: payload.minDealAmount,
      };

      searchHouseInfoDetail(
        houseInfo,
        payload.page,
        ({ data }) => {
          commit("CLEAR_HOUSE_INFO_LIST");
          commit("SEARCH_HOUSE_INFO_LIST", data.houseInfoList);
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

    writeComment(context, commentInfo) {
      writeHouseComment(
        commentInfo,
        ({ data }) => {
          console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    updateComment(context, commentInfo) {
      updateHouseComment(
        commentInfo,
        ({ data }) => {
          console.log(data);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    selectComment({ commit }, aptCode) {
      selectHouseComment(
        aptCode,
        ({ data }) => {
          console.log(data);
          commit("SEARCH_COMMENT_LIST", data.houseCommentList);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    deleteComment(context, houseCommentId) {
      deleteHouseComment(
        houseCommentId,
        ({ data }) => {
          console.log("message : {}", data.message);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    async getHouseInfoDeal({ commit }, aptCode) {
      await searchHouseDeal(
        aptCode,
        async ({ data }) => {
          commit("CLEAR_HOUSE_DEAL_LIST");
          await commit("SEARCH_HOUSE_DEAL_LIST", data.houseDealList);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    addViewCnt({ commit }, aptCode) {
      addHouseInfoViewCnt(
        aptCode,
        ({ data }) => {
          commit("ADD_APT_VIEW");
          console.log(data.message);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    addLikeCnt({ commit }, payload) {
      updateAptLikeInfo(
        payload.aptCode,
        payload.memberId,
        payload.likeFlag,
        () => {
          if (payload.likeFlag == 0) {
            alert("즐겨찾기에 추가하였습니다.");
            commit("ADD_APT_LIKE");
            commit("CHECK_APT_LIKE", 1);
          } else {
            alert("즐겨찾기에서 제거하였습니다.");
            commit("DELETE_APT_LIKE");
            commit("CHECK_APT_LIKE", 0);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    checkAptLike({ commit }, payload) {
      checkAptLikeInfo(
        payload.aptCode,
        payload.memberId,
        ({ data }) => {
          commit("CHECK_APT_LIKE", data.likeFlag);
          console.log("check apt like data : ", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default houseInfoStore;
