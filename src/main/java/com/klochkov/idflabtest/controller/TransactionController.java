package com.klochkov.idflabtest.controller;

import com.klochkov.idflabtest.dto.transaction.RequestTransactionDto;
import com.klochkov.idflabtest.dto.transaction.ResponseTransactionDto;
import com.klochkov.idflabtest.entity.Transaction;
import com.klochkov.idflabtest.mapper.TransactionMapper;
import com.klochkov.idflabtest.service.impl.FacadeServiceImpl;
import com.klochkov.idflabtest.service.impl.TransactionServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transactions")
@Validated
public class TransactionController {
    private final TransactionServiceImpl transactionService;
    private final TransactionMapper transactionMapper;
    private final FacadeServiceImpl facadeService;

    /**
     * Method for getting request and creating response after finding.
     *
     * @param requestTransactionDto - the body of request.
     * @return - if transaction will be saved status ok will be returned.
     */
    @PostMapping()
    public ResponseEntity<ResponseTransactionDto> saveTransaction(@Valid
                                                                  @RequestBody
                                                                  RequestTransactionDto requestTransactionDto) {
        Transaction transaction = transactionMapper.toTransaction(requestTransactionDto);
        Transaction transactionSaved = facadeService.saveTransactionAndChangeBalance(transaction);
        ResponseTransactionDto responseTransactionDto = transactionMapper.toResponseTransactionDto(transactionSaved);
        return ResponseEntity.ok(responseTransactionDto);
    }
    /**
     * Method for giving response where response is list of responseTransactionDto where limit_exceeded is true.
     *
     * @return - list of responseTransactionDto where limit_exceeded is true.
     */
    @GetMapping()
    public ResponseEntity<List<ResponseTransactionDto>> getAllTransactionsWithLimitExceeded() {
        List<Transaction> allTransactionsWithLimitExceeded = transactionService
                .getAllTransactionsWithLimitExceeded();
        List<ResponseTransactionDto> responseTransactionDtos = transactionMapper
                .toResponseTransactionDtos(allTransactionsWithLimitExceeded);
        return ResponseEntity.ok(responseTransactionDtos);
    }
}
