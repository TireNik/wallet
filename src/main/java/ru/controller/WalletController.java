package ru.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.dto.WalletRequestDto;
import ru.dto.WalletResponseDto;
import ru.service.WalletService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
@Slf4j
public class WalletController {

    private final WalletService walletService;

    @PostMapping
    public WalletResponseDto operation(@RequestBody WalletRequestDto dto) {
        log.info("Request: {}", dto);
        return walletService.updateBalance(dto);
    }

    @GetMapping("/{WALLET_UUID}")
    public WalletResponseDto getBalance(@PathVariable("WALLET_UUID") UUID walletId) {
        return walletService.getBalance(walletId);
    }
}
