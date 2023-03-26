package com.superdog.springboot.mapper;

import com.lczyfz.edp.springboot.core.mapper.CrudMapper;
import com.superdog.springboot.entity.EsPaper;
import com.superdog.springboot.entity.example.EsPaperExample;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EsPaperMapper extends CrudMapper<EsPaper, EsPaperExample> {

}