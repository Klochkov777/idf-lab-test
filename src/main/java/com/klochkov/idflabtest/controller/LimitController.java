package com.klochkov.idflabtest.controller;

import com.klochkov.idflabtest.dto.limit.ResponseLimitAccountDto;
import com.klochkov.idflabtest.entity.LimitAccount;
import com.klochkov.idflabtest.enumeration.Category;
import com.klochkov.idflabtest.mapper.LimitAccountMapper;
import com.klochkov.idflabtest.service.LimitAccountService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.annotation.Nullable;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/limit")
@Validated
public class LimitController {
    private final LimitAccountService limitAccountService;
    private final LimitAccountMapper mapper;
    /**
     * Method for getting request and creating response after finding.
     *
     * @param category - category.
     * @param account - account.
     * @param id - id.
     * @param limit - limit.
     * @return - if transaction will be saved status ok will be returned.
     */
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "set new limit and change balance",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ResponseLimitAccountDto.class))})})
    @ApiResponse(responseCode = "400", description = "Invalid parameters provided")
    @PostMapping()
    public ResponseEntity<ResponseLimitAccountDto> setLimit(@Nullable @RequestParam(required = false)
                                                                String category,
                                                            @Nullable @RequestParam(required = false)
                                                            Long account,
                                                            @Nullable @RequestParam(required = false)
                                                            String id,
                                                            @RequestParam @NotNull
                                                                BigDecimal limit) {
        Optional<ResponseLimitAccountDto> optional = Optional
                .ofNullable(new ResponseLimitAccountDto());
        if (id != null) {
            LimitAccount limitAccount = limitAccountService.setLimitByLatestLimitId(UUID.fromString(id), limit);
            return ResponseEntity.ok(mapper.toResponseLimitAccountDto(limitAccount));
        } else if (!category.isBlank() && account != null) {
            LimitAccount limitAccount = limitAccountService
                    .setLimitByCategoryAndAccount(Category.valueOf(category), account, limit);
            return ResponseEntity.ok(mapper.toResponseLimitAccountDto(limitAccount));
        }
        return ResponseEntity.ok(optional.orElseThrow(() -> new ValidationException("Not valid parameters")));
    }
}
