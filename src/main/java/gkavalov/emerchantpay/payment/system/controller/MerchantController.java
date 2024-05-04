package gkavalov.emerchantpay.payment.system.controller;

import gkavalov.emerchantpay.payment.system.exception.ActiveMerchantException;
import gkavalov.emerchantpay.payment.system.exception.CorruptCsvFileException;
import gkavalov.emerchantpay.payment.system.exception.EmptyCsvFileException;
import gkavalov.emerchantpay.payment.system.exception.InactiveMerchantException;
import gkavalov.emerchantpay.payment.system.mapper.MerchantMapper;
import gkavalov.emerchantpay.payment.system.model.dto.CreateUpdateMerchantDto;
import gkavalov.emerchantpay.payment.system.model.dto.MerchantDto;
import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.dto.transaction.AuthorizeTransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.model.entity.Transaction;
import gkavalov.emerchantpay.payment.system.service.MerchantService;
import gkavalov.emerchantpay.payment.system.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
@RequestMapping(path = MerchantController.MERCHANTS_PATH)
@RequiredArgsConstructor
public class MerchantController {

    public static final String MERCHANTS_PATH = "/merchants";

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

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE, produces = TEXT_PLAIN_VALUE)
    public ResponseEntity<String> importMerchants(@RequestParam("file") final MultipartFile merchantsCsv)
            throws EmptyCsvFileException, CorruptCsvFileException {
        if (merchantsCsv.isEmpty()) {
            throw new EmptyCsvFileException();
        }

        try {
            long importedMerchants = merchantService.bulkImport(merchantsCsv.getInputStream());
            return ResponseEntity.ok("%d merchants were imported".formatted(importedMerchants));
        } catch (final IOException e) {
            throw new CorruptCsvFileException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchantDto> updateMerchant(@PathVariable("id") final Long id,
                                                      @RequestBody final CreateUpdateMerchantDto merchantUpdate) {
        Merchant updated = merchantService.updateMerchant(id, merchantUpdate);
        return ResponseEntity.ok(merchantMapper.toDto(updated));
    }

    @DeleteMapping(value = "/{id}", produces = TEXT_PLAIN_VALUE)
    public ResponseEntity<String> deleteMerchant(@PathVariable("id") final Long id) throws ActiveMerchantException {
        merchantService.deleteMerchant(id);
        return ResponseEntity.ok("Merchant %d deleted".formatted(id));
    }

    @PostMapping("/{id}/transactions")
    public ResponseEntity<TransactionDto> createTransaction(@PathVariable("id") final Long id,
                                                            @RequestBody final AuthorizeTransactionDto authTransaction)
            throws InactiveMerchantException, URISyntaxException {
        final Merchant merchant = merchantService.isMerchantActive(id);
        Transaction transaction = transactionService.createTransactionForMerchant(authTransaction, merchant);
        return ResponseEntity.created(new URI("/transactions/" + transaction.getUuid().toString())).build();
    }
}
