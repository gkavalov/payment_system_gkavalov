package gkavalov.emerchantpay.payment.system.controller;

import gkavalov.emerchantpay.payment.system.exception.InactiveMerchantException;
import gkavalov.emerchantpay.payment.system.mapper.TransactionMapper;
import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.Transaction;
import gkavalov.emerchantpay.payment.system.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(path = TransactionController.TRANSACTIONS_PATH)
@RequiredArgsConstructor
public class TransactionController {

    public static final String TRANSACTIONS_PATH = "/transactions";

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @GetMapping
    public ResponseEntity<Set<TransactionDto>> getAllTransactions() {
        return ResponseEntity.ok(transactionMapper.toDto(transactionService.getAllTransactions()));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<TransactionDto> getTransaction(@PathVariable("uuid") final UUID uuid) {
        return ResponseEntity.ok(transactionMapper.toDto(transactionService.getTransaction(uuid)));
    }

    @PostMapping("/{uuid}")
    public ResponseEntity<String> referTransaction(@PathVariable("uuid") final UUID uuid,
                                                   @RequestBody final TransactionDto transactionDto)
            throws URISyntaxException, InactiveMerchantException {
        final Transaction transaction = transactionService.referTransaction(uuid, transactionDto);
        return ResponseEntity.created(new URI(transaction.getUuid().toString())).build();
    }
}
