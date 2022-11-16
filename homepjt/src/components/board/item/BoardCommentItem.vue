<template>
  <v-container>
    <v-row justify="end">
        수정 | 삭제
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
export default {
  name: "BoardCommentItem",
  props: {
    comment: Object,
  },
};
</script>

<style></style>
