<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout justify-center wrap>
      <v-flex xs12 md8 lg4>
        <material-card
          color="primary"
          title="Uploadez un fichier vidéo"
          text="Cette vidéo sera analysée."
        >
          <v-container py-0 px-0>
            <v-layout wrap>
              <v-flex xs12 md12>
                <form-file-upload v-on:uploaded="uploadFinished"></form-file-upload>
              </v-flex>
            </v-layout>
          </v-container>
        </material-card>
      </v-flex>

      <v-flex xs12 md8 lg6>
        <material-card
          color="primary"
          title="Informations sur la séance"
          text="Avant de commencer une séance, complétez ce formulaire."
        >
          <v-form>
            <v-container py-0>
              <v-layout wrap>
                <v-flex xs12 md12>
                  <v-text-field v-model="subject" class="purple-input" label="Sujet"/>
                </v-flex>
                <v-flex xs12 md4>
                  <v-text-field v-model="room" label="Salle" class="purple-input"/>
                </v-flex>
                <v-flex xs12 md4>
                  <v-text-field v-model="publiq" label="Public" class="purple-input"/>
                </v-flex>
                <v-flex xs12 md4>
                  <v-text-field
                    v-model.number="participants"
                    label="Nombre de participants"
                    type="number"
                    class="purple-input"
                  />
                </v-flex>
                <v-flex xs12 md4>
                  <v-text-field v-model="date" label="Date" type="date" class="purple-input"/>
                </v-flex>
                <v-flex xs12 md4>
                  <v-text-field
                    v-model="beginningTime"
                    label="Heure de début"
                    type="time"
                    class="purple-input"
                  />
                </v-flex>
                <v-flex xs12 md4>
                  <v-text-field
                    v-model="endingTime"
                    label="Heure de fin"
                    type="time"
                    class="purple-input"
                  />
                </v-flex>

                <v-flex xs12>
                  <v-textarea
                    class="purple-input"
                    v-model="description"
                    label="Déscriptif"
                    value="Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                  />
                </v-flex>
                <v-flex xs12 text-xs-right>
                  <v-btn color="danger">Annuler</v-btn>
                  <v-btn
                    class="mx-0 font-weight-light"
                    color="primary"
                    @click.prevent="createSession"
                    :title="uploadStatusFinished ?   'Transmettez les données pour l\'analyse' :'Attendez la fin du téléchargement de la vidéo pour valider.' "
                  >Valider</v-btn>
                </v-flex>
              </v-layout>
            </v-container>
          </v-form>
        </material-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import SessionCreationService from "../services/SessionCreation";

import config from "../config";
export default {
  data: () => ({
    uploadStatusFinished: false,
    subject: "",
    room: "",
    publiq: "",
    date: "",
    beginningTime: "",
    endingTime: "",
    description: "",
    participants: 0
  }),
  methods: {
    async createSession() {
      if (config.apiCallEnabled) {
        const sessionData = {
          subject: this.subject,
          room: this.room,
          publiq: this.publiq,
          date: this.date,
          beginningTime: this.beginningTime,
          endingTime: this.endingTime,
          description: this.description,
          participants: this.participants
        };
        console.log(sessionData);
        try {
          var response = await SessionCreationService.createSession(
            sessionData
          );
        } catch (error) {
          console.trace(error);
        }
      }
    },
    async uploadFinished(status) {
      this.uploadStatusFinished = status;
    }
  }
};
</script>

<style lang="css">
h3 {
  text-align: center;
  margin-bottom: 30px;
  font-size: 14px;
  color: #555;
}
</style>
