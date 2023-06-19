<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入订单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="处理进度" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择">
          <el-option
            v-for="item in statusArr"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
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
      <label>提现成功：<span>{{other.success}}元</span></label> ｜
      <label>提现失败：<span>{{other.fail}}元</span></label> ｜
      <label>未处理：<span>{{other.wait}}元</span></label> 
    </div>
    <el-row :gutter="10" class="mb8">
      <!-- <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['business:withdraw:add']"
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
          v-hasPermi="['business:withdraw:edit']"
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
          v-hasPermi="['business:withdraw:remove']"
        >删除</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:withdraw:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="withdrawList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!-- <el-table-column label="id" align="center" prop="id" /> -->
      <el-table-column label="订单号" align="center" prop="orderNo" />
      <el-table-column label="账户" align="center" prop="userName" />
      <el-table-column label="真实姓名" align="center" prop="realName" />
      <el-table-column label="提现金额（¥）" align="center" prop="optAmount" width="120"/>
      <el-table-column label="提现金额（$）" align="center" prop="usdtAmount" width="150"/>
      <el-table-column label="操作前金额" align="center" prop="beforeAmount" />
      <el-table-column label="操作后金额" align="center" prop="afterAmount" />
      <el-table-column label="USDT钱包地址" align="center" prop="walletAddr" width="200" />
      <el-table-column label="银行信息" align="center" prop="bankCardNum" width="300">
        <template slot-scope="scope">
          <div v-if="scope.row.optType === 1">
            <div>账户名称：{{ scope.row.realName }}</div>
            <div>开户银行：{{ scope.row.bankName }}</div>
            <div>银行账号：{{ scope.row.bankCardNum }}</div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="提现类型" align="center" prop="optType" >
        <template slot-scope="scope">
          <span v-if="scope.row.optType === 1">提现到银行卡</span>
          <span v-else-if="scope.row.optType === 2">提现到钱包</span>
          <span v-else-if="scope.row.optType === 0">系统扣款</span>
        </template>
      </el-table-column>
      <el-table-column label="处理进度" align="center" prop="status">
        <template slot-scope="scope">
          <span v-if="scope.row.status === 0">待审核</span>
          <span v-else-if="scope.row.status === 1">到账成功</span>
          <span v-else-if="scope.row.status === 2">审核不通过</span>
        </template>
      </el-table-column>
      <el-table-column label="申请时间" align="center" prop="optTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.optTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作人" align="center" prop="operator" />
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="small"
            type="primary"
            @click="handleCheck(scope.row.id)"
            v-hasPermi="['business:withdraw:check']"
            v-if="scope.row.status === 0"
          >审核</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:withdraw:remove']"
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
        <el-form-item label="订单号" prop="orderNo">
          <el-input v-model="form.orderNo" placeholder="请输入订单号" />
        </el-form-item>
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
        <el-form-item label="钱包地址" prop="walletAddr">
          <el-input v-model="form.walletAddr" placeholder="请输入钱包地址" />
        </el-form-item>
        <el-form-item label="银行卡号" prop="bankCardNum">
          <el-input v-model="form.bankCardNum" placeholder="请输入银行卡号" />
        </el-form-item>
        <el-form-item label="银行名称" prop="bankName">
          <el-input v-model="form.bankName" placeholder="请输入银行名称" />
        </el-form-item>
        <el-form-item label="开户行地址" prop="bankAddr">
          <el-input v-model="form.bankAddr" placeholder="请输入开户行地址" />
        </el-form-item>
        <el-form-item label="申请时间" prop="optTime">
          <el-date-picker clearable
            v-model="form.optTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择申请时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="审核时间" prop="checkTime">
          <el-date-picker clearable
            v-model="form.checkTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择审核时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="操作人" prop="operator">
          <el-input v-model="form.operator" placeholder="请输入操作人" />
        </el-form-item>
        <el-form-item label="上级代理" prop="userAgent">
          <el-input v-model="form.userAgent" placeholder="请输入上级代理" />
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
    <!-- 提现审核 -->
    <el-dialog title="提交审核" :visible.sync="examineOpen" width="500px" append-to-body>
      <el-form ref="examineform" :model="examineForm" :rules="rules" label-width="80px">
        <el-form-item label="审核" prop="status">
          <el-select v-model="examineForm.status" placeholder="请选择审核状态">
            <el-option label="通过" :value="1"></el-option>
            <el-option label="拒绝" :value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="examineForm.remark" placeholder="请输入审核备注描述，没有可不填" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="examineSub">确 定</el-button>
        <el-button @click="examineOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listWithdraw, getWithdraw, delWithdraw, addWithdraw, updateWithdraw, examineWithdraw } from "@/api/business/withdraw";
import { dateFormat} from '@/utils/auth'

export default {
  name: "Withdraw",
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
      withdrawList: [],
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
        remark: [
          { required: true, message: "备注不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "请选择审核状态", trigger: "change" }
        ],
        
      },
      statusArr: [
        { label: '全部', value: ''},
        { label: '待审核', value: 0},
        { label: '提现成功', value: 1},
        { label: '审核不通过', value: 2},
      ],//银行卡状态
      //时间
      dateRange:[],
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
        fail: 0,//失败
        success: 0,//成功
        wait: 0,//未处理
      },//其他数据
      listId: '',//提现审核id
      examineOpen: false,//审核状态
      examineForm: {},//审核提交数据
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
      listWithdraw(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.withdrawList = response.rows;
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
        orderNo: null,
        userName: null,
        optAmount: null,
        beforeAmount: null,
        afterAmount: null,
        walletAddr: null,
        bankCardNum: null,
        bankName: null,
        bankAddr: null,
        optType: null,
        status: null,
        optTime: null,
        checkTime: null,
        operator: null,
        userAgent: null,
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
      getWithdraw(id).then(response => {
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
            updateWithdraw(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWithdraw(this.form).then(response => {
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
        return delWithdraw(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/withdraw/export', {
        ...this.queryParams
      }, `withdraw_${new Date().getTime()}.xlsx`)
    },
    getDefaultTime() {
      let end = new Date();
      let start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
      this.dateRange[0] = dateFormat("YYYY-mm-dd" , start) + ' 00:00:00'
      this.dateRange[1] = dateFormat("YYYY-mm-dd" , end) + ' 23:59:59'
    },
    // 提现审核
    handleCheck(id){
      this.listId = id
      this.examineForm.id = id
      this.examineOpen = true
    },
    examineSub(){
      this.$refs["examineform"].validate(valid => {
        if (valid) {
          examineWithdraw(this.examineForm).then(response => {
            this.$modal.msgSuccess("操作成功");
            this.examineOpen = false;
            this.getList();
          });
        }
      });
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
