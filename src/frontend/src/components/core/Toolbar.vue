<template>
  <v-toolbar color="#000" fixed="true" scroll-toolbar-off-screen="true" dense="true">
    <img :src="logo" height="40">
    <v-toolbar-title class="white--text">Realtime Keynote Feedback</v-toolbar-title>
    <v-spacer></v-spacer>
    <v-toolbar-items class="hidden-sm-and-down">
      <v-btn v-for="(link,index) in links" :key="index" :to="link.to" flat>
        <v-icon>{{ link.icon }}</v-icon>
        &nbsp;{{link.text}}
      </v-btn>
    </v-toolbar-items>
  </v-toolbar>
</template>

<script>
import { mapMutations } from "vuex";

export default {
  data: () => ({
    logo: "./img/automaton.svg",
    links: [
      {
        to: "/dashboard",
        icon: "mdi-view-dashboard",
        text: "Dashboard"
      },
      {
        to: "/nouvelle-seance",
        icon: "mdi-chart-bubble",
        text: "Uploader une séance"
      },
      {
        to: "/mes-seances",
        icon: "mdi-clipboard-outline",
        text: "Mes séances"
      },

      {
        to: "/mon-compte",
        icon: "mdi-account",
        text: "Mon compte"
      }
    ],
    responsive: false
  })
};
</script>

<style>
.active-perso {
  background-color: #2082d1;
}
</style>
