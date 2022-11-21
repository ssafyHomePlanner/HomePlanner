<template>
  <v-container fluid style="max-width: 700px" class="mb-12">
    <v-row justify="center" class="mt-3">
      <v-col lg="10" md="10" sm="12" class="text-center">
        <h2>회원가입</h2>
      </v-col>
      <v-col lg="10" md="10" sm="12" class="text-center">
        <v-form class="mb-5" ref="form" lazy-validation>
          <v-row>
            <v-col>
              <v-text-field
                autofocus="true"
                v-model="memberId"
                :rules="memberIdRules"
                label="아이디를 입력하세요."
                @keyup.enter="joinMember"
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
            @keyup.enter="joinMember"
            type="password"
            required
          ></v-text-field>
          <v-text-field
            v-model="memberPasswordConfirm"
            :rules="memberPasswordConfirmRules"
            label="비밀번호 확인을 입력하세요."
            @keyup.enter="joinMember"
            type="password"
            required
          ></v-text-field>
          <v-text-field
            v-model="memberName"
            :rules="memberNameRules"
            label="이름을 입력하세요."
            @keyup.enter="joinMember"
            required
          ></v-text-field>
          <v-radio-group v-model="memberGender" row>
            <v-radio label="남" value="m"></v-radio>
            <v-radio label="여" value="w"></v-radio>
          </v-radio-group>
          <v-text-field
            v-model="memberAge"
            label="생년월일을 입력하세요."
            :rules="memberAgeRules"
            @keyup.enter="joinMember"
            hint="ex) 961225"
            required
          ></v-text-field>
          <v-text-field
            v-model="memberPhone"
            label="핸드폰번호를 입력하세요."
            :rules="memberPhoneRules"
            @keyup.enter="joinMember"
            hint="'-'를 제외하고 입력하세요"
            required
          ></v-text-field>
          <v-text-field
            v-model="memberEmail"
            label="이메일을 입력하세요."
            :rules="memberEmailRules"
            @keyup.enter="joinMember"
            type="email"
            hint="abcd@gmail.com"
            required
          ></v-text-field>
          <v-btn
            class="mt-3"
            block
            elevation="2"
            color="primary"
            @click="joinMember"
            :disabled="
              memberId.length < 1 ||
              memberPassword.length < 1 ||
              memberPasswordConfirm.length < 1 ||
              memberName.length < 1 ||
              memberAge.length < 1 ||
              memberPhone.length < 1 ||
              memberEmail.length < 1
            "
            >확인</v-btn
          >
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
      memberId: "",
      memberPassword: "",
      memberPasswordConfirm: "",
      memberName: "",
      memberGender: "m",
      memberAge: "",
      memberPhone: "",
      memberEmail: "",
      memberSalt: 123,
      memberIdRules: [(v) => !!v || "아이디는 필수입니다."],
      memberNameRules: [(v) => !!v || "이름은 필수입니다."],
      memberAgeRules: [(v) => !!v || "생년월일은 필수입니다."],
      memberPhoneRules: [(v) => !!v || "핸드폰 번호는 필수입니다."],
      memberEmailRules: [(v) => !!v || "이메일은 필수입니다."],
      memberPasswordRules: [(v) => !!v || "비밀번호는 필수입니다."],
      memberPasswordConfirmRules: [(v) => !!v || "비밀번호 확인은 필수입니다."],
      duplicationCheck: false,
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
        this.duplicationCheck = false;
      }
      // 아이디 중복 X
      else {
        alert("사용 가능한 아이디 입니다!!!");
        this.duplicationCheck = true;
      }
    },

    joinMember(e) {
      e.preventDefault();

      if (!this.duplicationCheck) {
        alert("아이디 중복체크가 필요합니다.");
        return;
      }

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
