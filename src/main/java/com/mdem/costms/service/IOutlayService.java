package com.mdem.costms.service;

import com.mdem.costms.model.Outlay;

import java.util.List;

public interface IOutlayService {
    List<Outlay> getOutlaysByUserId(long userId);
}
