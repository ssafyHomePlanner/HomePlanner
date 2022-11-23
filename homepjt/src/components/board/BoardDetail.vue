<template>
  <v-container fill-height class="mt-6 mb-8">
    <v-row align="end" justify="center">
      <v-col cols="auto">
        <div class="board-detail-title">
          {{ board.title }}
        </div>
      </v-col>
      <v-spacer></v-spacer>
      <v-col cols="auto" v-if="checkId()">
        <v-btn text @click="moveUpdate"> 수정 </v-btn>
        <v-btn text @click="clickDeleteBoard"> 삭제 </v-btn>
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
        <v-sheet rounded="pill" color="white" elevation="3" height="50" width="90" class="ma-8">
          <v-container class="heart-shape">
            <v-row justify="center" class="align-center">
              <v-btn icon color="pink" v-if="checkLike">
                <v-icon large color="red darken-2" @click="updateLikeCnt"> mdi-heart </v-icon>
              </v-btn>
              <v-btn icon color="pink" v-else>
                <v-icon large color="red darken-2" @click="updateLikeCnt"> mdi-heart-outline </v-icon>
              </v-btn>
              <span class="ml-2">
                {{ board.likeCnt }}
              </span>
            </v-row>
          </v-container>
        </v-sheet>
      </v-col>
    </v-row>
    <v-row justify="start">
      <v-col cols="col-2">댓글 {{ boardComment.length }}</v-col>
    </v-row>
    <v-row class="mb-3">
      <v-divider></v-divider>
    </v-row>
    <board-comment-item v-for="(comment, index) in boardComment" :key="index" :comment="comment" />

    <v-container class="mt-5">
      <v-row>
        <v-col cols="col-8">
          <v-text-field
            @keyup.enter="clickEnrollComment"
            v-model="comment.content"
            counter="25"
            hint="댓글을 작성하세요"
            label="댓글"
            solo
          ></v-text-field>
        </v-col>
        <v-col cols="col-4">
          <v-btn @click="clickEnrollComment" color="primary" elevation="3" large>등록</v-btn>
        </v-col>
      </v-row>
    </v-container>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";

import BoardCommentItem from "./item/BoardCommentItem.vue";

const boardStore = "boardStore";
const memberStore = "memberStore";

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
    ...mapState(boardStore, ["board", "boardComment", "likeFlag"]),
    ...mapState(memberStore, "userInfo"),

    checkLike() {
      const payload = {
        boardId: this.board.id,
        memberId: this.$store.state.memberStore.userInfo.id,
      };
      this.checkBoardLike(payload);

      console.log("check board like = ", payload);
      console.log(this.likeFlag);
      console.log("check like methods => ", this.likeFlag);

      return this, this.likeFlag;
      // if (!this.likeFlag) {
      //   return true;
      // }
      // return false;
    },
  },
  created() {
    if (this.$store.state.memberStore.userInfo.id != this.$store.state.boardStore.board.memberId) {
      this.addBoardView(this.board.id);
    }

    const payload = {
      boardId: this.board.id,
      memberId: this.$store.state.memberStore.userInfo.id,
    };
    this.checkBoardLike(payload);
    console.log("check board like = ", payload);
    console.log(this.likeFlag);
  },
  components: {
    BoardCommentItem,
  },
  methods: {
    clickEnrollComment() {
      this.comment.boardId = this.board.id;
      // this.comment.memberId = this.$store.state.member.id;
      this.comment.memberId = this.$store.state.memberStore.userInfo.id;
      this.writeBoardComment(this.comment);
      this.comment = {};
    },
    clickDeleteBoard() {
      this.deleteBoard(this.board.id);
      this.moveList();
    },
    moveList() {
      this.$router.push({ name: "boardList" }).catch(() => {});
    },
    moveUpdate() {
      this.$router.push({ name: "boardUpdate" }).catch(() => {});
    },

    checkId() {
      if (this.$store.state.memberStore.userInfo.id == this.$store.state.boardStore.board.memberId) {
        return true;
      } else {
        return false;
      }
    },

    updateLikeCnt() {
      console.log("update Like Cnt");
      console.log(this.board.id);
      console.log(this.$store.state.memberStore.userInfo.id);
      console.log(this.likeFlag);
      const payload = {
        boardId: this.board.id,
        memberId: this.$store.state.memberStore.userInfo.id,
        likeFlag: this.likeFlag,
      };
      console.log(payload.boardId + " " + payload.memberId + " " + payload.likeFlag);
      this.addBoardLike(payload);
    },
    ...mapActions(boardStore, [
      "deleteBoard",
      "searchBoardComment",
      "writeBoardComment",
      "addBoardView",
      "addBoardLike",
      "checkBoardLike",
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
