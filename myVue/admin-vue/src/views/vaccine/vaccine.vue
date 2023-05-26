<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="vaccine.vacname" clearable placeholder="疫苗名称" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="vaccine.vactype" clearable placeholder="使用方式" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="vaccine.vaccount" clearable placeholder="疫苗数量" />
      </el-form-item>
      <el-form-item>
        <el-select v-model="vaccine.deleted" placeholder="疫苗状态">
          <el-option v-for="item in options" :key="item.id" v-model:value="item.id" v-text="item.name">
          </el-option>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">
        查询
      </el-button>
      <router-link :to="'/vaccine/editVac'">
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
      style="width: 130%">
      <el-table-column type="index" width="50" label="序号" />
      <el-table-column prop="vacname" width="130" label="疫苗名称" />
      <el-table-column prop="price" width="130" label="疫苗价格" />
      <el-table-column prop="vactype" width="130" label="服用类型" />
      <el-table-column prop="classifyName" width="130" label="疫苗类型">
      </el-table-column>
      <el-table-column prop="vaccount" width="130" label="剩余数量" />
      <el-table-column prop="introduce" width="130" label="疫苗介绍" :show-overflow-tooltip="true"/>
      <el-table-column prop="deleted" width="130" label="疫苗状态">
        <template slot-scope="scope">
          {{ scope.row.deleted === 1 ? "上架状态" : "下架状态" }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button v-if="scope.row.deleted == 1" type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">下架</el-button>
          <el-button v-else="scope.row.deleted == 1" type="danger" size="mini" icon="el-icon-plus" @click="recoverById(scope.row.id)">上架</el-button>
          <router-link :to="'/vaccine/editVac/' + scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑</el-button>
          </router-link>
          <el-button @click="getDetail(scope.row.id)" type="info" size="mini">查看详情</el-button>
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

    <el-dialog title="查看疫苗详情" :visible.sync="dialogTableVisble" :data="detail">
      <template slot-scope="scope">
        <el-form :model="detail">
          <el-row>
            <el-input v-model="detail.vacname" readonly>
              <template slot="prepend">疫苗名称</template>
            </el-input>
          </el-row>
          <el-row>
            <el-input v-model="detail.price" readonly>
              <template slot="prepend">疫苗价格</template>
            </el-input>
          </el-row>
          <el-row>
            <el-input v-model="detail.vactype" readonly>
              <template slot="prepend">服用类型</template>
            </el-input>
          </el-row>
          <el-row>
            <el-input v-model="detail.classifyName" readonly>
              <template slot="prepend">疫苗类型</template>
            </el-input>
          </el-row>
          <el-row>
            <el-input v-model="detail.vaccount" readonly>
              <template slot="prepend">剩余数量</template>
            </el-input>
          </el-row>
          <el-row>
            <el-input v-model="detail.introduce" readonly>
              <template slot="prepend">疫苗介绍</template>
            </el-input>
          </el-row>
        </el-form>
      </template>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" align="center" @click="$event => dialogTableVisble = false">关闭</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script>
  //引入接口定义的js文件
  import vaccineApi from "@/api/vaccine";
  export default {
    //定义变量和初始值
    data() {
      return {
        current: 1, //当前页
        limit: 6, //每页显示记录数
        vaccine: {}, //条件封装对象
        list: [], //每页数据集合
        total: 0, //总记录数
        dialogTableVisble: false,
        detail: [],//详细信息
        options: [
          {
            id: 0,
            name: "选择状态"
          },
          {
            id: 1,
            name: "上架"
          },
          {
            id: 2,
            name: "下架"
          }
        ]
      };
    },
    created() {
      if (this.$route.params && this.$route.params.id){
        //获取classifyId
        const id = this.$route.params.id
        this.vaccine.classifyId = id
        console.log("id:"+id)
      }
      this.getList();
    },
    methods: {
      getList(page = 1) {
        console.log("classifyId:"+this.vaccine.classifyId)
        this.current = page;
        vaccineApi.select(this.limit, this.current, this.vaccine).then(response => {
            this.list = response.data.records;
            this.total = response.data.total;
            console.log()
          }) //请求成功
          .catch((error) => {
            console.log(error);
          }); //请求失败
      },

      removeDataById(id) {
        this.$confirm("此操作将下架该疫苗,是否继续?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(() => {
          vaccineApi.delete(id).then(response => {
            this.$message({
              type: "success",
              message: "下架成功！",
            });
            //刷新页面
            this.getList(this.current);
          });
        });
      },

      recoverById(id) {
        this.$confirm("确定上架操作吗？","提示",{
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(() => {
          vaccineApi.cancel(id).then(response => {
            this.$message({
              type: "success",
              message: "上架成功！"
            })
            this.getList(this.current)
          })
        })
      },

      getDetail(id){
        vaccineApi.getById(id).then(response => {
          this.detail = response.data
        })
        //点击查看详情：弹出弹框
        this.dialogTableVisble = true
      },

    },
  };
</script>
