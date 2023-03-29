package com.superdog.springboot.controller;

import com.lczyfz.edp.springboot.core.controller.BaseController;
import com.lczyfz.edp.springboot.core.entity.CommonResult;
import com.lczyfz.edp.springboot.core.entity.PageResult;
import com.lczyfz.edp.springboot.core.utils.BeanCustomUtils;
import com.lczyfz.edp.springboot.core.utils.MsgCodeUtils;
import com.lczyfz.edp.springboot.core.utils.StringUtils;
import com.lczyfz.edp.springboot.core.validation.Create;
import com.lczyfz.edp.springboot.core.validation.Update;
import com.superdog.springboot.entity.EsPaper;
import com.superdog.springboot.service.EsPaperElementService;
import com.superdog.springboot.service.EsPaperService;
import com.superdog.springboot.service.EsUserService;
import com.superdog.springboot.vo.EsPaperElementVO;
import com.superdog.springboot.vo.EsPaperPageVO;
import com.superdog.springboot.vo.EsPaperVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author superdog
 * @version 2023-3-27
 */
@Api(value = "EsPaperController", tags = "考试管理系统试卷接口")
@RestController
@RequestMapping("/${apiPath}/es/paper")
public class EsPaperController extends BaseController {

    @Resource
    EsUserService esUserService;

    @Resource
    EsPaperService esPaperService;

    @Resource
    EsPaperElementService esPaperElementService;

    @ApiOperation(value = "添加试卷", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
    public CommonResult add(@Validated(Create.class) @RequestBody EsPaperVO esPaperVO, BindingResult bindingResult,
                            @RequestParam String username, @RequestParam String password) {

        logger.info("add paper|param: {}", esPaperVO);
        logger.info("add paper|auth username: {}", username);
        logger.info("add paper|auth password: {}", password);

        CommonResult result = new CommonResult().init();
//        参数校验
        if (bindingResult.hasErrors()) {
            return (CommonResult) result.failIllegalArgument(bindingResult.getFieldErrors()).end();
        }
//        权限校验
        if (!esUserService.checkTeacher(username, password)) {
            result.error(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
            logger.info("add paper|author error: {}", username);
            return (CommonResult) result.end();
        }

        EsPaper esPaper = new EsPaper();
        BeanCustomUtils.copyProperties(esPaperVO, esPaper);
//        出卷人存在性验证
        if (esUserService.idNotExist(esPaper.getOwner())) {
            result.error(MsgCodeUtils.MSG_CODE_DATA_NOT_EXIST);
            result.setErrMsg(result.getErrMsg() + "owner");
            logger.info("add paper|owner not exist error: {}", esPaper);
            return (CommonResult) result.end();
        }
//        日期检查
        Date startTime = esPaper.getStartTime();
        Date endTime = esPaper.getEndTime();
        logger.info("startTime:{}", startTime);
        logger.info("endTime:{}", endTime);
        logger.info("now:{}", new Date());
        if (startTime == null || endTime == null || new Date().after(startTime) || startTime.after(endTime) || startTime.equals(endTime)) {
            result.error(MsgCodeUtils.MSG_CODE_ILLEGAL_ARGUMENT);
            result.setErrMsg(result.getErrMsg() + "startTime不得早于当前时间且endTime不得早于或等于startTime");
            logger.info("add paper|date error: {}", esPaper);
            return (CommonResult) result.end();
        }
//        初始化试卷题目数量为0
        esPaper.setCount(0);

//        增操作
        if (esPaperService.save(esPaper) <= 0) {
            result.error(MsgCodeUtils.MSC_DATA_ADDDATA_ERROR);
            logger.info("add paper|save error: {}", esPaperVO);
            return (CommonResult) result.end();
        }

        result.success("esPaper", esPaper);
        logger.info("add paper|success: {}", esPaper);
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "删除试卷", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    defaultValue = "123456"),
            @ApiImplicitParam(
                    name = "id",
                    value = "id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "123456"
            )
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
            logger.info("delete paper|param null error");
            return (CommonResult) result.end();
        }
//        权限校验
        if (!esUserService.checkTeacher(username, password)) {
            result.error(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
            logger.info("delete paper|author error: {}", username);
            return (CommonResult) result.end();
        }
//        删除操作
        EsPaper esPaper = esPaperService.get(id);
        if (esPaperService.delete(esPaper) <= 0) {
            result.error(MsgCodeUtils.MSC_DATA_DRODATA_ERROR);
            logger.info("delete paper|delete user error: {}", id);
            return (CommonResult) result.end();
        }

        result.success();
        logger.info("delete paper|success: {}", id);
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "更新试卷", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    defaultValue = "123456"),
            @ApiImplicitParam(
                    name = "id",
                    value = "id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "123456"
            ),
            @ApiImplicitParam(
                    name = "id",
                    value = "id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "123456"
            )
    })
    @PutMapping("/{id}")
    public CommonResult update(@PathVariable("id") String id, @Validated(Update.class) @RequestBody EsPaperVO esPaperVO, BindingResult bindingResult,
                               @RequestParam String username, @RequestParam String password) {

        logger.info("update paper|param: {}", id);
        logger.info("update paper|param: {}", esPaperVO);
        logger.info("update paper|auth username: {}", username);
        logger.info("update paper|auth password: {}", password);

        CommonResult result = new CommonResult().init();

        //        参数校验
        if (bindingResult.hasErrors()) {
            return (CommonResult) result.failIllegalArgument(bindingResult.getFieldErrors()).end();
        }
        if (StringUtils.isBlank(id)) {
            result.error(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            logger.info("update paper|param null error");
            return (CommonResult) result.end();
        }
//        权限校验
        if (!esUserService.checkTeacher(username, password)) {
            result.error(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
            logger.info("update paper|author error: {}", username);
            return (CommonResult) result.end();
        }
//        修改出卷人存在性校验
        if (esUserService.idNotExist(esPaperVO.getOwner())) {
            result.error(MsgCodeUtils.MSG_CODE_DATA_NOT_EXIST);
            result.setErrMsg(result.getErrMsg() + "owner");
            logger.info("update paper|owner not exist error: {}", esPaperVO);
            return (CommonResult) result.end();
        }
//        存在校验
        EsPaper esPaper = esPaperService.get(id);
        if (esPaper == null) {
            result.error(MsgCodeUtils.MSG_CODE_DATA_NOT_EXIST);
            result.setErrMsg(result.getErrMsg() + "paper");
            logger.info("update paper|data not exist: {}", id);
            return (CommonResult) result.end();
        }

        esPaperVO.setCount(esPaper.getCount());
        BeanCustomUtils.copyProperties(esPaperVO, esPaper);
//        日期检查
        Date startTime = esPaper.getStartTime();
        Date endTime = esPaper.getEndTime();
        if (startTime == null || endTime == null || new Date(System.currentTimeMillis()).after(startTime) || startTime.after(endTime)) {
            result.error(MsgCodeUtils.MSG_CODE_ILLEGAL_ARGUMENT);
            result.setErrMsg(result.getErrMsg() + "startTime不得早于当前时间且endTime不得早于startTime");
            logger.info("update paper|date error: {}", esPaper);
        }
//        更新操作
        if (esPaperService.save(esPaper) <= 0) {
            result.error(MsgCodeUtils.MSG_DATAITEM_UPDATE_ERRO);
            logger.info("update paper|update error: {}", id);
            return (CommonResult) result.end();
        }

        result.success("esPaper", esPaper);
        logger.info("update paper|success: {}", id);
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "获得单个试卷", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    defaultValue = "123456"),
            @ApiImplicitParam(
                    name = "id",
                    value = "id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "123456"
            )
    })
    @GetMapping("/{id}")
    public CommonResult get(@PathVariable("id") String id,
                            @RequestParam String username, @RequestParam String password) {

        logger.info("get paper|param: {}", id);
        logger.info("get paper|auth username: {}", username);
        logger.info("get paper|auth password: {}", password);
        CommonResult result = new CommonResult().init();
//        参数校验
        if (StringUtils.isBlank(id)) {
            result.error(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            logger.info("get paper|param null error");
            return (CommonResult) result.end();
        }
//        权限校验
        if (!esUserService.checkTeacher(username, password)) {
            result.error(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
            logger.info("get paper|author error: {}", username);
            return (CommonResult) result.end();
        }
//        读操作
        EsPaper esPaper = esPaperService.get(id);
        if (esPaper == null) {
            result.error(MsgCodeUtils.MSG_CODE_DATA_NOT_EXIST);
            result.setErrMsg(result.getErrMsg() + "id");
            logger.info("get paper|paper null error: {}", id);
            return (CommonResult) result.end();
        }

        result.success("esPaper", esPaper);
        logger.info("get paper|success: {}", id);
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "试卷列表", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
    public PageResult<EsPaper> list(@Validated @RequestBody(required = false) EsPaperPageVO esPaperPageVO, BindingResult bindingResult,
                                    @RequestParam String username, @RequestParam String password) {
        logger.info("list paper|param: {}", esPaperPageVO);
        logger.info("list paper|auth username: {}", username);
        logger.info("list paper|auth password: {}", password);

        PageResult<EsPaper> result = new PageResult<EsPaper>().init();
//        参数校验
        if (bindingResult.hasErrors()) {
            return (PageResult<EsPaper>) result.failIllegalArgument(bindingResult.getFieldErrors()).end();
        }
//        权限校验
        if (!esUserService.checkTeacher(username, password)) {
            result.error(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
            logger.info("list paper|author error: {}", username);
            return (PageResult<EsPaper>) result.end();
        }
//        读操作
        result.success(esPaperService.page(esPaperPageVO));
        logger.info("list paper|success");
        return (PageResult<EsPaper>) result.end();
    }

    @ApiOperation(value = "试题批量加入题目", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    defaultValue = "123456"),
            @ApiImplicitParam(
                    name = "id",
                    value = "id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "123456"
            )
    })
    @PostMapping("/element/{id}")
    public CommonResult addElementsToPaper(@PathVariable("id") String id, @RequestBody List<EsPaperElementVO> esPaperElementVOS,
                                           BindingResult bindingResult, @RequestParam String username, @RequestParam String password) {
        logger.info("add elements to paper|param: {}", id);
        logger.info("add elements to paper|param: {}", esPaperElementVOS);
        logger.info("add elements to paper|auth username: {}", username);
        logger.info("add elements to paper|auth password: {}", password);

        CommonResult result = new CommonResult().init();
//        参数校验
        if (bindingResult.hasErrors()) {
            return (CommonResult) result.failIllegalArgument(bindingResult.getFieldErrors()).end();
        }
        if (StringUtils.isBlank(id)) {
            result.error(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            logger.info("add elements to paper|param null error");
            return (CommonResult) result.end();
        }
//        权限校验
        if (!esUserService.checkTeacher(username, password)) {
            result.error(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
            logger.info("add elements to paper|author error: {}", username);
            return (CommonResult) result.end();
        }
//        试卷存在性验证
        EsPaper esPaper = esPaperService.get(id);
        if (esPaper == null) {
            result.error(MsgCodeUtils.MSG_CODE_DATA_NOT_EXIST);
            result.setErrMsg(result.getErrMsg() + "paper");
            logger.info("add elements to paper|paper not exist: {}", id);
            return (CommonResult) result.end();
        }

        esPaperElementVOS.forEach(System.out::println);
        result.success("num", esPaperElementService.addList(id, esPaperElementVOS));
        logger.info("add elements to paper|success");
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "试卷批量删除题目", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    defaultValue = "123456"),
            @ApiImplicitParam(
                    name = "id",
                    value = "id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "123456"
            )
    })
    @DeleteMapping("/element/{id}")
    public CommonResult deleteElementsFromPaper(@PathVariable("id") String id, @RequestBody List<String> elements,
                                                BindingResult bindingResult, @RequestParam String username, @RequestParam String password) {
        logger.info("delete elements from paper|param: {}", id);
        logger.info("delete elements from paper|auth username: {}", username);
        logger.info("delete elements from paper|auth password: {}", password);

        CommonResult result = new CommonResult().init();
//        参数校验
        if (bindingResult.hasErrors()) {
            return (CommonResult) result.failIllegalArgument(bindingResult.getFieldErrors()).end();
        }
        if (StringUtils.isBlank(id)) {
            result.error(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            logger.info("delete elements from paper|param null error");
            return (CommonResult) result.end();
        }
        if (elements == null || elements.isEmpty()) {
            result.error(MsgCodeUtils.MSG_CODE_ILLEGAL_ARGUMENT);
            result.setErrMsg(result.getErrMsg() + "elements");
            logger.info("delete elements from paper|elements null error: {}", elements);
            return (CommonResult) result.end();
        }
//        权限校验
        if (!esUserService.checkTeacher(username, password)) {
            result.error(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
            logger.info("delete elements from paper|author error: {}", username);
            return (CommonResult) result.end();
        }
//        试卷存在性验证
        EsPaper esPaper = esPaperService.get(id);
        if (esPaper == null) {
            result.error(MsgCodeUtils.MSG_CODE_DATA_NOT_EXIST);
            result.setErrMsg(result.getErrMsg() + "paper");
            logger.info("delete elements from paper|paper not exist: {}", id);
            return (CommonResult) result.end();
        }

        result.success("num", esPaperElementService.deleteList(id, elements));
        logger.info("delete elements from paper|success");
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "修改试卷中题目", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    defaultValue = "123456"),
            @ApiImplicitParam(
                    name = "id",
                    value = "id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "123456"
            )
    })
    @PutMapping("/element/{id}")
    public CommonResult updateElementOrderInPaper(@PathVariable("id") String id, @Validated(Update.class) @RequestBody EsPaperElementVO esPaperElementVO,
                                                  BindingResult bindingResult, @RequestParam String username, @RequestParam String password) {

        logger.info("update element order in paper|param: {}", id);
        logger.info("update element order in paper|auth username: {}", username);
        logger.info("update element order in paper|auth password: {}", password);


        CommonResult result = new CommonResult().init();
//        参数校验
        if (bindingResult.hasErrors()) {
            return (CommonResult) result.failIllegalArgument(bindingResult.getFieldErrors()).end();
        }
        if (StringUtils.isBlank(id)) {
            result.error(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            logger.info("update element order in paper|param null error");
            return (CommonResult) result.end();
        }
//        权限校验
        if (!esUserService.checkTeacher(username, password)) {
            result.error(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
            logger.info("update element order in paper|author error: {}", username);
            return (CommonResult) result.end();
        }
//        试卷存在性验证
        EsPaper esPaper = esPaperService.get(id);
        if (esPaper == null) {
            result.error(MsgCodeUtils.MSG_CODE_DATA_NOT_EXIST);
            result.setErrMsg(result.getErrMsg() + "paper");
            logger.info("update element order in paper|paper not exist: {}", id);
            return (CommonResult) result.end();
        }
//        更新操作
        if (!esPaperElementService.updateElementOrder(id, esPaperElementVO)) {
            result.error(MsgCodeUtils.MSG_DATAITEM_UPDATE_ERRO);
            logger.info("update element order in paper|update error: {}", id);
            return (CommonResult) result.end();
        }

        result.success();
        logger.info("update element order in paper|success: {}", id);
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "获得单个试卷的试题集", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    defaultValue = "123456"),
            @ApiImplicitParam(
                    name = "id",
                    value = "id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "123456"
            )
    })
    @GetMapping("/element/{id}")
    public CommonResult getPaperElements(@PathVariable("id") String id,
                                         @RequestParam String username, @RequestParam String password) {

        logger.info("get elements from paper|param: {}", id);
        logger.info("get elements from paper|auth username: {}", username);
        logger.info("get elements from paper|auth password: {}", password);


        CommonResult result = new CommonResult().init();
//        参数校验
        if (StringUtils.isBlank(id)) {
            result.error(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            logger.info("get elements from paper|param null error");
            return (CommonResult) result.end();
        }
//        权限校验
        if (!esUserService.checkTeacher(username, password)) {
            result.error(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
            logger.info("get elements from paper|author error: {}", username);
            return (CommonResult) result.end();
        }
//        试卷存在性验证
        EsPaper esPaper = esPaperService.get(id);
        if (esPaper == null) {
            result.error(MsgCodeUtils.MSG_CODE_DATA_NOT_EXIST);
            result.setErrMsg(result.getErrMsg() + "paper");
            logger.info("get elements from paper|paper not exist: {}", id);
            return (CommonResult) result.end();
        }

        result.success("elements", esPaperElementService.getElements(id));
        logger.info("get elements from paper|success: {}", id);
        return (CommonResult) result.end();
    }

}
