package com.santander.springbootapirest.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.santander.springbootapirest.dto.ClienteCreateDto;
import com.santander.springbootapirest.dto.ClienteUpdateDto;
import com.santander.springbootapirest.exception.BadRequestException;
import com.santander.springbootapirest.exception.ResourceNotFoundException;
import com.santander.springbootapirest.model.Cliente;
import com.santander.springbootapirest.service.ClienteService;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/clientes")
    public ResponseEntity<List<Cliente>> findAll() {
        List<Cliente> clientes = clienteService.findAll();
        return ResponseEntity.ok(clientes);
    }

    @PostMapping("/clientes")
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody ClienteCreateDto clienteCreateDto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo " + err.getField() + " " + err.getDefaultMessage()).toList();
            throw new BadRequestException(errors);
        }
        Cliente cliente = new Cliente();
        cliente.setNombre(clienteCreateDto.getNombre());
        cliente.setApellido(clienteCreateDto.getApellido());
        cliente.setEmail(clienteCreateDto.getEmail());
        clienteService.save(cliente);
    }

    @GetMapping("/clientes/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Long id) {
        Cliente cliente = clienteService.findById(id);
        if (cliente == null) {
            throw new ResourceNotFoundException("Cliente no encontrado");
        }
        return ResponseEntity.ok(cliente);
    }

    @PutMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(@Valid @RequestBody ClienteUpdateDto clienteUpdateDto, BindingResult result,
            @PathVariable Long id) {
        if (result.hasErrors()) {
            List<String> errors = result.getFieldErrors().stream()
                    .map(err -> "El campo " + err.getField() + " " + err.getDefaultMessage()).toList();
            throw new BadRequestException(errors);
        }
        Cliente clienteActual = clienteService.findById(id);
        if (clienteActual == null) {
            throw new BadRequestException("Cliente ingresado inválido");
        }
        if (clienteUpdateDto.getNombre() != null) {
            clienteActual.setNombre(clienteUpdateDto.getNombre());
        }
        if (clienteUpdateDto.getApellido() != null) {
            clienteActual.setApellido(clienteUpdateDto.getApellido());
        }
        if (clienteUpdateDto.getEmail() != null) {
            clienteActual.setEmail(clienteUpdateDto.getEmail());
        }
        clienteActual.setUpdatedAt(new Date());
        clienteService.save(clienteActual);
    }

    @DeleteMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Cliente clienteActual = clienteService.findById(id);
        if (clienteActual == null) {
            throw new BadRequestException("Cliente ingresado inválido");
        }
        clienteService.delete(id);
    }

}
