<template>
  <v-app>
    <v-main>
      <header-nav></header-nav>
      <router-view />
      <footer-nav></footer-nav>
    </v-main>
  </v-app>
</template>

<script>
import HeaderNav from "@/components/HeaderNav.vue";
import FooterNav from "@/components/FooterNav.vue";

export default {
  name: "App",

  components: {
    HeaderNav,
    FooterNav,
  },
  data: () => ({
    //
  }),
};
</script>

<style>
body {
  background-size: cover;
}
</style>
