<template>
  <div class="app-container">
    工作人员列表
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="worker.workername" clearable placeholder="人员名称" />
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">
        查询
      </el-button>
      <router-link :to="'/worker/editWorker'">
        <el-button type="primary" icon="el-icon-plus">
          新增
        </el-button>
      </router-link>
    </el-form>

    <!-- banner列表 -->
    <el-table
      :data="list"
      tooltip-effect="dark"
      stripe
      align="center"
      style="width: 100%">
      <el-table-column type="index" width="50" label="序号" />
      <el-table-column prop="workername" width="300" label="人员名称" />
      <el-table-column prop="worktime" width="300" label="工作时间" />
      <el-table-column prop="addressName" width="300" label="地点名称"/>
      <el-table-column label="操作" align="center" prop="deleted">
        <template slot-scope="scope">
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
          <router-link :to="'/worker/editWorker/' + scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑</el-button>
          </router-link>
        </template>
      </el-table-column>
    </el-table>
    <!--分页-->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total,prev,pager,next,jumper"
      @current-change="getList"
    />
  </div>
</template>

<script>
  import workerApi from "@/api/worker";
  export default {
    data() {
      return {
        current: 1, //当前页
        limit: 6, //每页显示记录数
        worker: {}, //条件封装对象
        list: [], //每页数据集合
        total: 0, //总记录数
      }
    },
    created() {
      if (this.$route.params && this.$route.params.id){
        const id = this.$route.params.id
        this.worker.addressId = id
      }
      this.getList()
    },
    methods: {
      getList(page = 1) {
        this.current = page
        console.log("接种点id："+this.worker.addressId)
        workerApi.select(this.limit, this.current, this.worker).then(response => {
          this.list = response.data.records;
          this.total = response.data.total;
        }) //请求成功
          .catch((error) => {
            console.log(error);
          }); //请求失败
      },
      removeDataById(id) {
        this.$confirm("此操作将永久删除该人员,是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          workerApi.delete(id).then(response => {
            this.$message({
              type: "success",
              message: "删除成功！",
            });
            this.getList(this.current);
          })
        })
      },
    }
  }
</script>

<style scoped>

</style>
