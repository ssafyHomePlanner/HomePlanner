<template>
  <div>
    <v-container class="bv-example-row mt-10">
      <h2>아파트 커뮤니티</h2>
      <v-row class="mb-1">
        <v-col class="text-right">
          <router-link :to="{ name: 'boardWrite' }">글쓰기</router-link>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <v-data-table
            :headers="headers"
            :items="boardList"
            :items-per-page="10"
            @click:row="clickRow"
            hide-default-footer
            class="elevation-1"
          >
          </v-data-table>
        </v-col>
      </v-row>
      <div class="text-center mt-8">
        <v-pagination v-model="page" :length="6"></v-pagination>
      </div>
    </v-container>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

const boardStore = "boardStore";
const memberStore = "memberStore";

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
    ...mapState(boardStore, ["boardList"]),
    ...mapState(memberStore, ["userInfo"]),
  },
  mounted() {
    this.searchBoardList(this.page);
  },
  methods: {
    clickRow(value) {
      // if (this.$store.state.member.id != value.memberId) {
      //   this.addBoardView(value.id);
      // }

      if (this.$store.state.memberStore.userInfo.id != value.memberId) {
        this.addBoardView(value.id);
      }

      this.insertBoardOne(value).then(() => {
        this.searchBoardComment(value.id);
      });
      this.moveBoardDetail();
    },
    moveBoardDetail() {
      this.$router.push({ name: "boardDetail" });
    },
    ...mapActions(boardStore, [
      "searchBoardList",
      "insertBoardOne",
      "searchBoardComment",
      "addBoardView",
    ]),
  },
};
</script>

<style></style>
