<template>
  <nav>
    <v-app-bar color="darken-1" dark app>
        <v-btn class="mx-2" fab dark small>
            <v-icon dark>emoji_events</v-icon>
        </v-btn>
        <v-toolbar-title>
            <span class="font-weight-light">TiDB's contributors</span>
        </v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn router :to="'/contributors'">
            <span style="text-transform: none;">Contributors</span>
        </v-btn>
        <v-btn router :to="'/pulls'">
            <span style="text-transform: none;">Pull requests</span>
        </v-btn>
        <v-menu offset-y>
            <template v-slot:activator="{on}">
                <v-btn text v-on="on">
                    <v-icon left>expand_more</v-icon>
                    <span style="text-transform: none;">Statistics</span>
                </v-btn>
            </template>
            <v-list flat>
                <v-list-item v-for="link in links" :key="link.text" router :to="link.route" active-class="border">
                    <v-list-item-title>{{link.text}}</v-list-item-title>
                </v-list-item>
            </v-list>
        </v-menu>
        <v-btn text href="https://github.com/pingcap/tidb">
            <span>Go to Github</span>
            <v-icon right>exit_to_app</v-icon>
        </v-btn>
    </v-app-bar>
  </nav>
</template>

<script>
export default {
    data: () => ({
        drawer: true,
        links: [
            {icon: 'dashboard', text: 'Dashboard', route:'/'},
            {icon: 'folder', text: 'Commit history', route:'/history'},
            {icon: 'person', text: 'The most contributor', route:'/most'}
        ]
    })
}
</script>

<style>
.border{
    border-left: 4px solid skyblue;
}
</style>