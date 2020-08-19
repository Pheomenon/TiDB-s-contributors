<template>
  <v-app style="background-color:gery lighten-1;">
    <v-card-title class="text-center justify-center py-6">
      <h1 class="font-weight-light display-3 basil--text">The top 30 contributors</h1>
    </v-card-title> 
          <ve-line height="800px" 
          :data="chartData" 
          :extend="extend" 
          :loading="loading" 
          :data-zoom="dataZoom" 
          :mark-point="markPoint"
          :colors="colors"
          :settings="chartSettings"
          ></ve-line>
        </v-app>
</template>

<script>
import listApi from '@/api/prlist'
export default {
  data () {
    this.chartSettings = {
      scale: true,
    }
    this.colors = ['#000080','#40E0D0', '#61a0a8',
      '#A52A2A', '#91c7ae','#EE7942', 
      '#ff66ff', '#FFDAB9','#6e7074',
      '#FF4500', '#ADD8E6']
    this.markPoint = {
      data: [
          {
            name: '最大值',
            type: 'max'
          }
        ]
      }
    this.dataZoom = [
        {
          type: 'slider',
          start: 0,
          end: 80
        }
      ]
    this.extend = {
      series: {
        label: {
          normal: {
            show: true
          }
        }
      }
    }
    return {
      chartData: {
        columns: [],
        rows: []
      },
      loading: true
    }
  },
  created() {
    this.getData()
  },
  methods: {
    getData() {
        listApi.getMost().then(response => {
        this.chartData.rows = response.data.data.rows
        this.chartData.columns = response.data.data.columns
      })
      this.loading = false
    }
  },
};
</script>