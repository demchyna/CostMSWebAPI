package com.mdem.cms.DAO;

import com.mdem.cms.model.Tariff;

import java.util.List;

public interface ITariffDao {
    List<Tariff> getTariffsByCategoryId(long categoryId);

}
