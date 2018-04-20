<template>
  <div class="login-wrap">
    <div class="ms-title">后台管理系统</div>
    <div class="ms-login">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="0px" class="demo-ruleForm">
        <el-form-item prop="username">
          <el-input v-model="ruleForm.username" placeholder="username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" placeholder="password" v-model="ruleForm.password"
                    @keyup.enter.native="submitForm('ruleForm')"></el-input>
        </el-form-item>
        <el-form-item prop="state">
          <el-select v-model="ruleForm.state" placeholder="请选择">
            <el-option
              v-for="item in options1"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm('ruleForm')">登录</el-button>
          <el-button type="primary" @click="regs">注册</el-button>
        </div>
      </el-form>

      <el-dialog title="注册" :visible.sync="dialogVisible"
                 :before-close="closeDialog" center width="30%">
        <el-form ref='form' :model="form" label-width="100px" :rules="rules2">
          <el-form-item label="学号" prop="name">
            <el-input style="width: 60%" type="text" v-model.number="form.name" placeholder="请输入用户名即学号"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="pwd">
            <el-input type="password" v-model.number="form.newPassword1" placeholder="请输入密码"
                      style="width: 60%"></el-input>
            <i v-if="newSuccessFlag" class="el-icon-success"
               style="margin-left: 20px; font-size: 18px; color: #67c23a"></i>
          </el-form-item>
          <el-form-item label="确认密码">
            <div style="display: flex; flex-direction: column;">
              <div>
                <el-input type="password" v-model.number="form.newPassword2" placeholder="请再次输入密码" style="width: 60%"
                          @blur="affirmPassword"></el-input>
                <i v-if="newSuccessFlag" class="el-icon-success"
                   style="margin-left: 20px; font-size: 18px; color: #67c23a"></i>
              </div>
              <span v-if="messageFlag" style="margin-left: 20px;  margin-top:10px; font-size: 10px; color: #f56c6c">输入的新密码不一致</span>
            </div>
          </el-form-item>
          <el-form-item label="身份" prop="state">
            <el-select v-model="form.state" placeholder="请选择" style="width: 60%">
              <el-option
                v-for="item in options2"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="onSubmit">注册</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
  export default {
    data: function () {
      return {
        dialogVisible: false,
        newSuccessFlag: false,
        loading: false,
        messageFlag: false,
        form: {
          name: '',
          newPassword1: '',
          newPassword2: '',
          state: 1
        },
        options: [{
          value: 1,
          label: '学生'
        }, {
          value: 2,
          label: '教师'
        }, {
          value: 0,
          label: '管理员'
        }],
        rules2: {
          name: [
            {required: true, message: '请输入学号', trigger: 'blur'},
          ],
          // pwd:[
          //   { required: true, message: '请输入密码', trigger: 'blur' },
          // ]
        },
        ruleForm: {
          username: '',
          password: '',
          state: 1
        },
        options1: [{
          value: 1,
          label: '学生'
        }, {
          value: 2,
          label: '教师'
        }, {
          value: 0,
          label: '管理员'
        }],
        options2: [{
          value: 1,
          label: '学生'
        }, {
          value: 2,
          label: '教师'
        }],
        rules: {
          username: [
            {required: true, message: '请输入用户名', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'blur'}
          ]
        }
      }
    },
    methods: {
      openLoading(text) {
        const loading = this.$loading({
          lock: true,
          text: text,
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        return loading;
      },
      submitForm(formName) {
        const self = this;
        self.$refs[formName].validate((valid) => {
          if (valid) {
            let loading = this.openLoading('努力加载数据中...');
            this.postToApi("user/login", this.ruleForm, data => {
              loading.close();
              if (data.result == 'success') {
                localStorage.setItem('ms_username', self.ruleForm.username);
                localStorage.setItem('ms_psw', self.ruleForm.password);
                localStorage.setItem('ms_state', self.ruleForm.state);
                self.$router.push('/readme');
              }
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

          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      regs() {
        this.dialogVisible = true
      },
      closeDialog() {
        this.form = {
          name: '',
          newPassword1: '',
          newPassword2: '',
          state: 1
        }
        this.dialogVisible = false
      },
      affirmPassword() {
        if (this.form.newPassword1 !== this.form.newPassword2) {
          this.messageFlag = true;
          this.newSuccessFlag = false;
        } else {
          this.newSuccessFlag = true;
          this.messageFlag = false
        }
      },
      onSubmit() {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            this.postToApi(
              "user/add", this.form,
              data => {
                console.log(data)
                if (data.status === 1) {
                  this.$message({
                    message: data.msg,
                    type: "success"
                  });
                  this.dialogVisible = false;
                } else {
                  this.$message({
                    message: data.msg,
                    type: "error"
                  });
                }
              }, err => {
                this.$message({
                  message: data.msg,
                  type: "error"
                });
              });
          } else {
            return false;
          }
        });
      }
    },

  }
</script>

<style scoped>
  .login-wrap {
    position: relative;
    width: 100%;
    height: 100%;
  }

  .ms-title {
    position: absolute;
    top: 50%;
    width: 100%;
    margin-top: -230px;
    text-align: center;
    font-size: 30px;
    color: #fff;

  }

  .el-select {
    width: 100%
  }

  .ms-login {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 300px;
    height: 200px;
    margin: -150px 0 0 -190px;
    padding: 40px;
    border-radius: 5px;
    background: #fff;
  }

  .login-btn {
    text-align: center;
  }

  .login-btn button {
    width: 130px;
    height: 36px;
  }
</style>
