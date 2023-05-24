<template>
  <div>
    <v-container fluid style="max-width: 1100px">
      <v-row justify="center">
        <v-col lg="10" md="10" sm="12" class="text-center">
          <h2>아이디 찾기</h2>
        </v-col>
        <v-col lg="6" md="6" sm="8">
          <v-form class="mb-5" ref="form">
            <v-text-field v-model="memberName" label="회원 이름" required></v-text-field>
            <v-text-field v-model="memberPhone" label="회원 전화번호" required></v-text-field>
            <v-btn block elevation="2" color="primary" @click="checkMemberId">확인</v-btn>
          </v-form>
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
      memberName: "",
      memberPhone: "",
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["findMemberInfoId"]),

    checkMemberId() {
      const memberInfo = {
        memberName: this.memberName,
        memberPhone: this.memberPhone,
      };
      this.findMemberInfoId(memberInfo);
    },
  },
};
</script>

<style></style>
