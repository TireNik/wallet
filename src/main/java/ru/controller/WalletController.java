package ru.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.dto.WalletRequestDto;
import ru.dto.WalletResponseDto;
import ru.service.WalletService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public WalletResponseDto operation(@RequestBody WalletRequestDto dto) {
        return walletService.updateBalance(dto);
    }

    @GetMapping("/{WALLET_UUID}")
    public WalletResponseDto getBalance(@PathVariable("WALLET_UUID") UUID walletId) {
        return walletService.getBalance(walletId);
    }
}
