<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout wrap>
      <v-flex md12 sm12 lg4>
        <material-chart-card
          :data="attentionChart.data"
          :options="attentionChart.options"
          color="blue"
          type="Line"
        >
          <h4 class="title font-weight-light">Évolution de l'attention</h4>
          <p class="category d-inline-flex font-weight-light">Attention moyenne par mois.</p>
          <template slot="actions">
            <v-icon class="mr-2" small>mdi-clock-outline</v-icon>
            <span class="caption grey--text font-weight-light">Sur les 6 derniers mois</span>
          </template>
        </material-chart-card>
      </v-flex>
      <v-flex md12 sm12 lg4>
        <material-chart-card
          :data="absentChart.data"
          :options="absentChart.options"
          :responsive-options="absentChart.responsiveOptions"
          color="red"
          type="Bar"
        >
          <h4 class="title font-weight-light">Évolution des absences</h4>
          <p class="category d-inline-flex font-weight-light">Pourcentage d'absents par mois.</p>

          <template slot="actions">
            <v-icon class="mr-2" small>mdi-clock-outline</v-icon>
            <span class="caption grey--text font-weight-light">Sur les 6 derniers mois</span>
          </template>
        </material-chart-card>
      </v-flex>
      <v-flex md12 sm12 lg4>
        <material-chart-card
          :data="attentionVariationChart.data"
          :options="attentionVariationChart.options"
          color="primary"
          type="Line"
        >
          <h3 class="title font-weight-light">Stabilité de l'attention</h3>
          <p class="category d-inline-flex font-weight-light">Évolution de la variation d'attention.</p>

          <template slot="actions">
            <v-icon class="mr-2" small>mdi-clock-outline</v-icon>
            <span class="caption grey--text font-weight-light">Sur les 6 derniers mois</span>
          </template>
        </material-chart-card>
      </v-flex>
      <v-flex sm6 xs12 md6 lg4>
        <material-stats-card
          color="primary"
          icon="mdi-account-group"
          title="Attention moyenne"
          :value="avgAttention+'/50'"
          sub-icon="mdi-information-outline"
          sub-text="sur les 6 derniers mois"
        />
      </v-flex>

      <v-flex sm6 xs12 md6 lg8>
        <v-alert :value="true" type="info" color="primary" style="margin-top:25px;">
          Votre niveau d'attention globale est dans la moyenne haute.
          Si vous souhaitez en savoir plus, consultez nos conseils pour améliorer l'attention de votre public.
        </v-alert>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import DashboardService from "../services/Dashboard";
import StatisticsHelper from "../helpers/StatisticsHelper";
import FormatterHelper from "../helpers/FormatterHelper";

export default {
  data() {
    return {
      responseData: [],
      avgAttention: "",
      attentionChart: {
        data: {
          labels: [],
          series: [[]]
        },
        options: {
          lineSmooth: this.$chartist.Interpolation.cardinal({
            tension: 0
          }),
          low: 0,
          high: 50, // creative tim: we recommend you to set the high sa the biggest value + something for a better look
          chartPadding: {
            top: 0,
            right: 0,
            bottom: 0,
            left: 0
          }
        }
      },
      attentionVariationChart: {
        data: {
          labels: [],
          series: [[]]
        },
        options: {
          lineSmooth: this.$chartist.Interpolation.cardinal({
            tension: 0
          }),
          low: 0,
          high: 50, // creative tim: we recommend you to set the high sa the biggest value + something for a better look
          chartPadding: {
            top: 0,
            right: 0,
            bottom: 0,
            left: 0
          }
        }
      },
      absentChart: {
        data: {
          labels: [],
          series: [[]]
        },
        options: {
          axisX: {
            showGrid: false
          },
          low: 0,
          high: 100,
          chartPadding: {
            top: 0,
            right: 5,
            bottom: 0,
            left: 0
          }
        },
        responsiveOptions: [
          [
            "screen and (max-width: 640px)",
            {
              seriesBarDistance: 5,
              axisX: {
                labelInterpolationFnc: function(value) {
                  return value[0];
                }
              }
            }
          ]
        ]
      },

      tabs: 0,
      list: {
        0: false,
        1: false,
        2: false
      }
    };
  },
  methods: {
    complete(index) {
      this.list[index] = !this.list[index];
    }
  },
  async created() {
    try {
      var response = await DashboardService.getDashboard();
      if (response.data) {
        this.responseData = response.data;
        this.attentionChart.data.series = [
          this.responseData.attentionAvgPerMonth
        ];
        var labels = FormatterHelper.getMonthsLabelsFromMonthsString(
          this.responseData.months
        );
        this.attentionChart.data.labels = [...labels];
        this.absentChart.data.series = [this.responseData.absentAvgPerMonth];
        this.absentChart.data.labels = [...labels];
        this.attentionVariationChart.data.series = [
          this.responseData.attentionDiffPerMonth
        ];
        this.attentionVariationChart.data.labels = [...labels];
        this.avgAttention = StatisticsHelper.roundStat(
          this.responseData.attentionAverage
        );
      }
    } catch (error) {
      console.trace(error);
    }
  }
};
</script>
