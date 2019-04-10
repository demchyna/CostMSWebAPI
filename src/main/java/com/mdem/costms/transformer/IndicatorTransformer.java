package com.mdem.costms.transformer;

import com.mdem.costms.DTO.IndicatorDto;
import com.mdem.costms.model.Indicator;
import com.mdem.costms.model.Meter;
import com.mdem.costms.model.Tariff;

import java.math.BigDecimal;

public class IndicatorTransformer {
    public static IndicatorDto toIndicatorDto(Indicator currentIndicator, Indicator previousIndicator) {

        Long previousValue = 0L;
        Long previousId = null;

        if (previousIndicator != null) {
            previousValue = previousIndicator.getCurrent();
            previousId = previousIndicator.getId();
        }

        return new IndicatorDto(
                currentIndicator.getId(),
                currentIndicator.getCurrent(),
                currentIndicator.getDate(),
                currentIndicator.getPayment(),
                currentIndicator.getDescription(),
                previousValue,
                previousId,
                currentIndicator.getMeter().getId(),
                currentIndicator.getTariff().getId(),
                currentIndicator.getTariff().getRate(),
                currentIndicator.getTariff().getCurrency(),
                currentIndicator.getTariff().getUnit().getName(),
                calculatePrice(currentIndicator, previousValue)
        );
    }

    public static Indicator toIndicatorEntity(IndicatorDto indicatorDto, Meter meter, Tariff tariff) {
        Indicator indicator = new Indicator();

        if (indicatorDto.getId() != null) {
            indicator.setId(indicatorDto.getId());
        }
        indicator.setCurrent(indicatorDto.getCurrent());
        indicator.setDate(indicatorDto.getDate());
        indicator.setPayment(indicatorDto.getPayment());
        indicator.setDescription(indicatorDto.getDescription());
        indicator.setPreviousId(indicatorDto.getPreviousId());
        indicator.setMeter(meter);
        indicator.setTariff(tariff);

        return indicator;
    }

    private static BigDecimal calculatePrice(Indicator currentIndicator, Long previousIndicator) {
        return BigDecimal.valueOf(currentIndicator.getCurrent() - previousIndicator)
                .multiply(currentIndicator.getTariff().getRate());
    }
}
