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
import PlannerView from '../views/planner/PlannerView';

Vue.use(VueRouter);

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
        component: BoardDetail,
      },
      {
        path: "write",
        name: "boardWrite",
        component: BoardWrite,
      },
      {
        path: "update",
        name: "boardUpdate",
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
    path: "/path",
    name: "pathView",
    component: PathView,
  },
  {
    path: "/planner",
    name: "plannerView",
    component: PlannerView,
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
