package com.mdem.cms.DAO;

import com.mdem.cms.model.Outlay;

import java.util.List;

public interface IOutlayDao {
    List<Outlay> getOutlaysByUserId(long userId);
}
