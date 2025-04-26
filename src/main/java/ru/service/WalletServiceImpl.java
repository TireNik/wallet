package ru.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        log.info("Сообщение: запрос на обновление баланса, кошелек: {}, операция: {}, сумма: {}", requestDto.getWalletId(), requestDto.getOperationType(), requestDto.getAmount());
        Wallet wallet = walletRepository.findByIdForUpdate(requestDto.getWalletId())
                .orElseThrow(() -> new NotFoundException("Кошелек не найден"));

        log.info("Баланс кошелька до обновления: {}", wallet.getBalance());
        if (requestDto.getOperationType() == OperationType.WITHDRAW) {
            if (wallet.getBalance() < requestDto.getAmount()) {
                throw new InsufficientFundsException("Не достаточно средств");
            }
            wallet.setBalance(wallet.getBalance() - requestDto.getAmount());
        } else if (requestDto.getOperationType() == OperationType.DEPOSIT) {
                wallet.setBalance(wallet.getBalance() + requestDto.getAmount());
        }
        walletRepository.save(wallet);
        log.info("Сообщение: баланс кошелька успешно обновлен {}", wallet.getBalance());
        WalletResponseDto walletResponseDto = walletMapper.toWalletResponseDto(wallet);
        log.info("Баланс кошелька после обновления: {}", walletResponseDto.getBalance());
        return walletResponseDto;
    }
}
