<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="项目名称" prop="projectName">
        <el-input
          v-model="queryParams.projectName"
          placeholder="请输入项目名称"
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
          v-hasPermi="['business:project:add']"
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
          v-hasPermi="['business:project:edit']"
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
          v-hasPermi="['business:project:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['business:project:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="projectList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="排序" align="center" prop="sort" />
      <el-table-column label="项目名称" align="center" prop="projectName" />
      <el-table-column label="图片" align="center" prop="img">
        <template slot-scope="scope">
          <img class="img-class" :src="resourceDomain.resourceDomain + scope.row.img" />
        </template> 
      </el-table-column>
      <el-table-column label="项目金额(万元)" align="center" prop="projectAmount" />
      <el-table-column label="收益率（%）" align="center" prop="incomeRate" />
      <el-table-column label="期限(分钟)" align="center" prop="limitTime" />
      <el-table-column label="起投金额" align="center" prop="minAmount" />
      <el-table-column label="是否开启" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" type="success">可投</el-tag>
          <el-tag v-else-if="scope.row.status === 1" type="danger">不可投</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="开始时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['business:project:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['business:project:remove']"
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
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :loading="formLoading">
      <el-form ref="form" :model="form" :rules="rules" label-width="150px">
        <el-form-item label="产品名称" prop="projectName">
          <el-input v-model="form.projectName" placeholder="请输入产品名称" />
        </el-form-item>
        <el-form-item label="项目金额(万元)" prop="projectAmount">
          <el-input v-model="form.projectAmount" placeholder="请输入项目金额(万元)" />
        </el-form-item>
        <el-form-item label="收益率" prop="incomeRate">
          <el-input v-model="form.incomeRate" placeholder="请输入收益率" />
        </el-form-item>
        <el-form-item label="期限(分钟)" prop="limitTime">
          <el-input v-model="form.limitTime" placeholder="请输入期限(分钟)" />
        </el-form-item>
        <el-form-item label="起投金额" prop="minAmount">
          <el-input v-model="form.minAmount" placeholder="请输入起投金额" />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            v-model="form.startTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            v-model="form.endTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="项目进度" prop="schedule">
          <el-input v-model="form.schedule" placeholder="请输入项目进度" />
        </el-form-item>
        <el-form-item label="排序号(值越大越靠前)" prop="sort">
          <el-input v-model="form.sort" placeholder="请输入排序号(值越大越靠前)" />
        </el-form-item>
        <el-form-item label="图片上传" prop="img">
          <el-upload
              class="avatar-uploader"
              :action="upload.url"
              :file-list="upload.fileList"
              :headers="upload.headers"
              :show-file-list="false"
              :on-success="successHandle"
              :before-upload="beforeUploadHandle">
              <img v-if="form.img" :src="resourceDomain.resourceDomain + form.img" class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status">
            <el-option label="开启" :value="0"></el-option>
            <el-option label="关闭" :value="1"></el-option>
          </el-select>
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
import { listProject, getProject, delProject, addProject, updateProject } from "@/api/business/project";
import { getToken } from "@/utils/auth";
import Cookies from "js-cookie";

export default {
  name: "Project",
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
      projectList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        projectName: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        endTime: [
          { required: true, message: "请选择结束时间", trigger: "blur" }
        ],
        startTime: [
          { required: true, message: "请选择开始时间", trigger: "blur" }
        ],
        incomeRate: [
          { required: true, message: "请输入收益率", trigger: "blur" }
        ],
        limitTime: [
          { required: true, message: "请输入期限", trigger: "blur" }
        ],
        status: [
          { required: true, message: "请选择状态", trigger: "blur" }
        ],
        projectName: [
          { required: true, message: "请输入产品名称", trigger: "blur" }
        ],
        projectAmount: [
          { required: true, message: "请输入项目金额", trigger: "blur" }
        ],
        minAmount: [
          { required: true, message: "请输入起投金额", trigger: "blur" }
        ],
        schedule: [
          { required: true, message: "请输入项目进度", trigger: "blur" }
        ],
      },
      // 上传参数
      upload: {
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/info/upload",
        // 上传的文件列表
        fileList: []
      },
      formLoading: false,
      resourceDomain: {},
    };
  },
  created() {
    this.getList();
    this.getCookie()
  },
  methods: {
    /** 查询【请填写功能名称】列表 */
    getList() {
      this.loading = true;
      listProject(this.queryParams).then(response => {
        this.projectList = response.rows;
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
        projectName: null,
        projectAmount: null,
        incomeRate: null,
        limitTime: null,
        minAmount: null,
        status: null,
        startTime: null,
        endTime: null,
        schedule: null,
        guaranteeCompany: null,
        sort: null,
        createTime: null,
        modifyTime: null,
        img: null
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
      this.title = "添加";
      this.upload.fileList = [];
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getProject(id).then(response => {
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
            updateProject(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProject(this.form).then(response => {
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
        return delProject(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('business/project/export', {
        ...this.queryParams
      }, `project_${new Date().getTime()}.xlsx`)
    },
    // 文件提交处理
    submitUpload() {
      this.$refs.upload.submit();
    },
    beforeUploadHandle (file) {
       this.formLoading = true
        if (file.type !== 'image/jpg' && file.type !== 'image/jpeg' && file.type !== 'image/png' && file.type !== 'image/gif') {
          this.$message.error('只支持jpg、png、gif格式的图片！')
          return false
        }
      },
    // 上传成功
    successHandle (response, file, fileList) {
      this.fileList = fileList
      if (response && response.code === 200) {
        this.form.img = response.data.filePath;
      } else {
        // this.$message.error(response.msg)
      }
      this.formLoading = false
    },
    getCookie() {
      this.resourceDomain = JSON.parse(Cookies.get("config"));
      console.log(this.resourceDomain)
    }
  }
};
</script>
<style>
.img-class{
  height: 50px;
}
.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>