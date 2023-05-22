<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="会员账号" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入会员账号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="项目名" prop="projectName">
        <el-input
          v-model="queryParams.projectName"
          placeholder="请输入项目名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择">
          <el-option value="" label="全部"></el-option>
          <el-option value="0" label="未结算"></el-option>
          <el-option value="1" label="已结算"></el-option>
          <el-option value="2" label="结算异常"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="操作时间" prop="optTime">
        <el-date-picker
          v-model="dateRange"
          style="width: 340px"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="datetimerange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :picker-options="pickerOptions"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 其他数据 -->
    <div class="details">
      <label>已收益（总）：<span>{{other.success}}元</span></label> ｜
      <label>未收益（总）：<span>{{other.wait}}元</span></label> 
    </div>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:order:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="订单号" align="center" prop="orderNo" />
      <el-table-column label="项目名" align="center" prop="projectName" />
      <el-table-column align="center" prop="startTime" width="180">
        <template slot="header">
          <div>姓名</div>
          <div>投资人用户名</div>
        </template>
        <template slot-scope="scope">
          <div>{{ scope.row.realName }}</div>
          <div>{{ scope.row.userName }}</div>
        </template>
      </el-table-column>
      <el-table-column label="投资金额" align="center" prop="amount" />
      <!-- <el-table-column label="项目ID" align="center" prop="projectId" /> -->
      <el-table-column label="收益率(%)" align="center" prop="incomeRate" />
      <el-table-column label="期限(分钟)" align="center" prop="limitTime" />
      <el-table-column align="center" prop="startTime" width="180">
        <template slot="header">
          <div>开始时间</div>
          <div>结束时间</div>
        </template>
        <template slot-scope="scope">
          <div>{{ parseTime(scope.row.startTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</div>
          <div>{{ parseTime(scope.row.endTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="forecastReturnTime" width="180">
        <template slot="header">
          <div>预计返款时间</div>
          <div>实际返款时间</div>
        </template>
        <template slot-scope="scope">
          <div>{{ parseTime(scope.row.forecastReturnTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</div>
          <div>{{ parseTime(scope.row.actualReturnTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</div>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="forecastReturnAmount" width="150">
        <template slot="header">
          <div>预计返款金额</div>
          <div>实际返款金额</div>
        </template>
        <template slot-scope="scope">
          <div>{{ scope.row.forecastReturnAmount + scope.row.amount}}</div>
          <div>{{ scope.row.actualReturnAmount + scope.row.amount }}</div>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" type="danger">未结算</el-tag>
          <el-tag v-else-if="scope.row.status === 1" type="success">已结算</el-tag>
          <el-tag v-else-if="scope.row.status === 2" type="danger">结算异常</el-tag>
        </template>  
      </el-table-column>
      <el-table-column label="订单时间" align="center" prop="orderTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.orderTime, '{y}-{m}-{d}  {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:order:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:order:remove']"
          >删除</el-button> -->
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改【请填写功能名称】对话框 -->
    <!-- <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入订单号" />
        </el-form-item>
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="项目ID" prop="projectId">
          <el-input v-model="form.projectId" placeholder="请输入项目ID" />
        </el-form-item>
        <el-form-item label="项目名" prop="projectName">
          <el-input v-model="form.projectName" placeholder="请输入项目名" />
        </el-form-item>
        <el-form-item label="真实姓名" prop="realName">
          <el-input v-model="form.realName" placeholder="请输入真实姓名" />
        </el-form-item>
        <el-form-item label="投资金额" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入投资金额" />
        </el-form-item>
        <el-form-item label="收益率" prop="incomeRate">
          <el-input v-model="form.incomeRate" placeholder="请输入收益率" />
        </el-form-item>
        <el-form-item label="期限(分钟)" prop="limitTime">
          <el-input v-model="form.limitTime" placeholder="请输入期限(分钟)" />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker clearable
            v-model="form.startTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker clearable
            v-model="form.endTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择结束时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="预计返款时间" prop="forecastReturnTime">
          <el-date-picker clearable
            v-model="form.forecastReturnTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择预计返款时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="实际返款时间" prop="actualReturnTime">
          <el-date-picker clearable
            v-model="form.actualReturnTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择实际返款时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="预计返款利息" prop="forecastReturnAmount">
          <el-input v-model="form.forecastReturnAmount" placeholder="请输入预计返款利息" />
        </el-form-item>
        <el-form-item label="实际返款利息" prop="actualReturnAmount">
          <el-input v-model="form.actualReturnAmount" placeholder="请输入实际返款利息" />
        </el-form-item>
        <el-form-item label="上级代理" prop="userAgent">
          <el-input v-model="form.userAgent" placeholder="请输入上级代理" />
        </el-form-item>
        <el-form-item label="订单时间" prop="orderTime">
          <el-date-picker clearable
            v-model="form.orderTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择订单时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog> -->
  </div>
</template>

<script>
import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/business/order";
import { dateFormat} from '@/utils/auth'

export default {
  name: "Order",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 【请填写功能名称】表格数据
      orderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: null,
        userName: null,
        projectName: null,
        status: "",
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderNo: [
          { required: true, message: "订单号不能为空", trigger: "blur" }
        ],
        userName: [
          { required: true, message: "用户名不能为空", trigger: "blur" }
        ],
      },
      // 时间
      dateRange: [],
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      other: {
        success: 0,//已收益
        wait: 0,//未收益
      },//其他数据
    };
  },
  created() {
    this.getDefaultTime()
    this.getList();
  },
  methods: {
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      listOrder(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.orderList = response.rows;
        this.total = response.total;
        this.other = response.other
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        orderNo: null,
        userName: null,
        projectId: null,
        projectName: null,
        realName: null,
        amount: null,
        incomeRate: null,
        limitTime: null,
        startTime: null,
        endTime: null,
        forecastReturnTime: null,
        actualReturnTime: null,
        forecastReturnAmount: null,
        actualReturnAmount: null,
        status: null,
        userAgent: null,
        orderTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加【请填写功能名称】";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getOrder(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改【请填写功能名称】";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrder(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除【请填写功能名称】编号为"' + ids + '"的数据项？').then(function() {
        return delOrder(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    },
    getDefaultTime() {
      let end = new Date();
      let start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
      this.dateRange[0] = dateFormat("YYYY-mm-dd" , start) + ' 00:00:00'
      this.dateRange[1] = dateFormat("YYYY-mm-dd" , end) + ' 23:59:59'
    }
  }
};
</script>
<style scoped>
.details{
  margin-bottom: 20px;
}
.details span{
  color: red;
}
</style>
