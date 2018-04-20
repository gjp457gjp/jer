<template>
  <div>
    <div class="content-body">
      <div class="content-title-tools">
        <div class='content-title'>
          <h1>教师信息列表</h1>
        </div>
        <div class="content-tools">
        </div>
      </div>
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <el-form ref="form" :inline="true" :model="form" style="margin-bottom: -20px">
            <el-form-item>
              <el-input clearable v-model="form.tname" placeholder="输入教师姓名" style="width: 300px"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" @click="onSubmit">查询</el-button>
            </el-form-item>
          </el-form>
        </div>
        <div class="table">
          <el-table :data="teachers" border stripe style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection">
            </el-table-column>
            <el-table-column prop="tno" label="教师号" width="80">
            </el-table-column>
            <el-table-column prop="tname" label="教师姓名" width="80">
            </el-table-column>
            <el-table-column prop="phone" label="手机号码" width="80">
            </el-table-column>
            <el-table-column prop="qqnum" label="QQ号码" width="80">
            </el-table-column>
            <el-table-column prop="pids" label="课题">
            </el-table-column>
            <el-table-column label="操作" width="200">
              <template slot-scope="scope">
                <el-button type="text" size="small" @click="handleDetail(scope.row)">编辑</el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>

        <el-dialog title="教师信息" :visible.sync="dialogVisibleTeacher"
                   :before-close="closeDialog" center width="50%">
          <el-form id="imgInputer" ref="form" :rules="rules" :model="teacherForm"  label-width="30%" style="align-content: center">
            <el-form-item label="教师号">
              <el-input v-model.number="teacherForm.tno" style="width: 60%"></el-input>
            </el-form-item>
            <el-form-item label="教师姓名" prop='tname'>
              <el-input v-model="teacherForm.tname" style="width: 60%"></el-input>
            </el-form-item>
            <el-form-item label="手机号码" prop='phone'>
              <el-input v-model.number="teacherForm.phone" style="width: 60%"></el-input>
            </el-form-item>
            <el-form-item label="QQ号码" prop='qqnum'>
              <el-input v-model.number="teacherForm.qqnum" style="width: 60%"></el-input>
            </el-form-item>
            <el-form-item label="课题号" prop='pids'>
              <el-select v-model="teacherForm.pids" style="width: 60%" multiple placeholder="请选择">
                <el-option
                  v-for="item in pidTitle"
                  :key="item.pid"
                  :label="item.title"
                  :value="item.pid">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item  class="dialog-footer">
              <el-button type="primary" @click="handleSaveTeacher('form')">确定</el-button>
              <el-button @click="handleCancel('form')">取消</el-button>
            </el-form-item>
          </el-form>
        </el-dialog>
        <div class="content-bottom">
          <el-button type="primary" round @click="showDialog">新增教师</el-button>
          <el-popover ref="popover5" placement="top" width="160" v-model="visible2">
            <p>您确定要删除教师信息吗？</p>
            <div style="text-align: right; margin: 0">
              <el-button size="mini" type="text" @click="visible2 = false">取消</el-button>
              <el-button type="primary" size="mini" @click="delTeacher">确定</el-button>
            </div>
          </el-popover>
          <el-button type="danger" v-popover:popover5 round :disabled="multipleSelection.length==0">删除教师</el-button>
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
        teachers: [],
        total: 0,
        visible2: false,
        dialogVisibleTeacher: false,
        multipleSelection: [],
        teacherForm: {
          _id: 0,
          tname: ''
        },
        pidTitle:[],
        form: {
          tname: ''
        },
        query: {
          currentPage: 1,
          pageSize: 10,
          obj: {},
        },
        rules: {
          tno: [
            {required: true, message: '教师号不能为空', trigger: 'blur'},
            {type: 'number', message: '教师号必须为数字值', trigger: 'blur'}
          ],
          tname: [
            {required: true, message: '教师姓名不能为空', trigger: 'blur'}
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
    mounted: function () {
      this.loadPidTitle()
      this.loadAdvertTeachers()
    },
    methods: {
      loadAdvertTeachers() {
        let loading = this.openLoading('努力加载数据中...');
        this.getToApi("teacher/list", this.query, data => {
          loading.close();
          this.teachers = data.result;
          this.total = data.total;
        }, (err) => {
          loading.close();
          this.$message({
            message: '提示，服务端繁忙！',
            type: 'error'
          });
        });
      },
      loadPidTitle() {
        let loading = this.openLoading('努力加载数据中...');
        this.getToApi("project/pidTitle", this.query, data => {
          loading.close();
          this.pidTitle = data.result;
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
        this.dialogVisibleTeacher = false;
        this.teacherForm = {
          _id: 0,
          tno:'',
          tname: '',
          phone: '',
          qqnum: '',
          pids: ''
        };
        this.$refs[done].resetFields();
      },
      onSubmit() {
        let query = {
          currentPage: 1,
          pageSize: 10,
          tname: this.form.tname,
        };
        let loading = this.openLoading('数据加载中...');
        this.getToApi("teacher/list", query, data => {
          loading.close();
          this.teachers = data.result;
          this.total = data.total;
        }, (err) => {
          loading.close();
          this.$message({
            message: '提示，服务端繁忙！',
            type: 'error'
          })
        });

      },

      showDialog() {
        this.teacherForm =  {
          _id: 0,
          tname: '',
        };
        this.dialogVisibleTeacher = true;
      },
      handleSelectionChange(val) {
        this.multipleSelection = val;
      },
      handleCurrentChange(page) {
        this.query.currentPage = page;
        this.query.tname = this.form.tname;
        this.loadAdvertTeachers();
      },
      handleSaveTeacher(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let loading = this.openLoading('正在提交数据...');
            console.log(this.teacherForm._id)
            if(this.teacherForm._id === 0) {
              let that = this;
              delete this.teacherForm._id;
              this.teacherForm.pids = this.teacherForm.pids.toString()
              that.postToApi("teacher/add", JSON.parse(JSON.stringify(this.teacherForm)), data => {
                loading.close();
                that.$message({
                  showClose: true,
                  message: '新增教师成功！',
                  type: 'success'
                });
                that.loadAdvertTeachers();
                that.dialogVisibleTeacher = false;
              }, (err) => {
                loading.close();
                this.$message({
                  message: '提示，服务端繁忙！',
                  type: 'error'
                });
              })
            } else {
              let pids = this.teacherForm.pids.toString()
              let teacher = {
                id: this.teacherForm.id,
                phone: this.teacherForm.phone,
                qqnum: this.teacherForm.qqnum,
                tname: this.teacherForm.tname,
                tno: this.teacherForm.tno,
                pids: pids
              }
              let fd = JSON.parse(JSON.stringify(teacher))
              this.postToApi("teacher/update/" + fd.id, fd, data => {
                loading.close();
                this.$message({
                  showClose: true,
                  message: '修改教师成功！',
                  type: 'success'
                });
                this.dialogVisibleTeacher = false;
                this.loadAdvertTeachers();
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
              message: '请填入正确的教师信息',
              type: 'error'
            });
          }
        });
      },

      delTeacher() {
        let idArray=this.multipleSelection.map(o=>{
          return o.id
        });
        this.postToApi("teacher/delete", {ids:idArray}, data => {
          this.$message({
            showClose: true,
            message: '删除成功！',
            type: 'success'
          });
          this.multipleSelection=[];
          this.visible2=false;
          this.loadAdvertTeachers()
        }, (err) =>{
          this.$message({
            message: '提示，服务端繁忙！',
            type: 'error'
          });
        })
      },
      handleDetail(advertEventDetail) {
        let loading = this.openLoading('努力加载数据中...');
        this.getToApi("teacher/"+ advertEventDetail.id, this.query, data => {
          loading.close();
          this.teacherForm = data.result;
          this.dialogVisibleTeacher = true;
        }, err => {
          loading.close();
          this.$message({
            message: '提示，服务端繁忙！',
            type: 'error'
          });
        })
      },
      closeDialog(){
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
