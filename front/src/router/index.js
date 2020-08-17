import Vue from 'vue'
import VueRouter from 'vue-router'
import Applicants from '@/views/Applicants'
import Dashboard from '@/views/Dashboard'
import Project from '@/views/Project'

Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'dashboard',
    component: Dashboard
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/applicants',
    name: 'applicants',
    component: Applicants
  },
  {
    path: '/project',
    name: 'project',
    component: Project
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
