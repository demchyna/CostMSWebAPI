package com.mdem.costms.DAO;

import com.mdem.costms.model.Tariff;

import java.util.List;

public interface ITariffDao {
    List<Tariff> getTariffsByCategoryId(long categoryId);

}
