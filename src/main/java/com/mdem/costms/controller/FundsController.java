package com.mdem.costms.controller;

import com.mdem.costms.DTO.FundsDto;
import com.mdem.costms.model.Funds;
import com.mdem.costms.model.User;
import com.mdem.costms.service.impl.FundsService;
import com.mdem.costms.service.impl.UserService;
import com.mdem.costms.transformer.FundsTransformer;
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

@SuppressWarnings("deprecation")
@RestController
@RequestMapping("api/funds")
@Api(tags = {"Funds"}, description = "Operations for work with funds")
public class FundsController {
    private final FundsService fundsService;
    private final UserService userService;

    @Autowired
    public FundsController(FundsService fundsService, UserService userService) {
        this.fundsService = fundsService;
        this.userService = userService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #fundsDto.userId == authentication.details.id)")
    @ApiOperation(value = "Add a new funds")
    public void createFunds(@Validated @RequestBody FundsDto fundsDto) {
        User user = userService.getById(fundsDto.getUserId());
        Funds funds = FundsTransformer.toFundsEntity(fundsDto, user);
        fundsService.create(funds);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('ADMIN') or (hasRole('USER') and returnObject.userId == authentication.details.id)")
    @ApiOperation(value = "Search a funds with an ID", response = Funds.class)
    public FundsDto getFundsById(@PathVariable Long id) {
        Funds funds = fundsService.getById(id);
        return FundsTransformer.toFundsDto(funds);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #fundsDto.userId == authentication.details.id)")
    @ApiOperation(value = "Update an existing funds")
    public void updateFunds(@Validated @RequestBody FundsDto fundsDto) {
        User user = userService.getById(fundsDto.getUserId());
        Funds funds = FundsTransformer.toFundsEntity(fundsDto, user);
        fundsService.update(funds);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #fundsDto.userId == authentication.details.id)")
    @ApiOperation(value = "Delete an existing funds")
    public void deleteFunds(@Validated @RequestBody FundsDto fundsDto) {
        User user = userService.getById(fundsDto.getUserId());
        Funds funds = FundsTransformer.toFundsEntity(fundsDto, user);
        fundsService.delete(funds);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "View a list of available funds", response = Iterable.class)
    public List<FundsDto> getAllFunds() {
        @SuppressWarnings("unchecked") List<Funds> fundsList = fundsService.getAll();
        List<FundsDto> fundsDto = new ArrayList<>();
        for (Funds funds : fundsList) {
            fundsDto.add(FundsTransformer.toFundsDto(funds));
        }
        return fundsDto;
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('ADMIN') or (hasRole('USER') and #userId == authentication.details.id)")
    @ApiOperation(value = "View a list of available funds for selected user", response = Iterable.class)
    public List<FundsDto> getFundsByUserId(@PathVariable Long userId) {
        List<Funds> fundsList =  fundsService.getFundsByUserId(userId);
        List<FundsDto> fundsDto = new ArrayList<>();
        for (Funds funds : fundsList) {
            fundsDto.add(FundsTransformer.toFundsDto(funds));
        }
        return fundsDto;
    }
}
