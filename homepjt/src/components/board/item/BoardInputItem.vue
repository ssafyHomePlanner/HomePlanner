<template>
  <v-row class="mb-1">
    <v-col style="text-align: left">
      <v-form ref="form" v-model="valid" lazy-validation>
        <v-text-field
          v-model="title"
          :rules="titleRules"
          label="제목을 입력하세요."
          required
        ></v-text-field>
        <v-textarea
          v-model="content"
          :rules="contentRules"
          label="내용을 입력하세요."
          required
        ></v-textarea>
      </v-form>
      <v-btn color="primary" elevation="2">글작성</v-btn>
    </v-col>
  </v-row>
</template>

<script>
export default {
  name: "BoardInputItem",
  data() {
    return {
      title: "",
      content: "",
      titleRules: [(v) => !!v || "제목을 입력해주세요"],
      contentRules: [(v) => !!v || "내용을 입력해주세요"],
    };
  },
  methods: {
    // onSubmit(event) {
    //   event.preventDefault();
    //   let err = true;
    //   let msg = "";
    //   !this.article.userid &&
    //     ((msg = "작성자 입력해주세요"),
    //     (err = false),
    //     this.$refs.userid.focus());
    //   err &&
    //     !this.article.subject &&
    //     ((msg = "제목 입력해주세요"),
    //     (err = false),
    //     this.$refs.subject.focus());
    //   err &&
    //     !this.article.content &&
    //     ((msg = "내용 입력해주세요"),
    //     (err = false),
    //     this.$refs.content.focus());
    //   if (!err) alert(msg);
    //   else
    //     this.type === "register" ? this.registArticle() : this.modifyArticle();
    // },
    // registArticle() {
    //   http
    //     .post(`/board`, {
    //       userid: this.article.userid,
    //       subject: this.article.subject,
    //       content: this.article.content,
    //     })
    //     .then(({ data }) => {
    //       let msg = "등록 처리시 문제가 발생했습니다.";
    //       if (data === "success") {
    //         msg = "등록이 완료되었습니다.";
    //       }
    //       alert(msg);
    //       this.moveList();
    //     });
    // },
    moveList() {
      this.$router.push({ name: "boardList" });
    },
  },
};
</script>

<style></style>
