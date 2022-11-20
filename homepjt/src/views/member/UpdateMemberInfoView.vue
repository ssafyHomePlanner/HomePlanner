<template>
  <v-container fluid style="max-width: 700px" class="mb-12">
    <v-row justify="center" class="mt-3">
      <v-col lg="10" md="10" sm="12" class="text-center">
        <h2>회원 정보 수정</h2>
      </v-col>
      <v-col lg="10" md="10" sm="12" class="text-center">
        <v-form class="mb-5" ref="form" lazy-validation>
          <v-text-field
            v-model="memberPassword"
            label="비밀번호를 입력하세요"
            type="password"
            required
          ></v-text-field>
          <v-text-field
            v-model="memberPasswordConfirm"
            label="비밀번호 확인을 입력하세요."
            type="password"
            required
          ></v-text-field>
          <v-text-field v-model="userInfo.name" hint="이름을 입력하세요" required></v-text-field>
          <v-row>성별</v-row>
          <v-radio-group v-model="memberGender" row>
            <v-radio label="남" value="m"></v-radio>
            <v-radio label="여" value="w"></v-radio>
          </v-radio-group>
          <v-text-field v-model="memberAge" hint="ex) 961225" required></v-text-field>
          <v-text-field
            v-model="memberPhone"
            hint="'-'를 제외하고 입력하세요"
            required
          ></v-text-field>
          <v-text-field
            v-model="memberEmail"
            type="email"
            hint="abcd@gmail.com"
            required
          ></v-text-field>
          <v-btn block elevation="2" color="primary" @click="updateMember">확인</v-btn>
        </v-form>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
const memberStore = "memberStore";

export default {
  data() {
    return {
      memberPassword: "",
      memberName: "",
      memberGender: "",
      memberAge: "",
      memberPhone: "",
      memberEmail: "",
    };
  },

  mounted() {
    this.memberName = this.$store.state.memberStore.userInfo.name;
    this.memberGender = this.$store.state.memberStore.userInfo.gender;
    this.memberAge = this.$store.state.memberStore.userInfo.age;
    this.memberPhone = this.$store.state.memberStore.userInfo.phone;
    this.memberEmail = this.$store.state.memberStore.userInfo.email;
  },

  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },

  methods: {
    ...mapActions(memberStore, ["updateMemberInfo"]),
    updateMember() {
      const memberInfo = {
        id: this.userInfo.id,
        pw: this.memberPassword,
        name: this.memberName,
        gender: this.memberGender,
        age: this.memberAge,
        phone: this.memberPhone,
        email: this.memberEmail,
      };

      this.updateMemberInfo(memberInfo);
    },
  },
};
</script>

<style></style>
