package com.santander.springbootapirest.service;

import java.util.List;

import com.santander.springbootapirest.model.Cliente;

public interface ClienteService {

    public List<Cliente> findAll();

    public Cliente save(Cliente cliente);

    public Cliente findById(Long id);

    public void delete(Long id);

}
