package net.chensee.base.action.folder.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.chensee.base.action.folder.service.FolderService;
import net.chensee.base.action.folder.po.FolderPo;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author : shibo
 * @Date : 2019/7/22 14:20
 */
@RestController
@RequestMapping("")
@Slf4j
public class FolderController {

    @Resource
    private FolderService folderService;

    @ApiOperation(value = "获取所有文件夹")
    @RequestMapping(value = "/folder", method = RequestMethod.GET)
    public ObjectResponse getAllFolder() {
        return folderService.getAllFolder();
    }

    @ApiOperation(value = "添加文件夹")
    @RequestMapping(value = "/folder", method = RequestMethod.POST)
    public ObjectResponse addFolder(@RequestBody @Validated FolderPo folderPo) {
        return folderService.addFolder(folderPo);
    }

    @ApiOperation(value = "修改文件夹")
    @RequestMapping(value = "/folder", method = RequestMethod.PUT)
    public BaseResponse updateFolder(@RequestBody @Validated FolderPo folderPo) throws Exception{
        return folderService.updateFolder(folderPo);
    }

    @ApiOperation(value = "删除文件夹")
    @RequestMapping(value = "/folder/{id}", method = RequestMethod.DELETE)
    public BaseResponse deleteFolder(@PathVariable Long id) throws Exception{
        return folderService.deleteFolder(id);
    }

    @ApiOperation(value = "根据ID查找文件夹")
    @RequestMapping(value = "/folder/{id}", method = RequestMethod.GET)
    public BaseResponse getById(@PathVariable Long id) {
        return folderService.getById(id);
    }
    @ApiOperation(value = "根据文件夹ID查找可操作人员的信息")
    @RequestMapping(value = "/folder/{id}/usersInfo", method = RequestMethod.GET)
    public BaseResponse getUsersInfoById(@PathVariable Long id) {
        return folderService.getUsersInfoById(id);
    }
}
