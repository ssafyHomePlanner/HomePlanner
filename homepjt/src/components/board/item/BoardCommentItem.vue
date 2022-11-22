<template>
  <v-container>
    <v-row justify="end">
      <v-btn text @click="deleteComment" v-if="checkId()"> 삭제 </v-btn>
    </v-row>
    <v-row class="mt-3 mb-3">
      <v-col cols="col-4">
        <v-container>
          <span class="mr-4">
            {{ comment.memberId }}
          </span>
          {{ comment.content }}
        </v-container>
      </v-col>
      <v-container>
        <v-row justify="end"> 작성 일자: {{ comment.writeDate }} </v-row>
      </v-container>
    </v-row>
    <v-row>
      <v-divider></v-divider>
    </v-row>
  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";

const boardStore = "boardStore";
const memberStore = "memberStore";

export default {
  name: "BoardCommentItem",
  props: {
    comment: Object,
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    deleteComment() {
      this.deleteBoardComment(this.comment);
    },
    checkId() {
      // console.log(this.userInfo.id);
      // console.log(this.comment.memberId);

      // console.log(this.userInfo.id == this.comment.memerId);
      // console.log(this.userInfo.id.trim());
      // console.log(this.comment.memberId.trim());

      if (this.userInfo.id === this.comment.memerId) {
        return true;
      } else {
        return false;
      }
    },
    ...mapActions(boardStore, ["deleteBoardComment"]),
  },
};
</script>

<style></style>
