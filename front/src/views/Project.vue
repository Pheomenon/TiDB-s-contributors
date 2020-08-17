<template>
  <div class="projects">
    <h1 class="subheading grey--text">Overview</h1>
    <v-container>
      <v-layout row wrap class="mb-4">
        <v-tooltip bottom>
          <template v-slot:activator="{ on }">
            <v-btn small outlined color="green" @click="sortBy('title')" class="mr-2" dark v-on="on">
              <v-icon left small>folder</v-icon>
              <span class="caption text-lowercase">By project title</span>
            </v-btn>
          </template>
          <span>sort project</span>
        </v-tooltip>
        <v-tooltip bottom>
          <template v-slot:activator="{ on }">
            <v-btn small outlined color="green" @click="sortBy('title')" class="mr-2" dark v-on="on">
              <v-icon left small>person</v-icon>
              <span class="caption text-lowercase">By name</span>
            </v-btn>
          </template>
          <span>sort person</span>
        </v-tooltip>
      </v-layout>
      <v-card flat v-for="project in projects" :key="project.title" class="mb-1">
        <v-layout row wrap :class="[`pa-3 project ${project.status}`]">
          <v-flex xs12 md6>
            <div class="caption grey--text">Person</div>
            <div>{{project.person}}</div>
          </v-flex>
          <v-flex xs-6 sm4 md2>
            <div class="caption grey--text">Due by</div>
            <div>{{project.due}}</div>
          </v-flex>
        </v-layout>
      </v-card>
    </v-container>
  </div>
</template>

<script>
export default {
  data : () => ({
    projects: [
      {title:'Site Web',person:"KSJA",due:"10/10/1923",status:"complete"},
      {title:'Site Web',person:"dewd",due:"10/10/1923",status:"no"},
      {title:'Site Web',person:"rew",due:"10/10/1923",status:"up"},
      {title:'Site Web',person:"fds",due:"10/10/1923",status:"yes"}
    ]
  }),
  methods: {
    sortBy(prop){
      this.projects.sort((a,b) => a[prop] < b[prop] ? -1:1)
    }
  }
}
</script>

<style>
.project.complete {
  border-left: 4px solid #3ecff3;
}
.project.no {
  border-left: 4px solid orange;
}
.project.up {
  border-left: 4px solid red;
}
.project.yes {
  border-left: 4px solid yellow;
}
</style>