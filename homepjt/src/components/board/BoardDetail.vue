<template>
  <v-container fill-height class="mt-6">
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
        <div class="board-detail-member">
          {{ board.memberId }}
        </div>
      </v-col>
    </v-row>
    <v-row justify="start">
      <v-col cols="col-4">
        <v-icon large color="#999999"> mdi-message-text </v-icon> 건
        <v-icon large color="#999999"> mdi-eye </v-icon>
        {{ board.viewCnt }}회 작성 날짜:
        {{ board.writeDate }}
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
      <v-col cols="col-2">댓글</v-col>
    </v-row>
    <v-divider></v-divider>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";
export default {
  computed: {
    ...mapState(["board"]),
  },
  methods: {
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
    ...mapActions(["deleteBoard"]),
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
