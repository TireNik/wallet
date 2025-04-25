package ru.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Table(name = "wallets")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Getter
@Setter
public class Wallet {
    @Id
    UUID id;

    @Column(nullable = false)
    Long balance;
}
