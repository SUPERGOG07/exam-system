package com.superdog.springboot.service;

import com.lczyfz.edp.springboot.core.entity.Page;
import com.lczyfz.edp.springboot.core.service.CrudService;
import com.lczyfz.edp.springboot.core.utils.StringUtils;
import com.superdog.springboot.entity.EsPaper;
import com.superdog.springboot.entity.example.EsPaperExample;
import com.superdog.springboot.entity.example.EsPaperStudentExample;
import com.superdog.springboot.mapper.EsPaperMapper;
import com.superdog.springboot.mapper.EsPaperStudentMapper;
import com.superdog.springboot.vo.EsPaperPageVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author superdog
 * @version 2023-3-27
 */
@Service
@Transactional(readOnly = true)
public class EsPaperService extends CrudService<EsPaperMapper, EsPaper, EsPaperExample> {

    @Resource
    EsPaperStudentMapper esPaperStudentMapper;

    @Override
    public int delete(EsPaper entity) {
        String paperId = entity.getId();

//        删除试卷关联学生
        EsPaperStudentExample esPaperStudentExample = new EsPaperStudentExample();
        esPaperStudentExample.createCriteria().andPaperEqualTo(paperId);
        esPaperStudentMapper.deleteByExample(esPaperStudentExample);

        return super.delete(entity);
    }

    public Page<EsPaper> page(EsPaperPageVO esPaperPageVO) {
        Page<EsPaper> page = new Page<>(esPaperPageVO.getPageNo(), esPaperPageVO.getPageSize(), esPaperPageVO.getOrderBy());

        EsPaperExample esPaperExample = new EsPaperExample();
        esPaperExample.createCriteria().andDelFlagEqualTo(EsPaper.DEL_FLAG_NORMAL);
//        是否限定出卷者
        if (StringUtils.isNotBlank(esPaperPageVO.getOwner())) {
            esPaperExample.getOredCriteria().get(0).andOwnerEqualTo(esPaperPageVO.getOwner());
        }
//        排序
        if (StringUtils.isNotBlank(esPaperPageVO.getOrderBy())) {
            esPaperExample.setOrderByClause(esPaperPageVO.getOrderBy());
        }

        page.setCount(mapper.countByExample(esPaperExample));

        esPaperExample.setLimitStart(page.getFirstResult());
        esPaperExample.setPageSize(page.getPageSize());

        page.setList(mapper.selectByExample(esPaperExample));

        return page;
    }
}
