import Vue from "vue";
import VueRouter from "vue-router";
import HomeView from "../views/HomeView.vue";
import BoardView from "../views/board/BoardView";
import BoardDetail from "../components/board/BoardDetail";
import BoardWrite from "../components/board/BoardWrite";
import BoardUpdate from "../components/board/BoardUpdate";
import BoardList from "../components/board/BoardList";
import AptListView from "../views/apt/AptListView";
import LogInView from "../views/member/LogInView";
import SignUpView from "../views/member/SignUpView";
import PathView from "../views/path/PathView";
import PathResultView from "../views/path/PathResultView";
import PlannerView from "../views/planner/PlannerView";
import PlannerResultView from "../views/planner/PlannerResultView";
import IdSearchView from "../views/member/IdSearchView";
import PasswordSearchView from "../views/member/PasswordSearchView";
import MyPageView from "../views/member/MyPageView";
import UpdateMemberInfoView from "../views/member/UpdateMemberInfoView";
import BookmarkView from "../views/bookmark/BookmarkView";
import AptResultView from "../views/apt/AptResultView";

import store from "@/store";

Vue.use(VueRouter);

// https://v3.router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  const checkToken = store.getters["memberStore/checkToken"];
  let token = sessionStorage.getItem("access-token");
  console.log("로그인 처리 전", checkUserInfo, token);

  if (checkUserInfo != null && token) {
    console.log("토큰 유효성 체크하러 가자!!!!");
    await store.dispatch("memberStore/getUserInfo", token);
  }
  if (!checkToken || checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    // next({ name: "login" });
    router.push({ name: "logInView" });
  } else {
    console.log("로그인 했다!!!!!!!!!!!!!.");
    next();
  }
};

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
  },
  {
    path: "/board",
    name: "boardView",
    component: BoardView,
    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "boardList",
        component: BoardList,
      },
      {
        path: "detail",
        name: "boardDetail",
        beforeEnter: onlyAuthUser,
        component: BoardDetail,
      },
      {
        path: "write",
        name: "boardWrite",
        beforeEnter: onlyAuthUser,
        component: BoardWrite,
      },
      {
        path: "update",
        name: "boardUpdate",
        beforeEnter: onlyAuthUser,
        component: BoardUpdate,
      },
    ],
  },
  {
    path: "/apt/list",
    name: "aptListView",
    component: AptListView,
  },
  {
    path: "/apt/result",
    name: "aptResultView",
    beforeEnter: onlyAuthUser,
    component: AptResultView,
  },
  {
    path: "/login",
    name: "logInView",
    component: LogInView,
  },
  {
    path: "/signup",
    name: "signUpView",
    component: SignUpView,
  },
  {
    path: "/mypage",
    name: "myPageView",
    beforeEnter: onlyAuthUser,
    component: MyPageView,
  },
  {
    path: "/update/member",
    name: "updateMemberInfoView",
    beforeEnter: onlyAuthUser,
    component: UpdateMemberInfoView,
  },
  {
    path: "/search/id",
    name: "idSearchView",
    component: IdSearchView,
  },
  {
    path: "/search/password",
    name: "passwordSearchView",
    component: PasswordSearchView,
  },
  {
    path: "/path",
    name: "pathView",
    beforeEnter: onlyAuthUser,
    component: PathView,
  },
  {
    path: "/path/result",
    name: "pathResult",
    beforeEnter: onlyAuthUser,
    component: PathResultView,
  },
  {
    path: "/planner",
    name: "plannerView",
    beforeEnter: onlyAuthUser,
    component: PlannerView,
  },

  {
    path: "/planner/result",
    name: "plannerResult",
    beforeEnter: onlyAuthUser,
    component: PlannerResultView,
  },
  {
    path: "/bookmark",
    name: "bookmarkView",
    beforeEnter: onlyAuthUser,
    component: BookmarkView,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
