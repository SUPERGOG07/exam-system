package com.superdog.springboot.service;

import com.lczyfz.edp.springboot.core.service.CrudService;
import com.superdog.springboot.entity.EsPaperElement;
import com.superdog.springboot.entity.example.EsPaperElementExample;
import com.superdog.springboot.mapper.EsPaperElementMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EsPaperElementService extends CrudService<EsPaperElementMapper, EsPaperElement, EsPaperElementExample> {
}
