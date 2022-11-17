<template>
  <v-container>
    <v-row justify="end">
      <v-btn text> 삭제 </v-btn>
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
  </v-container>
</template>

<script>
import { mapActions } from "vuex";

const boardStore = "boardStore";

export default {
  name: "BoardCommentItem",
  props: {
    comment: Object,
  },
  methods: {
    deleteComment() {
      this.deleteBoardComment(this.comment);
    },
    ...mapActions(boardStore, ["deleteBoardComment"]),
  },
};
</script>

<style></style>
