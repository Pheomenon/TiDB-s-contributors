<template>
    <nav>
        <v-navigation-drawer v-model="drawer" dark app class="grey darken-3 py-0">
            <v-container>
                <v-row>
                    <v-col md="9">
                        <v-progress-circular
                        :rotate="300"
                        :size="50"
                        :width="7"
                        :value="value"
                        color="lime accent-3"
                        class="mt-n1"
                        >
                            {{value}}
                        </v-progress-circular>
                    </v-col>
                    <v-col md="3">
                        <v-icon right class="mt-1">fas fa-bars</v-icon>
                    </v-col>
                </v-row>
            </v-container>
            <v-list nav>
                <v-list-item router to ="/">
                    <v-list-item-action>
                        <v-icon small>fas fa-tachometer-alt</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title>Dashboard</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item router to ="/applicants">
                    <v-list-item-action>
                        <v-icon small>fas fa-users</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title>Applicants</v-list-item-title>
                        </v-list-item-content>
                        <v-chip color="yellow darken-3" small>3</v-chip>
                </v-list-item>
                <v-list-item router to ="/project">
                    <v-list-item-action>
                        <v-icon small>fas fa-briefcase</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title>Jobs</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item router to ="/calendar">
                    <v-list-item-action>
                        <v-icon small>fas fa-calendar-alt</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title>Calendar</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item router to ="/reports">
                    <v-list-item-action>
                        <v-icon small>fas fa-clipboard-list</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title>Reports</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
            <v-layout row style="position: absolute; bottom:0">
                <v-flex md12>
                    <v-card color="grey darken-1">
                        <v-list-item dense>
                            <v-list-item-action>
                                <v-btn class="mx-2" fab dark small color="blue">
                                    <v-icon dark>mdi-heart</v-icon>
                                </v-btn>
                            </v-list-item-action>
                            <v-list-item-content>
                                <v-list-item-title>Pheomenon</v-list-item-title>
                        </v-list-item-content>
                        <v-icon dark class="mx-10"></v-icon>
                        </v-list-item>
                    </v-card>
                </v-flex>
            </v-layout>
        </v-navigation-drawer>
    </nav>
</template>

<script>
export default {
    data: () => ({
        drawer: true,
        interval: {},
        value: 0
    }),
    beforeDestroy (){
        clearInterval(this.interval)
    },
    mounted (){
        this.interval = setInterval(() => {
            if(this.value === 100){
                return(this.value = 0)
            }
            this.value += 10
        },1000)
    }
}
</script>

<style scoped>
.v-progress-circular {
    margin: 1rem;
}
</style>