package gkavalov.emerchantpay.payment.system.controller;

import gkavalov.emerchantpay.payment.system.exception.InactiveMerchantException;
import gkavalov.emerchantpay.payment.system.mapper.MerchantMapper;
import gkavalov.emerchantpay.payment.system.model.dto.MerchantDto;
import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.UpdateMerchantDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.model.entity.Transaction;
import gkavalov.emerchantpay.payment.system.service.MerchantService;
import gkavalov.emerchantpay.payment.system.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

@RestController
@RequestMapping(path = "merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;
    private final MerchantMapper merchantMapper;
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Set<MerchantDto>> getAllMerchants() {
        return ResponseEntity.ok(merchantMapper.toDto(merchantService.getAllMerchants()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchantDto> getMerchant(@PathVariable("id") final Long id) {
        return ResponseEntity.ok(merchantMapper.toDto(merchantService.getMerchant(id)));
    }

    @PostMapping
    public ResponseEntity<MerchantDto> createMerchant(@RequestBody final MerchantDto merchantDto)
            throws URISyntaxException {
        final Merchant merchant = merchantService.createMerchant(merchantDto);
        return ResponseEntity.created(new URI(merchant.getId().toString())).build();
    }

    @PostMapping("/import")
    public ResponseEntity<String> importMerchants(final MultipartFile usersCsv) {
        // TODO Imports new merchants and admins from CSV
        throw new UnsupportedOperationException();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchantDto> updateMerchant(@PathVariable("id") final Long id,
                                                      @RequestBody final UpdateMerchantDto merchantUpdate) {
        final Merchant merchant = merchantService.getMerchant(id);
        merchantMapper.updateMerchant(merchant, merchantUpdate);
        return ResponseEntity.ok(merchantMapper.toDto(merchant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> destroyMerchant(@PathVariable("id") final Long id) {
        merchantService.deleteMerchant(id);
        return ResponseEntity.ok("Merchant %d deleted".formatted(id));
    }

    @PostMapping("/{id}/transaction")
    public ResponseEntity<TransactionDto> createTransaction(@PathVariable("id") final Long id,
                                                            @RequestBody final TransactionDto transactionDto)
            throws InactiveMerchantException, URISyntaxException {
        final Merchant merchant = merchantService.isMerchantActive(id);
        Transaction transaction = transactionService.createTransactionForMerchant(transactionDto, merchant);
        return ResponseEntity.created(new URI("/v1/transactions/" + transaction.getUuid().toString())).build();
    }
}
