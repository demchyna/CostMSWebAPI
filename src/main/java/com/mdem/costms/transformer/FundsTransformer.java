package com.mdem.costms.transformer;

import com.mdem.costms.DTO.FundsDto;
import com.mdem.costms.model.Funds;
import com.mdem.costms.model.User;
import com.mdem.costms.util.FundsType;

public class FundsTransformer {
    public static FundsDto toFundsDto(Funds funds) {
        return new FundsDto(
                funds.getId(),
                funds.getDate(),
                funds.getSource(),
                funds.getValue(),
                funds.getCurrency(),
                funds.getType().toString(),
                funds.getDescription(),
                funds.getUser().getId()
        );
    }

    public static Funds toFundsEntity(FundsDto fundsDto, User user) {
        Funds funds = new Funds();

        if (fundsDto.getId() != null) {
            funds.setId(fundsDto.getId());
        }
        funds.setDate(fundsDto.getDate());
        funds.setSource(fundsDto.getSource());
        funds.setValue(fundsDto.getValue());
        funds.setCurrency(fundsDto.getCurrency());
        funds.setType(FundsType.valueOf(fundsDto.getType()));
        funds.setDescription(fundsDto.getDescription());
        funds.setUser(user);

        return funds;
    }
}
