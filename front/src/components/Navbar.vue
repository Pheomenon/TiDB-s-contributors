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
        <v-btn router :to="'/most'">
            <span style="text-transform: none;">The top 30 contributors</span>
        </v-btn>
        <v-btn router :to="'/history'">
            <span style="text-transform: none;">Commit History</span>
        </v-btn>
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
    })
}
</script>

<style>
.border{
    border-left: 4px solid skyblue;
}
</style>