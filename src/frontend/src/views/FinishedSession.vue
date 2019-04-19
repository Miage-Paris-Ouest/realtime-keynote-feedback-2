<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout justify-center wrap>
      <v-flex md4>
        <material-notification color="primary">
          <h3>
            <v-icon color="white" size="40">mdi-check</v-icon>&nbsp;Votre séance est terminée
          </h3>
          <p>Vous pouvez à présent regarder nos statistiques réalisées lors de votre séance.</p>

          <v-btn color="white" :outline="true" to="/dashboard">Revenir à mon dashboard</v-btn>

          <v-btn
            color="white"
            :outline="true"
            to="/statistiques-seance"
          >Voir les statistiques de cette séance</v-btn>
        </material-notification>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    title: "Cliquez pour accéder aux statistiques de cette séance",
    headers: [
      {
        sortable: false,
        text: "Titre",
        value: "name"
      },
      {
        sortable: false,
        text: "Date",
        value: "country"
      },
      {
        sortable: false,
        text: "Heure de début",
        value: "city"
      },
      {
        sortable: false,
        text: "Heure de fin",
        value: "salary"
      },
      {
        sortable: false,
        text: "Durée",
        value: "salary"
      },
      {
        sortable: false,
        text: "Indice d'attention",
        value: "salary",
        align: "right"
      }
    ],
    items: [
      {
        titre: "Conférence à l'espace des sciences",
        date: "12 mars 2019",
        debut: "15h",
        fin: "16h30",
        duree: "1h30",
        attention: "34"
      },
      {
        titre: "Conférence à l'espace des sciences",
        date: "12 mars 2019",
        debut: "15h",
        fin: "16h30",
        duree: "1h30",
        attention: "34"
      },
      {
        titre: "Conférence à l'espace des sciences",
        date: "12 mars 2019",
        debut: "15h",
        fin: "16h30",
        duree: "1h30",
        attention: "34"
      },
      {
        titre: "Conférence à l'espace des sciences",
        date: "12 mars 2019",
        debut: "15h",
        fin: "16h30",
        duree: "1h30",
        attention: "34"
      },
      {
        titre: "Conférence à l'espace des sciences",
        date: "12 mars 2019",
        debut: "15h",
        fin: "16h30",
        duree: "1h30",
        attention: "34"
      },
      {
        titre: "Conférence à l'espace des sciences",
        date: "12 mars 2019",
        debut: "15h",
        fin: "16h30",
        duree: "1h30",
        attention: "34"
      },
      {
        titre: "Conférence à l'espace des sciences",
        date: "12 mars 2019",
        debut: "15h",
        fin: "16h30",
        duree: "1h30",
        attention: "34"
      },
      {
        titre: "Conférence à l'espace des sciences",
        date: "12 mars 2019",
        debut: "15h",
        fin: "16h30",
        duree: "1h30",
        attention: "34"
      },
      {
        titre: "Conférence à l'espace des sciences",
        date: "12 mars 2019",
        debut: "15h",
        fin: "16h30",
        duree: "1h30",
        attention: "34"
      },
      {
        titre: "Conférence à l'espace des sciences",
        date: "12 mars 2019",
        debut: "15h",
        fin: "16h30",
        duree: "1h30",
        attention: "34"
      }
    ]
  })
};
</script>
<style lang="scss">
tr:hover {
  cursor: pointer;
}
</style>