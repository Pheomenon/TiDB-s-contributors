import Vue from 'vue'
import VueRouter from 'vue-router'
import Contributors from '@/views/Contributors'
import Dashboard from '@/views/Dashboard'
import PullRequests from '@/views/PullRequests'
import CommitHistory from '@/views/CommitHistory'
import MostContributor from '@/views/MostContributor'

Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'dashboard',
    component: Dashboard
  },
  {
    path: '/contributors',
    name: 'Contributors',
    component: Contributors
  },
  {
    path: '/pulls',
    name: 'pulls',
    component: PullRequests
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
