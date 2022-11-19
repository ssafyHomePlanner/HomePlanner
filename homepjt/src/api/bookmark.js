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
