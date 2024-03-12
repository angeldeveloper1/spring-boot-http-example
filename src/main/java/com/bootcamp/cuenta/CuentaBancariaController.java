package com.bootcamp.cuenta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/cuenta")
@PreAuthorize("hasRole('ADMIN')")
public class CuentaBancariaController{

    private CuentaBancariaService cuentaBancariaService;

    @Autowired
    public CuentaBancariaController(CuentaBancariaService cuentaBancariaService) {
        this.cuentaBancariaService = cuentaBancariaService;
    }
    @GetMapping
    public Page<CuentaBancaria> getCuentaBancarias(@PageableDefault(size = 3, page = 0)Pageable pageable){
        return cuentaBancariaService.getAllCuentas(pageable);
    }

    @GetMapping("{idCuenta}")
    public CuentaBancaria getCuenta(@PathVariable Long idCuenta) {
        return cuentaBancariaService.getCuenta(idCuenta);
    }

    @PostMapping
    public ResponseEntity<Long> createCuenta(@RequestBody CuentaBancaria cuentaBancaria){
        Long idCuenta = cuentaBancariaService.createCuenta(cuentaBancaria);
        return new ResponseEntity<>(idCuenta, HttpStatus.CREATED);
    }

    @PutMapping("{idCuenta}")
    public CuentaBancaria updateCuenta(@PathVariable("idCuenta") Long idCuenta, @RequestBody CuentaBancaria actualizarCuenta){
        return cuentaBancariaService.updateCuenta(idCuenta,actualizarCuenta);
    }

    @DeleteMapping("{idCuenta}")
    public ResponseEntity<?> deleteCuenta(@PathVariable("idCuenta") Long idCuenta){
        cuentaBancariaService.deleteCuenta(idCuenta);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
