import { createPlanner, updatePlanner, selectPlanner, deletePlanner } from "@/api/planner.js";

const plannerStore = {
  namespaced: true,
  state: {
    plannerInfo: {},
    plannerInfoList: [],
  },
  getters: {},
  mutations: {
    SEARCH_PLANNER_INFO(state, payload) {
      state.plannerInfo = payload;
    },
    SEARCH_PLANNER_INFO_LIST(state, payload) {
      state.plannerInfoList = payload;
    },
    CLEAR_PLANNER_INFO(state) {
      state.plannerInfo = null;
    },
    CLEAR_PLANNER_INFO_LIST(state) {
      state.plannerInfoList = [];
    },
  },
  actions: {
    createCommentInfo(payload) {
      const commentInfo = {};
    },
  },
};

export default plannerStore;
