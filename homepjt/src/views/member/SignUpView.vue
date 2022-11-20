<template>
  <v-container fluid style="max-width: 700px" class="mb-12">
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
              <v-btn @click="checkIdDuplicated">아이디 중복 확인</v-btn>
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
          <v-text-field v-model="memberName" label="이름을 입력하세요." required></v-text-field>
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
          <v-btn block elevation="2" color="primary" @click="joinMember">확인</v-btn>
        </v-form>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
const memberStore = "memberStore";

export default {
  data() {
    return {
      memberId: null,
      memberPassword: null,
      memberPasswordConfirm: null,
      memberName: null,
      memberGender: "",
      memberAge: null,
      memberPhone: null,
      memberEmail: null,
      memberSalt: 123,
    };
  },

  computed: {
    ...mapState(memberStore, ["isDuplicatedId"]),
  },
  methods: {
    ...mapActions(memberStore, ["checkMemberInfoId", "joinMemberInfo"]),
    ...mapMutations(memberStore, ["IS_DUPLICATED_ID"]),

    async checkIdDuplicated() {
      this.IS_DUPLICATED_ID(false);
      console.log(this.memberId);

      await this.checkMemberInfoId(this.memberId);

      console.log(this.isDuplicatedId);

      // 아이디 중복
      if (this.isDuplicatedId) {
        alert("중복된 아이디 입니다!!!");
      }
      // 아이디 중복 X
      else {
        alert("사용 가능한 아이디 입니다!!!");
      }
    },

    joinMember() {
      const memberInfo = {
        id: this.memberId,
        pw: this.memberPassword,
        name: this.memberName,
        gender: this.memberGender,
        age: this.memberAge,
        phone: this.memberPhone,
        email: this.memberEmail,
        salt: this.memberSalt,
      };

      this.joinMemberInfo(memberInfo);
    },
  },
};
</script>

<style></style>
