<template>
  <div class="app-container" align="center" style="height: 600px; width: 80%;margin-left: 10%">
    <br>
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="vaccine.vacname" clearable placeholder="疫苗名称" />
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
      <el-table-column prop="vacname" width="170" label="疫苗名称" />
      <el-table-column prop="price" width="170" label="疫苗价格" />
      <el-table-column prop="vactype" width="170" label="服用类型" />
      <el-table-column prop="vaccount" width="170" label="剩余数量" />
      <el-table-column prop="introduce" width="170" label="疫苗介绍" :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button v-if="scope.row.vaccount > 0" type="primary" size="mini" icon="el-icon-plus" @click="addOrder(scope.row.id)">预约</el-button>
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

    <el-dialog title="查看疫苗详情" :visible.sync="dialogTableVisble1" :data="detail">
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
        <el-button type="primary" align="center" @click="$event => dialogTableVisble1 = false">关闭</el-button>
      </div>
    </el-dialog>

    <el-dialog title="预约疫苗" :visible.sync="dialogTableVisble2">
      <el-form>
        <el-form-item label="选择接种地点">
          <el-select v-model="addressId" filterable style="width: 600px">
            <el-option v-for="item in addressList" :key="item.id" :label="item.addrname" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="checkWorker()">
            确定
          </el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-dialog title="预约疫苗" :visible.sync="dialogTableVisble3">
      <el-form>
        <el-form-item label="选择工作人员">
          <el-select v-model="workerId" filterable style="width: 600px">
            <el-option v-for="item in workerList" :key="item.id" :label="item.workername" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="add()">
            确定预约
          </el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>
<script>
  //引入接口定义的js文件
  import vaccineApi from "@/api/vaccine";
  import classifyApi from "@/api/classify";
  import orderApi from "@/api/order";
  export default {
    //定义变量和初始值
    data() {
      return {
        order: {},
        current: 1, //当前页
        limit: 6, //每页显示记录数
        vaccine: {}, //条件封装对象
        list: [], //每页数据集合
        total: 0, //总记录数
        dialogTableVisble1: false,
        dialogTableVisble2: false,
        dialogTableVisble3: false,
        addressList: [],
        workerList: [],
        addressId: 0,
        workerId: 0,
        detail: [],//详细信息
        classify: {},//查询出classify的详细信息便于查询其是否下架
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
      //获取classifyId
      const id = this.$route.params.id
      this.vaccine.classifyId = id
      this.classify.id = id
      console.log("id:"+id)
      //判断疫苗类型是否下架
      classifyApi.getClassify(id).then(response => {
        this.classify = response.data
        console.log("classify是否下架："+this.classify.deleted)
        if (this.classify.deleted == 1){
          this.getList();
        } else if (this.classify.deleted == 2){
          this.$message.info("该类型疫苗已下架")
        }
      })
    },
    methods: {
      getList(page = 1) {
        console.log("classifyId:"+this.vaccine.classifyId)
        this.current = page
        vaccineApi.select(this.limit, this.current, this.vaccine.classifyId, this.vaccine.vacname).then(response => {
          this.list = response.data.records;
          this.total = response.data.total;
          console.log()
        }) //请求成功
          .catch((error) => {
            console.log(error);
          }); //请求失败
      },

      getDetail(id){
        vaccineApi.getDetail(id).then(response => {
          this.detail = response.data
        })
        //点击查看详情：弹出弹框
        this.dialogTableVisble1 = true
      },

      getAddress(){
        classifyApi.getAddress().then(response => {
          this.addressList = response.data
        })
      },

      getWorkers() {
        classifyApi.getWorkers(addressId).then(response => {
          this.workerList = response.data
        })
      },

      checkWorker(){
        if (this.addressId != 0){
          classifyApi.getWorkers(this.addressId).then(response => {
            this.workerList = response.data
          })
          this.dialogTableVisble3 = true
        }
      },

      addOrder(vaccineId){
        this.vaccine.id = vaccineId
        this.getAddress()
        this.dialogTableVisble2 = true
      },

      add(){
        this.order.vaccineId = this.vaccine.id
        this.order.workerId = this.workerId
        orderApi.addOrder(this.order).then(() => {
          this.$message.success("预约成功")
        })
        //预约成功之后更新疫苗数量
        location.reload()
        this.dialogTableVisble3 = false
        this.dialogTableVisble2 = false
      }
    },
  };
</script>
