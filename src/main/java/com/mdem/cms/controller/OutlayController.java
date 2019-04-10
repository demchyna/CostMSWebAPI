package com.mdem.cms.controller;

import com.mdem.cms.DTO.OutlayDto;
import com.mdem.cms.model.Outlay;
import com.mdem.cms.model.User;
import com.mdem.cms.service.impl.OutlayService;
import com.mdem.cms.service.impl.UserService;
import com.mdem.cms.transformer.OutlayTransformer;
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
@RequestMapping("api/outlay")
@Api(tags = {"Outlay"}, description = "Operations for work with outlays")
public class OutlayController {
    private OutlayService outlayService;
    private UserService userService;

    @Autowired
    public OutlayController(OutlayService outlayService, UserService userService) {
        this.outlayService = outlayService;
        this.userService = userService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #outlayDto.userId == authentication.details.id)")
    @ApiOperation(value = "Add a new outlays")
    public void createOutlay(@Validated @RequestBody OutlayDto outlayDto) {
        User user = userService.getById(outlayDto.getUserId());
        Outlay outlay = OutlayTransformer.toOutlayEntity(outlayDto, user);
        outlayService.create(outlay);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('ADMIN') or (hasRole('USER') and returnObject.userId == authentication.details.id)")
    @ApiOperation(value = "Search a outlay with an ID", response = Outlay.class)
    public OutlayDto getOutlayById(@PathVariable Long id) {
        Outlay outlay = outlayService.getById(id);
        return OutlayTransformer.toOutlayDto(outlay);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #outlayDto.userId == authentication.details.id)")
    @ApiOperation(value = "Update an existing outlay")
    public void updateOutlay(@Validated @RequestBody OutlayDto outlayDto) {
        User user = userService.getById(outlayDto.getUserId());
        Outlay outlay = OutlayTransformer.toOutlayEntity(outlayDto, user);
        outlayService.update(outlay);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #outlayDto.userId == authentication.details.id)")
    @ApiOperation(value = "Delete an existing outlay")
    public void deleteOutlay(@Validated @RequestBody OutlayDto outlayDto) {
        User user = userService.getById(outlayDto.getUserId());
        Outlay outlay = OutlayTransformer.toOutlayEntity(outlayDto, user);
        outlayService.delete(outlay);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "View a list of available outlays", response = Iterable.class)
    public List<OutlayDto> getAllOutlays() {
        List<Outlay> outlays = outlayService.getAll();
        List<OutlayDto> outlaysDto = new ArrayList<>();
        for (Outlay outlay : outlays) {
            outlaysDto.add(OutlayTransformer.toOutlayDto(outlay));
        }
        return outlaysDto;
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('ADMIN') or (hasRole('USER') and #userId == authentication.details.id)")
    @ApiOperation(value = "View a list of available outlays for selected user", response = Iterable.class)
    public List<OutlayDto> getOutlaysByUserId(@PathVariable Long userId) {
        List<Outlay> outlays =  outlayService.getOutlaysByUserId(userId);
        List<OutlayDto> outlaysDto = new ArrayList<>();
        for (Outlay outlay : outlays) {
            outlaysDto.add(OutlayTransformer.toOutlayDto(outlay));
        }
        return outlaysDto;
    }
}
