<template>
  <div>
    <div class="content-body">
      <div class="content-title-tools">
        <div class='content-title'>
          <h1>学生信息列表</h1>
        </div>
        <div class="content-tools">
        </div>
      </div>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <el-form ref="form" :inline="true" :model="form" style="margin-bottom: -20px">
            <el-form-item>
              <el-input clearable v-model="form.sname" placeholder="输入学生姓名" style="width: 300px"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" @click="loadAdvertStudents">查询</el-button>
              <!--<a target="_blank" id="export"></a>-->
              <!--<el-button type="primary" style="margin-left: 10px" @click="exp">导出</el-button>-->
            </el-form-item>
          </el-form>
        </div>
        <div class="table">
          <el-table :data="students" border stripe style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection">
            </el-table-column>
            <el-table-column prop="sno" label="学号">
            </el-table-column>
            <el-table-column prop="sname" label="学生姓名">
            </el-table-column>
            <el-table-column prop="sex" label="性别">
              <template slot-scope="scope">
                {{form.sex| sexFilter(scope.row.sex)}}
              </template>
            </el-table-column>
            <el-table-column prop="age" label="年龄">
            </el-table-column>
            <el-table-column prop="phone" label="手机号码">
            </el-table-column>
            <el-table-column prop="qqnum" label="QQ号码">
            </el-table-column>
            <el-table-column prop="major" label="专业">
            </el-table-column>
            <el-table-column prop="title" label="课题">
            </el-table-column>
            <el-table-column label="操作" width="200">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="handleDetail(scope.row.id)">编辑</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <el-dialog title="学生信息" :visible.sync="dialogVisibleStudent"
                   :before-close="closeDialog" center width="50%">
          <el-form id="imgInputer" ref="form" :rules="rules" :model="studentForm" label-width="30%"
                   style="align-content: center">
            <el-form-item label="学号" prop='sno'>
              <el-input v-model.number="studentForm.sno" style="width: 60%"></el-input>
            </el-form-item>
            <el-form-item label="学生姓名" prop='sname'>
              <el-input v-model="studentForm.sname" style="width: 60%"></el-input>
            </el-form-item>
            <el-form-item label="性别" prop='sex'>
              <el-radio-group v-model="studentForm.sex">
                <el-radio :label="0">男</el-radio>
                <el-radio :label="1">女</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="年龄" prop='age'>
              <el-input v-model.number="studentForm.age" style="width: 60%"></el-input>
            </el-form-item>
            <el-form-item label="手机号码" prop='phone'>
              <el-input v-model.number="studentForm.phone" style="width: 60%"></el-input>
            </el-form-item>
            <el-form-item label="QQ号码" prop='qqnum'>
              <el-input v-model.number="studentForm.qqnum" style="width: 60%"></el-input>
            </el-form-item>
            <el-form-item label="专业" prop='major'>
              <el-input v-model="studentForm.major" style="width: 60%"></el-input>
            </el-form-item>
            <el-form-item label="课题" prop='title'>
              <el-input disabled v-model="studentForm.title" style="width: 60%"></el-input>
            </el-form-item>
            <el-form-item class="dialog-footer">
              <el-button type="primary" @click="handleSaveStudent('form')">确定</el-button>
              <el-button @click="handleCancel('form')">取消</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>
        <div class="content-bottom">
          <el-button type="primary" round @click="showDialog">新增学生</el-button>
          <el-popover ref="popover5" placement="top" width="160" v-model="visible2">
            <p>您确定要删除学生信息吗？</p>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" type="text" @click="visible2 = false">取消</el-button>
              <el-button type="primary" size="mini" @click="delStudent">确定</el-button>
            </div>
          </el-popover>
          <el-button type="danger" v-popover:popover5 round :disabled="multipleSelection.length==0">删除学生</el-button>
          <el-pagination @current-change="handleCurrentChange" :current-page="query.page" :page-size="query.rows"
                         layout="total, prev, pager, next, jumper"
                         :total="total" style="float: right;">
          </el-pagination>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
  import config from '../lib/config';

  export default {
    data: function () {
      let validPhone = (rule, value, callback) => {
        const reg = /^1[3|4|5|7|8][0-9]\d{8}$/
        if (!value) {
          callback(new Error('请输入电话号码'))
        } else if (!reg.test(value)) {
          callback(new Error('请输入正确的11位手机号码'))
        } else {
          callback()
        }
      }
      return {
        students: [],
        total: 0,
        visible2: false,
        dialogVisibleStudent: false,
        multipleSelection: [],
        studentForm: {
          _id: 0,
          sname: '',
          sex: 0,
        },
        form: {
          sname: '',
        },
        query: {
          currentPage: 1,
          pageSize: 10,
          obj: {},
        },
        rules: {
          sno: [
            {required: true, message: '学号不能为空', trigger: 'blur'},
            {type: 'number', message: '学号必须为数字值', trigger: 'blur'}
          ],
          sname: [
            {required: true, message: '学生姓名不能为空', trigger: 'blur'}
          ],
          phone: [
            {required: true, message: '手机号码不能为空', trigger: 'blur'},
            {type: 'number', message: '手机号码必须为数字值', trigger: 'blur'}
          ],
          qqnum: [
            {required: true, message: '请填写正确的QQ号码', trigger: 'blur'},
            {type: 'number', message: 'QQ号码必须为数字值', trigger: 'blur'}
          ]
        },
      }
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
    mounted: function () {
      this.loadAdvertStudents()
    },
    methods: {
      loadAdvertStudents() {
        this.query.sname = this.form.sname;
        let loading = this.openLoading('努力加载数据中...');
        this.getToApi("student/list", this.query, data => {
          loading.close();
          this.students = data.result;
          this.total = data.total;
        }, (err) => {
          loading.close();
          this.$message({
            message: '提示，服务端繁忙！',
            type: 'error'
          });
        });
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
      handleCancel(done) {
        this.dialogVisibleStudent = false;
        this.studentForm = {
          _id: 0,
          sno: '',
          sname: '',
          sex: '',
          age: '',
          phone: '',
          qqnum: '',
          major: '',
          title: ''
        };
      },
      exp() {
        let sname = this.form.sname;
        let localServer = config.localServer + '/student/export?sname=' +
          '' + sname;
        document.getElementById("export").href = localServer;
        document.getElementById("export").click();
      },
      showDialog() {
        this.studentForm = {
          _id: 0,
          sname: '',
          sex: 0,
        };
        this.dialogVisibleStudent = true;
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      handleCurrentChange(page) {
        this.query.currentPage = page;
        this.query.sname = this.form.sname;
        this.loadAdvertStudents();
      },
      handleSaveStudent(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let loading = this.openLoading('正在提交数据...');
            if (this.studentForm._id === 0) {
              let that = this;
              delete this.studentForm._id;
              that.postToApi("student/add", JSON.parse(JSON.stringify(this.studentForm)), data => {
                loading.close();
                that.$message({
                  showClose: true,
                  message: '新增学生成功！',
                  type: 'success'
                });
                that.loadAdvertStudents();
                that.dialogVisibleStudent = false;
              }, (err) => {
                loading.close();
                this.$message({
                  message: '提示，服务端繁忙！',
                  type: 'error'
                });
              })
            } else {
              let fd = JSON.parse(JSON.stringify(this.studentForm))
              this.postToApi("student/update/" + fd.id, fd, data => {
                loading.close();
                this.$message({
                  showClose: true,
                  message: '修改学生成功！',
                  type: 'success'
                });
                this.dialogVisibleStudent = false;
                this.loadAdvertStudents();
              }, (err) => {
                loading.close();
                this.$message({
                  message: '提示，服务端繁忙！',
                  type: 'error'
                });
              })
            }
          } else {
            this.$message({
              message: '请填入正确的学生信息',
              type: 'error'
            });
          }
        });

      },

      delStudent() {
        let idArray = this.multipleSelection.map(o => {
          return o.id
        });
        this.postToApi("student/delete", {ids: idArray}, data => {
          this.$message({
            showClose: true,
            message: '删除成功！',
            type: 'success'
          });
          this.multipleSelection = [];
          this.visible2 = false;
          this.loadAdvertStudents()
        }, (err) => {
          this.$message({
            message: '提示，服务端繁忙！',
            type: 'error'
          });
        })

      },
      handleDetail(id) {
        let loading = this.openLoading('努力加载数据中...');
        this.getToApi("student/details/"+ id, this.query, data => {
          loading.close();
          this.studentForm = data.result;
          this.dialogVisibleStudent = true;
          }, (err) => {
          loading.close();
          this.$message({
            message: '提示，服务端繁忙！',
            type: 'error'
          });
        });
      },
      closeDialog() {
        this.handleCancel('form');
      }
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
