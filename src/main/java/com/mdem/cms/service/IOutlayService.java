package com.mdem.cms.service;

import com.mdem.cms.model.Outlay;

import java.util.List;

public interface IOutlayService {
    List<Outlay> getOutlaysByUserId(long userId);
}
