package com.superdog.springboot.service;

import com.lczyfz.edp.springboot.core.service.CrudService;
import com.superdog.springboot.entity.EsPaper;
import com.superdog.springboot.entity.EsPaperStudent;
import com.superdog.springboot.entity.EsUser;
import com.superdog.springboot.entity.example.EsPaperStudentExample;
import com.superdog.springboot.mapper.EsPaperMapper;
import com.superdog.springboot.mapper.EsPaperStudentMapper;
import com.superdog.springboot.mapper.EsUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author superdog
 * @version 2023-3-27
 */
@Service
@Transactional
public class EsPaperStudentService extends CrudService<EsPaperStudentMapper, EsPaperStudent, EsPaperStudentExample> {

    @Resource
    EsPaperMapper esPaperMapper;

    @Resource
    EsPaperStudentMapper esPaperStudentMapper;

    @Resource
    EsUserMapper esUserMapper;

    public int addList(String paperId, List<String> students) {
        int cnt = 0;

        for (String student : students) {

            EsUser esUser = esUserMapper.selectByPrimaryKey(student);
            if (esUser == null || esUser.getDelFlag().equals(EsUser.DEL_FLAG_DELETE) || !esUser.getRole().equals(EsUser.STUDENT)) {
                continue;
            }

            EsPaperStudentExample esPaperStudentExample = new EsPaperStudentExample();
            esPaperStudentExample.createCriteria().andPaperEqualTo(paperId).andStudentEqualTo(student)
                    .andSubmitFlagEqualTo(EsPaperStudent.SUBMIT_NO);

            List<EsPaperStudent> list = mapper.selectByExample(esPaperStudentExample);
            EsPaperStudent esPaperStudent;
            if (list == null || list.isEmpty()) {
                esPaperStudent = new EsPaperStudent();
                esPaperStudent.setPaper(paperId);
                esPaperStudent.setStudent(student);
                esPaperStudent.setSubmitFlag(EsPaperStudent.SUBMIT_NO);
            } else {
                esPaperStudent = list.get(0);
                esPaperStudent.setDelFlag(EsPaperStudent.DEL_FLAG_NORMAL);
            }
            if (save(esPaperStudent) > 0) {
                cnt++;
            }
        }
        return cnt;
    }

    private boolean paperStudentExist(String paperId, String studentId) {
        EsPaperStudentExample esPaperStudentExample = new EsPaperStudentExample();
        esPaperStudentExample.createCriteria().andPaperEqualTo(paperId).andStudentEqualTo(studentId)
                .andSubmitFlagEqualTo(EsPaperStudent.SUBMIT_NO);

        return mapper.countByExample(esPaperStudentExample) > 0;
    }

    public int cancel(String paperId) {
        int cnt = 0;

        EsPaperStudentExample esPaperStudentExample = new EsPaperStudentExample();
        esPaperStudentExample.createCriteria().andPaperEqualTo(paperId);

        List<EsPaperStudent> list = mapper.selectByExample(esPaperStudentExample);
        if (list == null || list.isEmpty()) {
            return 0;
        }

        for (EsPaperStudent esPaperStudent : list) {
            if (delete(esPaperStudent) > 0) {
                cnt++;
            }
        }


        return cnt;
    }

    public List<EsPaper> getPaperListByStudent(String student) {
        EsPaperStudentExample esPaperStudentExample = new EsPaperStudentExample();
        esPaperStudentExample.createCriteria().andStudentEqualTo(student).andDelFlagEqualTo(EsPaperStudent.DEL_FLAG_NORMAL).andSubmitFlagEqualTo(EsPaperStudent.SUBMIT_NO);

        List<EsPaper> result = new ArrayList<>();
        mapper.selectByExample(esPaperStudentExample).forEach(esPaperStudent -> {
            EsPaper esPaper = esPaperMapper.selectByPrimaryKey(esPaperStudent.getPaper());
            result.add(esPaper);
        });
        return result;
    }

    public EsPaperStudent getWithPaperAndStudent(String paper, String student) {
        EsPaperStudentExample esPaperStudentExample = new EsPaperStudentExample();
        esPaperStudentExample.createCriteria().andPaperEqualTo(paper).andStudentEqualTo(student)
                .andDelFlagEqualTo(EsPaperStudent.DEL_FLAG_NORMAL).andSubmitFlagEqualTo(EsPaperStudent.SUBMIT_NO);

        List<EsPaperStudent> list = mapper.selectByExample(esPaperStudentExample);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
