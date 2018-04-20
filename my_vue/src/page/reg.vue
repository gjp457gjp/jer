<template>
  <div>
    <div class="content-body" v-loading="loading">
      <div class="content-title-tools">
        <div class='content-title'>
          <h1>用户注册</h1>
        </div>
      </div>
      <div style="margin-left: 20%; width: 100%; height: 100%; margin-top: 5%">
        <el-card class="box-card" style="width: 30%;">
          <el-form ref='form' :model="form" label-width="100px" :rules="rules">
            <el-form-item label="学号" prop="name">
              <el-input style="width: 60%" type="number" v-model.number="form.name" placeholder="请输入用户名即学号"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input type="number" v-model.number="form.newPassword1" placeholder="请输入密码" style="width: 60%"></el-input>
              <i v-if="newSuccessFlag" class="el-icon-success" style="margin-left: 20px; font-size: 18px; color: #67c23a"></i>
            </el-form-item>
            <el-form-item label="确认密码">
              <div style="display: flex; flex-direction: column;">
                <div>
                  <el-input type="password" v-model.number="form.newPassword2" placeholder="请再次输入密码" style="width: 60%"
                            @blur="affirmPassword"></el-input>
                  <i v-if="newSuccessFlag" class="el-icon-success" style="margin-left: 20px; font-size: 18px; color: #67c23a"></i>
                </div>
                <span v-if="messageFlag" style="margin-left: 20px;  margin-top:10px; font-size: 10px; color: #f56c6c">输入的新密码不一致</span>
              </div>
            </el-form-item>
            <el-form-item prop="state">
              <el-select v-model="form.state" placeholder="请选择">
                <el-option
                  v-for="item in options"
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
          name: '',
          newPassword1: '',
          newPassword2: '',
          state:1
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
        rules: {
          name:[
            { required: true, message: '请输入学号', trigger: 'blur' },
          ],
          password:[
            { required: true, message: '请输入学密码', trigger: 'blur' },
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
            this.postToApi(
              "user/add", this.form,
              data => {
                console.log(data)
                if(data.status === 1) {
                  this.$message({
                    message: data.msg,
                    type: "success"
                  });
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
