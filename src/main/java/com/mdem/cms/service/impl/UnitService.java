package com.mdem.cms.service.impl;

import com.mdem.cms.model.Unit;
import com.mdem.cms.service.common.AbstractService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UnitService extends AbstractService<Unit, Long> {
}
