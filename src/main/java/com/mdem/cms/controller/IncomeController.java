package com.mdem.cms.controller;

import com.mdem.cms.DTO.IncomeDto;
import com.mdem.cms.model.Income;
import com.mdem.cms.model.User;
import com.mdem.cms.service.impl.IncomeService;
import com.mdem.cms.service.impl.UserService;
import com.mdem.cms.transformer.IncomeTransformer;
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

@RestController
@RequestMapping("api/income")
@Api(tags = {"Income"}, description = "Operations for work with incomes")
public class IncomeController {
    private IncomeService incomeService;
    private UserService userService;

    @Autowired
    public IncomeController(IncomeService incomeService, UserService userService) {
        this.incomeService = incomeService;
        this.userService = userService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #incomeDto.userId == authentication.details.id)")
    @ApiOperation(value = "Add a new incomes")
    public void createIncome(@Validated @RequestBody IncomeDto incomeDto) {
        User user = userService.getById(incomeDto.getUserId());
        Income income = IncomeTransformer.toIncomeEntity(incomeDto, user);
        incomeService.create(income);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('ADMIN') or (hasRole('USER') and returnObject.userId == authentication.details.id)")
    @ApiOperation(value = "Search a income with an ID", response = Income.class)
    public IncomeDto getIncomeById(@PathVariable Long id) {
        Income income = incomeService.getById(id);
        return IncomeTransformer.toIncomeDto(income);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #incomeDto.userId == authentication.details.id)")
    @ApiOperation(value = "Update an existing income")
    public void updateIncome(@Validated @RequestBody IncomeDto incomeDto) {
        User user = userService.getById(incomeDto.getUserId());
        Income income = IncomeTransformer.toIncomeEntity(incomeDto, user);
        incomeService.update(income);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #incomeDto.userId == authentication.details.id)")
    @ApiOperation(value = "Delete an existing income")
    public void deleteIncome(@Validated @RequestBody IncomeDto incomeDto) {
        User user = userService.getById(incomeDto.getUserId());
        Income income = IncomeTransformer.toIncomeEntity(incomeDto, user);
        incomeService.delete(income);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "View a list of available incomes", response = Iterable.class)
    public List<IncomeDto> getAllIncomes() {
        List<Income> incomes = incomeService.getAll();
        List<IncomeDto> incomesDto = new ArrayList<>();
        for (Income income : incomes) {
            incomesDto.add(IncomeTransformer.toIncomeDto(income));
        }
        return incomesDto;
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('ADMIN') or (hasRole('USER') and #userId == authentication.details.id)")
    @ApiOperation(value = "View a list of available incomes for selected user", response = Iterable.class)
    public List<IncomeDto> getIncomesByUserId(@PathVariable Long userId) {
        List<Income> incomes =  incomeService.getIncomesByUserId(userId);
        List<IncomeDto> incomesDto = new ArrayList<>();
        for (Income income : incomes) {
            incomesDto.add(IncomeTransformer.toIncomeDto(income));
        }
        return incomesDto;
    }
}
