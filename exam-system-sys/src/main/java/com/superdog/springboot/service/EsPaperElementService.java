package com.superdog.springboot.service;

import com.lczyfz.edp.springboot.core.service.CrudService;
import com.lczyfz.edp.springboot.core.utils.StringUtils;
import com.superdog.springboot.entity.EsElement;
import com.superdog.springboot.entity.EsPaper;
import com.superdog.springboot.entity.EsPaperElement;
import com.superdog.springboot.entity.example.EsPaperElementExample;
import com.superdog.springboot.mapper.EsElementMapper;
import com.superdog.springboot.mapper.EsPaperElementMapper;
import com.superdog.springboot.mapper.EsPaperMapper;
import com.superdog.springboot.vo.EsPaperElementDetailVO;
import com.superdog.springboot.vo.EsPaperElementVO;
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
public class EsPaperElementService extends CrudService<EsPaperElementMapper, EsPaperElement, EsPaperElementExample> {

    @Resource
    EsPaperMapper esPaperMapper;

    @Resource
    EsElementMapper esElementMapper;

    public int addList(String paperId, List<EsPaperElementVO> esPaperElementVOS) {

        int successNum = 0;

        EsPaper esPaper = esPaperMapper.selectByPrimaryKey(paperId);

        for (EsPaperElementVO esPaperElementVO : esPaperElementVOS) {
            String elementId = esPaperElementVO.getElement();
            Integer orderNum = esPaperElementVO.getOrderNum();
            if (StringUtils.isNotBlank(elementId) && orderNum != null && orderNum >= 0
                    && elementExist(elementId) && !paperElementExist(paperId, elementId)) {
                EsPaperElement esPaperElement = new EsPaperElement();
                esPaperElement.setPaper(paperId);
                esPaperElement.setElement(elementId);
                esPaperElement.setOrderNum(orderNum);

                if (save(esPaperElement) > 0) {
                    esPaper.setCount(esPaper.getCount() + 1);
                    successNum++;
                }

            }
        }

        esPaperMapper.updateByPrimaryKey(esPaper);

        return successNum;
    }

    private boolean elementExist(String elementId) {
        return esElementMapper.selectByPrimaryKey(elementId) != null;
    }

    private boolean paperElementExist(String paper, String element) {
        EsPaperElementExample esPaperElementExample = new EsPaperElementExample();
        esPaperElementExample.createCriteria().andPaperEqualTo(paper).andElementEqualTo(element).andDelFlagEqualTo(EsPaperElement.DEL_FLAG_NORMAL);
        return mapper.countByExample(esPaperElementExample) > 0;
    }

    public int deleteList(String paperId, List<String> elements) {

        int successNum = 0;

        EsPaper esPaper = esPaperMapper.selectByPrimaryKey(paperId);

        for (String element : elements) {
            EsPaperElementExample esPaperElementExample = new EsPaperElementExample();
            esPaperElementExample.createCriteria().andPaperEqualTo(paperId).andElementEqualTo(element);

            List<EsPaperElement> list = mapper.selectByExample(esPaperElementExample);
            if (list != null && list.size() == 1) {
                EsPaperElement esPaperElement = list.get(0);

                if (delete(esPaperElement) > 0) {
                    esPaper.setCount(esPaper.getCount() - 1);
                    successNum++;
                }
            }

        }
        return successNum;
    }

    public boolean updateElementOrder(String paperId, EsPaperElementVO esPaperElementVO) {

        String elementId = esPaperElementVO.getElement();
        Integer order = esPaperElementVO.getOrderNum();

        EsPaperElementExample esPaperElementExample = new EsPaperElementExample();
        esPaperElementExample.createCriteria().andPaperEqualTo(paperId).andElementEqualTo(elementId).andDelFlagEqualTo(EsPaperElement.DEL_FLAG_NORMAL);

        List<EsPaperElement> list = mapper.selectByExample(esPaperElementExample);
        if (list == null || list.isEmpty()) {
            return false;
        }
        EsPaperElement esPaperElement = list.get(0);
        esPaperElement.setOrderNum(order);
        return mapper.updateByPrimaryKey(esPaperElement) > 0;
    }

    public List<EsPaperElementDetailVO> getElements(String paperId) {
        EsPaperElementExample esPaperElementExample = new EsPaperElementExample();
        esPaperElementExample.createCriteria().andPaperEqualTo(paperId).andDelFlagEqualTo(EsPaperElement.DEL_FLAG_NORMAL);
        esPaperElementExample.setOrderByClause("order_num asc");

        List<EsPaperElement> esPaperElements = mapper.selectByExample(esPaperElementExample);

        List<EsPaperElementDetailVO> result = new ArrayList<>();
        esPaperElements.forEach(esPaperElement -> {
            EsElement esElement = esElementMapper.selectByPrimaryKey(esPaperElement.getElement());
            EsPaperElementDetailVO esPaperElementDetailVO = new EsPaperElementDetailVO();
            esPaperElementDetailVO.setQuestion(esElement.getQuestion());
            esPaperElementDetailVO.setAnswer(esElement.getAnswer());
            esPaperElementDetailVO.setOwner(esElement.getOwner());
            esPaperElementDetailVO.setOrder(esPaperElement.getOrderNum());
            result.add(esPaperElementDetailVO);
        });
        return result;
    }
}
