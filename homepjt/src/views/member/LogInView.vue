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
          <v-form class="mb-5" ref="form" v-model="valid" lazy-validation>
            <v-text-field
              v-model="memberId"
              :rules="memberIdRules"
              label="아이디"
              required
            ></v-text-field>
            <v-text-field
              v-model="memberPassword"
              :rules="memberPasswordRules"
              label="비밀번호"
              type="password"
              required
            ></v-text-field>
            <v-btn block elevation="2" color="success">확인</v-btn>
          </v-form>
          <v-col lg="10" md="10" sm="12" class="text-right">
            <router-link :to="{ name: 'idSearchView' }" class="link">
              아이디 찾기</router-link
          > | 
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
export default {
  data() {
    return {
      valid: false,
      memberId: "",
      memberPassword: "",
      saveId: false,
      memberIdRules: [
        (v) => !!v || "Name is required",
        (v) => v.length <= 10 || "Name must be less than 10 characters",
      ],
      memberPasswordRules: [
        (v) => !!v || "Name is required",
        (v) => v.length <= 10 || "Name must be less than 10 characters",
      ],
    };
  },
};
</script>

<style></style>
