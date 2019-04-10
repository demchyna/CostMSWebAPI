package com.mdem.costms.DAO;

import com.mdem.costms.model.Income;

import java.util.List;

public interface IIncomeDao {
    List<Income> getIncomesByUserId(long userId);
}
