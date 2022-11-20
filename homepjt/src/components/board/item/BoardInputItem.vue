<template>
  <v-sheet rounded="xl" color="white" elevation="5" height="400" width="800" class="ma-8">
    <v-container class="pa-6">
      <v-row class="mb-1">
        <v-col style="text-align: left">
          <v-form ref="form" lazy-validation>
            <v-text-field
              v-model="article.title"
              :rules="titleRules"
              label="제목을 입력하세요."
              required
            ></v-text-field>
            <v-textarea
              v-model="article.content"
              :rules="contentRules"
              label="내용을 입력하세요."
              required
            ></v-textarea>
          </v-form>
        </v-col>
      </v-row>
      <v-row>
        <v-col style="text-align: right">
          <v-btn @click="registArticle" color="primary" elevation="2">글작성</v-btn>
        </v-col>
      </v-row>
    </v-container>
  </v-sheet>
</template>

<script>
import { mapState, mapActions } from "vuex";

const boardStore = "boardStore";
const memberStore = "memberStore";

export default {
  name: "BoardInputItem",
  data() {
    return {
      article: {
        memberId: "",
        title: "",
        content: "",
      },
      titleRules: [(v) => !!v || "제목을 입력해주세요"],
      contentRules: [(v) => !!v || "내용을 입력해주세요"],
    };
  },
  computed: {
    ...mapState(memberStore, "userInfo"),
  },
  methods: {
    registArticle() {
      // this.article.memberId = this.$store.state.member.id;
      this.article.memberId = this.$store.state.memberStore.userInfo.id;
      this.writeBoard(this.article);
      this.moveList();
    },
    moveList() {
      this.$router.push({ name: "boardList" });
    },
    ...mapActions(boardStore, ["writeBoard"]),
  },
};
</script>

<style></style>
