<template>
  <div class="app-container">
    用户信息修改
    <el-form label-width="120px">
      <el-form-item label="用户手机">
      <el-input v-model="user.phone" readonly/>
      </el-form-item
    >
      <el-form-item label="用户账号">
      <el-input clearable v-model="user.username"/>
      </el-form-item
    >
      <el-form-item hidden label="用户密码" >
      <el-input v-model="user.password" />
      </el-form-item
    >
      <el-form-item label="用户性别">
      <template slot-scope="props">
        <el-row>
          <el-col :span="2">
            <span v-if="props.row.sex == 0">男</span>
            <span v-if="props.row.sex == 1">女</span>
          </el-col>
        </el-row>
      </template>
      </el-form-item
    >
      <el-form-item label="用户年龄">
      <el-input v-model="user.age" clearable/>
      </el-form-item
    >
      <el-form-item>
      <el-button type="primary" @click="updateUser"
    >保存</el-button
    >
      </el-form-item
    >
      </el-form
    >
  </div>
</template>

<script>
  import userApi from '../../api/user'
  export default {
    data(){
      return{
        //将表单中的数据传入data中的user中，再传入方法中
        user:{}
      }
    },
    created() {
      if (this.$route.params && this.$route.params.id){
        const id = this.$route.params.id
        this.getById(id)
      }
    },
    methods:{
      //通过id获取用户信息
      getById(id) {
        userApi.getById(id).then(response => {
          this.user = response.data
        })
      },
      //编辑
      updateUser(){
        userApi.updateUser(this.user).then(response => {
          //提示
          this.$message({
            type: 'success',
            message: '修改成功！'
          })
          this.$router.push({path: '/user/userList'})
        })
      }
    }
  }
</script>

<style scoped>

</style>
