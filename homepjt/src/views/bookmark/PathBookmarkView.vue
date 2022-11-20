<template>
  <div>
    <v-container class="bv-example-row mt-10">
      <h2>경로 즐겨찾기 목록</h2>

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
export default {};
</script>

<style></style>
