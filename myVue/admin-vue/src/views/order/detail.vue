<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="用户名称">
        <el-input v-model="order.param.username" readonly style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="疫苗名称">
        <el-input v-model="order.param.vacname" readonly style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="预约时间">
        <el-input v-model="order.param.orderStr" readonly style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="员工名称">
        <el-input v-model="order.param.workername" readonly style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="接种地点">
        <el-input v-model="order.param.addrname" readonly style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="疫苗费用">
        <el-input v-model="order.cost" readonly style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="是否接种">
        <el-input v-if="order.vacstatus === 1" value="已接种" readonly style="width: 1100px"/>
        <el-input v-else="order.vacstatus === 1" value="未接种" readonly style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="接种时间">
        <el-input v-model="order.param.vacStr" readonly style="width: 1100px"/>
      </el-form-item>
      <el-form-item label="是否取消">
        <el-input v-if="order.cancel === 1" value="已取消" readonly style="width: 1100px"/>
        <el-input v-else="order.cancel === 1" value="未取消" readonly style="width: 1100px"/>
      </el-form-item>
      <el-form-item>
        <router-link :to="'/order/order'">
          <el-button type="primary" @click="updateOrder">返回</el-button>
        </router-link>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import orderApi from "@/api/order"
  export default {
    data() {
      return {
        order: {},
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
        orderApi.getById(id).then(responce => {
          this.order = responce.data
        })
      }
    }
  }
</script>

<style scoped>

</style>
