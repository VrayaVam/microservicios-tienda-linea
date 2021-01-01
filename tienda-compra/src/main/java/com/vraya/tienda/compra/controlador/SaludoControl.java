package com.vraya.tienda.compra.controlador;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vraya.tienda.compra.entidad.Saludo;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping(value ="saludo")
public class SaludoControl {


    private final AtomicLong contador = new AtomicLong();
    private static final String template = "Bienvenida al mundo de cabeza %s";

    @GetMapping
    public Saludo saludar(@RequestParam (value="name", defaultValue=" Hola")String name){
        return new Saludo(contador.incrementAndGet(), String.format(template,name));

    }


}
