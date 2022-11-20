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
    createPlannerInfo(context, payload) {
      const plannerInfo = {
        aptAmount: payload.aptAmount,
        aptCode: payload.aptCode,
        aptName: payload.aptName,
        budget: payload.budget,
        expectedDate: payload.expectedDate,
        hopedDate: payload.hopedDate,
        loanAmount: payload.loanAmount,
        memberId: payload.memberId,
        savingPerMonth: payload.savingPerMonth,
        writeDate: payload.writeDate,
      };
      createPlanner(
        plannerInfo,
        ({ data }) => {
          console.log("message: {}", data.message);
        },
        ({ error }) => {
          console.log(error);
        }
      );
    },

    updatePlannerInfo(context, payload) {
      const plannerInfo = {
        aptAmount: payload.aptAmount,
        aptCode: payload.aptCode,
        aptName: payload.aptName,
        budget: payload.budget,
        expectedDate: payload.expectedDate,
        hopedDate: payload.hopedDate,
        loanAmount: payload.loanAmount,
        memberId: payload.memberId,
        savingPerMonth: payload.savingPerMonth,
        writeDate: payload.writeDate,
      };
      updatePlanner(
        plannerInfo,
        ({ data }) => {
          console.log("message: {}", data.message);
        },
        ({ error }) => {
          console.log(error);
        }
      );
    },

    selectPlannerInfo({ commit }, payload) {
      selectPlanner(
        payload.memberId,
        ({ data }) => {
          commit("SEARCH_PLANNER_INFO", data.plannerList);
        },
        (error) => {
          console.log(error);
        }
      );
    },

    deletePlannerInfo(context, plannerId) {
      deletePlanner(
        plannerId,
        ({ data }) => {
          console.log(data.message);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default plannerStore;
