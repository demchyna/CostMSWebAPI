package com.mdem.cms.service;

import com.mdem.cms.model.Meter;

import java.util.List;

public interface IMeterService {
    List<Meter> getMetersByCategoryId(long categoryId);
}
