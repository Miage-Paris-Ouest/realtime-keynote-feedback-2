<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout justify-center wrap>
      <v-flex md10 v-if="responseData.length">
        <material-card
          color="primary"
          title="Liste de vos séances"
          text="Ci-dessous la liste de vos séances. cliquez sur une ligne pour voir plus de détails."
        >
          <v-data-table :headers="headers" :items="responseDataComputed" hide-actions>
            <template slot="headerCell" slot-scope="{ header }">
              <span class="subheading font-weight-light text--darken-3" v-text="header.text"/>
            </template>
            <template slot="items" slot-scope="{ item }">
              <td :title="title">{{ item.subject}}</td>
              <td :title="title">{{ item.date }}</td>
              <td :title="title">{{ item.beginningTime }}</td>
              <td :title="title">{{ item.endingTime}}</td>
              <td :title="title">{{ item.duration }}</td>
              <td class="text-xs-right">{{ item.attention }}/50</td>
              <td :title="title" class="text-xs-right">
                <v-btn
                  color="primary"
                  :small="true"
                  :to="`/statistiques-seance/${item.sessionId}` "
                >
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
import StatisticsHelper from "../helpers/StatisticsHelper";
import FormatterHelper from "../helpers/FormatterHelper";
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
    responseData: []
  }),
   async mounted() {
    if (config.apiCallEnabled) {
      try {
        var response = await SessionsListService.getSessionsList();
        console.log(response.data);
        if (response.data) this.responseData = response.data;
      } catch (error) {
        console.trace(error);
      }
    }
  },
  computed: {
    responseDataComputed() {
      if (this.responseData && this.responseData.length) {
        var computed = [...this.responseData];
        return computed.map(data => {
          return {
            subject: data.SUBJECT,
            date: FormatterHelper.getDateFromDateTime(data.DATE),
            beginningTime: FormatterHelper.getTimeFromDateTime(
              data.BEGINNING_TIME
            ),
            endingTime: FormatterHelper.getTimeFromDateTime(data.ENDING_TIME),
            duration: FormatterHelper.getDurationFromString(data.DURATION),
            attention: StatisticsHelper.roundStat(data.ATTENTION_AVG),
            sessionId: data.ID
          };
        });
      }
    }
  }
};
</script>
<style lang="scss">
tr:hover {
  cursor: pointer;
}
</style>