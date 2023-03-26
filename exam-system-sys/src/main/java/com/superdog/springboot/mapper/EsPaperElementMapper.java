package com.superdog.springboot.mapper;

import com.lczyfz.edp.springboot.core.mapper.CrudMapper;
import com.superdog.springboot.entity.EsPaperElement;
import com.superdog.springboot.entity.example.EsPaperElementExample;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EsPaperElementMapper extends CrudMapper<EsPaperElement, EsPaperElementExample> {

}