<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout wrap>
      <v-flex md4>
        <material-card color="primary" title="Conférence Pierre et Marie Currie" text>
          <v-data-table :headers="headers" :items="itemsComputed" hide-actions>
            <template slot="headerCell" slot-scope="{ header }">
              <span class="subheading font-weight-light text--darken-3" v-text="header.text"/>
            </template>
            <template slot="items" slot-scope="{ item }">
              <th>{{ item.label }}</th>
              <td>{{ item.value }}</td>
            </template>
          </v-data-table>
        </material-card>
        <material-stats-card
          color="primary"
          icon="mdi-account-group"
          title="Attention moyenne"
          :value="attentionAverage+'/50'"
          sub-icon="mdi-information-outline"
          sub-text="sur les 2 dernieres heures"
        />
        <material-stats-card
          color="blue"
          icon="mdi-arrow-up"
          title="Attention maximum"
          :value="attentionMin+'/50'"
          sub-icon="mdi-information-outline"
          sub-text="sur les 2 dernieres heures"
        />
        <material-stats-card
          color="red"
          icon="mdi-arrow-down"
          title="Attention minimum"
          :value="attentionMin+'/50'"
          sub-icon="mdi-information-outline"
          sub-text="sur les 2 dernieres heures"
        />
      </v-flex>
      <v-flex md8>
        <material-complex-chart-card
          color="white"
          chart-type="Line"
          :data="attentionChart.data"
          :options="attentionChart.options"
        >
          <h4 class="title font-weight-light">Évolution de l'attention au fil du temps</h4>
          <p class="category d-inline-flex font-weight-light">
            <v-icon color="primary" small>mdi-arrow-up</v-icon>
            <span class="green--text">55%</span>&nbsp;
            Analysé depuis l'attention individuelle de chaque étudiant.
          </p>
        </material-complex-chart-card>
        <v-layout wrap row>
          <v-flex md6>
            <material-complex-chart-card
              color="white"
              chart-type="Polar"
              :options="dispersionChart.options"
              :data="dispersionChart.data"
            >
              <h4 class="title font-weight-light">Stimulus à l'origine d'une perte d'attention</h4>
              <p
                class="category d-inline-flex font-weight-light"
              >Nombre d'absents pendant la séance.</p>
            </material-complex-chart-card>
          </v-flex>
          <v-flex md6>
            <material-complex-chart-card
              color="white"
              chart-type="Polar"
              :options="dispersionChart.options"
              :data="dispersionChart.data"
            >
              <h4 class="title font-weight-light">Stimulus à l'origine d'une perte d'attention</h4>
              <p
                class="category d-inline-flex font-weight-light"
              >Nombre d'absents pendant la séance.</p>
            </material-complex-chart-card>
          </v-flex>
        </v-layout>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import SessionStatisticsService from "../services/SessionStatistics";
import config from "../config";
import FormatterHelper from "../helpers/FormatterHelper";
import StatisticsHelper from "../helpers/StatisticsHelper";

var chartColor = "#2196f3";
chartColor = "#fff";
export default {
  data() {
    return {
      responseData: null,
      subject: "",
      items: [],
      attentionAverage: 0,
      attentionMax: 0,
      attentionMin: 0,
      dispersionChart: {
        data: {
          arc: {
            custom: false
          },
          datasets: [
            {
              data: [40, 30, 21],
              backgroundColor: ["#00c292", "#2196f3", "#eb4a46"]
            }
          ],
          labels: ["Latéral", "Frontal", "Bavardage"]
        }
      },
      attentionChart: {
        data: {
          labels: ["15h", "15h15", "15h30", "15h45", "16h", "17h", "18h"],
          datasets: [
            {
              label: "Attention lors de la séance",
              backgroundColor: "#b8efe2",
              data: [40, 39, 10, 40, 39, 80, 40]
            }
          ]
        },
        options: {
          legend: {
            labels: {
              fontColor: "white"
            }
          }
        }
      },
      absenceChart: {
        data: {
          labels: ["15h", "15h15", "15h30", "15h45", "16h", "17h", "18h"],
          datasets: [
            {
              label: "Absences lors de la séance",
              backgroundColor: ["#00c292", "#2196f3"],
              data: [40, 10]
            }
          ],
          labels: ["Présents", "Absents"]
        },
        options: {
          legend: {
            labels: {
              // This more specific font property overrides the global property
              fontColor: "white"
            }
          }
        }
      }
    };
  },
  async mounted() {
    if (config.apiCallEnabled) {
      try {
        var response = await SessionStatisticsService.getSeanceStatistics(
          this.$route.params.id
        );
        if (response.data) {
          this.responseData = response.data;
          this.attentionAverage = StatisticsHelper.roundStat(
            this.responseData.attentionAverage
          );
          this.attentionMax = StatisticsHelper.roundStat(
            this.responseData.attentionMax
          );
          this.attentionMin = StatisticsHelper.roundStat(
            this.responseData.attentionMin
          );
        }
      } catch (error) {
        console.trace(error);
      }
    }
  },

  computed: {
    itemsComputed() {
      if (this.responseData) {
        const session = this.responseData.session;
        return [
          {
            label: "Public",
            value: session.public
          },
          {
            label: "Salle",
            value: session.room
          },
          {
            label: "Durée",
            value: "2h"
          },
          {
            label: "Effectif",
            value: session.participants
          },
          {
            label: "Début",
            value: FormatterHelper.getTimeFromDateTime(session.begginingTime)
          },
          {
            label: "Fin",
            value: FormatterHelper.getTimeFromDateTime(session.endingTime)
          }
        ];
      } else {
        return [
          {
            label: "Public",
            value: ""
          },
          {
            label: "Salle",
            value: ""
          },
          {
            label: "Durée",
            value: ""
          },
          {
            label: "Effectif",
            value: ""
          },
          {
            label: "Début",
            value: ""
          },
          {
            label: "Fin",
            value: ""
          }
        ];
      }
    }
  }
};
</script>
<style>
</style>
