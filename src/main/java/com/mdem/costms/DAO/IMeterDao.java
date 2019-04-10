package com.mdem.costms.DAO;

import com.mdem.costms.model.Meter;

import java.util.List;

public interface IMeterDao {
    List<Meter> getMetersByCategoryId(long categoryId);
}
