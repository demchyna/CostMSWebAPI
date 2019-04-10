package com.mdem.cms.DAO;

import com.mdem.cms.model.Meter;

import java.util.List;

public interface IMeterDao {
    List<Meter> getMetersByCategoryId(long categoryId);
}
