package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "share_log")
public class ShareLog {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "recipient_email")
    @NonNull
    private String recipientEmail;

    @Column(name = "phone_number")
    @NonNull
    private String phoneNumber;

    @Column(name = "creation_timestamp")
    private LocalDateTime creationTimestamp;

    @Column(name = "sent_status")
    @NonNull
    private Boolean sentStatus;

    @Column(name = "hashed_link")
    @NonNull
    private String hashedLink;

    @Column(name = "sender_name")
    @NonNull
    private String senderName;

    @Column(name = "sender_email")
    @NonNull
    private String senderEmail;
}

