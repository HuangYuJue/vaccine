<template>
  <div class="app-container">
    疫苗管理列表
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="orderVo.userName" clearable placeholder="用户名称" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="orderVo.vaccineName" clearable placeholder="疫苗名称" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="orderVo.workerName" clearable placeholder="人员名称" />
      </el-form-item>
      <el-form-item>
        <el-select v-model="orderVo.vacstatus" placeholder="接种状态">
          <el-option v-for="item in options1" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="orderVo.cancel" placeholder="是否取消">
          <el-option v-for="item in options2" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">
        查询
      </el-button>
    </el-form>

    <!-- banner列表 -->
    <el-table
      :data="list"
      tooltip-effect="dark"
      stripe
      align="center"
      style="width: 100%">
      <el-table-column type="index" width="50" label="序号" />
      <el-table-column prop="param.username" width="110" label="用户名称" />
      <el-table-column prop="param.vacname" width="110" label="疫苗名称" />
      <el-table-column prop="param.orderStr" width="110" label="预约时间" />
      <el-table-column prop="param.workername" width="110" label="员工名称"/>
      <el-table-column prop="param.addrname" width="110" label="接种点"/>
      <el-table-column prop="cost" width="110" label="费用"/>
      <el-table-column prop="vacstatus" width="110" label="是否接种">
        <template slot-scope="scope">
          {{ scope.row.vacstatus === 1 ? "已接种" : "未接种" }}
        </template>
      </el-table-column>
      <el-table-column prop="cancel" width="110" label="订单状态">
        <template slot-scope="scope">
          {{ scope.row.cancel === 1 ? "已取消" : "未取消" }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" prop="vacstatus">
        <template slot-scope="scope">
          <el-button v-if="scope.row.vacstatus == 2" type="primary" size="mini" icon="el-icon-check" @click="vastatusById(scope.row.id)">接种</el-button>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
          <router-link :to="'/order/detail/' + scope.row.id">
            <el-button type="info" size="mini">查看详情</el-button>
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
  import orderApi from "@/api/order";
  export default {
    data() {
      return {
        current: 1, //当前页
        limit: 6, //每页显示记录数
        orderVo: {}, //条件封装对象
        list: [], //每页数据集合
        total: 0, //总记录数
        options1: [{
          id: 0,
          name: "接种状态"
        },
          {
          id: 1,
          name: "已接种"
        },
          {
            id: 2,
            name: "未接种"
          }],
        options2: [{
          id: 0,
          name: "是否取消"
        },
          {
          id: 1,
          name: "已取消"
        },
          {
            id: 2,
            name: "未取消"
          }]
      }
    },
    created() {
      if (this.$route.params && this.$route.params.id){
        const id = this.$route.params.id
        this.order.addressId = id
      }
      this.getList()
    },
    methods: {
      getList(page = 1) {
        this.current = page
        orderApi.select(this.limit, this.current, this.orderVo).then(response => {
          this.list = response.data.records;
          this.total = response.data.total;
        }) //请求成功
          .catch((error) => {
            console.log(error);
          }); //请求失败
      },
      vastatusById(id){
        this.$confirm("确定已接种?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          orderApi.vastatusById(id).then(response => {
            this.$message({
              type: "success",
              message: "操作完成！",
            });
            this.getList(this.current);
          })
        })
      },
      removeDataById(id) {
        this.$confirm("此操作将永久删除该人员,是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          orderApi.delete(id).then(response => {
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
