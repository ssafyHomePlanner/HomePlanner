import { apiInstance } from "./index.js";

const api = apiInstance();

function listArticle(page, success, fail) {
  api.get(`/board/${page}`).then(success).catch(fail);
}

function writeArticle(article, success, fail) {
  api.post(`/board/write`, JSON.stringify(article)).then(success).catch(fail);
}

function getArticle(param, success, fail) {
  api
    .get(`/board/detail/${param.boardId}/${param.flag}`)
    .then(success)
    .catch(fail);
}

function modifyArticle(article, success, fail) {
  api.put(`/board/update`, JSON.stringify(article)).then(success).catch(fail);
}

function deleteArticle(boardId, success, fail) {
  api.delete(`/board/delete/${boardId}`).then(success).catch(fail);
}

function getArticleCommentList(boardId, success, fail) {
  api.get(`/board/comment/${boardId}`).then(success).catch(fail);
}

function writeArticleComment(comment, success, fail) {
  api
    .post(`board/comment/write`, JSON.stringify(comment))
    .then(success)
    .catch(fail);
}

export {
  listArticle,
  writeArticle,
  getArticle,
  modifyArticle,
  deleteArticle,
  getArticleCommentList,
  writeArticleComment,
};
