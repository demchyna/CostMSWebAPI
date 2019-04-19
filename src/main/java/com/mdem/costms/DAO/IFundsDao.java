package com.mdem.costms.DAO;

import com.mdem.costms.model.Funds;

import java.util.List;

public interface IFundsDao {
    List<Funds> getFundsByUserId(long userId);
}
