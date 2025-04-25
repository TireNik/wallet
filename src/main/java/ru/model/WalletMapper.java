package ru.model;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.dto.WalletResponseDto;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WalletMapper {
    Wallet toEntity(WalletResponseDto walletResponseDto);

    WalletResponseDto toWalletResponseDto(Wallet wallet);

    Wallet updateWithNull(WalletResponseDto walletResponseDto, @MappingTarget Wallet wallet);
}