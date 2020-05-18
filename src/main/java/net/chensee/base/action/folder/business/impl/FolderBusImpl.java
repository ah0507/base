package net.chensee.base.action.folder.business.impl;

import net.chensee.base.action.folder.mapper.FolderDao;
import net.chensee.base.action.folder.po.FolderPo;
import net.chensee.base.action.folder.business.FolderBus;
import net.chensee.base.action.folder.vo.FolderVo;
import net.chensee.base.action.user.vo.UserVo;
import net.chensee.base.common.po.BaseInfoPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author : shibo
 * @Date : 2019/7/22 11:09
 */
@Component
public class FolderBusImpl implements FolderBus {
    private static final Logger logger = LoggerFactory.getLogger(FolderPo.class);

    @Resource
    private FolderDao folderDao;

    @Override
    public List<FolderVo> getAllFolder() {
        return folderDao.getAllFolder();
    }

    @Override
    public void addFolder(FolderPo folderPo) {
        folderPo.setStatus(BaseInfoPo.Status_Able);
        folderDao.addFolder(folderPo);
    }

    @Override
    public void updateFolder(FolderPo folderPo){
        folderDao.updateFolder(folderPo);
        //更新用户写入文件夹的名称
        folderDao.updateUserWriteFolderName(folderPo.getId());
    }

    @Override
    public void deleteFolder(Long id){
        folderDao.deleteFolder(id);
        //删除可写入文件夹
        folderDao.deleteUserWriteFolder(id);
    }

    @Override
    public FolderVo getById(Long id) {
        return folderDao.getById(id);
    }

    @Override
    public List<UserVo> getUsersInfoById(Long id) {
        return folderDao.getUsersInfoById(id);
    }
}
