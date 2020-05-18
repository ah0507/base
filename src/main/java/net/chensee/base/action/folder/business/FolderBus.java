package net.chensee.base.action.folder.business;

import net.chensee.base.action.folder.po.FolderPo;
import net.chensee.base.action.folder.vo.FolderVo;
import net.chensee.base.action.user.vo.UserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author : shibo
 * @Date : 2019/7/22 11:07
 */
public interface FolderBus {

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
     * 根据文件夹ID查找可操作人员的信息
     * @param id
     * @return
     */
    List<UserVo> getUsersInfoById(@Param("id") Long id);
}
