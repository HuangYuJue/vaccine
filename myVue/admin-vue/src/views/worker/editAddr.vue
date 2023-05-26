<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="接种点名称">
        <el-input v-model="address.addrname" clearable style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="工作时间">
        <el-input v-model="address.opentime" clearable style="width: 1100px"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="updateAddress"
        >保存</el-button></el-form-item>
    </el-form>
  </div>
</template>

<script>
  import addressApi from "@/api/address"
  export default {
    data() {
      return {
        address: {},
        limit: 500,
        page: 1
      }
    },
    created() {
      if (this.$route.params && this.$route.params.id){
        const id = this.$route.params.id
        this.getById(id)
      }
    },
    methods: {
      getById(id){
        addressApi.getById(id).then(responce => {
          this.address = responce.data
        })
      },
      updateAddress(){
        const id = this.$route.params.id
        if (id == null){
          addressApi.add(this.address).then(responce => {
            this.$message({
              type:'success',
              message:'添加成功！'
            })
          })
          this.$router.push({path: '/worker/address'})
        } else {
          console.log("id:"+this.address.id)
          addressApi.update(this.address).then(responce => {
            this.$message({
              type:'success',
              message:'修改成功！'
            })
            this.$router.push({path: '/worker/address'})
          })
        }
      }
    }
  }
</script>

<style scoped>

</style>
