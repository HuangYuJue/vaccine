<template>
  <div class="app-container" style="height: 500px;margin-top: 100px">
    <div align="center" style="font-size: 18px">个人中心</div>
    <br>
    <el-form label-width="120px" align="center">
      <el-form-item label="手机账号">
        <el-input v-model="userInfo.phone" readonly style="width: 1100px"/>
      </el-form-item>

      <el-form-item label="用户名称">
        <el-input v-model="userInfo.username" readonly style="width: 1100px"/>
      </el-form-item>

      <el-form-item label="用户性别">
        <el-input v-if="userInfo.sex == 0" value="男" readonly style="width: 1100px"/>
        <el-input v-if="userInfo.sex == 1" value="女" readonly style="width: 1100px"/>
      </el-form-item>

      <el-form-item label="用户年龄">
        <el-input v-model="userInfo.age" readonly style="width: 1100px"/>
      </el-form-item>

      <el-button type="danger" v-if="oldPwd != ''" icon="el-icon-edit" @click="$event => dialogTableVisble2 = true">修改用户密码</el-button>
      <el-button type="danger" v-if="oldPwd == ''" icon="el-icon-edit" @click="$event => dialogTableVisble2 = true">设置用户密码</el-button>
      <el-button type="primary" icon="el-icon-edit" @click="$event => dialogTableVisble1 = true">修改基础信息</el-button>

    </el-form>


    <el-dialog align="center" title="修改基本资料" :visible.sync="dialogTableVisble1" :data="userInfo">
      <template slot-scope="scope">
        <el-form :model="userInfo">

          <el-form-item label="手机账号">
            <el-input v-model="userInfo.phone" readonly/>
          </el-form-item>

          <el-form-item label="用户名称">
            <el-input v-model="userInfo.username" clearable/>
          </el-form-item>

          <el-form-item label="用户性别">
            <el-select v-model="userInfo.sex" filterable style="width: 720px">
              <el-option v-for="item in sexs" :key="item.id" :label="item.name" :value="item.id"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="用户年龄">
            <el-input v-model="userInfo.age" clearable/>
          </el-form-item>
        </el-form>
      </template>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" align="center" @click="close()">取消</el-button>
        <el-button type="primary" align="center" @click="update()">确认</el-button>
      </div>
    </el-dialog>

    <el-dialog align="center" title="用户密码" :visible.sync="dialogTableVisble2">
      <template slot-scope="scope">
        <el-form>
          <el-row>
            <el-input v-model="pwd1" clearable>
              <template slot="prepend">设置密码</template>
            </el-input>
          </el-row>
          <el-row>
            <el-input v-model="pwd2" clearable>
              <template slot="prepend">确认密码</template>
            </el-input>
          </el-row>
        </el-form>
      </template>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" align="center" @click="$event => dialogTableVisble2 = false">取消</el-button>
        <el-button type="primary" align="center" @click="updatePass">确认</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script>
  import userInfoApi from "@/api/userInfo";
  export default {
    data(){
      return {
        userInfo: {},
        oldPwd: '',
        pwd1: '',
        pwd2: '',
        dialogTableVisble1: false,
        dialogTableVisble2: false,
        sexs:[{id: 1, name: '女'},{id: 0, name: '男'}]
      }
    },
    created() {
      this.getDetail()
    },
    methods: {
      getDetail() {
        userInfoApi.userDetail().then(response => {
          this.userInfo = response.data
          console.log("用户手机号："+this.userInfo.phone)
        }).catch(() => {
        })
      },
      getPwd(){
        userInfoApi.getPass().then(response => {
          this.oldPwd = response.data
        })
      },
      updatePass(){
        if (this.pwd1 != this.pwd2){
          this.$message.warning("密码不一致")
        } else {
          userInfoApi.updatePwd(this.pwd1).then(response => {
            this.$message.success("修改成功")
          }).catch(() => {
          })
          //关闭
          this.dialogTableVisble2 = false
          this.getDetail()
        }
      },
      update(){
        userInfoApi.update(this.userInfo).then(() => {
          this.$message.info("修改成功")
          this.dialogTableVisble1 = false
        }).catch(() => {
          this.$message.warning("修改失败")
        })
      },
      close(){
        //关闭弹框
        this.dialogTableVisble1 = false
        //重新加载，因为用v-model没保存按取消主页的信息会修改，需重新加载
        this.getDetail()
      }
    }
  }
</script>
