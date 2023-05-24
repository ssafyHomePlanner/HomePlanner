import { createPlanner, updatePlanner, selectPlannerList, deletePlanner } from "@/api/planner.js";

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
    selectPlannerInfo({ commit }, payload) {
      commit("SEARCH_PLANNER_INFO", payload);
    },
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
          alert("즐겨찾기에 저장되었습니다.");
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

    selectPlannerInfoList({ commit }, memberId) {
      selectPlannerList(
        memberId,
        ({ data }) => {
          console.log(data.plannerList);
          commit("CLEAR_PLANNER_INFO");
          commit("SEARCH_PLANNER_INFO_LIST", data.plannerList);
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
