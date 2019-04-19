<template>
  <div class="file-upload">
    <v-layout wrap v-if="!terminated">
      <v-flex v-if="!uploading" xs12 style="text-align:center;">
        <input type="file" id="file" ref="file" style="display:none;" v-on:change="startUpload()">
        <v-btn
          align-center
          color="primary"
          v-on:click="handleFileUpload()"
        >Choisissez une vidéo à uploader</v-btn>
      </v-flex>
      <transition name="fade">
        <v-flex xs12 v-if="uploading">
          <span v-if="uploading">{{fileName}}</span>
          <v-progress-linear v-if="uploading" :indeterminate="uploading"></v-progress-linear>
          <span v-if="uploading">Patientez pendant le transfert...</span>
        </v-flex>
      </transition>
    </v-layout>
    <transition name="fade">
      <v-layout v-if="terminated">
        <v-flex xs12 style="text-align:center;">
          <span>
            Téléchargement de
            {{this.file.name}} exécuté avec succès.
          </span>
        </v-flex>
      </v-layout>
    </transition>
  </div>
</template>

<script>
import UploadVideoService from "../../services/UploadVideo";
export default {
  inheritAttrs: false,
  name: "file-upload",
  components: {},

  props: {},

  data() {
    return {
      file: null,
      uploading: false,
      terminated: false
    };
  },
  methods: {
    handleFileUpload() {
      this.$refs.file.click();
    },
    async startUpload() {
      this.file = this.$refs.file.files[0];
      this.uploading = true;
      let formData = new FormData();
      formData.append("file", this.file);
      var response = await UploadVideoService.uploadVideo(formData);
      if (response.data) {
        let state = response.data.success;
        this.terminated = state;
        this.$emit("upload", state);
      } else {
        this.$emit("upload", false);
      }
    }
  },
  computed: {
    fileName() {
      return this.file ? this.file.name : "";
    }
  }
};
</script>

<style lang="scss">
.file-upload {
  margin-top: 20px;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 1s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
</style>
