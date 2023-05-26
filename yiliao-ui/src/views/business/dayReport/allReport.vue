<template>
  <div class="app-container">
    <div class="title">今日报表</div>
    <el-button type="success" @click="getList" :loading="loading">刷新数据</el-button>
    <div>
      <el-row :gutter="12">
        <el-col :span="8">
          <el-card shadow="always" class="margin-top10">
            <div>注册量</div>
            <div>{{ dayReportList.today.registerCount}} 人</div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="always" class="margin-top10">
            <div>充值量 / 人数</div>
            <div>{{ dayReportList.today.depositAmount}} 元 / {{ dayReportList.today.depositCount}} 人</div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="always" class="margin-top10">
            <div>投资量 / 人数</div>
            <div>{{ dayReportList.today.investmentAmount}} 元 / {{ dayReportList.today.investmentCount}} 人</div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="always" class="margin-top10">
            <div>提现量 / 人数</div>
            <div>{{ dayReportList.today.withdrawAmount}} 元 / {{ dayReportList.today.withdrawCount}} 人</div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="always" class="margin-top10">
            <div>已收益</div>
            <div>{{ dayReportList.today.returnIncome}} 元</div>
          </el-card>
        </el-col>
        <el-col :span="8">
          <el-card shadow="always" class="margin-top10">
            <div>待收益</div>
            <div>{{ dayReportList.today.waitReturnIncome}} 元</div>
          </el-card>
        </el-col>
      </el-row>
    </div>
    <div class="margin10">
      <div ref="chongzhi" style="height: 420px" />
    </div>
    <div>
      <div ref="zhuce" style="height: 420px" />
    </div>
  </div>
</template>

<script>
import { allReport } from "@/api/business/dayReport";
import * as echarts from "echarts";
export default {
  name: "DayReport",
  data() {
    return {
      // 遮罩层
      loading: true,
      dayReportList: {
        today: {
          registerCount:0,
          depositAmount:0,
          depositCount:0,
          investmentAmount:0,
          investmentCount:0,
          withdrawAmount:0,
          withdrawCount:0,
          waitReturnIncome:0,
        },
      },
      // 统计命令信息
      czstats: null,
      zcstats: null,
      historyData:[],//历史数据
      registerCountArr:[],//注册数据
      depositAmountArr:[],//充值数据
      investmentAmountArr: [],//投注数据
      withdrawAmountArr: [],//提现数据
      depositCountArr: [],//充值人数数据
      investmentCountArr: [],//投资人数数据
      withdrawCountArr: [],//提现人数数据
      dateArr: [],
      option:  {
          title: {
            text: '今日报表'
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['注册量', '充值量']
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          toolbox: {
            feature: {
              saveAsImage: {}
            }
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: [0,0,0]
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              name: '注册量',
              type: 'line',
              stack: 'Total',
              data: [0,0]
            },
            {
              name: '充值量',
              type: 'line',
              stack: 'Total',
              data: [0,0]
            },
          ]
      }
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
      this.registerCountArr = []
      this.depositAmountArr = []
      this.dateArr = []
      this.investmentAmountArr = []
      this.withdrawAmountArr = []
      this.depositCountArr = []
      this.investmentCountArr = []
      this.withdrawCountArr = []
      allReport({}).then(response => {
        
        this.dayReportList = response.data;
        this.total = response.total;
        this.loading = false;
        this.historyData = response.data.history
        this.historyData.forEach((item,index) => {
          this.investmentAmountArr.push(item.investmentAmount)
          this.withdrawAmountArr.push(item.withdrawAmount)
          this.registerCountArr.push(item.registerCount)
          this.depositAmountArr.push(item.depositAmount)
          this.depositCountArr.push(item.depositCount)
          this.investmentCountArr.push(item.investmentCount)
          this.withdrawCountArr.push(item.withdrawCount)
          this.dateArr.push(item.today)
        });
        this.initCharts(this.dateArr,this.depositAmountArr,this.investmentAmountArr,this.withdrawAmountArr)
        this.initCharts2(this.dateArr,this.registerCountArr,this.depositCountArr,this.investmentCountArr,this.withdrawCountArr)
      });
    },
    initCharts(riqi,chongzhi,touzhu,tixian) {        
      this.czstats = echarts.init(this.$refs.chongzhi, "macarons");
      let option = {
          title: {
            text: '金额报表'
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['充值金额','投资金额','提现金额']
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          toolbox: {
            feature: {
              saveAsImage: {}
            }
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: riqi
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              name: '充值金额',
              type: 'line',
              // stack: 'Total',
              data: chongzhi
            },
            {
              name: '投资金额',
              type: 'line',
              // stack: 'Total',
              data: touzhu
            },
            {
              name: '提现金额',
              type: 'line',
              // stack: 'Total',
              data: tixian
            }
          ]
      }
      this.czstats.setOption(option)
    },
    initCharts2(riqi,zhuce,chongzhi,touzhu,tixian) {        
      this.zcstats = echarts.init(this.$refs.zhuce, "macarons");
      let option = {
          title: {
            text: '人数报表'
          },
          tooltip: {
            trigger: 'axis'
          },
          legend: {
            data: ['注册人数','充值人数','投资人数','提现人数']
          },
          grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
          },
          toolbox: {
            feature: {
              saveAsImage: {}
            }
          },
          xAxis: {
            type: 'category',
            boundaryGap: false,
            data: riqi
          },
          yAxis: {
            type: 'value'
          },
          series: [
            {
              name: '注册人数',
              type: 'line',
              data: zhuce
            },
            {
              name: '充值人数',
              type: 'line',
              data: chongzhi
            },
            {
              name: '投资人数',
              type: 'line',
              data: touzhu
            },
            {
              name: '提现人数',
              type: 'line',
              data: tixian
            }
          ]
      }
      this.zcstats.setOption(option)
    },
    changeOption(){

    } 
  },
  components:{
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
.margin10{
  margin: 20px;
}
</style>