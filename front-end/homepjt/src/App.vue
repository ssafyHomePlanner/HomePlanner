<template>
  <v-app>
    <v-main>
      <div id="wrapper">
        <div>
          <header-nav></header-nav>
          <router-view />
        </div>
      </div>
      <footer>
        <footer-nav></footer-nav>
      </footer>
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
#wrapper {
  height: auto;
  min-height: 100%;
  padding-bottom: (64px);
}

footer {
  height: (64px);
  position: relative;
  transform: translateY(-100%);
}

body {
  background-size: cover;
}
</style>
