<template>
  <div class="app-container">
    <div id="main">
      <myheader/>
      <div class="main-container">
        <el-scrollbar class="page-component__scroll">
          <nuxt/>
        </el-scrollbar>
      </div>
      <myfooter/>
    </div>
  </div>
</template>
<script>
  import "~/assets/css/main.css"
  import "~/assets/css/app.css"
  import myheader from "@/layouts/myheader";
  import myfooter from "@/layouts/myfooter";
  export default {
    components: {
      myheader, myfooter
    }
  }
</script>
