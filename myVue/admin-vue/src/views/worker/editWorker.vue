<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="人员名称">
        <el-input v-model="worker.workername" clearable style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="工作地点">
        <el-select v-model="worker.addressId" filterable style="width: 1100px">
          <el-option v-for="item in addrList" :key="item.id" :label="item.addrname" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="工作时间">
        <el-input v-model="worker.worktime" clearable style="width: 1100px"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="updateWorker"
        >保存</el-button></el-form-item>
    </el-form>
  </div>
</template>

<script>
  import workerApi from "@/api/worker"
  import addressApi from "../../api/address";
  export default {
    data() {
      return {
        worker: {},
        limit: 500,
        page: 1,
        address: {},
        addrList: []
      }
    },
    created() {
      if (this.$route.params && this.$route.params.id){
        const id = this.$route.params.id
        this.getById(id)
      }
      this.addrList = []
      this.getAddrList()
    },
    methods: {
      getById(id){
        workerApi.getById(id).then(responce => {
          this.worker = responce.data
        })
      },
      updateWorker(){
        if (this.worker.id == null){
          workerApi.add(this.worker).then(responce => {
            this.$message({
              type:'success',
              message:'添加成功！'
            })
          })
          this.$router.push({path: '/worker/worker'})
        } else {
          console.log("id:"+this.worker.id)
          workerApi.update(this.worker).then(responce => {
            this.$message({
              type:'success',
              message:'修改成功！'
            })
            this.$router.push({path: '/worker/worker'})
          })
        }
      },
      getAddrList(){
        addressApi.select(500,1,this.address).then(responce => {
          this.addrList = responce.data.records
        })
      }
    }
  }
</script>

<style scoped>

</style>
