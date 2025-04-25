package ru.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dto.OperationType;
import ru.dto.WalletRequestDto;
import ru.dto.WalletResponseDto;
import ru.exception.InsufficientFundsException;
import ru.exception.NotFoundException;
import ru.model.Wallet;
import ru.model.WalletMapper;
import ru.repository.WalletRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Transactional
public class WalletServiceImpl implements WalletService {
    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    @Override
    public WalletResponseDto getBalance(UUID id) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Кошелек не найден"));
        return walletMapper.toWalletResponseDto(wallet);
    }

    @Override
    public WalletResponseDto updateBalance(WalletRequestDto requestDto) {
        Wallet wallet = walletRepository.findByIdForUpdate(requestDto.getWalletId())
                .orElseThrow(() -> new NotFoundException("Кошелек не найден"));

        if (requestDto.getOperationType() == OperationType.WITHDRAW) {
            if (wallet.getBalance() < requestDto.getAmount()) {
                throw new InsufficientFundsException("Не достаточно средств");
            }
            wallet.setBalance(wallet.getBalance() - requestDto.getAmount());
        } else if (requestDto.getOperationType() == OperationType.DEPOSIT) {
                wallet.setBalance(wallet.getBalance() + requestDto.getAmount());
        }

        return walletMapper.toWalletResponseDto(wallet);
    }
}
