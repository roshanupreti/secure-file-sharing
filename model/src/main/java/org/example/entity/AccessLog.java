package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "access_log")
public class AccessLog {

    @Id
    @Column(name = "id")
    @NonNull
    private String id;

    @Column(name = "client_fingerprint")
    @NonNull
    private String clientFingerPrint;

    @Column(name = "accessed_timestamp")
    private LocalDateTime accessedTimestamp;
}