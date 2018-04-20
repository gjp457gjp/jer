<template>
  <div>
    <Header></Header>
    <div class="article_list">
      <ul>
        <!--<li v-for="i in list">-->
          <!--<p>姓名: {{i.tname}}  教师号: {{i.tno}}  手机号: {{i.phone}}</p>-->
        <!--</li>-->
      </ul>
    </div>
  </div>
</template>
<script>
  import Header from '../components/header'
  import Footer from '../components/footer'
  export default {
    components: { Header, Footer},
    data () {
      return {
        list: []
      }
    },
    created () {
      this.getData()
    },
    methods: {
      getData () {
        this.$http.get('/api/jersey/teacher/list')
          .then((response) =>{
              this.list = response.data
            })
          .catch((error) => {
            console.log('///////////////////')
          })
      }
    }
  }
</script>
