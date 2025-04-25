package ru.dto;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Getter
public class WalletRequestDto {
    UUID walletId;
    OperationType operationType;
    Long amount;
}
