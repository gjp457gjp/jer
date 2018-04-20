<template>
  <div class="sidebar">
    <el-menu :default-active="onRoutes" class="el-menu-vertical-demo" unique-opened router>
      <template v-for="item in items">
        <template v-if="item.subs">
          <el-submenu :index="item.index">
            <template slot="title"><i :class="item.icon"></i>{{ item.title }}</template>
            <el-menu-item v-for="(subItem,i) in item.subs" :key="i" :index="subItem.index">{{ subItem.title }}
            </el-menu-item>
          </el-submenu>
        </template>
        <template v-else>
          <el-menu-item :index="item.index">
            <i :class="item.icon"></i>{{ item.title }}
          </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        items: [
          {
            icon: 'el-icon-setting',
            index: 'project',
            title: '课题'
          },
          {
            icon: 'el-icon-star-on',
            index: 'user',
            title: '用户'
          },
          {
            icon: 'el-icon-star-on',
            index: 'changePwd',
            title: '修改密码'
          }
        ]
      }
    },
    computed: {
      onRoutes() {
        return this.$route.path.replace('/', '');
      }
    },
    mounted: function () {
      this.loadItem()
    },
    methods: {
      loadItem(){
        let state = localStorage.getItem("ms_state");
        if(state == 0 ){
          //学生
          this.items.unshift({
            icon: 'el-icon-menu',
              index: 'student',
            title: '学生'
          })
          this.items.unshift({
            icon: 'el-icon-menu',
            index: 'teacher',
            title: '教师'
          })
          this.items.splice(3,1)
        }
      }
    }
  }
</script>

<style scoped>
  .sidebar {
    display: block;
    position: absolute;
    width: 250px;
    left: 0;
    top: 70px;
    bottom: 0;
    background: #2E363F;
  }

  .sidebar > ul {
    height: 100%;
  }
</style>
