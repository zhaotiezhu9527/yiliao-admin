<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="操作账户" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="关联订单号" prop="refNo" label-width="200">
        <el-input
          v-model="queryParams.refNo"
          placeholder="请输入关联订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="金额范围">
        <el-input
          v-model="queryParams.params.minAmount"
          placeholder="请输入最小金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-input
          v-model="queryParams.params.maxAmount"
          placeholder="请输入最大金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="收支类型" prop="status">
        <el-select v-model="queryParams.optType" placeholder="请选择">
          <el-option value="" label="全部"></el-option>
          <el-option value="1" label="系统充值"></el-option>
          <el-option value="2" label="系统扣款"></el-option>
          <el-option value="3" label="提现"></el-option>
          <el-option value="4" label="投资扣款"></el-option>
          <el-option value="5" label="投资回款"></el-option>
          <el-option value="6" label="签到"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="账单类型" prop="status">
        <el-select v-model="queryParams.type" placeholder="请选择">
          <el-option value="" label="全部"></el-option>
          <el-option value="1" label="收入"></el-option>
          <el-option value="2" label="支出"></el-option>
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
      <label>收入：<span>{{other.income}}元</span></label> ｜
      <label>支出：<span>{{other.expenditure}}元</span></label>
    </div>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:account:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="accountList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="id" align="center" prop="id" /> -->
      <el-table-column label="订单号" align="center" prop="accountNo" />
      <el-table-column label="交易对象" align="center" prop="userName" />
      <el-table-column label="交易金额" align="center" prop="optAmount" />
      <el-table-column label="操作前金额" align="center" prop="beforeAmount" />
      <el-table-column label="操作后金额" align="center" prop="afterAmount" />
      <el-table-column label="详情" align="center" prop="remark" />
      <el-table-column label="收支类型" align="center" prop="type" >
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type === 1" type="success">收入</el-tag>
          <el-tag v-else-if="scope.row.type === 2" type="danger">支出</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="类型" align="center" prop="optType" >
        <template slot-scope="scope">
          <span v-if="scope.row.optType === 1" type="success">系统充值</span>
          <span v-else-if="scope.row.optType === 2" type="danger">系统扣款</span>
          <span v-else-if="scope.row.optType === 3" type="danger">提现</span>
          <span v-else-if="scope.row.optType === 4" type="danger">投资扣款</span>
          <span v-else-if="scope.row.optType === 5" type="danger">投资回款</span>
          <span v-else-if="scope.row.optType === 6" type="danger">签到</span>
          <span v-else type="danger">未知类型</span>
        </template>  
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="optTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.optTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="关联订单号" align="center" prop="refNo" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:account:edit']"
          >修改</el-button> -->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:account:remove']"
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

    <!-- 添加或修改【请填写功能名称】对话框 -->
    <!-- <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="操作金额" prop="optAmount">
          <el-input v-model="form.optAmount" placeholder="请输入操作金额" />
        </el-form-item>
        <el-form-item label="操作前金额" prop="beforeAmount">
          <el-input v-model="form.beforeAmount" placeholder="请输入操作前金额" />
        </el-form-item>
        <el-form-item label="操作后金额" prop="afterAmount">
          <el-input v-model="form.afterAmount" placeholder="请输入操作后金额" />
        </el-form-item>
        <el-form-item label="操作时间" prop="optTime">
          <el-date-picker clearable
            v-model="form.optTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择操作时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="上级代理" prop="userAgent">
          <el-input v-model="form.userAgent" placeholder="请输入上级代理" />
        </el-form-item>
        <el-form-item label="关联订单号" prop="refNo">
          <el-input v-model="form.refNo" placeholder="请输入关联订单号" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注" />
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
import { listAccount, getAccount, delAccount, addAccount, updateAccount } from "@/api/business/account";
import { dateFormat} from '@/utils/auth'

export default {
  name: "Account",
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
      accountList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        type: null,
        optType: null,
        refNo: null,
        params:{
          minAmount: '',
          maxAmount: '',
        }
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
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
        expenditure: 0,//支出
        income: 0,//收入
      },//其他数据
    };
  },
  created() {
    // this.getDefaultTime()
    this.getList();
  },
  methods: {
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      listAccount(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.accountList = response.rows;
        this.total = response.total;
        this.loading = false;
        this.other = response.other
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
        userName: null,
        optAmount: null,
        beforeAmount: null,
        afterAmount: null,
        type: null,
        optType: null,
        optTime: null,
        userAgent: null,
        refNo: null,
        remark: null
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
      getAccount(id).then(response => {
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
            updateAccount(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAccount(this.form).then(response => {
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
        return delAccount(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/account/export', {
        ...this.queryParams
      }, `account_${new Date().getTime()}.xlsx`)
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
