package com.mdem.costms.DAO;

import com.mdem.costms.model.Outlay;

import java.util.List;

public interface IOutlayDao {
    List<Outlay> getOutlaysByUserId(long userId);
}
