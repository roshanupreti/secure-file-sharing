package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pin_log")
public class PinLog {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "hashed_pin", nullable = false)
    private String hashedPin;

    @Column(name = "client_fingerprint", nullable = false)
    private String clientFingerPrint;

    @Column(name = "expiration_timestamp", nullable = false)
    private LocalDateTime expirationTimestamp;
}

