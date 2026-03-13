import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '@/store/user'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue'),
    meta: { public: true }
  },
  {
    path: '/',
    name: 'Layout',
    component: () => import('@/views/Layout.vue'),
    redirect: '/dashboard',
    children: [
      {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import('@/views/Dashboard.vue'),
        meta: { title: '首页' }
      },
      {
        path: '/drugs',
        name: 'DrugList',
        component: () => import('@/views/DrugList.vue'),
        meta: { title: '药品管理' }
      },
      {
        path: '/drugs/add',
        name: 'DrugAdd',
        component: () => import('@/views/DrugForm.vue'),
        meta: { title: '添加药品' }
      },
      {
        path: '/drugs/edit/:id',
        name: 'DrugEdit',
        component: () => import('@/views/DrugForm.vue'),
        meta: { title: '编辑药品' }
      },
      {
        path: '/family',
        name: 'FamilyMember',
        component: () => import('@/views/FamilyMember.vue'),
        meta: { title: '家庭成员' }
      },
      {
        path: '/categories',
        name: 'Categories',
        component: () => import('@/views/Categories.vue'),
        meta: { title: '药品分类' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const userStore = useUserStore()
  
  if (!to.meta.public && !userStore.isLoggedIn) {
    next('/login')
  } else if (to.path === '/login' && userStore.isLoggedIn) {
    next('/')
  } else {
    next()
  }
})

export default router
