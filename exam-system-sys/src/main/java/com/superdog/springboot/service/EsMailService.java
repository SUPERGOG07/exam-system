package com.superdog.springboot.service;

import com.lczyfz.edp.springboot.core.entity.MailMessage;
import com.lczyfz.edp.springboot.core.service.MailService;
import com.lczyfz.edp.springboot.core.utils.StringUtils;
import com.superdog.springboot.entity.EsPaperStudent;
import com.superdog.springboot.entity.example.EsPaperStudentExample;
import com.superdog.springboot.mapper.EsPaperMapper;
import com.superdog.springboot.mapper.EsPaperStudentMapper;
import com.superdog.springboot.mapper.EsUserMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author superdog
 * @version 2023-3-27
 */
@Service
public class EsMailService extends MailService {

    @Resource
    EsPaperStudentMapper esPaperStudentMapper;

    @Resource
    EsPaperMapper esPaperMapper;

    @Resource
    EsUserMapper esUserMapper;

    @Async
    public void checkAndSendEmail(String paper) {
        int num = check(paper);
        if (num > 0) {
            System.out.println("===================================================");
            System.out.println("send email");
            String teacher = esPaperMapper.selectByPrimaryKey(paper).getOwner();
            String email = esUserMapper.selectByPrimaryKey(teacher).getEmail();
            if (StringUtils.isNotBlank(email)) {
                sendEmail(email, num);
            }
        }
    }

    private int check(String paper) {
        EsPaperStudentExample esPaperStudentExample = new EsPaperStudentExample();
        esPaperStudentExample.createCriteria().andPaperEqualTo(paper).andDelFlagEqualTo(EsPaperStudent.DEL_FLAG_NORMAL).andSubmitFlagEqualTo(EsPaperStudent.SUBMIT_YES);

        Calendar timeLine = Calendar.getInstance();
        timeLine.add(Calendar.MINUTE, -50);
        Date dateLine = timeLine.getTime();

        esPaperStudentExample.getOredCriteria().get(0).andEndTimeLessThan(dateLine);

        return esPaperStudentMapper.countByExample(esPaperStudentExample);
    }

    private void sendEmail(String email, int num) {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        MailMessage message = new MailMessage();
        message.setSendTo(email);
        message.setSubject("考试管理系统批改通知");
        String msg = String.format("当前还有%d份试卷未批改，请及时修改", num) + "\n" + time;
        message.setContent(msg);
        sendMessageCarryFile(message);
    }
}
