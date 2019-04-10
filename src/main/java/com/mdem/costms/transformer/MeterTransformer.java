package com.mdem.costms.transformer;

import com.mdem.costms.DTO.MeterDto;
import com.mdem.costms.model.Category;
import com.mdem.costms.model.Meter;
import com.mdem.costms.model.Unit;

public class MeterTransformer {
    public static MeterDto toMeterDto(Meter meter) {
        return new MeterDto(
                meter.getId(),
                meter.getName(),
                meter.getDescription(),
                meter.getCategory().getId(),
                meter.getUnit().getId()
        );
    }

    public static Meter toMeterEntity(MeterDto meterDto, Category category, Unit unit) {
        Meter meter = new Meter();

        if (meterDto.getId() != null) {
            meter.setId(meterDto.getId());
        }
        meter.setName(meterDto.getName());
        meter.setDescription(meterDto.getDescription());
        meter.setCategory(category);
        meter.setUnit(unit);

        return meter;
    }
}
