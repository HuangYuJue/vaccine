<template>
  <div class="header-container">
    <div class="wrapper">
      <!-- logo -->
      <div class="left-wrapper v-link selected">
        <img style="width: 50px" width="50" src="~assets/images/logo.png">
        <span class="text">疫苗预约平台</span>
      </div>

      <!-- search -->
      <div class="search-container">
        <div class="search-wrapper">
          <div class="vaccine-search">
            <el-autocomplete class="search-input" prefix-icon="el-icon-search"
                             v-model="claname" :fetch-suggestions="querySearchAsync" placeholder="点击输入疫苗类型名称"
                             @select="handleSelect">
            </el-autocomplete>
          </div>
        </div>
      </div>

      <!-- right -->
      <div class="right-wrapper">
        <span class="v-link clickable">帮助中心</span>
        <span v-if="name == ''" class="v-link clickable" @click="$event => showLogin()" id="loginDialog">登录/注册</span>
        <el-dropdown v-if="name != ''" @command="loginForm">
          <spam class="el-dropdown-link">
            {{name}}<i class="el-icon-arrow-down el-icon--right"></i>
          </spam>
          <el-dropdown-menu class="user-name-wrapper" slot="dropdown">
            <el-dropdown-item command="/index">返回主页</el-dropdown-item>
            <el-dropdown-item command="/user">个人中心</el-dropdown-item>
            <el-dropdown-item command="/order">疫苗订单</el-dropdown-item>
            <el-dropdown-item command="/logout" divided>退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </div>

    <!-- 登录成功，用户信息存于cookie -->
    <el-dialog :visible.sync="dialogUserFormVisible" style="text-align: left" top="160px"
               :modal-append-to-body="true" width="750px" @close="$event => closeDialog()">
      <div style="width: 700px; height: 250px">
        <!-- 手机验证码登录 -->
        <div align="center" v-if="dialogAtrr.showLoginType === 'phone'">
          <div style="width: 100%">
            <div style="position: static;width: 70%">
              <span class="title">{{ dialogAtrr.labelTips }}</span>
              <el-form>
                <el-form-item>
                  <el-input v-model="dialogAtrr.inputValue" :placeholder="dialogAtrr.placeholder" :maxlength="dialogAtrr.maxlength" class="input v-input">
                    <span slot="suffix" class="sendText v-link" v-if="dialogAtrr.second > 0">{{ dialogAtrr.second }}s </span>
                    <span slot="suffix" class="sendText v-link highlight clickable selected" v-if="dialogAtrr.second == 0" @click="getCodeFun()">重新发送 </span>
                  </el-input>
                </el-form-item>
              </el-form>
            <!-- 绑定登录/获取验证码按钮 -->
            <div class="send-button v-button" @click="codeClick()"> {{ dialogAtrr.loginBtn }} </div>
          </div>
          <div class="bottom">
            <br>
            <br>
            <div @click="passwordLogin()">
              <span class="third-text"> 切换为密码登录 </span></div>
            </div>
          </div>
        </div>
        <!-- 密码登录 -->
        <div align="center" v-if="dialogAtrr.showLoginType === 'password'" >
          <div style="width: 100%">
            <div style="position: static;width: 70%">
              <span class="title">密码登录</span>
              <el-form v-model="loginFormData" :rules="loginFormRules" ref="loginFormRef">
                <el-form-item prop="loginName">
                  <el-input v-model="loginFormData.phone" placeholder="请输入您的手机账号" :maxlength="dialogAtrr.maxlength" class="input v-input"></el-input>
                </el-form-item>
                <el-form-item prop="loginPw">
                  <el-input v-model="loginFormData.password" show-password placeholder="请输入您的登录密码" class="input v-input"/>
                </el-form-item>
              </el-form>
              <!-- 登录按钮 -->
              <div class="send-button v-button" @click="pwLogin()">登录</div>
            </div>
            <div class="bottom">
                <br>
                <div @click="phoneLogin()">
                  <span class="third-text"> 切换为手机短信验证码登录 </span>
                </div>
            </div>
          </div>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import cookie from 'js-cookie'
  import Vue from 'vue'
  import classifyApi from "@/api/classify"
  import msmApi from "@/api/msm"
  import userInfoApi from "@/api/userInfo";
  const defaultDialogAtrr1 = {
    showLoginType: 'phone',//验证码和密码登录切换
    labelTips: '验证码登录',//输入框提示
    inputValue: '',//输入框绑定对象
    placeholder: '请输入您的手机号',
    maxlength: 11,//输入框长度
    loginBtn: '获取验证码',//登录按钮或获取验证码
    sending: true,//是否发送验证码
    second: -1,//倒计时，second=0 ：重新发送，second>0 : 显示倒计时；second=0
    clearSmsTime: null
  }
  const defaultDialogAtrr2 = {
    showLoginType: 'password',//验证码和密码登录切换
  }
  export default {
    data(){
      const validatePass = (rule, value, callback) => {
        if (value.length < 5){
          callback(new Error('密码不能小于5位数'))
        } else {
          callback()
        }
      }
      return{
        claname: '',

        userInfo: {
          phone: '',
          code: ''
        },
        loginFormData: {
          phone: '',
          password: ''
        },
        logibFormRules: {
          password: [{ required: true, trigger: 'blur', validatePass }]
        },
        dialogUserFormVisible: false,
        //弹出层相关属性
        dialogAtrr: defaultDialogAtrr1,
        name: '',//用户登录显示的名称
      }
    },
    created() {
      //获取登录信息
      this.showInfo()
    },
    mounted() {
      //注册全局登录事件对象
      window.loginEvent = new Vue()
      //监听登录事件
      loginEvent.$on('loginDialogEvent', function (){
        document.getElementById("loginDialog").click()
      })
    },
    methods: {
      //输入值弹出下拉框
      querySearchAsync(queryString, cb){
        queryString = queryString.trim()
        if (queryString == ''){
          cb([]);
          return;
        }
        classifyApi.findByName(queryString).then(responce => {
          for (let i = 0,len = responce.data.length;i < len;i++){
            responce.data[i].value = responce.data[i].claname
            console.log("claname:"+responce.data[i].value)
          }
          cb(responce.data)
        })
      },

      //选择下拉框内容跳转
      handleSelect(item){
        window.location.href = '/vaccine/' + item.id
      },

      //绑定登录或获取验证码按钮
      codeClick(){
        if (this.dialogAtrr.loginBtn == '获取验证码'){
          //输入框绑定对象
          this.userInfo.phone = this.dialogAtrr.inputValue
          //获取验证码
          this.getCodeFun()
        } else {
          //调用方法：登录
          this.login()
        }
      },

      //绑定登录，点击显示登录层
      showLogin(){
        this.dialogUserFormVisible = true
        //初始化登录层相关参数
        this.dialogAtrr = {...defaultDialogAtrr1}
      },
      //绑定密码登录，点击显示登录层
      showLoginPw(){
        this.dialogUserFormVisible = true
        this.dialogAtrr = {...defaultDialogAtrr2}
      },

      //验证码登录
      login(){
        //输入框绑定对象
        this.userInfo.code = this.dialogAtrr.inputValue
        //判断是否登录按钮或获取验证码按钮文本
        if(this.dialogAtrr.loginBtn == '正在提交...'){
          this.$message.error('重新提交')
          return;
        }
        //判断验证码是否输入
        if (this.userInfo.code == '') {
          this.$message.error('验证码必须输入')
          return;
        }
        //判断输入的验证码长度是否合理
        if(this.userInfo.code.length != 4){
          this.$message.error('验证码格式不正确')
          return;
        }
        //设置登录按钮或获取验证码按钮文本的值
        this.dialogAtrr.loginBtn = '正在提交...'
        //调用userInfoApi接口中的login方法
        userInfoApi.loginCode(this.userInfo).then(response => {
          console.log(response.data)
          //登录成功，调用方法：设置cookie，将用户登录显示的名称与数据产生的token设置进cookie
          this.setCookies(response.data.username, response.data.token)
        }).catch(e => {
          //设置登录按钮或获取验证码按钮文本的值
          this.dialogAtrr.loginBtn = '马上登录'
        })
      },

      //密码登录
      pwLogin(){
        userInfoApi.loginPw(this.loginFormData).then(responce => {
          console.log(responce.data)
          this.setCookies(responce.data.username, responce.data.token)
        }).catch(e => {
          console.log("登陆失败")
        })
      },

      //设置cookie
      setCookies(name,token){
        cookie.set('token', token, { domain: 'localhost' })
        cookie.set('name', name, { domain: 'localhost' })
        //重新加载
        window.location.reload()
      },

      //获取验证码
      getCodeFun(){
        if(!(/^1[34578]\d{9}$/.test(this.userInfo.phone))){
          this.$message.error('手机号码不正确')
          return;
        }
        //初始化验证码相关属性
        this.dialogAtrr.inputValue = '' //输入框绑定对象
        this.dialogAtrr.placeholder = '请输入验证码' //输入框placeholder
        this.dialogAtrr.maxlength = 4 //输入框长度控制
        this.dialogAtrr.loginBtn = '马上登录' //登录  按钮或获取验证码按钮文本
        //控制重新发送,dialogAtrr.sending:是否可以发送验证码
        if(!this.dialogAtrr.sending) return;
        //发送短信验证码
        //调用方法:倒计时，即点击发送验证码会有倒计时效果
        this.timeDown();
        this.dialogAtrr.sending = false;
        //调用smsApi接口中的方法发送验证码
        msmApi.sendCode(this.userInfo.phone).then(response => {
          console.log("手机号："+this.userInfo.phone)
          //调用方法:倒计时
          this.timeDown();
        }).catch(e => {
          this.$message.error('发送失败，重新发送')
          //发送失败，调用方法:绑定登录，点击显示登录层，回到重新获取验证码界面
          this.showLogin()
        })
      },

      //倒计时(此时作用于验证码倒计时)
      timeDown(){
        //clearSmsTime:倒计时定时任务引用
        if(this.clearSmsTime){
          clearInterval(this.clearSmsTime);
        }
        //设置倒计时为60s，60s之后才能重新发送验证码
        this.dialogAtrr.second = 60
        //设置输入框提示内容
        this.dialogAtrr.labelTips = '验证码已发送至' + this.userInfo.phone
        //倒计时定时任务，setInterval方法:每隔一段时间就执行一次该方法里面的代码
        //本处setInterval方法表示每隔1000ms，this.dialogAtrr.second的值减一，依次为59、58...1，
        this.clearSmsTime = setInterval(() => {
          --this.dialogAtrr.second;//即--i，执行一次就减一
          if (this.dialogAtrr.second < 1) {//当执行-1之后值小于1，再回到初始值效果
            //clearInterval:清除效果，此处为清除定时器效果
            clearInterval(this.clearSmsTime);
            this.dialogAtrr.sending = true;
            this.dialogAtrr.second = 0;
          }
        }, 1000);
      },

      //关闭登录层
      closeDialog() {
        if(this.clearSmsTime) {
          //清除定时器效果
          clearInterval(this.clearSmsTime);
        }
      },

      //获取登录信息(判断是否登录)
      showInfo() {
        let token = cookie.get('token')
        if (token){
          this.name = cookie.get('name')
          console.log(this.name)
        }
      },

      //添加下拉列表事件处理，跳转访问路径command
      loginForm(command) {
        if('/logout' == command) {
          cookie.set('name', '', {domain: 'localhost'})
          cookie.set('token', '', {domain: 'localhost'})

          //跳转页面
          window.location.href = '/'
        } else if ('/index' == command) {
          window.location.href = '/'
        } else {
          window.location.href = command
        }
      },

      phoneLogin(){
        this.dialogAtrr.showLoginType = 'phone'
        this.showLogin()
      },

      passwordLogin(){
        this.dialogAtrr.showLoginType = 'password'
        this.showLoginPw()
      }
    }
  }
</script>

<style scoped>

</style>
