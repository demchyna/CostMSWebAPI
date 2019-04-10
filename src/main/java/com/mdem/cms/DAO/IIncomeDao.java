package com.mdem.cms.DAO;

import com.mdem.cms.model.Income;

import java.util.List;

public interface IIncomeDao {
    List<Income> getIncomesByUserId(long userId);
}
