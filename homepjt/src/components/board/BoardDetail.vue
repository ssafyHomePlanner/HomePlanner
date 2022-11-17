<template>
  <v-container fill-height class="mt-6 mb-8">
    <v-row align="end" justify="center">
      <v-col cols="auto">
        <div class="board-detail-title">
          {{ board.title }}
        </div>
      </v-col>
      <v-spacer></v-spacer>
      <v-col cols="auto">
        <span @click="moveUpdate">수정</span> |
        <span @click="clickDeleteBoard">삭제</span>
      </v-col>
    </v-row>
    <v-row justify="start">
      <v-col cols="col-2">
        <div class="board-detail-member">작성자: {{ board.memberId }}</div>
      </v-col>
    </v-row>
    <v-row justify="start">
      <v-col cols="col-4">
        <v-icon large color="#999999"> mdi-message-text </v-icon>
        {{ boardComment.length }} 건
        <v-icon large color="#999999"> mdi-eye </v-icon>
        {{ board.viewCnt }}회 |
        <span class="board-detail-member">
          {{ board.writeDate }}
        </span>
      </v-col>
    </v-row>
    <v-row justify="start">
      <v-col cols="col-5">{{ board.content }}</v-col>
    </v-row>
    <v-row justify="start">
      <v-col cols="col-2" class="align-center">
        <v-sheet
          rounded="pill"
          color="white"
          elevation="3"
          height="50"
          width="90"
          class="ma-8"
        >
          <v-container class="heart-shape">
            <v-row justify="center" class="align-center">
              <v-icon class="mr-2" large color="red darken-2">
                mdi-heart
              </v-icon>
              {{ board.likeCnt }}
            </v-row>
          </v-container>
        </v-sheet>
      </v-col>
    </v-row>
    <v-row justify="start">
      <v-col cols="col-2">댓글 {{ boardComment.length }}</v-col>
    </v-row>
    <div>
      <v-divider></v-divider>
    </div>
    <board-comment-item
      v-for="(comment, index) in boardComment"
      :key="index"
      :comment="comment"
    />
    <v-divider></v-divider>
    <v-container>
      <v-row>
        <v-col cols="col-8">
          <v-text-field
            v-model="comment.content"
            counter="25"
            hint="댓글을 작성하세요"
            label="댓글"
            solo
          ></v-text-field>
        </v-col>
        <v-col cols="col-4">
          <v-btn @click="clickEnrollComment" color="primary" elevation="3" large
            >등록</v-btn
          >
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";

import BoardCommentItem from "./item/BoardCommentItem.vue";

const boardStore = "boardStore";

export default {
  data() {
    return {
      comment: {
        memberId: "",
        content: "",
        boardId: "",
      },
    };
  },
  computed: {
    ...mapState(boardStore, ["board", "boardComment"]),
  },
  components: {
    BoardCommentItem,
  },
  methods: {
    clickEnrollComment() {
      this.comment.boardId = this.board.id;
      (this.comment.memberId = this.$store.state.member.id),
        this.writeBoardComment(this.comment).then(() => {
          this.searchBoardComment(this.board.id);
        });
    },
    clickDeleteBoard() {
      this.deleteBoard(this.board.id);
      this.moveList();
    },
    moveList() {
      this.$router.push({ name: "boardList" });
    },
    moveUpdate() {
      this.$router.push({ name: "boardUpdate" });
    },
    ...mapActions(boardStore, [
      "deleteBoard",
      "searchBoardComment",
      "writeBoardComment",
    ]),
  },
};
</script>

<style>
.board-detail-title {
  font-size: 41px;
  font-weight: bold;
  color: black;
}
.board-detail-member {
  font-size: 18px;
  font-weight: normal;
  color: #8e8585;
}
.heart-shape {
  position: relative;
  top: 8px;
}
</style>
