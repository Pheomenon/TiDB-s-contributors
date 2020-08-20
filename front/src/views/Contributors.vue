<template>
  <v-layout row>
    <v-flex md3>
      <v-app style="background-color:gery lighten-1;"
             class="ma-n4">
        <v-container ml-5
                     mt-5>
          <h4 class="mb-5 font-weight-bold">Filters</h4>
          <v-text-field @change="getList()"
                        v-model="condition.key"
                        label="Search contributor's name"
                        prepend-icon="search"
                        class="ml-3 mr-12"></v-text-field>
          <v-col cols="12"
                 sm="6">
            <v-date-picker @change="getList()"
                           v-model="condition.dates"
                           range
                           landscape>
              <v-tooltip bottom>
                <template v-slot:activator="{ on, attrs }">
                  <v-icon right
                          color="primary"
                          dark
                          v-bind="attrs"
                          v-on="on">build</v-icon>
                </template>
                <span>Please select at least two dates</span>
              </v-tooltip>
            </v-date-picker>
          </v-col>
        </v-container>
      </v-app>
    </v-flex>
    <v-flex md9>
      <v-app style="background-color:white;"
             class="ma-n4">
        <v-container>
          <template>
            <v-data-table :loading="loading"
                          :headers="headers"
                          :items="list"
                          :items-per-page="15"
                          class="mr-2">
              <template v-slot:top>
                <v-toolbar flat
                           color="white">
                  <v-toolbar-title>Contributors</v-toolbar-title>
                  <v-divider class="mx-4"
                             inset
                             vertical></v-divider>
                  <v-spacer></v-spacer>
                </v-toolbar>
              </template>
              <template v-slot:[`item.avatar`]="{ item }">
                <v-avatar size="30px"><img :src="[`/avatars/`]+item.author+[`.jpg`]"></v-avatar>
              </template>
              <template v-slot:[`item.link`]="{ item }">
                <v-btn :href="item.link">
                  <v-icon dark>mdi-forward</v-icon>
                </v-btn>
              </template>
              <template v-slot:[`item.status`]="{ item }">
                <span v-if="item.status == 'open'"
                      class="font-weight-medium text-capitalize"
                      style="color:SeaGreen">{{item.status}}</span>
                <span v-else
                      class="font-weight-medium text-capitalize"
                      style="color:red">{{item.status}}</span>
              </template>
            </v-data-table>
          </template>
        </v-container>
      </v-app>
    </v-flex>
  </v-layout>
</template>

<script>
import listApi from '@/api/prlist'
export default {
  data: () => ({
    headers: [
      { text: 'Avatar', align: 'left', sortable: false, value: 'avatar' },
      { text: 'Name', value: 'author' },
      { text: 'Status', value: 'status' },
      { text: 'Title', value: 'title' },
      { text: 'Commit time', value: 'time' },
      { text: 'Check details', value: 'link', sortable: false },
    ],
    list: [],
    total: 0,
    //用于封装查询条件
    condition: {},
    page: 1,
    limit: 500,
    loading: true,
  }),
  created() {
    this.condition = {}
    this.getList()
  },
  methods: {
    getList(page = 1) {
      this.loading = true
      this.page = page
      listApi
        .getPrList(this.condition, this.page, this.limit)
        .then((response) => {
          this.list = response.data.data.rows
          this.total = response.data.data.total
        })
      this.loading = false
    },
  },
}
</script>