package com.superdog.springboot.mapper;

import com.lczyfz.edp.springboot.core.mapper.CrudMapper;
import com.superdog.springboot.entity.EsUser;
import com.superdog.springboot.entity.example.EsUserExample;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EsUserMapper extends CrudMapper<EsUser, EsUserExample> {

}