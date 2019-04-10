package com.mdem.costms.service.impl;

import com.mdem.costms.model.Unit;
import com.mdem.costms.service.common.AbstractService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UnitService extends AbstractService<Unit, Long> {
}
