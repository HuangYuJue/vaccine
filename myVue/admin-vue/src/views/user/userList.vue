<template>
  <div class="app-container">
    用户管理列表
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="searchObj.username" clearable placeholder="用户名称" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchObj.phone" clearable placeholder="用户手机号" />
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">
        查询
      </el-button>
    </el-form>

    <div align="left">
      <el-button type="danger" size="mini" @click="removeRows()">批量删除</el-button>
    </div>

    <!-- banner列表 -->
    <el-table
      :data="list"
      tooltip-effect="dark"
      stripe
      align="center"
      style="width: 100%"
      @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column type="index" width="50" label="序号" />
      <el-table-column prop="phone" width="220" label="用户手机" />
      <el-table-column prop="username" width="220" label="用户名称" />
      <el-table-column prop="sex" width="220" label="用户性别">
        <template slot-scope="scope">
          {{ scope.row.sex === 1 ? "女" : "男" }}
        </template>
      </el-table-column>
      <el-table-column prop="age" width="250" label="年龄" />
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>

        <router-link :to="'/user/edit/' + scope.row.id">
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
  //引入接口定义的js文件
  import userApi from "@/api/user";
  export default {
    //定义变量和初始值
    data() {
      return {
        current: 1, //当前页
        limit: 6, //每页显示记录数
        searchObj: {}, //条件封装对象
        list: [], //每页数据集合
        total: 0, //总记录数
        multipleSelection: [], //批量选择中所选择的记录列表
      };
    },
    created() {
      this.getList();
    },
    methods: {
      //获取用户列表
      getList(page = 1) {
        this.current = page;
        userApi.findAllUser(this.limit, this.current, this.searchObj).then(response => {
            this.list = response.data.records;
            this.total = response.data.total;
          }) //请求成功
          .catch((error) => {
            console.log(error);
          }); //请求失败
      },

      //删除医院设置的方法
      removeDataById(id) {
        this.$confirm("此操作将永久删除该用户,是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          userApi.deleteUser(id).then(response => {
            this.$message({
              type: "success",
              message: "删除成功！",
            });
            //刷新页面
            this.getList();
          });
        });
      },

      //获取选择复选框的id值
      handleSelectionChange(selection) {
        this.multipleSelection = selection;
      },

      //批量删除
      removeRows(ids) {
        this.$confirm("此操作将永久删除用户信息,是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          var ids = [];
          for (var i = 0; i < this.multipleSelection.length; i++) {
            var obj = this.multipleSelection[i];
            var id = obj.id;
            ids.push(id);
          }
          userApi.batchRemove(ids).then(response => {
            //提示
            this.$message({
              type: "success",
              message: "删除成功！",
            });
            //刷新页面
            this.getList();
          });
        });
      },
    },
  };
</script>
