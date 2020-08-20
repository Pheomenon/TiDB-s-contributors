import Vue from 'vue'
import VueRouter from 'vue-router'
import Contributors from '@/views/Contributors'
import CommitHistory from '@/views/CommitHistory'
import MostContributor from '@/views/MostContributor'

Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'contributors',
    component: Contributors
  },
  {
    path: '/contributors',
    name: 'Contributors',
    component: Contributors
  },
  {
    path: '/history',
    name: 'history',
    component: CommitHistory
  },
  {
    path: '/most',
    name: 'most',
    component: MostContributor
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
