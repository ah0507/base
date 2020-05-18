package net.chensee.base.action.folder.mapper;

import net.chensee.base.action.folder.po.FolderPo;
import net.chensee.base.action.folder.vo.FolderVo;
import net.chensee.base.action.user.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author : shibo
 * @Date : 2019/7/22 10:58
 */
public interface FolderDao {

    /**
     * 获取所有文件夹
     * @return
     */
    List<FolderVo> getAllFolder();

    /**
     * 添加文件夹
     * @param folderPo
     */
    void addFolder(FolderPo folderPo);

    /**
     * 修改文件夹
     * @param folderPo
     */
    void updateFolder(FolderPo folderPo);

    /**
     * 删除文件夹
     * @param id
     */
    void deleteFolder(@Param("id") Long id);

    /**
     * 通过ID查找文件夹
     * @param id
     * @return
     */
    FolderVo getById(@Param("id") Long id);

    /**
     * 通过业务查找文件夹
     * @param business
     * @param businessId
     * @return
     */
    List<FolderVo> getByBusiness(@Param("business") String business,
                           @Param("businessId") String businessId);

    /**
     * 更新用户写入文件夹的名称
     * @param folderId
     */
    void updateUserWriteFolderName(@Param("folderId") Long folderId);

    /**
     * 删除用户可写入文件夹
     * @param folderId
     */
    void deleteUserWriteFolder(@Param("folderId") Long folderId);

    /**
     * 查看部门业务文件夹下的用户业务文件夹
     * @return
     */
    List<FolderVo> getUserFolderByDeptId(@Param("deptId") String deptId);

    /**
     * 根据文件夹ID查找可操作人员的信息
     * @param id
     * @return
     */
    List<UserVo> getUsersInfoById(@Param("id") Long id);

    /**
     * 根据文件夹ID判断文件夹是否为叶子节点
     * @param folderId
     * @return
     */
    Long checkIsLeaf(@Param("folderId") Long folderId);

    /**
     * 根据文件夹ID获取下属一级的子级文件夹
     * @param folderId
     * @return
     */
    List<FolderVo> getChildFolders(@Param("folderId") Long folderId);

    /**
     * 批量删除文件夹
     * @param folderIdList
     * @return
     */
    void deleteBatchFolder(@Param("folderIdList") List<Long> folderIdList);

    /**
     * 获取批量文件夹
     * @param folderIdList
     * @return
     */
    List<FolderVo> getBatchFolders(@Param("folderIdList") List<Long> folderIdList);
}
