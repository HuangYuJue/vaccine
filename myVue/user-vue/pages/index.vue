<template>
  <div class="home page-component">

    <!-- search -->
    <div class="search-container">
      <div class="search-wrapper">
        <div class="vaccine-search">
          <el-autocomplete class="search-input" prefix-icon="el-icon-search"
                           v-model="claname" :fetch-suggestions="querySearchAsync" placeholder="点击输入疫苗类型名称"
                           @select="handleSelect">
          </el-autocomplete>
        </div>
      </div>
    </div>

    <el-carousel trigger="click" height="450px" arrow="always" indicator-position="outside">
      <el-carousel-item v-for="item in imgList" :key="item.name">
        <img :src="item.src" style="height: 100%;width: 100%" :title="item.title">
      </el-carousel-item>
    </el-carousel>

  </div>
</template>

<script>
  import classifyApi from "@/api/classify";
export default {
  components: {},
  data() {
    return {
      claname: '',
      imgList: [{name: 'item1', src: require("@/assets/images/item1.png"), title: "这是item1"},
        {name: 'item2', src: require("@/assets/images/item2.png"), title: "这是item2"}]
    }
  },
  methods: {
    //输入值弹出下拉框
    querySearchAsync(queryString, cb){
      queryString = queryString.trim()
      if (queryString == ''){
        cb([]);
        return;
      }
      classifyApi.findByName(queryString).then(responce => {
        for (let i = 0;i < responce.data.length;i++){
          responce.data[i].value = responce.data[i].claname
          console.log("claname:"+responce.data[i].value)
        }
        cb(responce.data)
      })
    },

    //选择下拉框内容跳转
    handleSelect(item){
      window.location.href = '/vaccine/classify/' + item.id
    }
  }
}
</script>
