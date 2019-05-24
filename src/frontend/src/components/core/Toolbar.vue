<template>
  <v-toolbar color="#000" fixed="true" scroll-toolbar-off-screen="true" dense="true">
    <v-btn to="/" flat style="text-transform:none;">
      <img :src="logo" height="40">
      <v-toolbar-title class="white--text">Realtime Keynote Feedback</v-toolbar-title>
    </v-btn>
    <v-spacer></v-spacer>
    <v-toolbar-items class="hidden-sm-and-down">
      <v-bottom-sheet v-model="sheet">
        <v-list>
          <v-subheader>Séances en cours d'analyse par nos serveurs, le traitement peut durer plusieurs minutes.</v-subheader>
          <v-list-tile @click="sheet = false">
            <v-list-tile-avatar>
              <v-avatar size="32px" tile>
                <v-progress-circular indeterminate color="primary" :size="20"></v-progress-circular>
              </v-avatar>
            </v-list-tile-avatar>
            <v-list-tile-title>Conférence à l'espace des sciences</v-list-tile-title>
          </v-list-tile>
        </v-list>
      </v-bottom-sheet>

      <v-btn v-for="(link,index) in links" :key="index" :to="link.to" flat>
        <v-icon>{{ link.icon }}</v-icon>
        &nbsp;{{link.text}}
      </v-btn>
      <v-btn @click="sheet = !sheet" color="purple" flat>
        <v-icon>mdi-clock</v-icon>&nbsp;
        en traitement
      </v-btn>
    </v-toolbar-items>
  </v-toolbar>
</template>

<script>
import { mapMutations } from "vuex";

export default {
  data: () => ({
    sheet: false,
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
