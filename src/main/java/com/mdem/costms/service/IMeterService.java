package com.mdem.costms.service;

import com.mdem.costms.model.Meter;

import java.util.List;

public interface IMeterService {
    List<Meter> getMetersByCategoryId(long categoryId);
}
