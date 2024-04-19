package com.red.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.red.common.mapper.SysLogMapper;
import com.red.common.pojo.SysLog;
import com.red.common.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {
    @Autowired
    @Qualifier("sysLogMapper")
    private SysLogMapper sysLogMapper;
}
