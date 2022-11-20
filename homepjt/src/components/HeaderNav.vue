<template>
  <div>
    <v-toolbar>
      <v-row>
        <v-toolbar-title class="ml-4" @click="moveToHome()">
          <v-btn text style="font-size: 20px"> Home Planner </v-btn></v-toolbar-title
        >
        <v-col cols="col-8">
          <v-container>
            <v-row justify="space-around">
              <router-link :to="{ name: 'plannerView' }" class="link">
                <v-icon size="25" color="blue darken-2"> mdi-draw-pen </v-icon>
                아파트 구매계획 세우기</router-link
              >
              <router-link :to="{ name: 'pathView' }" class="link">
                <v-icon size="25" color="blue darken-2"> mdi-map-marker-distance </v-icon>
                아파트 경로 찾기</router-link
              >
              <router-link :to="{ name: 'boardView' }" class="link">
                <v-icon size="25" color="blue darken-2"> mdi-account-multiple </v-icon>
                아파트 커뮤니티</router-link
              >
            </v-row>
          </v-container>
        </v-col>

        <!-- after login -->
        <v-col cols="col-2" v-if="userInfo">
          <v-container class="mr-4">
            <v-row justify="end">
              <router-link :to="{ name: 'myPageView' }" class="link mr-4"> 마이페이지</router-link>
              <!-- <router-link :to="{ name: 'logInView' }" class="link"> 로그아웃</router-link> -->
              <v-icon size="25" color="blue darken-2" @click.prevent="onClickLogout">
                mdi-account-multiple
              </v-icon>
              로그아웃
            </v-row>
          </v-container>
        </v-col>

        <!-- before login -->
        <v-col cols="col-2" v-else>
          <v-container class="mr-4">
            <v-row justify="end">
              <router-link :to="{ name: 'signUpView' }" class="link mr-4"> 회원가입</router-link>
              <router-link :to="{ name: 'logInView' }" class="link"> 로그인</router-link>
            </v-row>
          </v-container>
        </v-col>
      </v-row>
    </v-toolbar>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
const memberStore = "memberStore";

export default {
  data() {
    return {};
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userLogout"]),
    onClickLogout() {
      console.log(this.userInfo);
      this.userLogout(this.userInfo.id);
      sessionStorage.removeItem("access-token");
      sessionStorage.removeItem("refresh-token");
      // if (this.$router.name != "home") {
      alert("로그아웃 되었습니다!!");
      this.$router.push({ name: "home" });
      // }
    },

    moveToHome() {
      this.$router.push({ name: "home" }).catch(() => {});
    },
  },
};
</script>

<style>
v-toolbar {
  height: 20px;
  width: 100%;
}

.link {
  text-decoration: none;
}
</style>
