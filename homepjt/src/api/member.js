import { apiInstance } from "./index.js";

const api = apiInstance();

// 로그인
async function login(member, success, fail) {
  await api.post(`/member/login`, JSON.stringify(member)).then(success).catch(fail);
}

//회원인증
async function findById(memberId, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/member/info/${memberId}`).then(success).catch(fail);
}

// 토큰 재발급
async function tokenRegeneration(member, success, fail) {
  api.defaults.headers["refresh-token"] = sessionStorage.getItem("refresh-token");
  await api.post(`/member/refresh`, member).then(success).catch(fail);
}

// 로그아웃
async function logout(memberId, success, fail) {
  await api.get(`/member/logout/${memberId}`).then(success).catch(fail);
}

// 회원탈퇴
function deleteMember(memberId, success, fail) {
  api.delete(`/member/delete/${memberId}`).then(success).catch(fail);
}

// 회원정보 상세 보기
function selectMemberDetail(memberId, success, fail) {
  api.get(`/member/detail/${memberId}`).then(success).catch(fail);
}

// 회원 아이디 찾기
function findMemberId(memberName, memberPhone, success, fail) {
  api.get(`/member/findId/${memberName}/${memberPhone}`).then(success).catch(fail);
}

// 회원 비밀번호 찾기
function findMemberPw(memberId, memberPhone, success, fail) {
  api.get(`/member/findpw/${memberId}/${memberPhone}`).then(success).catch(fail);
}

// 아이디 중복 확인
function checkMemberId(memberId, success, fail) {
  api.get(`/member/idCheck/${memberId}`).then(success).catch(fail);
}

// 회원가입
function joinMember(memberInfo, success, fail) {
  api.post(`/member/join`, JSON.stringify(memberInfo)).then(success).catch(fail);
}

// 회원 정보 수정
function updateMember(memberInfo, success, fail) {
  api.put(`/member/update`, JSON.stringify(memberInfo)).then(success).catch(fail);
}

// 회원 최근 검색 조회
function selectRecentData(memberId, success, fail) {
  api.get(`/member/recent/${memberId}`).then(success).catch(fail);
}

// 회원 최근 검색 등록
function insertRecentData(memberId, data, success, fail) {
  api.post(`/member/recent/${memberId}/${data}`).then(success).catch(fail);
}

// 회원 최근 검색 전체 삭제
function deleteRecentDataAll(memberId, success, fail) {
  api.delete(`/member/recent/${memberId}`).then(success).catch(fail);
}

// 회원 최근 검색 삭제
function deleteRecentData(memberId, recentId, success, fail) {
  api.delete(`/member/recent/${memberId}/${recentId}`).then(success).catch(fail);
}

export {
  login,
  findById,
  tokenRegeneration,
  logout,
  deleteMember,
  selectMemberDetail,
  findMemberId,
  findMemberPw,
  checkMemberId,
  joinMember,
  updateMember,
  selectRecentData,
  insertRecentData,
  deleteRecentDataAll,
  deleteRecentData,
};
