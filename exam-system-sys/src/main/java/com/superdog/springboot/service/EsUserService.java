package com.superdog.springboot.service;

import com.lczyfz.edp.springboot.core.entity.Page;
import com.lczyfz.edp.springboot.core.service.CrudService;
import com.lczyfz.edp.springboot.core.utils.StringUtils;
import com.superdog.springboot.entity.EsUser;
import com.superdog.springboot.entity.example.EsUserExample;
import com.superdog.springboot.mapper.EsUserMapper;
import com.superdog.springboot.util.EncodeUtils;
import com.superdog.springboot.vo.EsUserPageVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author superdog
 * @version 2023-3-27
 */
@Service
@Transactional(readOnly = true)
public class EsUserService extends CrudService<EsUserMapper, EsUser, EsUserExample> {

    @Override
    public int save(EsUser entity) {
        entity.setPassword(EncodeUtils.encodePassword(entity.getPassword()));
        return super.save(entity);
    }

    public Page<EsUser> page(EsUserPageVO esUserPageVO) {
        Page<EsUser> page = new Page<>(esUserPageVO.getPageNo(), esUserPageVO.getPageSize(), esUserPageVO.getOrderBy());

        EsUserExample esUserExample = new EsUserExample();
        esUserExample.createCriteria().andDelFlagEqualTo(EsUser.DEL_FLAG_NORMAL);

        if (StringUtils.isNotBlank(esUserPageVO.getOrderBy())) {
            esUserExample.setOrderByClause(esUserPageVO.getOrderBy());
        }
        if (StringUtils.isNotBlank(esUserPageVO.getRole())) {
            esUserExample.getOredCriteria().get(0).andRoleEqualTo(esUserPageVO.getRole());
        }

        page.setCount(mapper.countByExample(esUserExample));

        esUserExample.setLimitStart(page.getFirstResult());
        esUserExample.setPageSize(page.getPageSize());

        page.setList(mapper.selectByExample(esUserExample));

        return page;
    }

    /**
     * 用户名重复检测
     *
     * @param username
     * @return
     */
    public boolean nameExist(String username) {
        EsUserExample esUserExample = new EsUserExample();
        esUserExample.createCriteria().andUsernameEqualTo(username);
//        列表不为空，则用户名存在
        return !mapper.selectByExample(esUserExample).isEmpty();
    }

    /**
     * id存在性检测
     *
     * @param id
     * @return
     */
    public boolean idNotExist(String id) {
        return mapper.selectByPrimaryKey(id) == null;
    }

    public boolean checkTeacher(String username, String password) {
        return checkRole(username, password, EsUser.TEACHER);
    }

    public boolean checkStudent(String username, String password) {
        return checkRole(username, password, EsUser.STUDENT);
    }

    private boolean checkRole(String username, String password, String role) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return false;
        }

        EsUserExample esUserExample = new EsUserExample();
        esUserExample.createCriteria().andDelFlagEqualTo(EsUser.DEL_FLAG_NORMAL).andUsernameEqualTo(username);

//        检测用户名唯一性
        List<EsUser> list = mapper.selectByExample(esUserExample);
        if (list.size() != 1) {
            return false;
        }

//        密码检测
        EsUser esUser = list.get(0);
        if (!EncodeUtils.validatePassword(password, esUser.getPassword())) {
            return false;
        }

        return StringUtils.isNotBlank(esUser.getRole()) && StringUtils.equals(esUser.getRole(), role);
    }
}
