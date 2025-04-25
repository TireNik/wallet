package ru.dto;

import lombok.experimental.FieldDefaults;

import java.util.UUID;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class WalletResponseDto {
    UUID id;
    Long balance;
}
