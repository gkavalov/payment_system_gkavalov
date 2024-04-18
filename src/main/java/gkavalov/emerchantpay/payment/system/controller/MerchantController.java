package gkavalov.emerchantpay.payment.system.controller;

import gkavalov.emerchantpay.payment.system.exception.InactiveMerchant;
import gkavalov.emerchantpay.payment.system.mapper.MerchantMapper;
import gkavalov.emerchantpay.payment.system.model.dto.MerchantDto;
import gkavalov.emerchantpay.payment.system.model.dto.TransactionDto;
import gkavalov.emerchantpay.payment.system.model.entity.Merchant;
import gkavalov.emerchantpay.payment.system.service.MerchantService;
import gkavalov.emerchantpay.payment.system.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(path = "${payment.system.api.version}/merchants")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;
    private final MerchantMapper merchantMapper;
    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Set<MerchantDto>> getAllMerchants() {
        return ResponseEntity.ok(merchantMapper.toDto(merchantService.getAllMerchants()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MerchantDto> updateMerchant(@PathVariable("id") final Long id, @RequestBody final MerchantDto merchantUpdate) {
        final Merchant merchant = merchantService.getMerchant(id);
        merchantMapper.updateMerchant(merchant, merchantUpdate);
        return ResponseEntity.ok(merchantMapper.toDto(merchant));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity destroyMerchant(@PathVariable("id") final Long id) {
        merchantService.deleteMerchant(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/payment")
    public ResponseEntity acceptPayment(@PathVariable("id") final Long id, @RequestBody final TransactionDto transactionDto) throws InactiveMerchant {
        final Merchant merchant = merchantService.isMerchantActive(id);
        transactionService.createTransactionForMerchant(transactionDto, merchant);
        return ResponseEntity.ok().build();
    }
}
