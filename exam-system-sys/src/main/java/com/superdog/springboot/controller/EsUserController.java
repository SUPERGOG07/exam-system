package com.superdog.springboot.controller;

import com.lczyfz.edp.springboot.core.controller.BaseController;
import com.lczyfz.edp.springboot.core.entity.CommonResult;
import com.lczyfz.edp.springboot.core.entity.PageResult;
import com.lczyfz.edp.springboot.core.utils.BeanCustomUtils;
import com.lczyfz.edp.springboot.core.utils.MsgCodeUtils;
import com.lczyfz.edp.springboot.core.utils.StringUtils;
import com.lczyfz.edp.springboot.core.validation.Create;
import com.lczyfz.edp.springboot.core.validation.Update;
import com.superdog.springboot.entity.EsUser;
import com.superdog.springboot.service.EsUserService;
import com.superdog.springboot.vo.EsUserPageVO;
import com.superdog.springboot.vo.EsUserVO;
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
@Api(value = "EsUserController",tags = "考试管理系统用户接口")
@RestController
@RequestMapping("/${apiPath}/es/user")
public class EsUserController extends BaseController {
    @Resource
    EsUserService esUserService;

    @ApiOperation(value = "添加用户",notes = "Request-DateTime样例：2023-01-01 00:00:00")
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
                    defaultValue = "2023-01-01 00:00:00")
    })
    @PostMapping
    public CommonResult add(@Validated(Create.class) @RequestBody EsUserVO esUserVO, BindingResult bindingResult) {
        logger.info("add user|param: {}",esUserVO);
        CommonResult result = new CommonResult().init();
//        参数校验
        if (bindingResult.hasErrors()) {
            return (CommonResult) result.failIllegalArgument(bindingResult.getFieldErrors()).end();
        }

        EsUser esUser = new EsUser();
        BeanCustomUtils.copyProperties(esUserVO, esUser);
//        角色字段校验
        String[] roles = {EsUser.TEACHER, EsUser.STUDENT};
        if (StringUtils.isNotBlank(esUser.getRole())&&!StringUtils.containsAny(esUser.getRole(),roles)) {
            result.error(MsgCodeUtils.MSG_CODE_ILLEGAL_ARGUMENT);
            result.setErrMsg(result.getErrMsg()+"role字段只允许'teacher'和'student'两种字段");
            logger.info("add user|role error: {}",esUserVO);
            return (CommonResult) result.end();
        }
//        用户名去重
        if(esUserService.nameExist(esUser.getUsername())){
            result.error(MsgCodeUtils.MSG_CODE_SYSTEM_USER_LOGIN_NAME_EXIST);
            logger.info("add user|name exist error: {}",esUserVO);
            return (CommonResult) result.end();
        }
//        增操作
        if(esUserService.save(esUser)<=0){
            result.error(MsgCodeUtils.MSC_DATA_ADDDATA_ERROR);
            logger.info("add user|save user error: {}",esUserVO);
            return (CommonResult) result.end();
        }

        result.success("esUser",esUser);
        logger.info("add user|success: {}",esUser);
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "删除用户",notes = "Request-DateTime样例：2023-01-01 00:00:00")
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
                    defaultValue = "560284650732253184"
            )
    })
    @DeleteMapping("/{id}")
    public CommonResult delete(@PathVariable("id") String id) {
        logger.info("delete user|param: {}",id);
        CommonResult result = new CommonResult().init();
//        参数校验
        if(StringUtils.isBlank(id)){
            result.error(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            logger.info("delete user|param null error");
            return (CommonResult) result.end();
        }
//        删操作
        EsUser esUser = esUserService.get(id);
        if(esUserService.delete(esUser)<=0){
            result.error(MsgCodeUtils.MSC_DATA_DRODATA_ERROR);
            logger.info("delete user|delete user error: {}",id);
            return (CommonResult) result.end();
        }

        result.success();
        logger.info("delete user|success: {}",id);
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "修改用户",notes = "Request-DateTime样例：2023-01-01 00:00:00")
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
                    defaultValue = "560284650732253184"
            )
    })
    @PutMapping("/{id}")
    public CommonResult update(@PathVariable("id") String id, @Validated(Update.class) @RequestBody EsUserVO esUserVO,BindingResult bindingResult) {
        logger.info("update user|param: {}",id);
        logger.info("update user|param: {}",esUserVO);
        CommonResult result = new CommonResult().init();
//        参数校验
        if (bindingResult.hasErrors()) {
            return (CommonResult) result.failIllegalArgument(bindingResult.getFieldErrors()).end();
        }

        if(StringUtils.isBlank(id)){
            result.error(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            logger.info("update user|param null error");
            return (CommonResult) result.end();
        }
//        角色字段校验
        String[] roles = {"teacher","student"};
        if (StringUtils.isNotBlank(esUserVO.getRole())&&!StringUtils.containsAny(esUserVO.getRole(),roles)) {
            result.error(MsgCodeUtils.MSG_CODE_ILLEGAL_ARGUMENT);
            result.setErrMsg(result.getErrMsg()+"role字段只允许'teacher'和'student'两种字段");
            logger.info("update user|role error: {}",esUserVO);
            return (CommonResult) result.end();
        }
//        存在校验
        EsUser esUser = esUserService.get(id);
        if(esUser==null){
            result.error(MsgCodeUtils.MSG_CODE_DATA_NOT_EXIST);
            result.setErrMsg(result.getErrMsg() + "user");
            logger.info("update user|user null error: {}", id);
            return (CommonResult) result.end();
        }
        BeanCustomUtils.copyProperties(esUserVO,esUser);
//        更新操作
        if(esUserService.save(esUser)<=0){
            result.error(MsgCodeUtils.MSG_DATAITEM_UPDATE_ERRO);
            logger.info("update user|update user error: {}",id);
            return (CommonResult) result.end();
        }

        result.success("esUser",esUser);
        logger.info("update user|success: {}",id);
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "获取单个用户",notes = "Request-DateTime样例：2023-01-01 00:00:00")
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
                    defaultValue = "560284650732253184"
            )
    })
    @GetMapping("/{id}")
    public CommonResult get(@PathVariable("id") String id) {
        logger.info("get user|param: {}",id);
        CommonResult result = new CommonResult().init();
//        参数校验
        if(StringUtils.isBlank(id)){
            result.error(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            logger.info("get user|param null error");
            return (CommonResult) result.end();
        }
//        读操作
        EsUser esUser = esUserService.get(id);
        if(esUser==null){
            result.error(MsgCodeUtils.MSG_CODE_DATA_NOT_EXIST);
            result.setErrMsg(result.getErrMsg() + "user");
            logger.info("get user|user null error: {}", id);
            return (CommonResult) result.end();
        }

        result.success("esUser",esUser);
        logger.info("get user|success: {}",id);
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "获取用户列表",notes = "Request-DateTime样例：2023-01-01 00:00:00")
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
                    defaultValue = "2023-01-01 00:00:00")
    })
    @GetMapping("/list")
    public PageResult<EsUser> list(@Validated @RequestBody(required = false) EsUserPageVO esUserPageVO, BindingResult bindingResult) {
        logger.info("list user|param: {}", esUserPageVO);
        PageResult<EsUser> result = new PageResult<EsUser>().init();
//        参数校验
        if (bindingResult.hasErrors()) {
            return (PageResult<EsUser>) result.failIllegalArgument(bindingResult.getFieldErrors()).end();
        }
//        读操作
        result.success(esUserService.page(esUserPageVO));
        logger.info("list user|success");
        return (PageResult<EsUser>) result.end();
    }

}
