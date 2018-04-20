// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import utiils from './utils/index.js'
import axios from 'axios'
import 'element-ui/lib/theme-chalk/index.css';
import ElementUI from 'element-ui';
import 'vue-awesome/icons'
import Validator from 'vue-validator'
Vue.use(Validator)
Vue.use(ElementUI)
Vue.prototype.$http = axios
Vue.prototype.$utils = utiils
Vue.config.productionTip = false
import './lib/helper'
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  Validator,
  components: { App },
  template: '<App/>'
})
