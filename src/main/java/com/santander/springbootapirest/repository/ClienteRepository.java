package com.santander.springbootapirest.repository;

import org.springframework.data.repository.CrudRepository;

import com.santander.springbootapirest.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {
    // Agregamos los métodos personalizados que necesitemos
}
