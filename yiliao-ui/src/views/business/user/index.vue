<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <!-- <el-form-item label="推荐人" prop="userAgent">
        <el-input
          v-model="queryParams.userAgent"
          placeholder="请输入推荐人用户名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item> -->
      <el-form-item label="用户姓名" prop="realName">
        <el-input
          v-model="queryParams.realName"
          placeholder="请输入用户姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="userStatus">
        <el-select v-model="queryParams.userStatus" placeholder="请选择">
          <el-option
            v-for="item in statusArr"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="注册时间" prop="registerTime">
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

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['business:user:add']"
        >新增会员</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          size="mini"
          @click="handleBalance"
          v-hasPermi="['business:user:optMoney']"
        >增减余额</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['business:user:edit']"
        >修改</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:user:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用户ID" align="center" prop="id" />
      <el-table-column label="邀请码" align="center" prop="inviteCode" />
      <el-table-column label="用户名" align="center" prop="userName" />
      <el-table-column label="姓名" align="center" prop="realName" />
      <el-table-column label="账户余额" align="center" prop="balance" />
      <el-table-column label="用户详情" align="center" prop="userName" width="150">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="success"
            @click="handleDetail(scope.row)"
            v-hasPermi="['business:user:query']"
          >用户详情</el-button>
        </template>
      </el-table-column>
      <el-table-column label="USDT钱包地址" align="center" prop="walletAddr" width="200"/>
      <el-table-column label="银行信息" align="center" prop="bankName" width="300">
        <template slot-scope="scope">
          <div>{{scope.row.bankName}}</div>
          <div>{{scope.row.bankAddr}}</div>
          <div>{{scope.row.bankCardNum}}</div>
        </template>
      </el-table-column>
      <!-- <el-table-column label="支行信息" align="center" prop="bankAddr" />
      <el-table-column label="银行卡号" align="center" prop="bankCardNum" /> -->
      <el-table-column label="状态" align="center" prop="userStatus" width="200">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.userStatus"
            @change="changeStatus(scope.row.id,scope.row.userStatus)"
            :active-value="0"
            :inactive-value="1"
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="会员等级" align="center" prop="userLevelId" >
        <template slot-scope="scope">
          <span>{{scope.row.userLevelId === 0 ? '普通会员' : '未知等级' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="推荐人用户名" align="center" prop="userAgent" />
      
      <el-table-column align="center" width="180">
        <template slot="header">
          <div>注册时间</div>
          <div>上次登陆时间</div>
        </template>
        <template slot-scope="scope">
          <div>{{ scope.row.registerTime }}</div>
          <div>{{ scope.row.lastTime }}</div>
        </template>
      </el-table-column>
      <el-table-column align="center" width="180">
        <template slot="header">
          <div>注册IP</div>
          <div>上次登陆IP</div>
        </template>
        <template slot-scope="scope">
          <div>{{ scope.row.registerIp }}</div>
          <div>{{ scope.row.lastIp }}</div>
        </template>
      </el-table-column>
      <!-- <el-table-column label="最后修改时间" align="center" prop="modifyTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.modifyTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column> -->
      <el-table-column label="会员关联" align="center" width="300px">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="success"
            @click="queryChange('shangji',scope.row.id)"
          >上级用户</el-button>
          <el-button
            size="mini"
            type="success"
            @click="queryChange('xiaji',scope.row.id)"
          >下级用户</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" fixed="right" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:user:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="balanceUpdate(scope.row)"
            v-hasPermi="['business:user:optMoney']"
          >余额重置</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:user:remove']"
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
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="用户名" prop="userName">
          <el-input :disabled="true" v-model="form.userName" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="真实姓名" prop="realName">
          <el-input v-model="form.realName" placeholder="请输入真实姓名" />
        </el-form-item>
        <el-form-item label="身份证号码" prop="idCard">
          <el-input v-model="form.idCard" placeholder="请输入身份证号码" />
        </el-form-item>
        <el-form-item label="登录密码">
          <el-input v-model="form.loginPwd" placeholder="不输入表示不修改" />
        </el-form-item>
        <el-form-item label="支付密码">
          <el-input v-model="form.payPwd" placeholder="不输入表示不修改支付密码" />
        </el-form-item>
        <!-- <el-form-item label="邀请码" prop="inviteCode">
          <el-input v-model="form.inviteCode" placeholder="请输入邀请码" />
        </el-form-item> -->
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
        <!-- <el-form-item label="是否实名" prop="isRealName">
          <el-select v-model="form.isRealName" placeholder="请选择是否实名">
            <el-option label="已实名" :value="0"></el-option>
            <el-option label="未实名" :value="1"></el-option>
          </el-select>
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 添加对话框 -->
    <el-dialog title="添加会员" :visible.sync="addOpen" width="500px" append-to-body>
      <el-form ref="addform" :model="addForm" :rules="rules" label-width="80px">
        <el-form-item label="推荐人" prop="userAgent">
          <el-input v-model="addForm.userAgent" placeholder="请输入推荐人用户名" />
        </el-form-item>
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="addForm.userName" placeholder="请输入4-12位数字或字母" />
        </el-form-item>
        <el-form-item label="密码" prop="loginPwd">
          <el-input v-model="addForm.loginPwd" placeholder="请输入6-12位数字或字母" />
        </el-form-item>
        <el-form-item label="支付密码" prop="payPwd">
          <el-input v-model="addForm.payPwd" placeholder="请输入6位数字" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="addSub">确 定</el-button>
        <el-button @click="addCancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 增减余额对话框 -->
    <el-dialog title="增减余额" :close-on-click-modal="false" :visible.sync="balanceOpen" width="500px" append-to-body>
      <el-form ref="balanceform" :model="balanceForm" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="balanceForm.userName" placeholder="请输入4-12位数字或字母" />
        </el-form-item>
        <el-form-item label="金额" prop="money">
          <el-input v-model="balanceForm.money" placeholder="请输入金额" />
        </el-form-item>
        <el-form-item label="增减类型" prop="type">
          <el-select v-model="balanceForm.type" placeholder="请选择上下分类型">
            <el-option label="上分" :value="1"></el-option>
            <el-option label="下分" :value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="货币类型" prop="type">
          <el-select v-model="balanceForm.amountType" placeholder="请选择货币类型">
            <el-option label="人民币" :value="1"></el-option>
            <el-option label="USDT" :value="2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="修改理由" prop="remark">
          <el-input v-model="balanceForm.remark" placeholder="请填写备注" />
          <el-tag class="reasonClass" type="success" v-for="(item,index) in reasonList" @click="changeReason(item)" :key="index">{{ item }}</el-tag>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="balanceSub">确 定</el-button>
        <el-button @click="balanceCancel">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 上下级用户弹窗 -->
    <el-dialog title="详情" :visible.sync="detailsOpen" width="1200px" append-to-body>
      <el-table :data="detailList">
        <el-table-column label="用户ID" align="center" prop="id"/>
        <el-table-column label="邀请码" align="center" prop="inviteCode" />
        <el-table-column label="姓名" align="center" prop="realName" />
        <el-table-column label="用户名" align="center" prop="userName" />
        <el-table-column label="银行名称" align="center" prop="bankName" />
        <el-table-column label="支行信息" align="center" prop="bankAddr" />
        <el-table-column label="银行卡号" align="center" prop="bankCardNum" />
        <el-table-column label="冻结" align="center" prop="userStatus">
          <template slot-scope="scope">
            <span>{{scope.row.userStatus === 0 ? '正常' : '冻结' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="会员等级" align="center" prop="userLevelId" >
          <template slot-scope="scope">
            <span>{{scope.row.userLevelId === 0 ? '普通会员' : '未知等级' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="推荐人用户名" align="center" prop="userAgent" />
        <el-table-column label="账户余额" align="center" prop="balance" />
      </el-table>
      <pagination
        v-show="detailsTotal>0"
        :total="detailsTotal"
        :page.sync="userform.pageNum"
        :limit.sync="userform.pageSize"
        @pagination="queryChange(detailType,userform.id)"
      />
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="detailsOpen = false">确 定</el-button>
        <el-button @click="detailsOpen = false">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 用户详情 -->
    <el-dialog title="用户详情" :visible.sync="userOpen" width="800px" append-to-body>
      <el-form ref="form" :model="userform" :rules="rules" label-width="150px">
        <el-form-item label="ID">
          <el-input :disabled="true" v-model="userform.id" />
        </el-form-item>
        <el-form-item label="推荐人">
          <el-input :disabled="true" v-model="userform.userAgent" />
        </el-form-item>
        <el-form-item label="用户名">
          <el-input :disabled="true" v-model="userform.userName"/>
        </el-form-item>
        <el-form-item label="真实姓名">
          <el-input :disabled="true" v-model="userform.realName"/>
        </el-form-item>
        <el-form-item label="身份证号码">
          <el-input :disabled="true" v-model="userform.idCard" />
        </el-form-item>
        <el-form-item label="USDT地址">
          <el-input :disabled="true" v-model="userform.walletAddr"/>
        </el-form-item>
        <el-form-item label="银行名称">
          <el-input :disabled="true" v-model="userform.bankName"/>
        </el-form-item>
        <el-form-item label="支行地址">
          <el-input :disabled="true" v-model="userform.bankAddr" />
        </el-form-item>
        <el-form-item label="银行卡号">
          <el-input :disabled="true" v-model="userform.bankCardNum" />
        </el-form-item>
        <!-- <el-form-item label="登录密码">
          <el-input :disabled="true" v-model="userform.loginPwd" />
        </el-form-item>
        <el-form-item label="交易密码">
          <el-input :disabled="true" v-model="userform.payPwd" />
        </el-form-item> -->
        <el-form-item label="余额">
          <el-input :disabled="true" v-model="userform.balance" />
        </el-form-item>
        <el-form-item label="是否锁定">
          <el-select :disabled="true" v-model="userform.userStatus" placeholder="请选择">
            <el-option label="正常" :value="0"></el-option>
            <el-option label="锁定" :value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="会员等级">
          <el-select :disabled="true" v-model="userform.userLevelId" placeholder="请选择">
            <el-option label="普通会员" :value="0"></el-option>
            <el-option label="未知等级" :value="1"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="userOpen = false">确 定</el-button>
        <el-button @click="userOpen = false">取 消</el-button>
      </div>
    </el-dialog>
    <!-- 修改余额对话框 -->
    <el-dialog title="重置余额" :visible.sync="resetBalanceStatus" width="500px" append-to-body>
      <el-form ref="resetBalanceForm" :model="resetBalanceForm" :rules="rules" label-width="120px">
        <el-form-item label="用户名">
          <el-input :disabled="true" v-model="resetBalanceForm.userName" placeholder="请输入4-12位数字或字母" />
        </el-form-item>
        <el-form-item label="修改后余额" prop="balance">
          <el-input v-model="resetBalanceForm.balance" placeholder="请输入金额" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="balanceUpdateSub">确 定</el-button>
        <el-button @click="resetBalanceForm = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listUser, getUser, delUser, addUser, updateUser,balanceUser ,resetBalance,getAgentUser ,getSubUser} from "@/api/business/user";
import { dateFormat} from '@/utils/auth'

export default {
  name: "User",
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
      userList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示添加弹出层
      addOpen: false,
      // 是否显示增减余额弹出层
      balanceOpen: false,
      // 时间
      dateRange:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        userStatus: "",
        userAgent: null,
        registerTime: null,
      },
      // 表单参数
      form: {},
      // 表单参数
      addForm: {},
      // 增减余额表单数据
      balanceForm: {
        remark:'',
      },
      // 表单校验
      rules: {
        userName: [
          { required: true, message: "用户名不能为空", trigger: "blur" }
        ],
        userAgent: [
          { required: true, message: "推荐人不能为空", trigger: "blur" }
        ],
        loginPwd: [
          { required: true, message: "用户名不能为空", trigger: "blur" }
        ],
        payPwd: [
          { required: true, message: "用户名不能为空", trigger: "blur" }
        ],
        money: [
          { required: true, message: "金额不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "请选择上分类型", trigger: "change" }
        ],
        remark: [
          { required: true, message: "请填写备注", trigger: "blur" }
        ],
        balance: [
        { required: true, message: "金额不能为空", trigger: "blur" }
        ],
        amountType: [
          { required: true, message: "请选择余额类型", trigger: "change" }
        ],
      },
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
      statusArr: [
        { label: '全部', value: ''},
        { label: '正常', value: 0},
        { label: '已冻结', value: 1},
      ],//银行卡状态
      detailList:[],//详情数据
      detailsOpen: false,//上下级详情弹窗
      // 用户详情数据
      userform: {
        pageNum: 1,
        pageSize: 10,
      },
      // 用户详情弹窗状态
      userOpen: false,
      detailsTotal: 0,
      detailType:'',
      detailValue:'',
      resetBalanceStatus: false,//重置余额弹窗
      resetBalanceForm: {
        id: '',
        userName: '',
        balance: '',
      },//重置余额数据
      reasonList:['增加投资','提款失败退回','保证金退回','提款成功扣除','股权分红','股权']
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
      listUser(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.userList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 添加取消按钮
    addCancel() {
      this.addOpen = false;
      this.adReset();
    },
    // 增减余额表单重置
    balanceCancel(){
      this.balanceOpen = false;
      // this.balanceReset();
    },
    // 表单重置
    addReset() {
      this.form = {
        userName: null,
        userAgent: null,
        loginPwd: null,
        payPwd: null,
      };
      this.resetForm("addform");
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        userName: null,
        balance: null,
        realName: null,
        idCard: null,
        loginPwd: null,
        payPwd: null,
        inviteCode: null,
        walletAddr: null,
        bankCardNum: null,
        bankName: null,
        bankAddr: null,
        userStatus: null,
        userLevelId: null,
        userAgent: null,
        isRealName: null,
        registerTime: null,
        registerIp: null,
        lastTime: null,
        lastIp: null,
        modifyTime: null
      };
      this.resetForm("form");
    },
    // 余额表单重置
    balanceReset() {
      this.balanceForm = {
        userName: null,
        money: null,
        type: null,
        remark: null,
      };
      this.resetForm("balanceform");
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
      this.addOpen = true;
    },
    // 增减余额
    handleBalance(){
      // this.balanceReset();
      this.balanceOpen = true;
    },
    addSub(){
      this.$refs["addform"].validate(valid => {
        if (valid) {
          addUser(this.addForm).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.addOpen = false;
            this.getList();
          });
        }
      });
    },
    // 增减余额
    balanceSub(){
      this.$refs["balanceform"].validate(valid => {
        if (valid) {
          balanceUser(this.balanceForm).then(response => {
            this.$modal.msgSuccess("操作成功");
            this.balanceOpen = false;
            this.getList();
          });
        }
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getUser(id).then(response => {
        this.form = response.data;
        this.form.loginPwd = ''
        this.form.payPwd = ''
        this.open = true;
        this.title = "修改";
      });
    },
    // 查看详情 
    handleDetail(row){
      const id = row.id || this.ids
      getUser(id).then(response => {
        this.userform = response.data;
        this.userOpen = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateUser(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addUser(this.form).then(response => {
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
        return delUser(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/user/export', {
        ...this.queryParams
      }, `user_${new Date().getTime()}.xlsx`)
    },
    //查询上下级
    queryChange(type,id){
      this.detailType = type
      this.userform["id"] = id
      if(type === 'shangji'){
        getAgentUser(this.userform).then(response => {
          this.detailList = response.rows;
          this.detailsTotal = response.total;
          this.detailsOpen = true
        });
      }else if(type === 'xiaji'){
        getSubUser(this.userform).then(response => {
          this.detailList = response.rows;
          this.detailsTotal = response.total;
          this.detailsOpen = true
        });
      }
    },
    getDefaultTime() {
      let end = new Date();
      let start = new Date();
      start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
      this.dateRange[0] = dateFormat("YYYY-mm-dd" , start) + ' 00:00:00'
      this.dateRange[1] = dateFormat("YYYY-mm-dd" , end) + ' 23:59:59'
    },
    // 余额重置
    balanceUpdate(row){
      this.resetBalanceStatus = true
      this.resetBalanceForm.id = row.id
      this.resetBalanceForm.userName = row.userName
      this.resetBalanceForm.balance = row.balance
    },
    // 提交重置余额
    balanceUpdateSub(){
      this.$refs["resetBalanceForm"].validate(valid => {
        if (valid) {
          resetBalance(this.resetBalanceForm).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.resetBalanceStatus = false;
            this.getList();
          });
        }
      });
    },
    // 修改冻结状态
    changeStatus(id,status){
      console.log(id,status)
      updateUser(
        {
          id: id,
          userStatus : status
        }
      ).then(response => {
        this.$modal.msgSuccess("修改成功");
      });
    },
    //修改理由
    changeReason(value){
      this.balanceForm.remark = value
    },
  }
};
</script>
<style>
.reasonClass{
  margin-right: 10px;
  cursor: pointer;
}
</style>
