package com.superdog.springboot.mapper;

import com.lczyfz.edp.springboot.core.mapper.CrudMapper;
import com.superdog.springboot.entity.EsPaperStudent;
import com.superdog.springboot.entity.example.EsPaperStudentExample;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EsPaperStudentMapper  extends CrudMapper<EsPaperStudent, EsPaperStudentExample> {

}