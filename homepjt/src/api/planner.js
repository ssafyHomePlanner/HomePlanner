import { apiInstance } from "./index.js";

const api = apiInstance();

// 플래너 생성
function createPlanner(plannerInfo, success, fail) {
  api.post(`/planner`, JSON.stringify(plannerInfo)).then(success).catch(fail);
}

// 플래너 수정
function updatePlanner(plannerInfo, success, fail) {
  api.put(`/planner`, JSON.stringify(plannerInfo)).then(success).catch(fail);
}

// 플래너 조회
function selectPlannerList(memberId, success, fail) {
  api.get(`/planner/${memberId}`).then(success).catch(fail);
}

// 플래너 삭제
function deletePlanner(plannerId, success, fail) {
  api.delete(`/planner/${plannerId}`).then(success).catch(fail);
}

export { createPlanner, updatePlanner, selectPlannerList, deletePlanner };
