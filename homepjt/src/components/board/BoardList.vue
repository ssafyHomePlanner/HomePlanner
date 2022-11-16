<template>
  <div>
    <v-container class="bv-example-row mt-3">
      <v-row class="mb-1">
        <v-col class="text-right">
          <v-btn color="primary">글쓰기</v-btn>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-data-table
            :headers="headers"
            :items="boardList"
            :items-per-page="5"
            hide-default-footer
            class="elevation-1"
          >
          </v-data-table>
        </v-col>
      </v-row>
      <div class="text-center">
        <v-pagination v-model="page" :length="6"></v-pagination>
      </div>
    </v-container>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
export default {
  data() {
    return {
      page: 1,
      headers: [
        {
          text: "글번호",
          value: "id",
        },
        {
          text: "제목",
          value: "title",
        },
        {
          text: "글쓴이",
          value: "memberId",
        },
        {
          text: "조회",
          value: "viewCnt",
        },
        {
          text: "날짜",
          value: "writeDate",
        },
      ],
    };
  },
  computed: {
    ...mapState(["boardList"]),
  },
  created: {},
  mounted() {
    this.searchBoardList(this.page);
  },
  methods: {
    ...mapActions(["searchBoardList"]),
  },
};
</script>

<style></style>
