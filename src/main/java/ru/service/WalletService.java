package ru.service;

import ru.dto.WalletRequestDto;
import ru.dto.WalletResponseDto;

import java.util.UUID;

public interface WalletService {
    WalletResponseDto getBalance(UUID id);
    WalletResponseDto updateBalance(WalletRequestDto requestDto);
}
