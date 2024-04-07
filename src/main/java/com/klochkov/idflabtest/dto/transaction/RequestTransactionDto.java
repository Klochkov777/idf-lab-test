package com.klochkov.idflabtest.dto.transaction;

import com.klochkov.idflabtest.validator.annotation.CheckFormatCategory;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;


@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RequestTransactionDto {
    @NotNull
    private Long accountFrom;
    @NotNull
    private Long accountTo;
    @NotNull
    @Pattern(regexp = "[A-Z]{3}", message = "Currency abbreviation must consist of three uppercase letters")
    private String currencyShortname;
    @NotNull
    @Digits(integer = 15, fraction = 2)
    private String sum;
    @NotBlank
    @CheckFormatCategory
    private String expenseCategory;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private String dateTime;
}
