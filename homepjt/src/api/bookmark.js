import { apiInstance } from "./index.js";

const api = apiInstance();

// 관심 경로 리스트 출력
function searchPath(pahtInfo, success, fail) {
  api.post(`/bookmark/path/search`, JSON.stringify(pahtInfo)).then(success).catch(fail);
}

// 관심 경로 등록
function insertPath(pathInfo, memberId, pathName, success, fail) {
  api
    .post(`/bookmark/path/${memberId}/${pathName}`, JSON.stringify(pathInfo))
    .then(success)
    .catch(fail);
}

// 관심 경로 삭제
function deletePath(bookmarkPathId, success, fail) {
  api.delete(`/bookmark/path/${bookmarkPathId}`).then(success).catch(fail);
}

// 관심 상품 삭제
function deleteBookmarkApt(bookmarkAptId, success, fail) {
  api.delete(`/bookmark/${bookmarkAptId}`).then(success).catch(fail);
}

// 관심 상품 조회
function selectBookmarkApt(memberId, success, fail) {
  api.get(`/bookmark/${memberId}`).then(success).catch(fail);
}

// 관심 상품 등록
function insertBookmarkApt(memberId, aptCode, success, fail) {
  api.post(`/bookmark/${memberId}/${aptCode}`).then(success).catch(fail);
}

// 게시글 좋아요 회원 등록
function updateBoardLikeMember(boardId, memberId, success, fail) {
  api.post(`/bookmark/${boardId}/${memberId}`).then(success).catch(fail);
}

// 게시글 좋아요 클릭 여부 확인
function checkBoardLikeMember(boardId, memberId, success, fail) {
  api.get(`/bookmark/${boardId}/${memberId}`).then(success).catch(fail);
}

export {
  searchPath,
  insertPath,
  deletePath,
  deleteBookmarkApt,
  selectBookmarkApt,
  insertBookmarkApt,
  updateBoardLikeMember,
  checkBoardLikeMember,
};
