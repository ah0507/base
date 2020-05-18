package net.chensee.base.action.dictionary.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.chensee.base.action.dictionary.service.DictionaryGroupService;
import net.chensee.base.action.dictionary.po.DictionaryGroupPo;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author : shibo
 * @date : 2019/6/5 17:38
 */
@RestController
@RequestMapping("")
@Slf4j
public class DictionaryGroupController {

    @Resource
    private DictionaryGroupService dictionaryGroupService;

    @ApiOperation(value = "获取所有字典组")
    @RequestMapping(value = "/dictionaryGroup", method = RequestMethod.GET)
    public ObjectResponse getAllDictionaryGroup(@RequestParam(defaultValue = "16") Integer pageSize,
                                                @RequestParam(defaultValue = "1")Long pageNumber) {
        return dictionaryGroupService.getAllDictionaryGroup(pageSize, pageNumber);
    }

    @ApiOperation(value = "获取所有字典组")
    @RequestMapping(value = "/dictionaryGroup/all", method = RequestMethod.GET)
    public ObjectResponse getAllDictionaryGroup() {
        return dictionaryGroupService.getAllDictionaryGroup();
    }

    @ApiOperation(value = "根据名称查找字典组")
    @RequestMapping(value = "/dictionaryGroup/search/condition", method = RequestMethod.GET)
    public ObjectResponse getByName(String name,
                                    @RequestParam(defaultValue = "16") Integer pageSize,
                                    @RequestParam(defaultValue = "1") Long pageNumber) {
        return dictionaryGroupService.getByName(name, pageSize, pageNumber);
    }

    @ApiOperation(value = "添加字典组")
    @RequestMapping(value = "/dictionaryGroup", method = RequestMethod.POST)
    public ObjectResponse addDictionaryGroup(@RequestBody @Validated DictionaryGroupPo dictionaryGroupPo) {
        if(dictionaryGroupPo.getCode() == null || dictionaryGroupPo.getName() == null) {
            return ObjectResponse.fail("字典组编码或名称不可为空!");
        }
        return dictionaryGroupService.addDictionaryGroup(dictionaryGroupPo);
    }

    @ApiOperation(value = "修改字典组")
    @RequestMapping(value = "/dictionaryGroup", method = RequestMethod.PUT)
    public BaseResponse updateDictionaryGroup(@RequestBody @Validated DictionaryGroupPo dictionaryGroupPo) {
        if(dictionaryGroupPo.getCode() == null || dictionaryGroupPo.getName() == null) {
            return ObjectResponse.fail("字典组编码或名称不可为空!");
        }
        return dictionaryGroupService.updateDictionaryGroup(dictionaryGroupPo);
    }

    @ApiOperation(value = "删除字典组")
    @RequestMapping(value = "/dictionaryGroup/{id}", method = RequestMethod.DELETE)
    public BaseResponse deleteDictionaryGroup(@PathVariable Long id) {
        return dictionaryGroupService.deleteDictionaryGroup(id);
    }
}
