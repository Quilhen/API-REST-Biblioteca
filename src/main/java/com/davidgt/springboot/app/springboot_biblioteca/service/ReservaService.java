package com.davidgt.springboot.app.springboot_biblioteca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davidgt.springboot.app.springboot_biblioteca.repository.LibroRepository;
import com.davidgt.springboot.app.springboot_biblioteca.repository.ReservaRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private LibroRepository libroRepository;

    



}
