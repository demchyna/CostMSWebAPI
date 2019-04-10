package com.mdem.costms.service;

import com.mdem.costms.model.Income;

import java.util.List;

public interface IIncomeService {
    List<Income> getIncomesByUserId(long userId);
}
