<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="日期" prop="today">
        <el-input
          v-model="queryParams.today"
          placeholder="请输入日期"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="充值金额" prop="depositAmount">
        <el-input
          v-model="queryParams.depositAmount"
          placeholder="请输入充值金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="提现金额" prop="withdrawAmount">
        <el-input
          v-model="queryParams.withdrawAmount"
          placeholder="请输入提现金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="投资金额" prop="investmentAmount">
        <el-input
          v-model="queryParams.investmentAmount"
          placeholder="请输入投资金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收益金额" prop="incomeAmount">
        <el-input
          v-model="queryParams.incomeAmount"
          placeholder="请输入收益金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="注册人数" prop="registerCount">
        <el-input
          v-model="queryParams.registerCount"
          placeholder="请输入注册人数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="充值人数" prop="depositCount">
        <el-input
          v-model="queryParams.depositCount"
          placeholder="请输入充值人数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="提现人数" prop="withdrawCount">
        <el-input
          v-model="queryParams.withdrawCount"
          placeholder="请输入提现人数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="投资人数" prop="investmentCount">
        <el-input
          v-model="queryParams.investmentCount"
          placeholder="请输入投资人数"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['business:dayReport:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['business:dayReport:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['business:dayReport:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:dayReport:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dayReportList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="日期" align="center" prop="today" />
      <el-table-column label="充值金额" align="center" prop="depositAmount" />
      <el-table-column label="提现金额" align="center" prop="withdrawAmount" />
      <el-table-column label="投资金额" align="center" prop="investmentAmount" />
      <el-table-column label="收益金额" align="center" prop="incomeAmount" />
      <el-table-column label="注册人数" align="center" prop="registerCount" />
      <el-table-column label="充值人数" align="center" prop="depositCount" />
      <el-table-column label="提现人数" align="center" prop="withdrawCount" />
      <el-table-column label="投资人数" align="center" prop="investmentCount" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:dayReport:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:dayReport:remove']"
          >删除</el-button>
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

    <!-- 添加或修改日报表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="日期" prop="today">
          <el-input v-model="form.today" placeholder="请输入日期" />
        </el-form-item>
        <el-form-item label="充值金额" prop="depositAmount">
          <el-input v-model="form.depositAmount" placeholder="请输入充值金额" />
        </el-form-item>
        <el-form-item label="提现金额" prop="withdrawAmount">
          <el-input v-model="form.withdrawAmount" placeholder="请输入提现金额" />
        </el-form-item>
        <el-form-item label="投资金额" prop="investmentAmount">
          <el-input v-model="form.investmentAmount" placeholder="请输入投资金额" />
        </el-form-item>
        <el-form-item label="收益金额" prop="incomeAmount">
          <el-input v-model="form.incomeAmount" placeholder="请输入收益金额" />
        </el-form-item>
        <el-form-item label="注册人数" prop="registerCount">
          <el-input v-model="form.registerCount" placeholder="请输入注册人数" />
        </el-form-item>
        <el-form-item label="充值人数" prop="depositCount">
          <el-input v-model="form.depositCount" placeholder="请输入充值人数" />
        </el-form-item>
        <el-form-item label="提现人数" prop="withdrawCount">
          <el-input v-model="form.withdrawCount" placeholder="请输入提现人数" />
        </el-form-item>
        <el-form-item label="投资人数" prop="investmentCount">
          <el-input v-model="form.investmentCount" placeholder="请输入投资人数" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDayReport, getDayReport, delDayReport, addDayReport, updateDayReport } from "@/api/business/dayReport";

export default {
  name: "DayReport",
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
      // 日报表表格数据
      dayReportList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        today: null,
        depositAmount: null,
        withdrawAmount: null,
        investmentAmount: null,
        incomeAmount: null,
        registerCount: null,
        depositCount: null,
        withdrawCount: null,
        investmentCount: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        today: [
          { required: true, message: "日期不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询日报表列表 */
    getList() {
      this.loading = true;
      listDayReport(this.queryParams).then(response => {
        this.dayReportList = response.rows;
        this.total = response.total;
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
        today: null,
        depositAmount: null,
        withdrawAmount: null,
        investmentAmount: null,
        incomeAmount: null,
        registerCount: null,
        depositCount: null,
        withdrawCount: null,
        investmentCount: null
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
      this.title = "添加日报表";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getDayReport(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改日报表";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateDayReport(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDayReport(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除日报表编号为"' + ids + '"的数据项？').then(function() {
        return delDayReport(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/dayReport/export', {
        ...this.queryParams
      }, `dayReport_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
