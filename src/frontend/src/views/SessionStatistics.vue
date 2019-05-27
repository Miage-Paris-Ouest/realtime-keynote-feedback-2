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
          :value="attentionMax+'/50'"
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
            <!--
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
            </material-complex-chart-card>-->
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
      attentionMin: 0
      /* dispersionChart: {
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
      },*/
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
            this.responseData.ATTENTION_AVG
          );
          this.attentionMax = StatisticsHelper.roundStat(
            this.responseData.ATTENTION_MAX
          );
          this.attentionMin = StatisticsHelper.roundStat(
            this.responseData.ATTENTION_MIN
          );
          this.attentionChart = {
            data: {
              labels: this.getTimeLabels(
                response.data.SESSION.BEGINNING_TIME,
                response.data.SESSION.ENDING_TIME,
                response.data.SESSION.DURATION
              ),
              datasets: [
                {
                  label: "Attention lors de la séance",
                  backgroundColor: "#b8efe2",
                  data: response.data.SESSION_ANALYTICS_DATA.map(stat => {
                    return StatisticsHelper.roundStat(stat);
                  })
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
          };
        }
      } catch (error) {
        console.trace(error);
      }
    }
  },

  computed: {
    itemsComputed() {
      if (this.responseData) {
        const session = this.responseData.SESSION;
        return [
          {
            label: "Public",
            value: session.PUBLIC
          },
          {
            label: "Salle",
            value: session.ROOM
          },
          {
            label: "Durée",
            value: FormatterHelper.getDurationFromString(session.DURATION)
          },
          {
            label: "Effectif",
            value: session.PARTICIPANTS
          },
          {
            label: "Début",
            value: FormatterHelper.getTimeFromDateTime(session.BEGINNING_TIME)
          },
          {
            label: "Fin",
            value: FormatterHelper.getTimeFromDateTime(session.ENDING_TIME)
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
  },
  methods: {
    getTimeLabels(bTime, eTime, duration) {
      var partsBTime = bTime.split(":");
      var partsETime = eTime.split(":");
      var partsDuration = duration.split(":");
      var diff = parseInt(partsDuration[0]) * 60 + parseInt(partsDuration[1]);
      var labels = [];
      var minutesSplit = ["", "15", "30", "45"];
      var count = parseInt(partsBTime[1]) / 3;
      var currentHour = parseInt(partsBTime[0]);

      while (count < diff) {
        if (count % minutesSplit.length === 0 && count > 0) {
          currentHour++;
          if (currentHour == 24) {
            currentHour = 0;
          }
        }
        labels.push(
          currentHour + "h" + minutesSplit[count % minutesSplit.length]
        );
        count += 15;
      }
      console.log(JSON.stringify(labels));
      return labels;
    }
  }
};
</script>
<style>
</style>
