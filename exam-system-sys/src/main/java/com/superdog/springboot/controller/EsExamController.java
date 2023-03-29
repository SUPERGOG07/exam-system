package com.superdog.springboot.controller;

import com.lczyfz.edp.springboot.core.controller.BaseController;
import com.lczyfz.edp.springboot.core.entity.CommonResult;
import com.lczyfz.edp.springboot.core.utils.MsgCodeUtils;
import com.lczyfz.edp.springboot.core.utils.StringUtils;
import com.lczyfz.edp.springboot.core.validation.Create;
import com.superdog.springboot.entity.EsPaper;
import com.superdog.springboot.entity.EsPaperStudent;
import com.superdog.springboot.service.EsMailService;
import com.superdog.springboot.service.EsPaperService;
import com.superdog.springboot.service.EsPaperStudentService;
import com.superdog.springboot.service.EsUserService;
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
@Api(value = "EsExamController", tags = "考试管理系统考试接口")
@RestController
@RequestMapping("/${apiPath}/es/exam")
public class EsExamController extends BaseController {

    @Resource
    EsUserService esUserService;

    @Resource
    EsPaperService esPaperService;

    @Resource
    EsPaperStudentService esPaperStudentService;

    @Resource
    EsMailService esMailService;

    @ApiOperation(value = "下发试卷", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    name = "students",
                    value = "学生列表",
                    paramType = "body",
                    dataType = "string",
                    required = true,
                    defaultValue = "123456")
    })
    @PostMapping("/{paper}")
    public CommonResult share(@PathVariable("paper") String paper, @Validated(Create.class) @RequestBody List<String> students, BindingResult bindingResult,
                              @RequestParam String username, @RequestParam String password) {
        logger.info("share paper to students|param: {}", paper);
        logger.info("share paper to students|auth username: {}", username);
        logger.info("share paper to students|auth password: {}", password);

        CommonResult result = new CommonResult().init();
//        参数校验
        if (bindingResult.hasErrors()) {
            return (CommonResult) result.failIllegalArgument(bindingResult.getFieldErrors()).end();
        }
        if (StringUtils.isBlank(paper)) {
            result.error(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            logger.info("share paper to students|param null error");
            return (CommonResult) result.end();
        }
//        权限校验
        if (!esUserService.checkTeacher(username, password)) {
            result.error(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
            logger.info("share paper to students|author error: {}", username);
            return (CommonResult) result.end();
        }
//        试卷存在性验证
        EsPaper esPaper = esPaperService.get(paper);
        if (esPaper == null) {
            result.error(MsgCodeUtils.MSG_CODE_DATA_NOT_EXIST);
            result.setErrMsg(result.getErrMsg() + "paper");
            logger.info("share paper to students|paper not exist: {}", paper);
            return (CommonResult) result.end();
        }

        result.success("num", esPaperStudentService.addList(paper, students));
        logger.info("share paper to students|success");
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "撤回试卷", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    value = "试卷Id",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "123456")
    })
    @DeleteMapping("/{id}")
    public CommonResult cancel(@PathVariable("id") String id,
                               @RequestParam String username, @RequestParam String password) {

        logger.info("cancel sending paper to students|param: {}", id);
        logger.info("cancel sending paper to students|auth username: {}", username);
        logger.info("cancel sending paper to students|auth password: {}", password);

        CommonResult result = new CommonResult().init();
//        参数校验
        if (StringUtils.isBlank(id)) {
            result.error(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            logger.info("cancel sending paper to students|param null error");
            return (CommonResult) result.end();
        }
//        权限校验
        if (!esUserService.checkTeacher(username, password)) {
            result.error(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
            logger.info("cancel sending paper to students|author error: {}", username);
            return (CommonResult) result.end();
        }
//        试卷存在性验证
        EsPaper esPaper = esPaperService.get(id);
        if (esPaper == null) {
            result.error(MsgCodeUtils.MSG_CODE_DATA_NOT_EXIST);
            result.setErrMsg(result.getErrMsg() + "paper");
            logger.info("cancel sending paper to students|paper not exist: {}", id);
            return (CommonResult) result.end();
        }

        result.success("num", esPaperStudentService.cancel(id));
        logger.info("cancel sending paper to students|success: {}", id);
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "学生查看试卷列表", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    name = "student",
                    value = "学生标识",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "123456")
    })
    @GetMapping("/{student}")
    public CommonResult examListByStudent(@PathVariable("student") String student,
                                          @RequestParam String username, @RequestParam String password) {

        logger.info("list paper by student|param: {}", student);
        logger.info("list paper by student|auth username: {}", username);
        logger.info("list paper by student|auth password: {}", password);

        CommonResult result = new CommonResult().init();
//        参数校验
        if (StringUtils.isBlank(student)) {
            result.error(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            logger.info("list paper by student|param null error");
            return (CommonResult) result.end();
        }
//        权限校验
        if (!esUserService.checkStudent(username, password)) {
            result.error(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
            logger.info("list paper by student|author error: {}", username);
            return (CommonResult) result.end();
        }

        result.success("exams", esPaperStudentService.getPaperListByStudent(student));
        logger.info("list paper by student|success: {}", student);
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "开始考试", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    name = "paper",
                    value = "试卷",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "123456"),
            @ApiImplicitParam(
                    name = "student",
                    value = "学生",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "123456")
    })
    @PutMapping("/start/{paper}/{student}")
    public CommonResult startExam(@PathVariable("paper") String paper, @PathVariable("student") String student,
                                  @RequestParam String username, @RequestParam String password) {

        logger.info("start exam|param: {}", paper);
        logger.info("start exam|param: {}", student);
        logger.info("start exam|auth username: {}", username);
        logger.info("start exam|auth password: {}", password);

        CommonResult result = new CommonResult().init();
//        参数校验
        if (StringUtils.isBlank(student) || StringUtils.isBlank(paper)) {
            result.error(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            logger.info("start exam|param null error");
            return (CommonResult) result.end();
        }
//        权限校验
        if (!esUserService.checkStudent(username, password)) {
            result.error(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
            logger.info("start exam|author error: {}", username);
            return (CommonResult) result.end();
        }
//        试卷存在性验证
        EsPaper esPaper = esPaperService.get(paper);
        if (esPaper == null) {
            result.error(MsgCodeUtils.MSG_CODE_DATA_NOT_EXIST);
            result.setErrMsg(result.getErrMsg() + "paper");
            logger.info("cancel sending paper to students|paper not exist: {}", paper);
            return (CommonResult) result.end();
        }
//        存在性验证
        EsPaperStudent esPaperStudent = esPaperStudentService.getWithPaperAndStudent(paper, student);
        if (esPaperStudent == null) {
            result.error(MsgCodeUtils.MSG_CODE_DATA_NOT_EXIST);
            logger.info("start exam|data exist error: {}", paper + "/" + student);
            return (CommonResult) result.end();
        }

//        设置开始时间
        esPaperStudent.setStartTime(new Date());

        if (esPaperStudentService.save(esPaperStudent) <= 0) {
            result.error(MsgCodeUtils.MSG_DATAITEM_UPDATE_ERRO);
            logger.info("start exam|update error: {}", paper + "/" + student);
            return (CommonResult) result.end();
        }

        result.success("esPaperStudent", esPaperStudent);
        logger.info("start exam|success: {}", paper + "/" + student);
        return (CommonResult) result.end();
    }

    @ApiOperation(value = "完成考试", notes = "Request-DateTime样例：2018-09-29 11:26:20")
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
                    name = "paper",
                    value = "试卷",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "123456"),
            @ApiImplicitParam(
                    name = "student",
                    value = "学生",
                    paramType = "path",
                    dataType = "string",
                    required = true,
                    defaultValue = "123456")
    })
    @PutMapping("/end/{paper}/{student}")
    public CommonResult finish(@PathVariable("paper") String paper, @PathVariable("student") String student,
                               @RequestParam String username, @RequestParam String password) {

        logger.info("finish exam|param: {}", paper);
        logger.info("finish exam|param: {}", student);
        logger.info("finish exam|auth username: {}", username);
        logger.info("finish exam|auth password: {}", password);

        CommonResult result = new CommonResult().init();
//        参数校验
        if (StringUtils.isBlank(student) || StringUtils.isBlank(paper)) {
            result.error(MsgCodeUtils.MSG_CODE_ID_IS_NULL);
            logger.info("finish exam|param null error");
            return (CommonResult) result.end();
        }
//        权限校验
        if (!esUserService.checkStudent(username, password)) {
            result.error(MsgCodeUtils.MSG_CODE_NOT_PERMISSION);
            logger.info("finish exam|author error: {}", username);
            return (CommonResult) result.end();
        }
//        试卷存在性验证
        EsPaper esPaper = esPaperService.get(paper);
        if (esPaper == null) {
            result.error(MsgCodeUtils.MSG_CODE_DATA_NOT_EXIST);
            result.setErrMsg(result.getErrMsg() + "paper");
            logger.info("cancel sending paper to students|paper not exist: {}", paper);
            return (CommonResult) result.end();
        }
//        存在性验证
        EsPaperStudent esPaperStudent = esPaperStudentService.getWithPaperAndStudent(paper, student);
        if (esPaperStudent == null) {
            result.error(MsgCodeUtils.MSG_CODE_DATA_NOT_EXIST);
            logger.info("finish exam|data exist error: {}", paper + "/" + student);
            return (CommonResult) result.end();
        }

//        设置结束时间
        esPaperStudent.setEndTime(new Date());
//        设置已提交标记
        esPaperStudent.setSubmitFlag(EsPaperStudent.SUBMIT_YES);

        if (esPaperStudentService.save(esPaperStudent) <= 0) {
            result.error(MsgCodeUtils.MSG_DATAITEM_UPDATE_ERRO);
            logger.info("finish exam|update error: {}", paper + "/" + student);
            return (CommonResult) result.end();
        }

        result.success("esPaperStudent", esPaperStudent);
        logger.info("finish exam|success: {}", paper + "/" + student);
        esMailService.checkAndSendEmail(paper);
        return (CommonResult) result.end();
    }
}
