package com.juhai.web.controller.system;
import java.util.Date;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONObject;
import cn.xuyanwu.spring.file.storage.FileStorageService;
import com.alibaba.fastjson2.JSON;
import com.juhai.business.domain.Project;
import com.juhai.business.service.IParamterService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.juhai.common.annotation.Log;
import com.juhai.common.core.controller.BaseController;
import com.juhai.common.core.domain.AjaxResult;
import com.juhai.common.enums.BusinessType;
import com.juhai.system.domain.FileInfo;
import com.juhai.system.service.IFileInfoService;
import com.juhai.common.utils.poi.ExcelUtil;
import com.juhai.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件信息Controller
 * 
 * @author zhaotiezhu
 * @date 2023-05-21
 */
@RestController
@RequestMapping("/system/info")
public class FileInfoController extends BaseController
{
    @Autowired
    private IFileInfoService fileInfoService;

    @Autowired
    private FileStorageService fileStorageService;//注入实列

    @Autowired
    private IParamterService paramterService;

    /**
     * 查询文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(FileInfo fileInfo)
    {
        startPage();
        List<FileInfo> list = fileInfoService.selectFileInfoList(fileInfo);
        if (CollUtil.isNotEmpty(list)) {
            Map<String, String> params = paramterService.getAllParamByMap();
            String resourceDomain = params.get("resource_domain");
            for (FileInfo temp : list) {
                temp.setFileFullPath(resourceDomain + temp.getFilePath());
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出文件信息列表
     */
    @PreAuthorize("@ss.hasPermi('system:info:export')")
    @Log(title = "文件信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FileInfo fileInfo)
    {
        List<FileInfo> list = fileInfoService.selectFileInfoList(fileInfo);
        ExcelUtil<FileInfo> util = new ExcelUtil<FileInfo>(FileInfo.class);
        util.exportExcel(response, list, "文件信息数据");
    }

    /**
     * 获取文件信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:query')")
    @GetMapping(value = "/{fileId}")
    public AjaxResult getInfo(@PathVariable("fileId") Long fileId)
    {
        return success(fileInfoService.selectFileInfoByFileId(fileId));
    }

    /**
     * 新增文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:add')")
    @Log(title = "文件信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FileInfo fileInfo)
    {
        return toAjax(fileInfoService.insertFileInfo(fileInfo));
    }

    /**
     * 修改文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "文件信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FileInfo fileInfo)
    {
        return toAjax(fileInfoService.updateFileInfo(fileInfo));
    }

    /**
     * 删除文件信息
     */
    @PreAuthorize("@ss.hasPermi('system:info:remove')")
    @Log(title = "文件信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{fileIds}")
    public AjaxResult remove(@PathVariable Long[] fileIds)
    {
        return toAjax(fileInfoService.deleteFileInfoByFileIds(fileIds));
    }

    /**
     * 上传文件
     */
    @PreAuthorize("@ss.hasPermi('system:info:upload')")
    @Log(title = "文件信息", businessType = BusinessType.INSERT)
    @PostMapping("/upload")
    public AjaxResult uploadImage(MultipartFile file) {
        cn.xuyanwu.spring.file.storage.FileInfo fileInfo = fileStorageService.of(file)
                .upload();

        FileInfo info = new FileInfo();
        info.setFileName(null);
        info.setFilePath(fileInfo.getBasePath() + fileInfo.getFilename());
        info.setFileFullPath(fileInfo.getUrl());
        info.setCreateTime(new Date());
        fileInfoService.insertFileInfo(info);
        return AjaxResult.success(info);
    }
}
