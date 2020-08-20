<template>
  <v-footer dark
            padless
            app
            inset>
    <v-card class="flex"
            flat
            title
            color="black"
            :elevation="10">
      <v-card-text class="py-2 white--text text-center">
        {{new Date().getFullYear()}} -
        <a class="font-weight-regular"
           style="text-decoration:none; color:white;"
           href="https://github.com/Pheomenon">Pheomenon</a>
      </v-card-text>
    </v-card>
  </v-footer>
</template>
