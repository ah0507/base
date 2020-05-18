package net.chensee.base.action.folder.service;

import net.chensee.base.action.folder.po.FolderPo;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;
import org.apache.ibatis.annotations.Param;

/**
 * @Author : shibo
 * @Date : 2019/8/2 17:07
 */
public interface FolderService {

    /**
     * 获取所有文件夹
     * @return
     */
    ObjectResponse getAllFolder();

    /**
     * 添加文件夹
     * @param folderPo
     */
    ObjectResponse addFolder(FolderPo folderPo);

    /**
     * 修改文件夹
     * @param folderPo
     */
    BaseResponse updateFolder(FolderPo folderPo) throws Exception;

    /**
     * 删除文件夹
     * @param id
     */
    BaseResponse deleteFolder(@Param("id") Long id) throws Exception;

    /**
     * 通过ID查找文件夹
     * @param id
     * @return
     */
    ObjectResponse getById(@Param("id") Long id);

    /**
     * 通过文件夹ID查找可操作人员的信息
     * @param id
     * @return
     */
    ObjectResponse getUsersInfoById(@Param("id") Long id);
}
