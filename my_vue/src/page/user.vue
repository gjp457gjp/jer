<template>
  <div>
    <div class="content-body">
      <div class="content-title-tools">
        <div class='content-title'>
          <h1>个人信息</h1>
        </div>
        <div class="content-tools">
        </div>
      </div>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
        </div>
        <el-form v-if="disableStudent" ref="form" :model="studentForm" label-width="30%"
                 style="align-content: center">
          <el-form-item label="学号" prop='sno'>
            <el-input disabled v-model.number="studentForm.sno" style="width: 60%"></el-input>
          </el-form-item>
          <el-form-item label="学生姓名" prop='sname'>
            <el-input disabled v-model="studentForm.sname" style="width: 60%"></el-input>
          </el-form-item>
          <el-form-item label="性别" prop='sex'>
            <el-radio-group disabled v-model="studentForm.sex">
              <el-radio :label="0">男</el-radio>
              <el-radio :label="1">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="年龄" prop='age'>
            <el-input disabled v-model.number="studentForm.age" style="width: 60%"></el-input>
          </el-form-item>
          <el-form-item label="手机号码" prop='phone'>
            <el-input disabled v-model.number="studentForm.phone" style="width: 60%"></el-input>
          </el-form-item>
          <el-form-item label="QQ号码" prop='qqnum'>
            <el-input disabled v-model.number="studentForm.qqnum" style="width: 60%"></el-input>
          </el-form-item>
          <el-form-item label="专业" prop='major'>
            <el-input disabled v-model="studentForm.major" style="width: 60%"></el-input>
          </el-form-item>
          <el-form-item label="课题" prop='title'>
            <el-input disabled v-model="studentForm.title" style="width: 60%"></el-input>
          </el-form-item>
        </el-form>

        <el-form v-if="disableTeacher" ref="form" :model="teacherForm" label-width="30%" style="align-content: center">
          <el-form-item label="教师号">
            <el-input disabled v-model.number="teacherForm.tno" style="width: 60%"></el-input>
          </el-form-item>
          <el-form-item label="教师姓名" prop='tname'>
            <el-input disabled v-model="teacherForm.tname" style="width: 60%"></el-input>
          </el-form-item>
          <el-form-item label="手机号码" prop='phone'>
            <el-input disabled v-model.number="teacherForm.phone" style="width: 60%"></el-input>
          </el-form-item>
          <el-form-item label="QQ号码" prop='qqnum'>
            <el-input disabled v-model.number="teacherForm.qqnum" style="width: 60%"></el-input>
          </el-form-item>
          <el-form-item label="课题号" prop='pids'>
            <el-input disabled v-model.number="teacherForm.pids" style="width: 60%"></el-input>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script>
  import config from '../lib/config';
  import ElInput from "element-ui/packages/input/src/input";

  export default {
    components: {ElInput},
    data: function () {
      return {
        studentForm: {
          _id: 0,
          sname: '',
          sex: 0,
        },
        teacherForm: {
          _id: 0,
          tname: ''
        },
        disableStudent: false,
        disableTeacher: false,
        state: 3,
        query: {
          currentPage: 1,
          pageSize: 10,
          obj: {},
        },
      }
    },
    computed: {

    },
    mounted: function () {
      this.test();
      this.loadData();
    },
    filters: {
      sexFilter: function (sex, value) {
        if (value == '0') {
          return '男';
        } else {
          return '女';
        }
      }
    },
    methods: {
      test(){
        this.state = localStorage.getItem("ms_state")
        if(this.state == 1) {
          this.disableStudent = true
        } else if(this.state ==2){
          this.disableTeacher = true;
        }
      },
      openLoading(text) {
        const loading = this.$loading({
          lock: true,
          text: text,
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        return loading;
      },
      loadData() {
        let loading = this.openLoading('努力加载数据中...');
        let state = localStorage.getItem("ms_state")
        if (state == 1) {
          let sno = localStorage.getItem('ms_username');
          this.postToApi("student/details/" + sno, this.query, data => {
            loading.close();
            this.studentForm = data.result;
          }, (err) => {
            loading.close();
            this.$message({
              message: '提示，服务端繁忙！',
              type: 'error'
            });
          });
        } else if (state == 2) {
          let tid = localStorage.getItem('ms_username');
          this.postToApi("teacher/details/" + tid, this.query, data => {
            loading.close();
            this.teacherForm = data.result;
          }, (err) => {
            loading.close();
            this.$message({
              message: '提示，服务端繁忙！',
              type: 'error'
            });
          });
        }
      },
    }
  };
</script>
<style>
  .el-upload--picture-card {
    background-color: #fff;
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    box-sizing: border-box;
    width: 275px;
    height: 144px;
    text-align: center;
    position: relative;
    overflow: hidden;
  }

  .el-upload-list__item {
    width: 175px;
    height: 144px;
  }
</style>
