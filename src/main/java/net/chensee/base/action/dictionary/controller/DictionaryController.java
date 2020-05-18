package net.chensee.base.action.dictionary.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.chensee.base.action.dictionary.service.DictionaryService;
import net.chensee.base.action.dictionary.po.DictionaryPo;
import net.chensee.base.common.BaseResponse;
import net.chensee.base.common.ObjectResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author : shibo
 * @date : 2019/6/5 17:37
 */
@RestController
@RequestMapping("")
@Slf4j
public class DictionaryController {

    @Resource
    private DictionaryService dictionaryService;

    @ApiOperation(value = "根据字典组ID查找字典")
    @RequestMapping(value = "/dictionary/group/{groupId}", method = RequestMethod.GET)
    public ObjectResponse getByGroupId(@PathVariable Long groupId) {
        return dictionaryService.getByGroupId(groupId);
    }

    @ApiOperation(value = "添加字典")
    @RequestMapping(value = "/dictionary", method = RequestMethod.POST)
    public ObjectResponse addDictionary(@RequestBody @Validated DictionaryPo dictionaryPo) {
        if(dictionaryPo.getCode() == null || dictionaryPo.getName() == null) {
            return ObjectResponse.fail("字典编码或名称不可为空!");
        }
        return dictionaryService.addDictionary(dictionaryPo);
    }

    @ApiOperation(value = "修改字典")
    @RequestMapping(value = "/dictionary", method = RequestMethod.PUT)
    public BaseResponse updateDictionary(@RequestBody @Validated DictionaryPo dictionaryPo) {
        if(dictionaryPo.getCode() == null || dictionaryPo.getName() == null) {
            return ObjectResponse.fail("字典编码或名称不可为空!");
        }
        return dictionaryService.updateDictionary(dictionaryPo);
    }

    @ApiOperation(value = "删除字典")
    @RequestMapping(value = "/dictionary/{id}", method = RequestMethod.DELETE)
    public BaseResponse deleteDictionary(@PathVariable Long id) {
        return dictionaryService.deleteDictionary(id);
    }
}
