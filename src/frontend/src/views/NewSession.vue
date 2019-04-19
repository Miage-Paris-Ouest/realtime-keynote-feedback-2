<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout justify-center wrap>
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
                  <form-file-upload></form-file-upload>
                </v-flex>
                <v-flex xs12 md12>
                  <v-text-field class="purple-input" label="Sujet"/>
                </v-flex>
                <v-flex xs12 md6>
                  <v-text-field label="Salle" class="purple-input"/>
                </v-flex>
                <v-flex xs12 md6>
                  <v-text-field label="Public" class="purple-input"/>
                </v-flex>
                <v-flex xs12 md4>
                  <v-text-field label="Heure de début" type="time" class="purple-input"/>
                </v-flex>
                <v-flex xs12 md4>
                  <v-text-field label="Heure de fin" type="time" class="purple-input"/>
                </v-flex>
                <v-flex xs12 md4>
                  <v-text-field label="Nombre de participants" type="number" class="purple-input"/>
                </v-flex>
                <v-flex xs12>
                  <v-textarea
                    class="purple-input"
                    label="Déscriptif"
                    value="Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                  />
                </v-flex>
                <v-flex xs12 text-xs-right>
                  <v-btn color="danger">Annuler</v-btn>
                  <v-btn class="mx-0 font-weight-light" color="primary">C'est parti !</v-btn>
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
  //

  methods: {
    async createSession() {
      if (config.apiCallEnabled) {
        var response = await SessionCreationService.createSession();
        if (response.data) this.items = response.data;
      }
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
