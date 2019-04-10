package com.mdem.cms.service;

import com.mdem.cms.model.Income;

import java.util.List;

public interface IIncomeService {
    List<Income> getIncomesByUserId(long userId);
}
