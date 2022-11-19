import { apiInstance } from "./index.js";

const api = apiInstance();

// 게시글 리스트 조회
function listArticle(page, success, fail) {
  api.get(`/board/${page}`).then(success).catch(fail);
}

// 게시글 등록
function writeArticle(article, success, fail) {
  api.post(`/board/write`, JSON.stringify(article)).then(success).catch(fail);
}

// 게시글 조회
function getArticle(param, success, fail) {
  api.get(`/board/detail/${param.boardId}/${param.flag}`).then(success).catch(fail);
}

// 게시글 수정
function modifyArticle(article, success, fail) {
  api.put(`/board/update`, JSON.stringify(article)).then(success).catch(fail);
}

// 게시글 삭제
function deleteArticle(boardId, success, fail) {
  api.delete(`/board/delete/${boardId}`).then(success).catch(fail);
}

// 게시글 조회수 증가
function addArticleView(boardId, success, fail) {
  api.put(`/board/update/view/${boardId}`).then(success).catch(fail);
}

// 게시글 댓글 조회
function getArticleCommentList(boardId, success, fail) {
  api.get(`/board/comment/${boardId}`).then(success).catch(fail);
}

// 게시글 댓글 작성
function writeArticleComment(comment, success, fail) {
  return api.post(`board/comment/write`, JSON.stringify(comment)).then(success).catch(fail);
}

// 게시글 댓글 삭제
function deleteArticleComment(boardCommentId, success, fail) {
  return api.delete(`board/comment/delete/${boardCommentId}`).then(success).catch(fail);
}

export {
  listArticle,
  writeArticle,
  getArticle,
  modifyArticle,
  deleteArticle,
  addArticleView,
  getArticleCommentList,
  writeArticleComment,
  deleteArticleComment,
};
