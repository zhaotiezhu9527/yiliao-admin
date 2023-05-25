<template>
  <div class="app-container">
    <div class="title">今日报表</div>
    <div>
      <el-row :gutter="12">
        <el-col :span="8">
          <el-card shadow="always" class="margin-top10">
            <div>注册量</div>
            <div>{{ dayReportList.today.registerCount}}</div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="always" class="margin-top10">
            <div>充值量/人数</div>
            <div>{{ dayReportList.today.depositAmount}}元 / {{ dayReportList.today.depositCount}}</div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="always" class="margin-top10">
            <div>投资量/人数</div>
            <div>{{ dayReportList.today.investmentAmount}}元 / {{ dayReportList.today.investmentCount}}</div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="always" class="margin-top10">
            <div>提现量/人数</div>
            <div>{{ dayReportList.today.withdrawAmount}}元 / {{ dayReportList.today.withdrawCount}}</div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="always" class="margin-top10">
            <div>已收益</div>
            <div>{{ dayReportList.today.returnIncome}}元</div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="always" class="margin-top10">
            <div>待收益</div>
            <div>{{ dayReportList.today.waitReturnIncome}}元</div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <!-- <line-chart :data="data" area /> -->
  </div>
</template>

<script>
import { allReport } from "@/api/business/dayReport";
export default {
  name: "DayReport",
  data() {
    return {
      // 遮罩层
      loading: true,
      dayReportList: {},
    };
  },
  mounted (){
    this.initCharts();  
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询日报表列表 */
    getList() {
      this.loading = true;
      allReport({}).then(response => {
        this.dayReportList = response.data;
        this.total = response.total;
        this.loading = false;
      });
    },
    initCharts() {        
        console.log(this.$refs.chart); 
        console.log(this.$echarts)
        // 绘制图表        
        // return      
        let myChart = this.$echarts.init(document.getElementById('chart'));            
        myChart.setOption({        
            title: { text: "在Vue中使用echarts" },        
            tooltip: {},        
            xAxis: {          
                data: ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"],        
            },        
            yAxis: {},        
            series: [          
                {            
                    name: "销量",            
                    type: "bar",            
                    data: [5, 20, 36, 10, 10, 20],          
                },        
            ],      
        });    
    },  
}
};
</script>
<style>
.margin-top10{
  margin-top: 10px;
  text-align: center;
}
.title{
  font-size: 20px;
  width: 100%;
  text-align: center;
}
</style>