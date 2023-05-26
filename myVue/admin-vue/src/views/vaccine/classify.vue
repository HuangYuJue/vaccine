<template>
  <div class="app-container">
    疫苗分类列表
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="classify.claname" clearable placeholder="类型名称" />
      </el-form-item>
      <el-form-item>
        <el-select v-model="classify.deleted" placeholder="类型状态">
          <el-option v-for="item in options" :key="item.id" v-model:value="item.id" v-text="item.name">
          </el-option>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">
        查询
      </el-button>
      <router-link :to="'/vaccine/editClassify'">
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
      <el-table-column prop="claname" width="400" label="类型名称" />
      <el-table-column prop="deleted" width="400" label="类型状态">
        <template slot-scope="scope">
          {{ scope.row.deleted === 1 ? "上架状态" : "下架状态" }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" prop="deleted">
        <template slot-scope="scope">
          <el-button v-if="scope.row.deleted == 1" type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">下架</el-button>
          <el-button v-else="scope.row.deleted == 1" type="danger" size="mini" icon="el-icon-plus" @click="recoverById(scope.row.id)">上架</el-button>
          <router-link :to="'/vaccine/editClassify/' + scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑</el-button>
          </router-link>
          <router-link :to="'/vaccine/vaccine/' + scope.row.id">
            <el-button type="info" size="mini">查看所属疫苗</el-button>
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
  import classifyApi from "@/api/classify";
  export default {
    data() {
      return {
        current: 1, //当前页
        limit: 6, //每页显示记录数
        classify: {}, //条件封装对象
        list: [], //每页数据集合
        total: 0, //总记录数
        options: [
          {
            id: 0,
            name: "选择状态"
          },
          {
            id: 1,
            name: "已上架"
          },
          {
            id: 2,
            name: "已下架"
          }
        ]
      }
    },
    created() {
      this.getList()
    },
    methods: {
      getList(page = 1) {
        this.current = page
        classifyApi.select(this.limit, this.current, this.classify).then(response => {
          this.list = response.data.records;
          this.total = response.data.total;
          console.log(response.data)
          console.log("name:"+this.classify)
        }) //请求成功
          .catch((error) => {
            console.log(error);
          }); //请求失败
      },
      removeDataById(id) {
        this.$confirm("此操作将下架该类型,是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          classifyApi.delete(id).then(response => {
            this.$message({
              type: "success",
              message: "下架成功！",
            });
            this.getList(this.current);
          })
        })
      },
      recoverById(id) {
        this.$confirm("确定上架操作吗？","提示",{
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          classifyApi.cancel(id).then(response => {
            this.$message({
              type: "success",
              message: "上架成功！"
            })
            this.getList(this.current)
          })
        })
      }
    }
  }
</script>

<style scoped>

</style>
