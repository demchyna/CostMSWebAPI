package com.mdem.costms.transformer;

import com.mdem.costms.DTO.IncomeDto;
import com.mdem.costms.model.Income;
import com.mdem.costms.model.User;

public class IncomeTransformer {
    public static IncomeDto toIncomeDto(Income income) {
        return new IncomeDto(
                income.getId(),
                income.getDate(),
                income.getSource(),
                income.getValue(),
                income.getCurrency(),
                income.getDescription(),
                income.getUser().getId()
        );
    }

    public static Income toIncomeEntity(IncomeDto incomeDto, User user) {
        Income income = new Income();

        if (incomeDto.getId() != null) {
            income.setId(incomeDto.getId());
        }
        income.setDate(incomeDto.getDate());
        income.setSource(incomeDto.getSource());
        income.setValue(incomeDto.getValue());
        income.setCurrency(incomeDto.getCurrency());
        income.setDescription(incomeDto.getDescription());
        income.setUser(user);

        return income;
    }
}
