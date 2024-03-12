package com.bootcamp.cuenta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CuentaBancariaService {

    private CuentaBancariaRepository cuentaBancariaRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(CuentaBancariaService.class);

    @Autowired
    public CuentaBancariaService(CuentaBancariaRepository cuentaBancariaRepository) {
        this.cuentaBancariaRepository = cuentaBancariaRepository;
    }

    @Transactional(readOnly = true)
    public Page<CuentaBancaria> getAllCuentas(Pageable pageable) {
        return cuentaBancariaRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public CuentaBancaria getCuenta(Long idCuenta) {
        return cuentaBancariaRepository.findById(idCuenta)
                .orElseThrow(() -> new NoSuchElementException("Cuenta con ese id no existe. ID: " +idCuenta));
    }

    public Long createCuenta(CuentaBancaria cuentaBancaria) {
        LOGGER.info("Creando cuenta {} ", cuentaBancaria);
        boolean numeroCuentaExiste = cuentaBancariaRepository.existsByNumeroCuenta(cuentaBancaria.getNumeroCuenta());
        if (numeroCuentaExiste) {
            LOGGER.warn("Numero cuenta {} ya le pertenece a otro cliente.", cuentaBancaria.getNumeroCuenta());
            throw new IllegalArgumentException("Numero de cuenta " +cuentaBancaria.getNumeroCuenta() + " ya existe.");
        }
        Long id = cuentaBancariaRepository.save(cuentaBancaria).getId();
        LOGGER.info("Cuenta Bancaria con id {} fue creada.", id);
        return id;
    }

    @Transactional
    public CuentaBancaria updateCuenta(Long idCuenta, CuentaBancaria actualizarCuenta) {
        CuentaBancaria existeCuentaBancariaRepo = cuentaBancariaRepository.findById(idCuenta)
                .orElseThrow(() -> new NoSuchElementException("Cuenta bancaria no existe. id: " + idCuenta));

        boolean numeroCuentaExiste = cuentaBancariaRepository.existsByNumeroCuentaAndIdIsNot(actualizarCuenta.getNumeroCuenta(),idCuenta);
        if (numeroCuentaExiste){
            throw new IllegalArgumentException("Numero de cuenta " +actualizarCuenta.getNumeroCuenta() + " ya existe");
        }
        existeCuentaBancariaRepo.setNumeroCuenta(actualizarCuenta.getNumeroCuenta());
        existeCuentaBancariaRepo.setBanco(actualizarCuenta.getBanco());
        existeCuentaBancariaRepo.setTitular(actualizarCuenta.getTitular());

        return existeCuentaBancariaRepo;
    }

    public void deleteCuenta(Long idCuenta) {
        boolean existeCuenta = cuentaBancariaRepository.existsById(idCuenta);
        if (!existeCuenta){
            throw new NoSuchElementException("Cuenta con id " + idCuenta + " no existe");
        }
        cuentaBancariaRepository.deleteById(idCuenta);
    }
}
