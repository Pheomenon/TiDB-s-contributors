<template>
  <v-container>
    <v-layout row wrap>
      <v-row class="fill-height">
        <v-col>
          <v-card-title class="text-center justify-center py-6">
            <h1 class="font-weight-light display-3">Commit History</h1>
          </v-card-title>
          <v-divider></v-divider>
          <v-sheet height="64">
            <v-toolbar flat color="white">
              <v-btn outlined class="mr-4" color="grey darken-2" @click="setToday">
                Today
              </v-btn>
              <v-btn fab text small color="grey darken-2" @click="prev">
                <v-icon small>mdi-chevron-left</v-icon>
              </v-btn>
              <v-btn fab text small color="grey darken-2" @click="next">
                <v-icon small>mdi-chevron-right</v-icon>
              </v-btn>
                <v-toolbar-title v-if="$refs.calendar">
                        {{ $refs.calendar.title }}
                    </v-toolbar-title>
                    <v-spacer></v-spacer>
                    <v-menu bottom right>
                        <template v-slot:activator="{ on, attrs }">
                        <v-btn
                            outlined
                            color="grey darken-2"
                            v-bind="attrs"
                            v-on="on"
                        >
                            <span>{{ typeToLabel[type] }}</span>
                            <v-icon right>mdi-menu-down</v-icon>
                        </v-btn>
                        </template>
                        <v-list>
                        <v-list-item @click="type = 'day'">
                            <v-list-item-title>Day</v-list-item-title>
                        </v-list-item>
                        <v-list-item @click="type = 'week'">
                            <v-list-item-title>Week</v-list-item-title>
                        </v-list-item>
                        <v-list-item @click="type = 'month'">
                            <v-list-item-title>Month</v-list-item-title>
                        </v-list-item>
                        </v-list>
                    </v-menu>
                    </v-toolbar>
                </v-sheet>
                <v-sheet height="600">
                    <v-calendar
                    ref="calendar"
                    v-model="focus"
                    color="primary"
                    :events="events"
                    :event-color="getEventColor"
                    :type="type"
                    :event-overlap-mode="mode"
                    @click:event="showEvent"
                    @click:more="viewDay"
                    @click:date="viewDay"
                    @change="updateRange"
                    ></v-calendar>
                    <v-menu
                    v-model="selectedOpen"
                    :close-on-content-click="false"
                    :activator="selectedElement"
                    offset-x
                    >
                    <v-card
                        color="grey lighten-4"
                        min-width="350px"
                        flat
                    >
                        <v-toolbar
                        :color="selectedEvent.color"
                        dark
                        >
                        <v-btn icon>
                            <v-icon>new_releases</v-icon>
                        </v-btn>
                        <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
                        <v-spacer></v-spacer>
                        <v-btn light :href="selectedEvent.link" :color="red">
                          get more detail
                        </v-btn>
                        </v-toolbar>
                        <v-card-text>
                        <span v-html="selectedEvent.details"></span>
                        </v-card-text>
                        <v-card-actions>
                        <v-btn 
                            outlined
                            text
                            color="secondary"
                            @click="selectedOpen = false"
                        >
                            Cancel
                        </v-btn>
                        </v-card-actions>
                    </v-card>
                    </v-menu>
                </v-sheet>
                </v-col>
            </v-row>
        </v-layout>
    </v-container>
</template>

<script>
import listApi from '@/api/prlist'
  export default {
    data: () => ({
      focus: '',
      type: 'month',
      typeToLabel: {
        month: 'Month',
        week: 'Week',
        day: 'Day',
      },
      mode: "column",
      selectedEvent: {},
      selectedElement: null,
      selectedOpen: false,
      events: [],
      list: [],
      colors: ['blue', 'indigo', 'deep-purple', 'cyan', 'green', 'orange', 'grey darken-1'],
    }),
    mounted () {
      this.$refs.calendar.checkChange()
    },
    methods: {
      viewDay ({ date }) {
        this.focus = date
        this.type = 'day'
      },
      getEventColor (event) {
        return event.color
      },
      setToday () {
        this.focus = ''
      },
      prev () {
        this.$refs.calendar.prev()
      },
      next () {
        this.$refs.calendar.next()
      },
      showEvent ({ nativeEvent, event }) {
        const open = () => {
          this.selectedEvent = event
          this.selectedElement = nativeEvent.target
          setTimeout(() => this.selectedOpen = true, 10)
        }

        if (this.selectedOpen) {
          this.selectedOpen = false
          setTimeout(open, 10)
        } else {
          open()
        }

        nativeEvent.stopPropagation()
      },
      updateRange ({ start, end }){
        const events = []
        listApi.getHistory(start.date,end.date).then(response => {
          this.list = response.data.data.rows
          for (let i = 0; i < this.list.length; i++) {
              events.push({
              name: this.list[i].title,
              start: this.list[i].time,
              end: this.list[i].time,
              color: this.colors[this.rnd(0, this.colors.length - 1)],
              details: this.list[i].detail,
              link: this.list[i].link
            })
          }
          this.events = events
        })
      },
      rnd (a, b) {
        return Math.floor((b - a + 1) * Math.random()) + a
      }
    },
  }
</script>