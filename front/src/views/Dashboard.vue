<template>
    <div>
      <h1 class="subheading green--text">Dashboard</h1>
      <v-container>
        <v-layout row wrap>
          <v-flex sm6 xs12 md6 lg3 v-for="item in list" :key="item.prDetail">
              <v-card class="ma-3">
              <v-list-item>
                <v-list-item-avatar>
                    <v-img :src="[`/avatars/`]+item.prAuthor+[`.jpg`]"></v-img>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title class="headline mb-1 text-left">{{item.prAuthor}}</v-list-item-title>
                  <div><v-divider></v-divider></div>
                </v-list-item-content>
              </v-list-item>
                <v-card-actions>
                  <v-spacer></v-spacer>

                  <v-btn
                    icon
                    @click="show = !show"
                  >
                    <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
                  </v-btn>
                </v-card-actions>

                <v-expand-transition>
                  <div v-show="show">
                    <v-divider></v-divider>
                    <div v-html="item.prDetail"></div>
                  </div>
                </v-expand-transition>
              </v-card>
            <!-- <v-card
              class="mx-auto"
              color="dark"
              dark
              max-width="800"
            >
              <v-card-text class="headline font-weight-bold">
                <div v-html="item.prDetail"></div>
              </v-card-text>

              <v-card-actions>
                <v-list-item class="grow">
                  <v-list-item-avatar color="grey darken-3">
                    <v-img
                      class="elevation-6"
                      src="https://avataaars.io/?avatarStyle=Transparent&topType=ShortHairShortCurly&accessoriesType=Prescription02&hairColor=Black&facialHairType=Blank&clotheType=Hoodie&clotheColor=White&eyeType=Default&eyebrowType=DefaultNatural&mouthType=Default&skinColor=Light"
                    ></v-img>
                  </v-list-item-avatar>

                  <v-list-item-content>
                    <v-list-item-title>Evan You</v-list-item-title>
                  </v-list-item-content>

                  <v-row
                    align="center"
                    justify="end"
                  >
                    <v-icon class="mr-1">mdi-heart</v-icon>
                    <span class="subheading mr-2">256</span>
                    <span class="mr-1">·</span>
                    <v-icon class="mr-1">mdi-share-variant</v-icon>
                    <span class="subheading">45</span>
                  </v-row>
                </v-list-item>
              </v-card-actions>
            </v-card> -->
          </v-flex>
          <v-flex sm6 xs12 md6 lg3>
            <v-card class="ma-3">
              <v-list-item>
                <v-list-item-avatar title class="mt-n7">
                  <v-sheet color="green" width="80" height="80" elevation="10">
                    <v-icon dark large>store</v-icon>
                  </v-sheet>
                </v-list-item-avatar>
                <v-list-item-content>
                  <div class="overline text-right">Article</div>
                  <v-list-item-title class="headline mb-1 text-right">4324</v-list-item-title>
                  <div><v-divider></v-divider></div>
                </v-list-item-content>
              </v-list-item>
              <v-card-actions>
                <v-icon text class="ma-2">person</v-icon>
                <div class="overline">Contributors</div>
              </v-card-actions>
            </v-card>
          </v-flex>
          <v-flex sm6 xs12 md6 lg3>
            <v-card class="ma-3">
              <v-list-item>
                <v-list-item-avatar title class="mt-n7">
                  <v-sheet color="green" width="80" height="80" elevation="10">
                    <v-icon dark large>store</v-icon>
                  </v-sheet>
                </v-list-item-avatar>
                <v-list-item-content>
                  <div class="overline text-right">Article</div>
                  <v-list-item-title class="headline mb-1 text-right">4324</v-list-item-title>
                  <div><v-divider></v-divider></div>
                </v-list-item-content>
              </v-list-item>
              <v-card-actions>
                <v-icon text class="ma-2">person</v-icon>
                <div class="overline">Contributors</div>
              </v-card-actions>
            </v-card>
          </v-flex>
          <v-flex sm6 xs12 md6 lg3>
            <v-card class="ma-3">
              <v-list-item>
                <v-list-item-avatar title class="mt-n7">
                  <v-sheet color="green" width="80" height="80" elevation="10">
                    <v-icon dark large>store</v-icon>
                  </v-sheet>
                </v-list-item-avatar>
                <v-list-item-content>
                  <div class="overline text-right">Article</div>
                  <v-list-item-title class="headline mb-1 text-right">4324</v-list-item-title>
                  <div><v-divider></v-divider></div>
                </v-list-item-content>
              </v-list-item>
              <v-card-actions>
                <v-icon text class="ma-2">person</v-icon>
                <div class="overline">Contributors</div>
              </v-card-actions>
            </v-card>
          </v-flex>
          <v-flex xs12 sm6 md4 lg3 v-for="person in team" :key="person.name">
            <v-card class="text-center ma-3">
              <v-responsive class="pt-4">
                <v-avatar size="100" class='red lighten-2'>
                  <v-img :src="person.avatar"></v-img>
                </v-avatar>
              </v-responsive>
              <v-card-text>
                <div class="subhrading">{{person.name}}</div>
                <div class="gery--text">{{person.role}}</div>
              </v-card-text>
              <v-card-actions>
                <v-btn outlined color = "orange">
                  <v-icon small left>message</v-icon>
                  <span>Message</span>
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </div>
</template>

<script>
import listApi from '@/api/prdetail'
export default {
  data : () => ({
    team:[
      {name: 'Iyad', role: 'XXX', avatar: '/logo1.png'},
      {name: 'XX', role: 'XXX', avatar: '/logo1.png'},
      {name: 'YYY', role: 'XXX', avatar: '/logo1.png'},
      {name: 'UUU', role: 'XXX', avatar: '/logo1.png'}
    ],
    list: [],
    total: 0,
    condition: {},
    page: 1,
    limit: 30,
    show: false,
  }),
  created () {
    this.condition = {};
    this.getList()
  },
  methods: {
    getList(page = 1) {
      this.loading = true
      this.page = page
      listApi.getPrList(this.condition,this.page,this.limit).then(response => {
        this.list = response.data.data.rows
        this.total = response.data.data.total
      })
      this.loading = false
    }
  },
};
</script>

<style>
pre {
  word-wrap: break-word;
  word-break: break-all;
}
code {
  word-wrap: break-word;
  word-break: break-all;
}
</style>