<template>
  <v-container fluid style="max-width: 1100px">
    <v-row justify="center" class="mt-3">
      <v-col lg="10" md="10" sm="12" class="text-center">
        <h2>회원가입</h2>
      </v-col>
      <v-col lg="10" md="10" sm="12" class="text-center">
        <v-form class="mb-5" ref="form" v-model="valid" lazy-validation>
          <v-row>
            <v-col>
              <v-text-field
                v-model="memberId"
                :rules="memberIdRules"
                label="아이디를 입력하세요."
                required
              ></v-text-field
            ></v-col>
            <v-col cols="auto">
              <v-btn>아이디 중복 확인</v-btn>
            </v-col>
          </v-row>
          <v-text-field
            v-model="memberPassword"
            :rules="memberPasswordRules"
            label="비밀번호를 입력하세요"
            type="password"
            required
          ></v-text-field>
          <v-text-field
            v-model="memberPasswordConfirm"
            :rules="memberPasswordConfirmRules"
            label="비밀번호 확인을 입력하세요."
            type="password"
            required
          ></v-text-field>
          <v-text-field
            v-model="memberName"
            label="이름을 입력하세요."
            required
          ></v-text-field>
          <v-radio-group v-model="memberGender" row>
            <v-radio label="남" value="m"></v-radio>
            <v-radio label="여" value="w"></v-radio>
          </v-radio-group>
          <v-text-field
            v-model="memberAge"
            label="생년월일을 입력하세요."
            hint="ex) 961225"
            required
          ></v-text-field>
          <v-text-field
            v-model="memberPhone"
            label="핸드폰번호를 입력하세요."
            hint="'-'를 제외하고 입력하세요"
            required
          ></v-text-field>
          <v-text-field
            v-model="memberEmail"
            label="이메일을 입력하세요."
            type="email"
            hint="abcd@gmail.com"
            required
          ></v-text-field>
          <v-btn block elevation="2" color="success">확인</v-btn>
        </v-form>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {};
</script>

<style></style>
