<template>
  <div>
    <v-toolbar>
      <v-container>
        <v-row justify="center">
          <v-img src="@/assets/logo.png" max-width="60" max-height="60" @click="moveToHome()"></v-img>

          <v-col cols="auto">
            <!-- <router-link :to="{ name: 'boardView' }" class="link">아파트 계획 세우기</router-link> -->
            <v-btn flat to="/board">아파트 계획 세우기</v-btn>
            <!-- <router-link :to="{ name: 'boardView' }" class="link"> 아파트 경로 찾기</router-link> -->
            <v-btn flat to="/">아파트 경로 찾기</v-btn>
            <!-- <router-link :to="{ name: 'boardView' }" class="link"> 아파트 커뮤니티</router-link> -->
            <v-btn flat to="/board">아파트 커뮤니티</v-btn>
          </v-col>

          <v-spacer></v-spacer>

          <v-col cols="auto">
            <!-- <router-link :to="{ name: 'boardView' }" class="link"> 회원가입</router-link> -->
            <v-btn flat to="/">회원가입</v-btn>
            <!-- <router-link :to="{ name: 'boardView' }" class="link"> 로그인</router-link> -->
            <v-btn flat to="/board">로그인</v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-toolbar>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {
    moveToHome() {
      this.$router.push({ name: "home" });
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

v-img {
  display: inline;
}
</style>
