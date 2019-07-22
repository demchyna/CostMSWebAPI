package com.mdem.costms.controller;

import com.mdem.costms.model.Unit;
import com.mdem.costms.service.IAbstractService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/unit")
@SuppressWarnings("deprecation")
@Api(tags = {"Unit"}, description = "Operations for work with units of measurement")
public class UnitController {

    private final IAbstractService<Unit, Long> unitService;

    @Autowired
    public UnitController(IAbstractService<Unit, Long> unitService) {
        this.unitService = unitService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Add a new unit")
    public void createUnit(@Validated @RequestBody Unit unit) {
        unitService.create(unit);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "Search a unit with an ID", response = Unit.class)
    public Unit getUnitById(@PathVariable Long id) {
        return unitService.getById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Update an existing unit")
    public void updateUnit(@Validated @RequestBody Unit unit) {
        unitService.update(unit);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Delete an existing unit")
    public void deleteUnit(@Validated @RequestBody Unit unit) {
        unitService.delete(unit);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @ApiOperation(value = "View a list of available units", response = Iterable.class)
    public List<Unit> getAllUnits() {
        return unitService.getAll();
    }
}