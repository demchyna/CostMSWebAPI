package com.mdem.cms.controller;

import com.mdem.cms.DTO.IndicatorDto;
import com.mdem.cms.model.Indicator;
import com.mdem.cms.model.Meter;
import com.mdem.cms.model.Tariff;
import com.mdem.cms.service.impl.IndicatorService;
import com.mdem.cms.service.impl.MeterService;
import com.mdem.cms.service.impl.TariffService;
import com.mdem.cms.transformer.IndicatorTransformer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController("indicatorController")
@RequestMapping("api/indicator")
@Api(tags = {"Indicator"}, description = "Operations for work with indicators of meters")
public class IndicatorController {

    private IndicatorService indicatorService;
    private MeterService meterService;
    private TariffService tariffService;

    @Autowired
    public IndicatorController(IndicatorService indicatorService, MeterService meterService, TariffService tariffService) {
        this.indicatorService = indicatorService;
        this.meterService = meterService;
        this.tariffService = tariffService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @indicatorController.getUserId(#indicatorDto.meterId) == authentication.details.id)")
    @ApiOperation(value = "Add a new indicator")
    public void createIndicator(@Validated @RequestBody IndicatorDto indicatorDto) {
        Meter meter = meterService.getById(indicatorDto.getMeterId());
        Tariff tariff = tariffService.getById(indicatorDto.getTariffId());
        Indicator indicator = IndicatorTransformer.toIndicatorEntity(indicatorDto, meter, tariff);
        indicatorService.create(indicator);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('ADMIN') or (hasRole('USER') and @indicatorController.getUserId(returnObject.meterId) == authentication.details.id)")
    @ApiOperation(value = "Search a indicator with an ID", response = Indicator.class)
    public IndicatorDto getIndicatorById(@PathVariable Long id) {
        Indicator currentIndicator = indicatorService.getById(id);
        Indicator previousIndicator = null;
        if (currentIndicator.getPreviousId() != null) {
            previousIndicator = indicatorService.getById(currentIndicator.getPreviousId());
        }
        return IndicatorTransformer.toIndicatorDto(currentIndicator, previousIndicator);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @indicatorController.getUserId(#indicatorDto.meterId) == authentication.details.id)")
    @ApiOperation(value = "Update an existing indicator")
    public void updateIndicator(@Validated @RequestBody IndicatorDto indicatorDto) {
        Meter meter = meterService.getById(indicatorDto.getMeterId());
        Tariff tariff = tariffService.getById(indicatorDto.getTariffId());
        Indicator indicator = IndicatorTransformer.toIndicatorEntity(indicatorDto, meter, tariff);
        indicatorService.update(indicator);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @indicatorController.getUserId(#indicatorDto.meterId) == authentication.details.id)")
    @ApiOperation(value = "Delete an existing indicator")
    public void deleteIndicator(@Validated @RequestBody IndicatorDto indicatorDto) {
        Meter meter = meterService.getById(indicatorDto.getMeterId());
        Tariff tariff = tariffService.getById(indicatorDto.getTariffId());
        Indicator indicator = IndicatorTransformer.toIndicatorEntity(indicatorDto, meter, tariff);
        indicatorService.delete(indicator);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "View a list of available indicators", response = Iterable.class)
    public List<IndicatorDto> getAllIndicators() {
        List<Indicator> indicators = indicatorService.getAll();
        List<IndicatorDto> indicatorsDto = new ArrayList<>();
        return getIndicatorsDto(indicators, indicatorsDto);
    }

    @RequestMapping(value = "/meter/{meterId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('ADMIN') or (hasRole('USER') and @indicatorController.getUserId(#meterId) == authentication.details.id)")
    @ApiOperation(value = "View a list of available indicators for selected meter", response = Iterable.class)
    public List<IndicatorDto> getIndicatorsByMeterId(@PathVariable Long meterId) {
        List<Indicator> indicators = indicatorService.getIndicatorsByMeterId(meterId);
        List<IndicatorDto> indicatorsDto = new ArrayList<>();
        return getIndicatorsDto(indicators, indicatorsDto);
    }

    @RequestMapping(value = "/last/meter/{meterId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('ADMIN') or (hasRole('USER') and @indicatorController.getUserId(#meterId) == authentication.details.id)")
    @ApiOperation(value = "Search a last added indicator for selected meter", response = Iterable.class)
    public Indicator getLastAddedIndicator(@PathVariable Long meterId) {
        return indicatorService.getLastAddedIndicatorByMeterId(meterId);
    }

    private List<IndicatorDto> getIndicatorsDto(List<Indicator> indicators, List<IndicatorDto> indicatorsDto) {
        Indicator previousIndicator = null;
        for (Indicator currentIndicator : indicators) {
            if (currentIndicator.getPreviousId() != null) {
                previousIndicator = indicatorService.getById(currentIndicator.getPreviousId());
            }
            IndicatorDto indicatorDto = IndicatorTransformer.toIndicatorDto(currentIndicator, previousIndicator);
            indicatorsDto.add(indicatorDto);
        }
        return indicatorsDto;
    }

    public Long getUserId(Long meterId) {
        return meterService.getById(meterId).getCategory().getUser().getId();
    }
}
