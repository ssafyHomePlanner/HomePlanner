import { apiInstance } from "./index.js";

const api = apiInstance();

function searchSido(success, fail) {
    api.get(`/dongcode`).then(success).catch(fail);
}

function searchGugun(sidoName, success, fail) {
    api.get(`/dongcode/gugun/${sidoName}`).then(success).catch(fail);
}

function searchDong(gugunName, success, fail) {
    api.get(`/dongcode/dong/${gugunName}`).then(success).catch(fail);
}

export {
    searchSido,
    searchGugun,
    searchDong,
};