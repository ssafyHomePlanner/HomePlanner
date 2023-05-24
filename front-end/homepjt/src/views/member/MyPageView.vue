<template>
  <v-container fluid style="max-width: 700px" class="mb-12">
    <v-row justify="center" class="mt-3">
      <v-col lg="10" md="10" sm="12" class="text-center">
        <h2>마이페이지</h2>
      </v-col>
      <v-col lg="10" md="10" sm="12" class="text-center">
        <v-form class="mb-5" ref="form">
          <v-row> 아이디 </v-row>
          <v-col> <v-text-field readonly v-model="userInfo.id"></v-text-field></v-col>

          <v-row> 이름 </v-row>
          <v-text-field readonly v-model="userInfo.name"></v-text-field>

          <v-row> 성별 </v-row>
          <v-radio-group v-model="userInfo.gender" row readonly>
            <v-radio label="남" value="m"></v-radio>
            <v-radio label="여" value="w"></v-radio>
          </v-radio-group>

          <v-row> 나이 </v-row>
          <v-text-field v-model="userInfo.age" required readonly></v-text-field>

          <v-row> 전화번호 </v-row>
          <v-text-field v-model="userInfo.phone" required readonly></v-text-field>

          <v-row> 이메일 </v-row>
          <v-text-field v-model="userInfo.email" type="email" required readonly></v-text-field>
          <v-btn block elevation="2" color="primary" @click="moveToUpdate"
            >회원 정보 수정하기</v-btn
          >
        </v-form>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState } from "vuex";
const memberStore = "memberStore";

export default {
  data() {
    return {};
  },

  created: {},

  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    moveToUpdate() {
      this.$router.push({ name: "updateMemberInfoView" }).catch(()=>{});
    },
  },
};
</script>

<style></style>
