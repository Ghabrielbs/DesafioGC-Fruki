package com.example.demo.cliente;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Entity
@Table(name = "cliente")
@Getter
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    Long id;

    @Column(name = "cnpj")
    @Pattern(regexp = "\\d{14}", message = "O CNPJ deve conter 14 dígitos numéricos")
    String cnpj;

    @Column(name = "cidade")
    String cidade;

    @Column(name = "estado")
    String estado;


}
