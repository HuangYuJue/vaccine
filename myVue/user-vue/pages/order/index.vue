<template>
  <div class="app-container" align="center">
    <br>
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="orderVo.vaccineName" clearable placeholder="疫苗名称" />
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
      style="width: 80%; height: 440px">
      <el-table-column prop="param.vacname" width="150" label="疫苗名称" />
      <el-table-column prop="param.orderStr" width="150" label="预约时间" />
      <el-table-column prop="param.workername" width="150" label="员工名称"/>
      <el-table-column prop="param.addrname" width="150" label="接种点"/>
      <el-table-column prop="cost" width="150" label="费用"/>
      <el-table-column prop="vacstatus" width="150" label="是否接种">
        <template slot-scope="scope">
          {{ scope.row.vacstatus === 1 ? "已接种" : "未接种" }}
        </template>
      </el-table-column>
      <el-table-column prop="cancel" width="150" label="订单状态">
        <template slot-scope="scope">
          {{ scope.row.cancel === 1 ? "已取消" : "未取消" }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" prop="cancel">
        <template slot-scope="scope">
          <el-button v-if="scope.row.cancel == 2 && scope.row.vacstatus == 2" type="danger" size="mini" icon="el-icon-close" @click="cancelById(scope.row.id)">取消</el-button>
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
        limit: 5, //每页显示记录数
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
      cancelById(id){
        this.$confirm("确定取消订单?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          orderApi.cancel(id).then(response => {
            this.$message({
              type: "success",
              message: "操作完成！",
            })
            this.getList(this.current);
          }).catch(() => {
            this.$message.warning("操作失败")
          })
        })
      }
    }
  }
</script>

<style scoped>

</style>
