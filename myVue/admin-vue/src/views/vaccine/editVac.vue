<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="疫苗名称">
        <el-input v-model="vaccine.vacname" clearable style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="疫苗价格">
        <el-input v-model="vaccine.price" clearable style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="服用类型">
        <el-input v-model="vaccine.vactype" clearable style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="疫苗类型">
        <el-select v-model="vaccine.classifyId" filterable style="width: 1100px">
          <el-option
            v-for="item in classifyList" :key="item.id" :label="item.claname" :value="item.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="剩余数量">
        <el-input v-model="vaccine.vaccount" clearable style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="疫苗介绍">
        <el-input v-model="vaccine.introduce" clearable style="width: 1100px"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="updateVaccine"
        >保存</el-button></el-form-item>
    </el-form>
  </div>
</template>

<script>
  import vaccineApi from "@/api/vaccine"
  import classifyApi from "@/api/classify"
  export default {
    data() {
      return {
        vaccine: {},
        classifyList: [],
        classify: {},
        limit: 500,
        page: 1
      }
    },
    created() {
      if (this.$route.params && this.$route.params.id){
        const id = this.$route.params.id
        this.getById(id)
      }
      //获取疫苗类型列表
      this.classifyList = []//置空
      this.getClassify()
    },
    methods: {
      getById(id){
        vaccineApi.getById(id).then(responce => {
          this.vaccine = responce.data
        })
      },
      getClassify(){
        classifyApi.select(this.limit,this.page,this.classify).then(responce => {
          this.classifyList = responce.data.records
        })
      },
      updateVaccine(){
        const id = this.$route.params.id
        if (id == null){
          vaccineApi.add(this.vaccine).then(responce => {
            this.$message({
              type:'success',
              message:'添加成功！'
            })
          })
          this.$router.push({path: '/vaccine/vaccine'})
        } else {
          console.log("id:"+this.vaccine.id)
          vaccineApi.update(this.vaccine).then(responce => {
            this.$message({
              type:'success',
              message:'修改成功！'
            })
            this.$router.push({path: '/vaccine/vaccine'})
          })
        }
      }
    }
  }
</script>

<style scoped>

</style>
