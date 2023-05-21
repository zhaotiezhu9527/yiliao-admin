<template>
  <div class="app-container">

    <el-row :gutter="10" class="mb8">
      <right-toolbar @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="paramterList" @selection-change="handleSelectionChange">
      <!-- <el-table-column type="selection" width="55" align="center" /> -->
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="参数名" align="center" prop="paramName" />
      <!-- <el-table-column label="参数key" align="center" prop="paramKey" /> -->
      <el-table-column label="参数值" align="center" prop="paramValue" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:paramter:edit']"
          >修改</el-button>
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
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="参数名" prop="paramName">
          <el-input v-model="form.paramName" placeholder="请输入参数名" />
        </el-form-item>
        <!-- <el-form-item label="参数key" prop="paramKey">
          <el-input v-model="form.paramKey" placeholder="请输入参数key" />
        </el-form-item> -->
        <el-form-item label="参数值" prop="paramValue">
          <el-input v-model="form.paramValue" type="textarea" placeholder="请输入内容" />
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
import { listParamter, getParamter, delParamter, addParamter, updateParamter } from "@/api/business/paramter";

export default {
  name: "Paramter",
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
      paramterList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        paramKey: [
          { required: true, message: "参数key不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      listParamter(this.queryParams).then(response => {
        this.paramterList = response.rows;
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
        paramName: null,
        paramKey: null,
        paramValue: null,
        isShow: null
      };
      this.resetForm("form");
    },
    /** 新增按钮操作 */
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getParamter(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateParamter(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addParamter(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
  }
};
</script>
