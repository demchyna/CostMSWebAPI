package com.mdem.costms.controller;

import com.mdem.costms.DTO.MeterDto;
import com.mdem.costms.model.Category;
import com.mdem.costms.model.Meter;
import com.mdem.costms.model.Unit;
import com.mdem.costms.service.impl.CategoryService;
import com.mdem.costms.service.impl.MeterService;
import com.mdem.costms.service.impl.UnitService;
import com.mdem.costms.transformer.MeterTransformer;
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

@RestController("meterController")
@RequestMapping("api/meter")
@SuppressWarnings("deprecation")
@Api(tags = {"Meter"}, description = "Operations for work with meters")
public class MeterController {

    private final MeterService meterService;
    private final CategoryService categoryService;
    private final UnitService unitService;

    @Autowired
    public MeterController(MeterService meterService, CategoryService categoryService, UnitService unitService) {
        this.meterService = meterService;
        this.categoryService = categoryService;
        this.unitService = unitService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @meterController.getUserId(#meterDto.categoryId) == authentication.details.id)")
    @ApiOperation(value = "Add a new meter")
    public void createMeter(@Validated @RequestBody MeterDto meterDto) {
        Category category = categoryService.getById(meterDto.getCategoryId());
        Unit unit = unitService.getById(meterDto.getUnitId());
        Meter meter = MeterTransformer.toMeterEntity(meterDto, category, unit);
        meterService.create(meter);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('ADMIN') or (hasRole('USER') and @meterController.getUserId(returnObject.categoryId) == authentication.details.id)")
    @ApiOperation(value = "Search a meter with an ID", response = Meter.class)
    public MeterDto getMeterById(@PathVariable Long id) {
        Meter meter = meterService.getById(id);
        return MeterTransformer.toMeterDto(meter);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @meterController.getUserId(#meterDto.categoryId) == authentication.details.id)")
    @ApiOperation(value = "Update an existing meter")
    public void updateMeter(@Validated @RequestBody MeterDto meterDto) {
        Category category = categoryService.getById(meterDto.getCategoryId());
        Unit unit = unitService.getById(meterDto.getUnitId());
        Meter meter = MeterTransformer.toMeterEntity(meterDto, category, unit);
        meterService.update(meter);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @meterController.getUserId(#meterDto.categoryId) == authentication.details.id)")
    @ApiOperation(value = "Delete an existing meter")
    public void deleteMeter(@Validated @RequestBody MeterDto meterDto) {
        Category category = categoryService.getById(meterDto.getCategoryId());
        Unit unit = unitService.getById(meterDto.getUnitId());
        Meter meter = MeterTransformer.toMeterEntity(meterDto, category, unit);
        meterService.delete(meter);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "View a list of available meters", response = Iterable.class)
    public List<MeterDto> getAllMeters() {
        @SuppressWarnings("unchecked") List<Meter> meters = meterService.getAll();
        List<MeterDto> metersDto = new ArrayList<>();
        for (Meter meter : meters) {
            metersDto.add(MeterTransformer.toMeterDto(meter));
        }
        return metersDto;
    }

    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('ADMIN') or (hasRole('USER') and @meterController.getUserId(#categoryId) == authentication.details.id)")
    @ApiOperation(value = "View a list of available meters from selected category", response = Iterable.class)
    public List<MeterDto> getMetersByCategoryIdAndUserId(@PathVariable Long categoryId) {
        List<Meter> meters = meterService.getMetersByCategoryId(categoryId);
        List<MeterDto> metersDto = new ArrayList<>();
        for (Meter meter : meters) {
            metersDto.add(MeterTransformer.toMeterDto(meter));
        }
        return metersDto;
    }

    public Long getUserId(Long categoryId) {
        return categoryService.getById(categoryId).getUser().getId();
    }
}