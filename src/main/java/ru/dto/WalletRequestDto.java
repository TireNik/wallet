package ru.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WalletRequestDto {
    UUID walletId;
    OperationType operationType;
    Long amount;
}
