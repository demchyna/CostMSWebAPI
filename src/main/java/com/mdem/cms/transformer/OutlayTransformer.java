package com.mdem.cms.transformer;

import com.mdem.cms.DTO.OutlayDto;
import com.mdem.cms.model.Outlay;
import com.mdem.cms.model.User;

public class OutlayTransformer {
    public static OutlayDto toOutlayDto(Outlay outlay) {
        return new OutlayDto(
                outlay.getId(),
                outlay.getDate(),
                outlay.getSource(),
                outlay.getValue(),
                outlay.getCurrency(),
                outlay.getDescription(),
                outlay.getUser().getId()
        );
    }

    public static Outlay toOutlayEntity(OutlayDto outlayDto, User user) {
        Outlay outlay = new Outlay();

        if (outlayDto.getId() != null) {
            outlay.setId(outlayDto.getId());
        }
        outlay.setDate(outlayDto.getDate());
        outlay.setSource(outlayDto.getSource());
        outlay.setValue(outlayDto.getValue());
        outlay.setCurrency(outlayDto.getCurrency());
        outlay.setDescription(outlayDto.getDescription());
        outlay.setUser(user);

        return outlay;
    }
}
