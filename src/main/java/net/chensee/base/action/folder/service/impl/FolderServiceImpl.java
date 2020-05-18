package net.chensee.base.action.folder.service.impl;

import net.chensee.base.action.folder.business.FolderBus;
import net.chensee.base.action.folder.service.FolderService;
import net.chensee.base.action.folder.po.FolderPo;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author : shibo
 * @Date : 2019/8/2 17:07
 */
@Service
@Transactional
public class FolderServiceImpl implements FolderService {
    private static final Logger logger = LoggerFactory.getLogger(FolderPo.class);

    @Resource
    private FolderBus folderBus;

    @Override
    public ObjectResponse getAllFolder() {
        return ObjectResponse.ok(folderBus.getAllFolder());
    }

    @Override
    public ObjectResponse addFolder(FolderPo folderPo) {
        try {
            folderBus.addFolder(folderPo);
            return ObjectResponse.ok(folderPo.getId());
        } catch (Exception e) {
            logger.error("添加文件夹出现异常", e);
            return ObjectResponse.fail();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse updateFolder(FolderPo folderPo) throws Exception{
        try {
            folderBus.updateFolder(folderPo);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("修改文件夹出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteFolder(Long id) throws Exception{
        try {
            folderBus.deleteFolder(id);
            return BaseResponse.ok();
        } catch (Exception e) {
            logger.error("删除文件夹出现异常", e);
            return BaseResponse.fail();
        }
    }

    @Override
    public ObjectResponse getById(Long id) {
        try {
            return ObjectResponse.ok(folderBus.getById(id));
        } catch (Exception e) {
            return ObjectResponse.fail();
        }
    }

    @Override
    public ObjectResponse getUsersInfoById(Long id) {
        try {
            return ObjectResponse.ok(folderBus.getUsersInfoById(id));
        } catch (Exception e) {
            return ObjectResponse.fail();
        }
    }
}
