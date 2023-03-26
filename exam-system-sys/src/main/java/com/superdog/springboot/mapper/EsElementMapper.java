package com.superdog.springboot.mapper;

import com.lczyfz.edp.springboot.core.mapper.CrudMapper;
import com.superdog.springboot.entity.EsElement;
import com.superdog.springboot.entity.example.EsElementExample;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EsElementMapper extends CrudMapper<EsElement, EsElementExample> {
}