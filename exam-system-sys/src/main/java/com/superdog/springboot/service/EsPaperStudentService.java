package com.superdog.springboot.service;

import com.lczyfz.edp.springboot.core.service.CrudService;
import com.superdog.springboot.entity.EsPaperStudent;
import com.superdog.springboot.entity.example.EsPaperStudentExample;
import com.superdog.springboot.mapper.EsPaperStudentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EsPaperStudentService extends CrudService<EsPaperStudentMapper, EsPaperStudent, EsPaperStudentExample> {
}
