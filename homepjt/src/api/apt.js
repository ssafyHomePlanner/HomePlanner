import { apiInstance } from "./index.js";

const api = apiInstance();

// 시도 출력
function searchSido(success, fail) {
  api.get(`/dongcode`).then(success).catch(fail);
}

//시군구 출력
function searchGugun(sidoName, success, fail) {
  api.get(`/dongcode/gugun/${sidoName}`).then(success).catch(fail);
}

//동 출력
function searchDong(gugunName, success, fail) {
  api.get(`/dongcode/dong/${gugunName}`).then(success).catch(fail);
}

//dongcode info 출력
function searchDongCode(dongCode, success, fail) {
  api.get(`/dongcode/info/${dongCode}`).then(success).catch(fail);
}

export { searchSido, searchGugun, searchDong, searchDongCode };
