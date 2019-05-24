<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout justify-center wrap>
      <v-flex md10>
        <material-card
          color="primary"
          title="Liste de vos séances"
          text="Ci-dessous la liste de vos séances. cliquez sur une ligne pour voir plus de détails."
        >
          <v-data-table :headers="headers" :items="items" hide-actions>
            <template slot="headerCell" slot-scope="{ header }">
              <span class="subheading font-weight-light text--darken-3" v-text="header.text"/>
            </template>
            <template slot="items" slot-scope="{ item }">
              <td :title="title">{{ item.titre }}</td>
              <td :title="title">{{ item.date }}</td>
              <td :title="title">{{ item.debut }}</td>
              <td :title="title">{{ item.fin }}</td>
              <td :title="title">{{ item.duree }}</td>
              <td class="text-xs-right">{{ item.attention }}/50</td>
              <td :title="title" class="text-xs-right">
                <v-btn color="primary" :small="true" :to="`/statistiques-seance/${item.id}` ">
                  <v-icon>mdi-chart-bar</v-icon>&nbsp;Détails
                </v-btn>
              </td>
            </template>
          </v-data-table>
        </material-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import SessionsListService from "../services/SessionsList";
import config from "../config";
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
        id: 1,
        titre: "Conférence à l'espace des sciences",
        date: "12 mars 2019",
        debut: "15h",
        fin: "16h30",
        duree: "1h30",
        attention: "34"
      },
      {
        id: 2,
        titre: "Conférence à l'espace des sciences",
        date: "12 mars 2019",
        debut: "15h",
        fin: "16h30",
        duree: "1h30",
        attention: "34"
      },
      {
        id: 3,
        titre: "Conférence à l'espace des sciences",
        date: "12 mars 2019",
        debut: "15h",
        fin: "16h30",
        duree: "1h30",
        attention: "34"
      },
      {
        id: 4,
        titre: "Conférence à l'espace des sciences",
        date: "12 mars 2019",
        debut: "15h",
        fin: "16h30",
        duree: "1h30",
        attention: "34"
      },
      {
        id: 5,
        titre: "Conférence à l'espace des sciences",
        date: "12 mars 2019",
        debut: "15h",
        fin: "16h30",
        duree: "1h30",
        attention: "34"
      },
      {
        id: 9,
        titre: "Conférence à l'espace des sciences",
        date: "12 mars 2019",
        debut: "15h",
        fin: "16h30",
        duree: "1h30",
        attention: "34"
      },
      { 
        id: 9,
        titre: "Conférence à l'espace des sciences",
        date: "12 mars 2019",
        debut: "15h",
        fin: "16h30",
        duree: "1h30",
        attention: "34"
      },
      { 
        id: 9,
        titre: "Conférence à l'espace des sciences",
        date: "12 mars 2019",
        debut: "15h",
        fin: "16h30",
        duree: "1h30",
        attention: "34"
      },
      { 
        id: 9,
        titre: "Conférence à l'espace des sciences",
        date: "12 mars 2019",
        debut: "15h",
        fin: "16h30",
        duree: "1h30",
        attention: "34"
      },
      {
        id: 9,
        titre: "Conférence à l'espace des sciences",
        date: "12 mars 2019",
        debut: "15h",
        fin: "16h30",
        duree: "1h30",
        attention: "34"
      }
    ]
  }),
  async created() {
    if (config.apiCallEnabled) {
      var response = await SeancesListService.getSeancesList();
      if (response.data) this.items = response.data;
    }
  }
};
</script>
<style lang="scss">
tr:hover {
  cursor: pointer;
}
</style>