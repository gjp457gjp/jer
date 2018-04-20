import Vue from 'vue'
import Router from 'vue-router'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/readme',
      meta: {
        requireAuth: true,  // 添加该字段，表示进入这个路由是需要登录的
      },
      component: resolve => require(['../components/common/Home.vue'], resolve),
      children:[
        {
          path: '/student',
          component: resolve => require(['../page/student.vue'], resolve)
        }, {
          path: '/teacher',
          component: resolve => require(['../page/teacher.vue'], resolve)
        }, {
          path: '/user',
          component: resolve => require(['../page/user.vue'], resolve),
        }, {
          path: '/project',
          component: resolve => require(['../page/project.vue'], resolve)
        },
        {
          path: '/changePwd',
          component: resolve => require(['../page/changePassword.vue'], resolve)
        },
      ]
    },
    {
      path: '/login',
      component: resolve => require(['../page/Login.vue'], resolve)
    },

  ]
})
