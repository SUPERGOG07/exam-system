package com.superdog.springboot.service;

import com.lczyfz.edp.springboot.core.service.CrudService;
import com.superdog.springboot.entity.EsPaper;
import com.superdog.springboot.entity.example.EsPaperExample;
import com.superdog.springboot.mapper.EsPaperMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EsPaperService extends CrudService<EsPaperMapper, EsPaper, EsPaperExample> {
}
