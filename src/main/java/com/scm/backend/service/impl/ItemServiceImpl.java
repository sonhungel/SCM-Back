package com.scm.backend.service.impl;

import com.scm.backend.service.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ItemServiceImpl implements ItemService {
}
