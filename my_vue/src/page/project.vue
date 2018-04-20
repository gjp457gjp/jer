<template>
  <div>
    <div class="content-body">
      <div class="content-title-tools">
        <div class='content-title'>
          <h1>课题信息列表</h1>
        </div>
        <div class="content-tools">
        </div>
      </div>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <el-form ref="form" :inline="true" :model="form" style="margin-bottom: -20px">
            <el-form-item>
              <el-input clearable v-model="form.title" placeholder="输入课题名称" style="width: 300px"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" @click="loadAdvertProjects">查询</el-button>
            </el-form-item>
          </el-form>
        </div>
        <div class="table">
          <el-table :data="projects" border stripe style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection">
            </el-table-column>
            <el-table-column prop="id" label="课题号" width="80">
            </el-table-column>
            <el-table-column prop="title" label="课题名称" width="80">
            </el-table-column>
            <el-table-column prop="pdesc" label="课题描述" width="400">
            </el-table-column>
            <el-table-column prop="util" label="所需工具" width="80">
            </el-table-column>
            <el-table-column prop="tname" label="教师姓名" width="80">
            </el-table-column>
            <el-table-column prop="sname" label="学生姓名" width="80">
            </el-table-column>
            <el-table-column prop="num" label="学生数" width="80">
            </el-table-column>
            <el-table-column prop="score" label="分数" width="80">
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template slot-scope="scope">
                <el-button v-if="disTeacher" type="text" size="small" @click="handleDetail(scope.row.id)">编辑</el-button>
                <el-button v-if="disTeacher" type="text" size="small" @click="showScore(scope.row.id)">录入成绩</el-button>
                <el-button v-if="disStudent" type="text" size="small" :disabled="scope.row.sname ? true : false" @click="showSelect(scope.row.id)">选择课题</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>

        <el-dialog title="课题信息" :visible.sync="dialogVisibleProject"
                   :before-close="closeDialog" center width="50%">
          <el-form id="imgInputer" ref="form" :rules="rules" :model="projectForm"  label-width="30%" style="align-content: center">
            <el-form-item label="课题名称" prop='title'>
              <el-input v-model="projectForm.title" style="width: 60%"></el-input>
            </el-form-item>
            <el-form-item label="教师姓名" prop='tid'>
              <el-select v-model="projectForm.tid" placeholder="请选择" style="width: 60%">
                <el-option
                  v-for="item in idName"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="所需工具" prop='util'>
              <el-input v-model="projectForm.util" style="width: 60%"></el-input>
            </el-form-item>
            <el-form-item label="学生数" prop='num'>
              <el-radio-group v-model="projectForm.num">
                <el-radio :label="1">单人</el-radio>
                <!--<el-radio :label="2">双人</el-radio>-->
              </el-radio-group>
            </el-form-item>
            <el-form-item label="课题描述" prop='pdesc'>
              <el-input type="textarea" v-model="projectForm.pdesc" style="width: 60%"></el-input>
            </el-form-item>
            <el-form-item  class="dialog-footer">
              <el-button type="primary" @click="handleSaveProject('form')">确定</el-button>
              <el-button @click="closeDialog">取消</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>

        <el-dialog title="选择课题" :visible.sync="dialogSelect"
                   :before-close="closeDialog" center width="50%">
          <el-form ref="student" :model="student"  label-width="30%" style="align-content: center">
            <el-form-item label="学生姓名" prop='sname'>
              <el-input v-model="student.name" style="width: 60%"></el-input>
            </el-form-item>
            <el-form-item  class="dialog-footer">
              <el-button type="primary" @click="selectProject">确定</el-button>
              <el-button @click="closeDialog">取消</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>

        <el-dialog title="录入成绩" :visible.sync="dialogScore"
                   :before-close="closeDialog" center width="50%">
          <el-form ref="project" :model="project" :rules="rules2" label-width="30%" style="align-content: center">
            <el-form-item label="分数" prop='score'>
              <el-input v-model.number="project.score" style="width: 60%"></el-input>
            </el-form-item>
            <el-form-item  class="dialog-footer">
              <el-button type="primary" @click="updateScore">确定</el-button>
              <el-button @click="closeDialog">取消</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>

        <div class="content-bottom">
          <el-button v-if="disTeacher" type="primary" round @click="showDialog">发布课题</el-button>
          <el-popover  ref="popover" placement="top" width="160" v-model="visible2">
            <p>您确定要删除课题信息吗？</p>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" type="text" @click="visible2 = false">取消</el-button>
              <el-button type="primary" size="mini" @click="delProject">确定</el-button>
            </div>
          </el-popover>
          <el-button v-show="disTeacher" type="danger" v-popover:popover round :disabled="multipleSelection.length==0">删除课题</el-button>
          <el-pagination @current-change="handleCurrentChange" :current-page="query.page" :page-size="query.rows" layout="total, prev, pager, next, jumper"
                         :total="total" style="float: right;">
          </el-pagination>
        </div>
      </el-card>

    </div>
  </div>
</template>

<script>
  export default {
    data: function () {
      return {
        projects: [],
        teachers: [],
        total: 0,
        visible2: false,
        dialogSelect:false,
        dialogVisibleProject: false,
        disStudent:false,
        disTeacher:false,
        dialogScore:false,
        multipleSelection: [],
        projectForm: {
          _id: 0,
          title: '',
        },
        form: {
          title: '',
        },
        student:{
          name:'',
          pid:''
        },
        project : {
          score:'',
          pid:''
        },
        idName : [],
        query: {
          currentPage: 1,
          pageSize: 10,
          obj: {},
        },
        rules: {
          title: [
            {required: true, message: '课题名称不能为空', trigger: 'blur'},
          ],
          pdesc: [
            {required: true, message: '课题描述不能为空', trigger: 'blur'}
          ],
          util: [
            {required: true, message: '所需工具不能为空', trigger: 'blur'},
          ],
          tid: [
            {required: true, message: '教师姓名不能为空', trigger: 'blur'},
          ]
        },
        rules2 : {
          score: [
            {pattern: /^100$|^(\d|[1-9]\d)$/, message: '分数不符合规则', trigger: 'blur'},
            {required: true, message: '分数不能为空', trigger: 'blur'},
          ],
        }
      }
    },
    filters: {

    },
    computed: {

    },
    mounted: function () {
      this.test()
      this.loadAdvertProjects();
      this.loadAdvertTeachers();
    },
    methods: {
      test(){
        this.state = localStorage.getItem("ms_state")
        if(this.state == 1) {
          this.disStudent = true
        } else if(this.state == 2){
          this.disTeacher = true;
        }else {
          this.disStudent = true
          this.disTeacher = true;
        }
      },
      loadAdvertProjects() {
        this.query.title = this.form.title;
        let loading = this.openLoading('努力加载数据中...');
        this.getToApi("project/list", this.query, data => {
          loading.close();
          this.projects = data.result;
          this.total = data.total;
        }, (err) => {
          loading.close();
          this.$message({
            message: '提示，服务端繁忙！',
            type: 'error'
          });
        });
      },
      loadAdvertTeachers() {
        let query = {};
        this.getToApi("teacher/list", query, data => {
          this.teachers =data.result;
          this.teachers.forEach(result =>{
            this.idName.push({
              id:result.tno,
              name:result.tname
            })
          })
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
      showDialog() {
        this.projectForm =  {
          _id: 0,
          title: '',
          num:1,
        };
        this.dialogVisibleProject = true;
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      handleCurrentChange(page) {
        this.query.currentPage = page;
        this.query.title = this.form.title;
        this.query.pageSize = 10;
        this.loadAdvertProjects();
      },

      handleSaveProject(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let loading = this.openLoading('正在提交数据...');
            if(this.projectForm._id === 0) {
              let that = this;
              delete this.projectForm._id;
              that.postToApi("project/add", this.projectForm, data => {
                loading.close();
                that.$message({
                  showClose: true,
                  message: '新增课题成功！',
                  type: 'success'
                });
                that.loadAdvertProjects();
                that.dialogVisibleProject = false;
              }, (err) => {
                loading.close();
                this.$message({
                  message: '提示，服务端繁忙！',
                  type: 'error'
                });
              })
            } else {
              let fd = JSON.parse(JSON.stringify(this.projectForm))
              this.postToApi("project/update/" + fd.id, fd, data => {
                loading.close();
                this.$message({
                  showClose: true,
                  message: '修改课题成功！',
                  type: 'success'
                });
                this.dialogVisibleProject = false;
                this.loadAdvertProjects();
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
              message: '请填入正确的课题信息',
              type: 'error'
            });
            // return false;
          }
        });

      },

      delProject() {
        let idArray=this.multipleSelection.map(o=>{
          return o.id
        });
        this.postToApi("project/delete", {ids:idArray}, data => {
          this.$message({
            showClose: true,
            message: '删除成功！',
            type: 'success'
          });
          this.multipleSelection=[];
          this.visible2=false;
          this.loadAdvertProjects()
        }, (err) =>{
          this.$message({
            message: '提示，服务端繁忙！',
            type: 'error'
          });
        })

      },
      handleDetail(id) {
        let loading = this.openLoading('努力加载数据中...');
        this.getToApi("project/"+ id, this.query, data => {
          loading.close();
          this.projectForm = data.result;
          this.dialogVisibleProject = true;
        }, (err) => {
          loading.close();
          this.$message({
            message: '提示，服务端繁忙！',
            type: 'error'
          });
        });
      },
      showSelect(id){
        this.dialogSelect = true
        this.student.pid = id;
      },
      showScore(id){
        this.dialogScore = true
        this.project.pid = id;
      },
      selectProject(){
        let loading = this.openLoading('努力加载数据中...');
        this.postToApi("project/select/"+ this.student.pid, {name: this.student.name}, data => {
          loading.close();
          this.loadAdvertProjects()
          this.dialogSelect = false
          this.$message({
            message: data.msg,
            type: data.result
          });
        }, (err) => {
          loading.close();
          this.$message({
            message: '提示，服务端繁忙！',
            type: 'error'
          });
        });
      },
      updateScore(){
        this.$refs['project'].validate((valid) => {
          if (valid) {
            let loading = this.openLoading('努力加载数据中...');
            this.postToApi("project/score/" + this.project.pid, {score: this.project.score}, data => {
              loading.close();
              this.loadAdvertProjects()
              this.dialogScore = false
              this.$message({
                message: data.msg,
                type: data.result
              });
            }, (err) => {
              loading.close();
              this.$message({
                message: '提示，服务端繁忙！',
                type: 'error'
              });
            });
          }
        })
      },
      closeDialog(){
        this.dialogVisibleProject = false;
        this.dialogSelect = false;
        this.dialogScore = false;
        this.project.score = ''
        this.student.name = ''
        this.projectForm = {
          _id: 0,
          id:'',
          title:'',
          pdesc:'',
          util:'',
          tid:'',
          sname:'',
          num:'',
          score:'',
        };
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
