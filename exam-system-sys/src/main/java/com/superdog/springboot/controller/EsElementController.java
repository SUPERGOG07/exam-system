package com.superdog.springboot.controller;

import com.lczyfz.edp.springboot.core.controller.BaseController;
import com.lczyfz.edp.springboot.core.entity.CommonResult;
import com.lczyfz.edp.springboot.core.entity.PageResult;
import com.lczyfz.edp.springboot.core.utils.BeanCustomUtils;
import com.lczyfz.edp.springboot.core.utils.MsgCodeUtils;
import com.lczyfz.edp.springboot.core.utils.StringUtils;
import com.lczyfz.edp.springboot.core.validation.Create;
import com.lczyfz.edp.springboot.core.validation.Update;
import com.superdog.springboot.entity.EsElement;
import com.superdog.springboot.service.EsElementService;
import com.superdog.springboot.service.EsUserService;
import com.superdog.springboot.vo.EsElementPageVO;
import com.superdog.springboot.vo.EsElementVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author superdog
 * @version 2023-3-27
 */
@Api(value = "EsElementController", tags = "考试管理系统试题接口")
@RestController
@RequestMapping("/${apiPath}/es/element")
public class EsElementController extends BaseController {

    @Resource
    EsElementService esElementService;

    @Resource
    EsUserService esUserService;

    @ApiOperation(value = "添加试题", notes = "Request-DateTime样例：2018-09-29 11:26:20")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Access-Token",
                    value = "访问token",
                    paramType = "header",
                    dataType = "string",
                    required = true),
            @ApiImplicitParam(
                    name = "Request-DateTime",
                    value = "发起请求时间",
                    paramType = "header",
                    dataType = "date",
                    required = true,
                    defaultValue = "2023-01-01 00:00:00"),
            @ApiImplicitParam(
                    name = "username",
                    value = "用户名",
                    paramType = "query",
                    dataType = "string",
                    required = true,
                    defaultValue = "test"),
            @ApiImplicitParam(
                    name = "password",
                    value = "密码",
                    paramType = "query",
                    dataType = "string",
                    required = true,
                    defaultValue = "123456")
    })
    @PostMapping
    public CommonResult add(@Validated(Create.class) @RequestBody EsElementVO esElementVO, BindingResult bindingResult,
                            @RequestParam String username, @RequestParam String password) {

        logger.info("add element|param: {}", esElementVO);
        logger.info("add element|auth username: {}", username);
        logger.info("add element|auth password: {}", password);

        CommonResult result = new CommonResult().init();
//        参数校验
        if (bindingResult.hasErrors()) {
            return (CommonResult) result.failIllegalArgument(bindingResult.getFieldErrors()).end();
        }
//        权限校验
        if (!esUserService.checkTeacher(username, password)) {
            result.error(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
            logger.info("add element|author error: {}", username);
            return (CommonResult) result.end();
        }

        EsElement esElement = new EsElement();
        BeanCustomUtils.copyProperties(esElementVO, esElement);
//        出题人存在性验证
        if(esUserService.idNotExist(esElement.getOwner())){
            result.error(MsgCodeUtils.MSG_CODE_DATA_NOT_EXIST);
            result.setErrMsg(result.getErrMsg()+"owner");
            logger.info("add element|owner not exist error: {}",esElementVO);
            return (CommonResult) result.end();
        }
//        增操作
        if (esElementService.save(esElement) <= 0) {
            result.error(MsgCodeUtils.MSC_DATA_ADDDATA_ERROR);
            logger.info("add element|save error: {}", esElementVO);
            return (CommonResult) result.end();
        }

        result.success("esElement", esElement);
        logger.info("add element|success: {}", esElement);
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "删除试题", notes = "Request-DateTime样例：2023-01-01 00:00:00")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Access-Token",
                    value = "访问token",
                    paramType = "header",
                    dataType = "string",
                    required = true),
            @ApiImplicitParam(
                    name = "Request-DateTime",
                    value = "发起请求时间",
                    paramType = "header",
                    dataType = "date",
                    required = true,
                    defaultValue = "2023-01-01 00:00:00"),
            @ApiImplicitParam(
                    name = "id",
                    value = "用户id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "560284650732253184"),
            @ApiImplicitParam(
                    name = "username",
                    value = "用户名",
                    paramType = "query",
                    dataType = "string",
                    required = true,
                    defaultValue = "test"),
            @ApiImplicitParam(
                    name = "password",
                    value = "密码",
                    paramType = "query",
                    dataType = "string",
                    required = true,
                    defaultValue = "123456")
    })
    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable("id") String id,
                               @RequestParam String username, @RequestParam String password) {
        logger.info("delete element|param: {}", id);
        logger.info("delete element|auth username: {}", username);
        logger.info("delete element|auth password: {}", password);
        CommonResult result = new CommonResult().init();
//        参数校验
        if (StringUtils.isBlank(id)) {
            result.error(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            logger.info("delete element|param null error");
            return (CommonResult) result.end();
        }
//        权限校验
        if (!esUserService.checkTeacher(username, password)) {
            result.error(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
            logger.info("delete element|author error: {}", username);
            return (CommonResult) result.end();
        }
//        删除操作
        EsElement esElement = esElementService.get(id);
        if (esElementService.delete(esElement) <= 0) {
            result.error(MsgCodeUtils.MSC_DATA_DRODATA_ERROR);
            logger.info("delete element|delete user error: {}", id);
            return (CommonResult) result.end();
        }

        result.success();
        logger.info("delete element|success: {}", id);
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "修改试题", notes = "Request-DateTime样例：2023-01-01 00:00:00")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Access-Token",
                    value = "访问token",
                    paramType = "header",
                    dataType = "string",
                    required = true),
            @ApiImplicitParam(
                    name = "Request-DateTime",
                    value = "发起请求时间",
                    paramType = "header",
                    dataType = "date",
                    required = true,
                    defaultValue = "2023-01-01 00:00:00"),
            @ApiImplicitParam(
                    name = "id",
                    value = "用户id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "560284650732253184"),
            @ApiImplicitParam(
                    name = "username",
                    value = "用户名",
                    paramType = "query",
                    dataType = "string",
                    required = true,
                    defaultValue = "test"),
            @ApiImplicitParam(
                    name = "password",
                    value = "密码",
                    paramType = "query",
                    dataType = "string",
                    required = true,
                    defaultValue = "123456")
    })
    @PutMapping("/{id}")
    public CommonResult update(@PathVariable("id") String id, @Validated(Update.class) @RequestBody EsElementVO esElementVO, BindingResult bindingResult,
                               @RequestParam String username, @RequestParam String password) {
        logger.info("update element|param: {}", id);
        logger.info("update element|param: {}", esElementVO);
        logger.info("update element|auth username: {}", username);
        logger.info("update element|auth password: {}", password);
        CommonResult result = new CommonResult().init();
//        参数校验
        if (bindingResult.hasErrors()) {
            return (CommonResult) result.failIllegalArgument(bindingResult.getFieldErrors()).end();
        }
        if (StringUtils.isBlank(id)) {
            result.error(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            logger.info("update element|param null error");
            return (CommonResult) result.end();
        }
//        权限校验
        if (!esUserService.checkTeacher(username, password)) {
            result.error(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
            logger.info("update element|author error: {}", username);
            return (CommonResult) result.end();
        }
//        修改出题人存在性校验
        if(esUserService.idNotExist(esElementVO.getOwner())){
            result.error(MsgCodeUtils.MSG_CODE_DATA_NOT_EXIST);
            result.setErrMsg(result.getErrMsg()+"owner");
            logger.info("update element|owner not exist error: {}",esElementVO);
            return (CommonResult) result.end();
        }
//        存在校验
        EsElement esElement = esElementService.get(id);
        if (esElement == null) {
            result.error(MsgCodeUtils.MSG_CODE_DATA_NOT_EXIST);
            result.setErrMsg(result.getErrMsg()+"element");
            logger.info("update element|data not exist: {}", id);
            return (CommonResult) result.end();
        }
//        更新操作
        BeanCustomUtils.copyProperties(esElementVO, esElement);
        if (esElementService.save(esElement) <= 0) {
            result.error(MsgCodeUtils.MSG_DATAITEM_UPDATE_ERRO);
            logger.info("update element|update error: {}", id);
            return (CommonResult) result.end();
        }

        result.success("esElement", esElement);
        logger.info("update element|success: {}", id);
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "获取单个试题", notes = "Request-DateTime样例：2023-01-01 00:00:00")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Access-Token",
                    value = "访问token",
                    paramType = "header",
                    dataType = "string",
                    required = true),
            @ApiImplicitParam(
                    name = "Request-DateTime",
                    value = "发起请求时间",
                    paramType = "header",
                    dataType = "date",
                    required = true,
                    defaultValue = "2023-01-01 00:00:00"),
            @ApiImplicitParam(
                    name = "id",
                    value = "用户id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "560284650732253184"),
            @ApiImplicitParam(
                    name = "username",
                    value = "用户名",
                    paramType = "query",
                    dataType = "string",
                    required = true,
                    defaultValue = "test"),
            @ApiImplicitParam(
                    name = "password",
                    value = "密码",
                    paramType = "query",
                    dataType = "string",
                    required = true,
                    defaultValue = "123456")
    })
    @GetMapping("/{id}")
    public CommonResult get(@PathVariable("id") String id,
                            @RequestParam String username, @RequestParam String password) {
        logger.info("get element|param: {}", id);
        logger.info("get element|auth username: {}", username);
        logger.info("get element|auth password: {}", password);
        CommonResult result = new CommonResult().init();
//        参数校验
        if (StringUtils.isBlank(id)) {
            result.error(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            logger.info("get element|param null error");
            return (CommonResult) result.end();
        }
//        权限校验
        if (!esUserService.checkTeacher(username, password)) {
            result.error(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
            logger.info("get element|author error: {}", username);
            return (CommonResult) result.end();
        }
//        读操作
        EsElement esElement = esElementService.get(id);
        if (esElement == null) {
            result.error(MsgCodeUtils.MSG_CODE_DATA_NOT_EXIST);
            result.setErrMsg(result.getErrMsg() + "id");
            logger.info("get element|esElement null error: {}", id);
            return (CommonResult) result.end();
        }

        result.success("esElement", esElement);
        logger.info("get element|success: {}", id);
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "获取试题列表", notes = "Request-DateTime样例：2023-01-01 00:00:00")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "Access-Token",
                    value = "访问token",
                    paramType = "header",
                    dataType = "string",
                    required = true),
            @ApiImplicitParam(
                    name = "Request-DateTime",
                    value = "发起请求时间",
                    paramType = "header",
                    dataType = "date",
                    required = true,
                    defaultValue = "2023-01-01 00:00:00"),
            @ApiImplicitParam(
                    name = "username",
                    value = "用户名",
                    paramType = "query",
                    dataType = "string",
                    required = true,
                    defaultValue = "test"),
            @ApiImplicitParam(
                    name = "password",
                    value = "密码",
                    paramType = "query",
                    dataType = "string",
                    required = true,
                    defaultValue = "123456")
    })
    @GetMapping("/list")
    public PageResult<EsElement> list(@Validated @RequestBody EsElementPageVO esElementPageVO, BindingResult bindingResult,
                                      @RequestParam String username, @RequestParam String password) {
        logger.info("list element|param: {}", esElementPageVO);
        logger.info("list element|auth username: {}", username);
        logger.info("list element|auth password: {}", password);

        PageResult<EsElement> result = new PageResult<EsElement>().init();
//        参数校验
        if (bindingResult.hasErrors()) {
            return (PageResult<EsElement>) result.failIllegalArgument(bindingResult.getFieldErrors()).end();
        }
//        权限校验
        if (!esUserService.checkTeacher(username, password)) {
            result.error(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
            logger.info("list element|author error: {}", username);
            return (PageResult<EsElement>) result.end();
        }
//        读操作
        result.success(esElementService.page(esElementPageVO));
        logger.info("list element|success");
        return (PageResult<EsElement>) result.end();
    }

}


