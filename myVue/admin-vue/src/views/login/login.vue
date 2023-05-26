<template>
  <!--登陆的容器-->
  <div class="login_container">
    <!-- 登录盒子  -->
    <div class="login_box">
      <!-- 头像 -->
      <div class="avatar_box">
        <img src="../../assets/images/touxiang.jpg" alt="">
      </div>
      <!-- 登录表单 -->
      <el-form class="login_form" :model="loginFormData" :rules="loginFormRules" ref="loginFromRef">
        <el-form-item prop="loginName">
          <el-input v-model="loginFormData.phone" clearable name="phone"></el-input>
        </el-form-item>
        <el-form-item  prop="password">
          <el-input :type="pwdType" v-model="loginFormData.password"/>
          <span class="show-pwd" @click="showPwd()">
              <svg-icon icon-class="eye" />
          </span>
        </el-form-item>
        <!-- 按钮 -->
        <el-form-item class="btns">
          <el-button :loading="loading" type="primary" @click.native.prevent="handleLogin">登录</el-button>
          <el-button type="info" @click="loginRef('loginFromRef')">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
  import {isvalidUsername} from "../../utils/validate";
  export default {
    name: "Login",
    data() {
      const validateUsername = (rule, value, callback) => {
        if (!isvalidUsername(value)){
          callback(new Error('请输入正确的用户名'))
        } else {
          callback()
        }
      }
      const validatePass = (rule, value, callback) => {
        if (value.length < 5){
          callback(new Error('密码不能小于5位数'))
        } else {
          callback()
        }
      }
      return {
        loginFormData: {
          phone: '',
          password: ''
        },
        loginFormRules: {
          phone: [{ required: true, trigger: 'blur', validator: validateUsername }],
          password: [{ required: true, trigger: 'blur', validator: validatePass }]
        },
        loading: false,
        pwdType: 'password',
        redirect: undefined
      };
    },
    watch: {
      $route: {
        handler: function(route) {
          this.redirect = route.query && route.query.redirect
        },
        immediate: true
      }
    },
    methods: {
      showPwd(){
        if (this.pwdType === 'password'){
          this.pwdType = ''
        } else {
          this.pwdType = 'password'
        }
      },
      //登录方法
      handleLogin(){
        this.$refs.loginFromRef.validate(valid => {
          if (valid) {
            this.loading = true
            this.$store.dispatch('login', this.loginFormData).then(response => {
              console.log(response)
              this.$message.success(response.message)//提示操作成功
              this.$router.push({path: this.redirect || '/index'})
              console.log("实现跳转了~")
              this.loading = false
            }).catch(() => {
              this.loading = false
              console.log('异常')
              return false
            })
          } else {
            console.log('error submit!!!')
            return false
          }
        })
      },
      //重置密码
      loginRef(){
        this.$refs.loginFromRef.resetFields();
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .login_container{
    background: aliceblue;
    height: 100%;
  }
  .login_box{
    width: 450px;
    height: 300px;
    background: #fff;
    border-radius: 3px;
    /*绝对定位*/
    position: absolute;
    /*左偏移*/
    left: 50%;
    /*上偏移*/
    top: 50%;
    /*减去容器自身的宽高*/
    transform: translate(-50%,-50%);
  }
  .login_box>.avatar_box{
    height: 130px;
    width: 130px;
    border:1px solid #E9EEF3;
    border-radius: 50%;
    padding: 5px;
    /*阴影*/
    box-shadow: 0 0 10px #ddd;
    position: absolute;
    left: 50%;
    transform: translate(-50%,-50%);
    background-color: lightpink;
  }
  .login_box>.avatar_box>img{
    width: 100%;
    height: 100%;
    border-radius: 50%;
    background-color: #eee;
  }
  /*表单设计css样式*/
  .login_form{
    /*绝对定位*/
    position: absolute;
    bottom: 0;
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
  }
  .btns{
    /*流式布局*/
    display: flex;
    justify-content: flex-end;
  }
  html,body,#app{
    height: 100%;
    margin: 0;
    padding: 0;
    font-size: 12px;
  }
  /*面包碎*/
  .el-breadcrumb{
    font-size: 12px;
    margin-bottom: 15px;
  }
  /*卡片*/
  .el-card {
    box-shadow: 0 1px 1px rgba(0,0,0,.15) !important;
  }
  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: darkgray;
    cursor: pointer;
    user-select: none;
  }
  .svg-container {
    padding: 6px 5px 6px 15px;
    color: darkgray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }
</style>
