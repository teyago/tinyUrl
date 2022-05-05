package com.goncharov.tinyurl.entity;

import lombok.*;
import org.checkerframework.common.value.qual.MinLen;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false, unique = true)
    private Long id;

    @NaturalId
    @Column(unique = true, nullable = false)
    private String alias;

    @MinLen(4)
    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private Integer counter;

    private LocalDateTime creationDate;
    private LocalDateTime expirationDate;
}
