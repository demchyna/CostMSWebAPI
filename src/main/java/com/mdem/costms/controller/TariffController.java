package com.mdem.costms.controller;

import com.mdem.costms.DTO.TariffDto;
import com.mdem.costms.model.Category;
import com.mdem.costms.model.Tariff;
import com.mdem.costms.model.Unit;
import com.mdem.costms.service.impl.CategoryService;
import com.mdem.costms.service.impl.TariffService;
import com.mdem.costms.service.impl.UnitService;
import com.mdem.costms.transformer.TariffTransformer;
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

@RestController("tariffController")
@RequestMapping("api/tariff")
@Api(tags = {"Tariff"}, description = "Operations for work with tariffs on services")
public class TariffController {

    private TariffService tariffService;
    private CategoryService categoryService;
    private UnitService unitService;

    @Autowired
    public TariffController(TariffService tariffService, CategoryService categoryService, UnitService unitService) {
        this.tariffService = tariffService;
        this.categoryService = categoryService;
        this.unitService = unitService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @tariffController.getUserId(#tariffDto.categoryId) == authentication.details.id)")
    @ApiOperation(value = "Add a new tariff")
    public void createTariff(@Validated @RequestBody TariffDto tariffDto) {
        Unit unit = unitService.getById(tariffDto.getUnitId());
        Category category = categoryService.getById(tariffDto.getCategoryId());
        Tariff tariff = TariffTransformer.toTariffEntity(tariffDto, category, unit);
        tariffService.create(tariff);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('ADMIN') or (hasRole('USER') and @tariffController.getUserId(returnObject.categoryId) == authentication.details.id)")
    @ApiOperation(value = "Search a tariff with an ID", response = Tariff.class)
    public TariffDto getTariffById(@PathVariable Long id) {
        Tariff tariff = tariffService.getById(id);
        return TariffTransformer.toTariffDto(tariff);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @tariffController.getUserId(#tariffDto.categoryId) == authentication.details.id)")
    @ApiOperation(value = "Update an existing tariff")
    public void updateTariff(@Validated @RequestBody TariffDto tariffDto) {
        Unit unit = unitService.getById(tariffDto.getUnitId());
        Category category = categoryService.getById(tariffDto.getCategoryId());
        Tariff tariff = TariffTransformer.toTariffEntity(tariffDto, category, unit);
        tariffService.update(tariff);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @tariffController.getUserId(#tariffDto.categoryId) == authentication.details.id)")
    @ApiOperation(value = "Delete an existing tariff")
    public void deleteTariff(@Validated @RequestBody TariffDto tariffDto) {
        Unit unit = unitService.getById(tariffDto.getUnitId());
        Category category = categoryService.getById(tariffDto.getCategoryId());
        Tariff tariff = TariffTransformer.toTariffEntity(tariffDto, category, unit);
        tariffService.delete(tariff);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "View a list of available tariffs", response = Iterable.class)
    public List<TariffDto> getAllTariffs() {
        List<Tariff> tariffs = tariffService.getAll();
        List<TariffDto> metersDto = new ArrayList<>();
        for (Tariff tariff : tariffs) {
            metersDto.add(TariffTransformer.toTariffDto(tariff));
        }
        return metersDto;
    }

    @RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('ADMIN') or (hasRole('USER') and @tariffController.getUserId(#categoryId) == authentication.details.id)")
    @ApiOperation(value = "View a list of available tariffs from selected category", response = Iterable.class)
    public List<TariffDto> getTariffsByCategoryId(@PathVariable Long categoryId) {
        List<Tariff> tariffs = tariffService.getTariffsByCategoryId(categoryId);
        List<TariffDto> metersDto = new ArrayList<>();
        for (Tariff tariff : tariffs) {
            metersDto.add(TariffTransformer.toTariffDto(tariff));
        }
        return metersDto;
    }

    public Long getUserId(Long categoryId) {
        return categoryService.getById(categoryId).getUser().getId();
    }
}