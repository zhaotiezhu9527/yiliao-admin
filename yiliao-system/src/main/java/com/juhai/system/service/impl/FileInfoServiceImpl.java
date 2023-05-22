package com.juhai.system.service.impl;

import java.util.List;
import com.juhai.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.juhai.system.mapper.FileInfoMapper;
import com.juhai.system.domain.FileInfo;
import com.juhai.system.service.IFileInfoService;

/**
 * 文件信息Service业务层处理
 * 
 * @author zhaotiezhu
 * @date 2023-05-21
 */
@Service
public class FileInfoServiceImpl implements IFileInfoService 
{
    @Autowired
    private FileInfoMapper fileInfoMapper;

    /**
     * 查询文件信息
     * 
     * @param fileId 文件信息主键
     * @return 文件信息
     */
    @Override
    public FileInfo selectFileInfoByFileId(Long fileId)
    {
        return fileInfoMapper.selectFileInfoByFileId(fileId);
    }

    /**
     * 查询文件信息列表
     * 
     * @param fileInfo 文件信息
     * @return 文件信息
     */
    @Override
    public List<FileInfo> selectFileInfoList(FileInfo fileInfo)
    {
        return fileInfoMapper.selectFileInfoList(fileInfo);
    }

    /**
     * 新增文件信息
     * 
     * @param fileInfo 文件信息
     * @return 结果
     */
    @Override
    public int insertFileInfo(FileInfo fileInfo)
    {
        fileInfo.setCreateTime(DateUtils.getNowDate());
        return fileInfoMapper.insertFileInfo(fileInfo);
    }

    /**
     * 修改文件信息
     * 
     * @param fileInfo 文件信息
     * @return 结果
     */
    @Override
    public int updateFileInfo(FileInfo fileInfo)
    {
        return fileInfoMapper.updateFileInfo(fileInfo);
    }

    /**
     * 批量删除文件信息
     * 
     * @param fileIds 需要删除的文件信息主键
     * @return 结果
     */
    @Override
    public int deleteFileInfoByFileIds(Long[] fileIds)
    {
        return fileInfoMapper.deleteFileInfoByFileIds(fileIds);
    }

    /**
     * 删除文件信息信息
     * 
     * @param fileId 文件信息主键
     * @return 结果
     */
    @Override
    public int deleteFileInfoByFileId(Long fileId)
    {
        return fileInfoMapper.deleteFileInfoByFileId(fileId);
    }
}
