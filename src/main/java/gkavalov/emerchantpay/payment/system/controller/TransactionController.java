package gkavalov.emerchantpay.payment.system.controller;

import gkavalov.emerchantpay.payment.system.exception.InactiveMerchantException;
import gkavalov.emerchantpay.payment.system.exception.NonPayableTransactionException;
import gkavalov.emerchantpay.payment.system.mapper.TransactionMapper;
import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.ChargeTransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.transaction.ChargeTransaction;
import gkavalov.emerchantpay.payment.system.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(path = "${payment.system.api.version}/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @GetMapping
    public ResponseEntity<Set<TransactionDto>> getAllTransactions() {
        return ResponseEntity.ok(transactionMapper.toTopLevelDto(transactionService.getAllTransactions()));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TransactionDto> getTransaction(@PathVariable("uuid") final UUID uuid) {
        return ResponseEntity.ok(transactionMapper.toTopLevelDto(transactionService.getTransaction(uuid)));
    }

    @PostMapping("/{uuid}/payment")
    public ResponseEntity<TransactionDto> acceptPaymentForTransaction(@PathVariable("uuid") final UUID uuid,
                                                                      @RequestBody final ChargeTransactionDto transactionDto)
            throws URISyntaxException, NonPayableTransactionException, InactiveMerchantException {
        final ChargeTransaction charge = transactionService.paymentForTransaction(uuid, transactionDto);
        return ResponseEntity.created(new URI(charge.getUuid().toString())).build();
    }
}
