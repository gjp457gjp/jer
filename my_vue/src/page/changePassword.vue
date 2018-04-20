<template>
  <div>
    <div class="content-body" v-loading="loading">
      <div class="content-title-tools">
        <div class='content-title'>
          <h1>用户密码修改</h1>
        </div>
      </div>
      <div style="margin-left: 20%; width: 100%; height: 100%; margin-top: 5%">
        <el-card class="box-card" style="width: 30%;">
          <el-form ref='form' :model="form" label-width="100px" :rules="rules">
            <el-form-item label="旧密码" prop="oldPassword">
              <el-input style="width: 60%" type="password" v-model.number="form.oldPassword" placeholder="请输入旧密码"></el-input>
            </el-form-item>
            <el-form-item label="新密码">
              <el-input type="password" v-model.number="form.newPassword1" placeholder="请输入新密码" style="width: 60%"></el-input>
              <i v-if="newSuccessFlag" class="el-icon-success" style="margin-left: 20px; font-size: 18px; color: #67c23a"></i>
            </el-form-item>
            <el-form-item label="确认新密码">
              <div style="display: flex; flex-direction: column;">
                <div>
                  <el-input type="password" v-model.number="form.newPassword2" placeholder="请再次输入新密码" style="width: 60%"
                            @blur="affirmPassword"></el-input>
                  <i v-if="newSuccessFlag" class="el-icon-success" style="margin-left: 20px; font-size: 18px; color: #67c23a"></i>
                </div>
                <span v-if="messageFlag" style="margin-left: 20px;  margin-top:10px; font-size: 10px; color: #f56c6c">输入的新密码不一致</span>
              </div>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">确认修改</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </div>
      <div class="content-bottom">
      </div>
    </div>
  </div>
</template>
<script>
  export default {
    components: {
    },
    data() {
      return {
        newSuccessFlag: false,
        loading: false,
        messageFlag: false,
        form: {
          oldPassword: '',
          newPassword1: '',
          newPassword2: '',
        },
        loginshow: false,
        rules: {
          oldPassword:[
            { required: true, message: '请输入旧密码', trigger: 'blur' },
          ]
        }
      };
    },
    mounted: function() {
    },
    methods: {
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
            // let id = this.getCooke('id');
            this.form.username = localStorage.getItem('ms_username');
            this.postToApi(
              "user/changePwd", this.form,
              data => {
                if(data.status === 1) {
                  this.$message({
                    message: data.msg,
                    type: "success"
                  });
                  localStorage.removeItem('ms_username')
                  this.$router.push('/login');
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
    }
  };
</script>
