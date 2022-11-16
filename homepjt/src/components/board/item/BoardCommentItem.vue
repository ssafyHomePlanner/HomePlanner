<template>
        <v-row class="mt-3 mb-3">
          <v-col cols="col-4">
              <v-container>
                  <span class="mr-4">
                      {{comment.memberId}}
                  </span>
                      {{comment.content}}
              </v-container>
          </v-col>
          <v-col cols="col-8">
                  작성 일자: {{comment.writeDate}}
          </v-col>
        </v-row>
</template>

<script>
export default {
    name: "BoardCommentItem",
    props: {
    comment: Object,
  },
}
</script>

<style>
</style>