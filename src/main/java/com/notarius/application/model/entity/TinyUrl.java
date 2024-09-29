package com.notarius.application.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tiny_url")
public class TinyUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tiny_url_id_seq")
    @Column(name = "tiny_url_id")
    private Long id;

    @Column(name = "source_url")
    private String sourceUrl;

    @Column(name = "generated_url")
    private String generatedUrl;

}
