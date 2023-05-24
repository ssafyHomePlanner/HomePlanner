<template>
  <v-sheet
    rounded="xl"
    color="white"
    elevation="5"
    height="350"
    width="800"
    class="ma-8"
  >
    <v-container class="pa-6">
      <v-row class="mb-1">
        <v-col style="text-align: left">
          <v-form ref="form" lazy-validation>
            <v-text-field
              v-model="board.title"
              :rules="titleRules"
              label="제목을 입력하세요."
              required
            ></v-text-field>
            <v-textarea
              v-model="board.content"
              :rules="contentRules"
              label="내용을 입력하세요."
              required
            ></v-textarea>
          </v-form>
          <v-btn @click="updateArticle" color="primary" elevation="2"
            >글수정</v-btn
          >
        </v-col>
      </v-row>
    </v-container>
  </v-sheet>
</template>

<script>
import { mapState, mapActions } from "vuex";

const boardStore = "boardStore";

export default {
  name: "BoardUpdateItem",

  computed: {
    ...mapState(boardStore, ["board"]),
  },
  data() {
    return {
      titleRules: [(v) => !!v || "제목을 입력해주세요"],
      contentRules: [(v) => !!v || "내용을 입력해주세요"],
    };
  },
  methods: {
    updateArticle() {
      this.updateBoard(this.board);
      this.moveList();
    },
    moveList() {
      this.$router.push({ name: "boardList" }).catch(()=>{});
    },
    ...mapActions(boardStore, ["updateBoard"]),
  },
};
</script>

<style></style>
