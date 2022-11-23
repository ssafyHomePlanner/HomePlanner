<template>
  <div>
    <v-container>
      <v-row justify="center" class="mt-3">
        <v-col lg="10" md="10" sm="12" class="text-right">
          아직 회원이 아니신가요?
          <router-link :to="{ name: 'signUpView' }" class="link">
            회원가입</router-link
          >
        </v-col>
      </v-row>
    </v-container>
    <v-container fluid style="max-width: 1100px">
      <v-row justify="center">
        <v-col lg="10" md="10" sm="12" class="text-center">
          <h2>로그인</h2>
        </v-col>
        <v-col lg="6" md="6" sm="8">
          <v-row class="text-right" justify="end">
            <v-checkbox v-model="saveId" label="아이디저장"></v-checkbox>
          </v-row>
          <v-form class="mb-5" ref="form" lazy-validation>
            <v-text-field
              :autofocus="true"
              v-model="member.id"
              :rules="memberIdRules"
              label="아이디"
              @keyup.enter="confirm"
              required
            ></v-text-field>
            <v-text-field
              v-model="member.pw"
              :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
              :type="show1 ? 'text' : 'password'"
              :rules="memberPasswordRules"
              label="비밀번호"
              @click:append="show1 = !show1"
              @keyup.enter="confirm"
              required
            ></v-text-field>
            <v-btn
              block
              elevation="2"
              class="mt-5"
              color="primary"
              @click="confirm"
              :disabled="member.id.length < 1 || member.pw.length < 1"
              >확인</v-btn
            >
          </v-form>
          <v-col lg="10" md="10" sm="12" class="text-right">
            <router-link :to="{ name: 'idSearchView' }" class="link">
              아이디 찾기</router-link
            >
            |
            <router-link :to="{ name: 'passwordSearchView' }" class="link">
              비밀번호 찾기</router-link
            >
          </v-col>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  data() {
    return {
      member: {
        id: "",
        pw: "",
      },
      show1: false,
      saveId: false,
      memberIdRules: [(v) => !!v || "아이디는 필수입니다."],
      memberPasswordRules: [(v) => !!v || "비밀번호는 필수입니다."],
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError", "userInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userConfirm", "getUserInfo"]),
    async confirm(e) {
      e.preventDefault();
      console.log("this.member.id = ", this.member.id);
      console.log("this.member.pw = ", this.member.pw);
      await this.userConfirm(this.member);
      let token = sessionStorage.getItem("access-token");
      if (this.isLogin) {
        await this.getUserInfo(token);

        console.log("user info id = ", this.userInfo);

        this.$router.push({ name: "home" }).catch(() => {});
      }
    },
  },
};
</script>

<style></style>
