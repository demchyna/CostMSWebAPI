package com.mdem.costms.service;

import com.mdem.costms.model.Funds;

import java.util.List;

public interface IFundsService {
    List<Funds> getFundsByUserId(long userId);
}
