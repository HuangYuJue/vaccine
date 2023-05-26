<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="类型名称">
      <el-input v-model="classify.claname" clearable style="width: 1100px"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="updateUser"
        >保存</el-button></el-form-item>
    </el-form>
  </div>
</template>

<script>
  import classifyApi from '../../api/classify'
  export default {
    data(){
      return{
        classify:{}
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
        classifyApi.getById(id).then(response => {
          this.classify = response.data
        })
      },
      //编辑
      updateUser(){
        const cid = this.classify.id
        console.log("id="+cid)
        console.log("classify:"+this.classify.claname)
        if (cid == null){
          classifyApi.add(this.classify).then(response => {
            this.$message({
              type: 'success',
              message: '添加成功！'
            })
          })
          this.$router.push({path: '/vaccine/classify'})
        } else {
          classifyApi.update(this.classify).then(response => {
            //提示
            this.$message({
              type: 'success',
              message: '修改成功！'
            })
            this.$router.push({path: '/vaccine/classify'})
          })
        }
      }
    }
  }
</script>

<style scoped>
.app-container{
  margin-top: 60px;
}
</style>
