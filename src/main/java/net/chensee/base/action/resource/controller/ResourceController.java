package net.chensee.base.action.resource.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.chensee.base.action.resource.service.ResourceService;
import net.chensee.base.action.resource.po.ResourcePo;
import net.chensee.base.annotation.CheckFolderAnnontation;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author : shibo
 * @date : 2019/6/12 16:38
 */
@RestController
@RequestMapping("")
@Slf4j
public class ResourceController {
    
    @Resource
    private ResourceService resourceService;

    @ApiOperation(value = "获取所有资源")
    @RequestMapping(value = "/resource", method = RequestMethod.GET)
    public ObjectResponse getAllResource() {
        return resourceService.getAllResource();
    }

    @ApiOperation(value = "添加资源")
    @CheckFolderAnnontation(0)
    @RequestMapping(value = "/resource", method = RequestMethod.POST)
    public BaseResponse addResource(@RequestBody @Validated ResourcePo resourcePo) {
        return resourceService.addResource(resourcePo);
    }

    @ApiOperation(value = "修改资源")
    @CheckFolderAnnontation(0)
    @RequestMapping(value = "/resource", method = RequestMethod.PUT)
    public BaseResponse updateResource(@RequestBody @Validated ResourcePo resourcePo) {
        return resourceService.updateResource(resourcePo);
    }

    @ApiOperation(value = "删除资源")
    @RequestMapping(value = "/resource/{id}", method = RequestMethod.DELETE)
    public BaseResponse deleteResource(@PathVariable Long id) {
        if (id == null){
            return BaseResponse.fail("资源ID不能为空");
        }
        return resourceService.deleteResource(id);
    }

    @ApiOperation(value = "根据ID获取资源")
    @RequestMapping(value = "/resource/{id}", method = RequestMethod.GET)
    public BaseResponse getResourceById(@PathVariable Long id) {
        if (id == null){
            return BaseResponse.fail("资源ID不能为空");
        }
        return resourceService.getResourceById(id);
    }
}
