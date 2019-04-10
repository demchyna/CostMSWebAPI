package com.mdem.cms.model.common;

import java.io.Serializable;

public interface IEntity extends Serializable {
    Long getId();
    void setId(Long id);
}
