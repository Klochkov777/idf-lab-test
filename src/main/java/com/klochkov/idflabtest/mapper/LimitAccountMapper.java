package com.klochkov.idflabtest.mapper;

import com.klochkov.idflabtest.dto.limit.ResponseLimitAccountDto;
import com.klochkov.idflabtest.entity.LimitAccount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LimitAccountMapper {
    ResponseLimitAccountDto toResponseLimitAccountDto(LimitAccount limitAccount);
}
