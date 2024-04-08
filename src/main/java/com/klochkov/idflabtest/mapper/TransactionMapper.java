package com.klochkov.idflabtest.mapper;

import com.klochkov.idflabtest.dto.transaction.RequestTransactionDto;
import com.klochkov.idflabtest.dto.transaction.ResponseTransactionDto;
import com.klochkov.idflabtest.entity.Transaction;
import com.klochkov.idflabtest.enumeration.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    /**
     * Method for mapping RequestTransactionDto to Transaction.
     *
     * @param requestTransactionDto - requestTransactionDto
     * @return transaction
     */
    @Mapping(target = "expenseCategory", source = "expenseCategory", qualifiedByName = "expenseCategoryStringToEnum")
    @Mapping(target = "dateTime", source = "dateTime", qualifiedByName = "parseStringToOffsetDateTime")
    @Mapping(target = "sum", source = "sum", qualifiedByName = "parseStringToBigDecimal")
    Transaction toTransaction(RequestTransactionDto requestTransactionDto);

    /**
     * Method for parsing String expense to Category.
     *
     * @param expensiveCategory - expensiveCategory
     * @return Category
     */
    @Named(value = "expenseCategoryStringToEnum")
    default Category expenseCategoryStringToEnum(String expensiveCategory) {
        return Category.valueOf(expensiveCategory);
    }
    /**
     * Method for parsing String dateTime to OffsetDateTime.
     *
     * @param dateTime - dateTime
     * @return Category
     */
    @Named(value = "parseStringToOffsetDateTime")
    default OffsetDateTime parseStringToOffsetDateTime(String dateTime) {
        return OffsetDateTime.parse(dateTime);
    }
    /**
     * Method parsing String dateTime to OffsetDateTime.
     *
     * @param sum - sum of transaction
     * @return Category
     */
    @Named(value = "parseStringToBigDecimal")
    default BigDecimal parseStringToBigDecimal(String sum) {
        return new BigDecimal(sum);
    }
    /**
     * Method for mapping Transaction to ResponseTransactionDto.
     *
     * @param transaction - Transaction transaction
     * @return transaction
     */
    @Mapping(target = "limitSum", source = "limitAccount.limitAmount")
    @Mapping(target = "limitDateTime", source = "limitAccount.date")
    @Mapping(target = "currency", source = "limitAccount.currency")
    @Mapping(target = "limitExceeded", source = "limitAccount.limitExceeded")
    ResponseTransactionDto toResponseTransactionDto(Transaction transaction);
    /**
     * Method for mapping Transaction to ResponseTransactionDto.
     *
     * @param transactions - list of transactions
     * @return List<ResponseTransactionDto> - list of responseTransactionDtos
     */
    List<ResponseTransactionDto> toResponseTransactionDtos(List<Transaction> transactions);
}
