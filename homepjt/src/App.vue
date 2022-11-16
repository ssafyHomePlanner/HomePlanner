<template>
  <v-app>
    <v-main>
      <header-nav></header-nav>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import HeaderNav from "@/components/HeaderNav.vue";

export default {
  name: "App",

  components: {
    HeaderNav,
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
