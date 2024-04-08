package com.klochkov.idflabtest.mapper;

import com.klochkov.idflabtest.dto.limit.ResponseLimitAccountDto;
import com.klochkov.idflabtest.entity.LimitAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LimitAccountMapper {
    /**
     * Method for mapping LimitAccount to ResponseLimitAccountDto.
     *
     * @param limitAccount - limitAccount
     * @return ResponseLimitAccountDto
     */
    ResponseLimitAccountDto toResponseLimitAccountDto(LimitAccount limitAccount);
}
