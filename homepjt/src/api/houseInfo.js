import { apiInstance } from "./index.js";

const api = apiInstance();

// 아파트 검색(시도, 구군, 동, 아파트 이름)
function searchHouseInfo(houseInfo, page, success, fail) {
  api.post(`/house/${page}`, JSON.stringify(houseInfo)).then(success).catch(fail);
}

// 아파트 검색 자동완성
function searchHouseInfoAuto(params, success, fail) {
  api.get(`/house`, { params: params }).then(success).catch(fail);
}

// 아파트 상세 검색(거래내역)
// function searchHouseInfoDetail(aptCode, success, fail) {
//   api.get(`/house/deal/${aptCode}`).then(success).catch(fail);
// }

export { searchHouseInfo, searchHouseInfoAuto };
