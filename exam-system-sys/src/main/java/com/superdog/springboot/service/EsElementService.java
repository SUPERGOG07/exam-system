package com.superdog.springboot.service;

import com.lczyfz.edp.springboot.core.entity.Page;
import com.lczyfz.edp.springboot.core.service.CrudService;
import com.lczyfz.edp.springboot.core.utils.StringUtils;
import com.superdog.springboot.entity.EsElement;
import com.superdog.springboot.entity.example.EsElementExample;
import com.superdog.springboot.mapper.EsElementMapper;
import com.superdog.springboot.vo.EsElementPageVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author superdog
 * @version 2023-3-27
 */
@Service
@Transactional(readOnly = true)
public class EsElementService extends CrudService<EsElementMapper, EsElement, EsElementExample> {

    public Page<EsElement> page(EsElementPageVO esElementPageVO) {
        Page<EsElement> page = new Page<>(esElementPageVO.getPageNo(), esElementPageVO.getPageSize(), esElementPageVO.getOrderBy());

        EsElementExample esElementExample = new EsElementExample();
        esElementExample.createCriteria().andDelFlagEqualTo(EsElement.DEL_FLAG_NORMAL);
//        是否限定出题者
        if(StringUtils.isNotBlank(esElementPageVO.getOwner())){
            esElementExample.getOredCriteria().get(0).andOwnerEqualTo(esElementPageVO.getOwner());
        }
//        排序
        if (StringUtils.isNotBlank(esElementPageVO.getOrderBy())) {
            esElementExample.setOrderByClause(esElementPageVO.getOrderBy());
        }

        page.setCount(mapper.countByExample(esElementExample));

        esElementExample.setLimitStart(page.getFirstResult());
        esElementExample.setPageSize(page.getPageSize());

        page.setList(mapper.selectByExample(esElementExample));

        return page;
    }
}
