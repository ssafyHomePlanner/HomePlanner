import jwtDecode from "jwt-decode";
import router from "@/router";
import {
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
} from "@/api/member.js";

const memberStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null,
    isValidToken: false,

    memberId: null,
    memberPw: null,

    isDuplicatedId: false,

    recentDataList: [],
    recentData: {},
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
    checkToken: function (state) {
      return state.isValidToken;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_IS_VALID_TOKEN: (state, isValidToken) => {
      state.isValidToken = isValidToken;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
    },
    SET_MEMBER_ID: (state, memberId) => {
      state.memberId = memberId;
    },
    SET_MEMBER_PW: (state, memberPw) => {
      state.memberPw = memberPw;
    },
    IS_DUPLICATED_ID: (state, flag) => {
      state.isDuplicatedId = flag;
    },
    SET_RECENT_DATA_LIST: (state, data) => {
      state.recentDataList = data;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        ({ data }) => {
          if (data.message === "success") {
            let accessToken = data["access-token"];
            let refreshToken = data["refresh-token"];
            // console.log("login success token created!!!! >> ", accessToken, refreshToken);
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            commit("SET_IS_VALID_TOKEN", true);
            sessionStorage.setItem("access-token", accessToken);
            sessionStorage.setItem("refresh-token", refreshToken);

            console.log(data);
            alert("로그인 성공!!");
          } else {
            alert("로그인 실패!!, 아이디 혹은 패스워드를 확인하세요!!");
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
            commit("SET_IS_VALID_TOKEN", false);
            router.push({ name: "logInView" });
          }
        },
        (error) => {
          alert("로그인 성공 !!! @@");
          router.push({ name: "logInView" });
          console.log(error);
        }
      );
    },
    async getUserInfo({ commit, dispatch }, token) {
      let decodeToken = jwtDecode(token);
      console.log("2. getUserInfo() decodeToken :: ", decodeToken);
      await findById(
        decodeToken.memberId,
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_USER_INFO", data.memberInfo);
            console.log("3. getUserInfo data >> ", data);
          } else {
            console.log("유저 정보 없음!!!!");
          }
        },
        async (error) => {
          console.log(
            "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
            error.response.status
          );
          commit("SET_IS_VALID_TOKEN", false);
          await dispatch("tokenRegeneration");
        }
      );
    },
    async tokenRegeneration({ commit, state }) {
      console.log("토큰 재발급 >> 기존 토큰 정보 : {}", sessionStorage.getItem("access-token"));
      await tokenRegeneration(
        JSON.stringify(state.userInfo),
        ({ data }) => {
          if (data.message === "success") {
            let accessToken = data["access-token"];
            console.log("재발급 완료 >> 새로운 토큰 : {}", accessToken);
            sessionStorage.setItem("access-token", accessToken);
            commit("SET_IS_VALID_TOKEN", true);
          }
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === 401) {
            console.log("갱신 실패");
            // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
            await logout(
              state.userInfo.userid,
              ({ data }) => {
                if (data.message === "success") {
                  console.log("리프레시 토큰 제거 성공");
                } else {
                  console.log("리프레시 토큰 제거 실패");
                }
                alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
                commit("SET_IS_LOGIN", false);
                commit("SET_USER_INFO", null);
                commit("SET_IS_VALID_TOKEN", false);
                router.push({ name: "loginView" });
              },
              (error) => {
                console.log(error);
                commit("SET_IS_LOGIN", false);
                commit("SET_USER_INFO", null);
              }
            );
          }
        }
      );
    },
    async userLogout({ commit }, userid) {
      await logout(
        userid,
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_IS_LOGIN", false);
            commit("SET_USER_INFO", null);
            commit("SET_IS_VALID_TOKEN", false);
          } else {
            console.log("유저 정보 없음!!!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    // 회원 탈퇴
    deleteMemberInfo({ commit }, memberId) {
      deleteMember(
        memberId,
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_IS_LOGIN", false);
            commit("SET_USER_INFO", null);
            commit("SET_IS_VALID_TOKEN", false);
          } else {
            console.log("유저 정보 없음 !!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    // 회원 정보 상세 보기
    selectMemberInfoDetail({ commit }, memberId) {
      selectMemberDetail(
        memberId,
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_USER_INFO", data.memberDto);
          } else {
            console.log("유저 정보 없음 !!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    // 회원 아이디 찾기
    findMemberInfoId({ commit }, payload) {
      findMemberId(
        payload.memberName,
        payload.memberPhone,
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_MEMBER_ID", payload.memberId);
          } else {
            console.log("유저 정보 없음 !!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    // 회원 비밀번호 찾기
    findMemberInfoPw({ commit }, payload) {
      findMemberPw(
        payload.memberId,
        payload.memberPhone,
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_MEMBER_PW", payload.memberPw);
          } else {
            console.log("유저 정보 없음 !!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    // 아이디 중복 확인
    async checkMemberInfoId({ commit }, memberId) {
      console.log("check id : ", memberId);
      await checkMemberId(
        memberId,
        ({ data }) => {
          if (data.message === "success") {
            console.log("아이디 중복 발생 X!!");
            commit("IS_DUPLICATED_ID", false);
          } else {
            console.log("아이디 중복 발생");
            commit("IS_DUPLICATED_ID", true);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    // 회원가입
    joinMemberInfo({ commit }, payload) {
      const memberInfo = {
        age: payload.age,
        email: payload.email,
        gender: payload.gender,
        id: payload.id,
        name: payload.name,
        phone: payload.phone,
        pw: payload.pw,
        salt: payload.salt,
      };

      console.log(commit);

      joinMember(
        memberInfo,
        ({ data }) => {
          if (data.message === "success") {
            console.log("회원가입 성공");
            router.push({ name: "logInView" });
          } else {
            alert("회원가입 실패!!");
            console.log("회원가입 실패");
          }
        },
        (error) => {
          alert("회원가입 실패!!");
          console.log(error);
        }
      );
    },

    // 회원 정보 수정
    updateMemberInfo({ commit }, payload) {
      console.log(commit);
      const memberInfo = {
        age: payload.age,
        email: payload.email,
        gender: payload.gender,
        id: payload.id,
        joinDate: "",
        name: payload.name,
        phone: payload.phone,
        pw: payload.pw,
        salt: payload.salt,
      };
      updateMember(
        memberInfo,
        async ({ data }) => {
          if (data.message === "success") {
            alert("회원정보 수정 성공 !! 다시 로그인 해주세요!!");
            // router.push({ name: "home" });
            await logout(
              memberInfo.id,
              ({ data }) => {
                if (data.message === "success") {
                  console.log("리프레시 토큰 제거 성공");
                } else {
                  console.log("리프레시 토큰 제거 실패");
                }
                commit("SET_IS_LOGIN", false);
                commit("SET_USER_INFO", null);
                commit("SET_IS_VALID_TOKEN", false);
                router.push({ name: "loginView" });
              },
              (error) => {
                console.log(error);
                commit("SET_IS_LOGIN", false);
                commit("SET_USER_INFO", null);
              }
            );
          } else {
            alert("회원정보 수정 실패");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    // 회원 최근 검색 조회
    selectRecentDataInfo({ commit }, memberId) {
      selectRecentData(
        memberId,
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_RECNET_DATA_LIST", []);
            commit("SET_RECNET_DATA_LIST", data.resultDataList);
          } else {
            console.log("회원 정보가 없습니다.");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    // 회원 최근 검색 등록
    insertRecentDataInfo(payload) {
      insertRecentData(
        payload.memberId,
        payload.data,
        ({ data }) => {
          if (data.message === "success") {
            console.log("최근 검색 등록 성공");
          } else {
            console.log("최근 검색 등록 실패");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },

    // 회원 최근 검색 전체 삭제
    deleteRecentDataInfoAll({ commit }, memberId) {
      deleteRecentDataAll(memberId, ({ data }) => {
        if (data.message === "success") {
          commit("SET_RECENT_DATA_LIST", []);
          console.log("최근 검색 전체 삭제 성공");
        } else {
          console.log("최근 검색 전체 삭제 실패");
        }
      });
    },

    // 회원 최근 검색 삭제
    deleteRecentDataInfo(payload) {
      deleteRecentData(
        payload.memberId,
        payload.recentId,
        ({ data }) => {
          if (data.message === "success") {
            console.log("최근 검색 삭제 성공");
          } else {
            console.log("최근 검색 삭제 실패");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default memberStore;
