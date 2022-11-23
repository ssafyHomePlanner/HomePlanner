import { apiInstance } from "./index.js";

const api = apiInstance();

// 아파트 검색(시도, 구군, 동, 아파트 이름)
function searchHouseInfo(houseInfo, page, success, fail) {
  api.post(`/house/${page}`, JSON.stringify(houseInfo)).then(success).catch(fail);
}

//아파트 상세 검색
function searchHouseInfoDetail(houseInfo, page, succes, fail) {
  api.post(`/house/detail/${page}`, JSON.stringify(houseInfo)).then(succes).catch(fail);
}

// 아파트 검색 자동완성
function searchHouseInfoAuto(params, success, fail) {
  api.get(`/house`, { params: params }).then(success).catch(fail);
}

// 아파트 댓글 쓰기
function writeHouseComment(commentInfo, success, fail) {
  return api.post(`/house/comment`, JSON.stringify(commentInfo)).then(success).catch(fail);
}

// 아파트 댓글 수정
function updateHouseComment(commentInfo, success, fail) {
  api.put(`/house/comment`, JSON.stringify(commentInfo)).then(success).catch(fail);
}

// 아파트 댓글 조회
function selectHouseComment(aptCode, success, fail) {
  api.get(`/house/comment/${aptCode}`).then(success).catch(fail);
}

// 아파트 댓글 삭제
function deleteHouseComment(houseCommentId, success, fail) {
  return api.delete(`/house/comment/${houseCommentId}`).then(success).catch(fail);
}

// 아파트 상세 검색(거래내역)
async function searchHouseDeal(aptCode, success, fail) {
  await api.get(`/house/deal/${aptCode}`).then(success).catch(fail);
}

// 아파트 조회수 증가
function addHouseInfoViewCnt(aptCode, success, fail) {
  api.put(`/house/update/view/${aptCode}`).then(success).catch(fail);
}

// 아파트 좋아요 수 갱신
function updateAptLikeInfo(aptCode, memberId, flag, success, fail) {
  api.put(`/house/update/${aptCode}/${memberId}/${flag}`).then(success).catch(fail);
}

// 아파트 좋아요 여부 확인
function checkAptLikeInfo(aptCode, memberId, success, fail) {
  api.get(`/house/check/like/${aptCode}/${memberId}`).then(success).catch(fail);
}

export {
  searchHouseInfo,
  searchHouseInfoAuto,
  writeHouseComment,
  updateHouseComment,
  selectHouseComment,
  deleteHouseComment,
  searchHouseInfoDetail,
  addHouseInfoViewCnt,
  searchHouseDeal,
  updateAptLikeInfo,
  checkAptLikeInfo,
};
