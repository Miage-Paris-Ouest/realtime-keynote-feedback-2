<template>
  <v-app>
    <core-filter/>
    <core-toolbar/>
    <core-view/>
  </v-app>
</template>

<style lang="scss">
@import "@/styles/index.scss";

/* Remove in 1.2 */
.v-datatable thead th.column.sortable i {
  vertical-align: unset;
}
body > #app {
  //background: url("../public/img/montagne.jpg");
  background-repeat: no-repeat;
  background-size: 2000px;
  background-attachment: fixed;
  background-position: center;
}
</style>
