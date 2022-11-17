<template>
  <div>
    <v-toolbar>
      <v-row>
        <v-toolbar-title class="ml-4" @click="moveToHome()">
          <v-btn text> Home Planner </v-btn></v-toolbar-title
        >
        <v-col cols="col-8">
          <v-container>
            <v-row justify="space-around">
              <router-link :to="{ name: 'plannerView' }" class="link"
                >아파트 계획 세우기</router-link
              >
              <router-link :to="{ name: 'pathView' }" class="link">
                아파트 경로 찾기</router-link
              >
              <router-link :to="{ name: 'boardView' }" class="link">
                아파트 커뮤니티</router-link
              >
            </v-row>
          </v-container>
        </v-col>
        <v-col cols="col-2">
          <v-container class="mr-4">
            <v-row justify="end">
              <router-link :to="{ name: 'signUpView' }" class="link mr-4">
                회원가입</router-link
              >
              <router-link :to="{ name: 'logInView' }" class="link">
                로그인</router-link
              >
            </v-row>
          </v-container>
        </v-col>
      </v-row>
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
