<template>
  <div>
    <v-data-table :headers="headers" :items="boardList" :items-per-page="5" class="elevation-1"> </v-data-table>
    <div class="text-center">
      <v-pagination v-model="page" :length="6"></v-pagination>
    </div>
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
          text: "articleno",
          value: "articleno",
          // sortable: true,
        },
        {
          text: "userid",
          value: "userid",
        },
        {
          text: "subject",
          value: "subject",
        },
        {
          text: "content",
          value: "content",
        },
      ],
    };
  },
  computed: {
    ...mapState(["boardList"]),
  },
  created: {},
  mounted() {
    this.searchBoardList();
  },
  methods: {
    ...mapActions(["searchBoardList"]),
  },
};
</script>

<style></style>
