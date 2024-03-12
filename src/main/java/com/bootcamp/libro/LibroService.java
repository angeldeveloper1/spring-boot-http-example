package com.bootcamp.libro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class LibroService {

    private final LibroRepository libroRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(LibroService.class);

    @Autowired
    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }
    @Transactional(readOnly = true)
    public Page<Libro> getAllLibros(Pageable pageable) {
        return libroRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Libro getLibro(Long libroId) {
        return libroRepository.findById(libroId)
                .orElseThrow(() -> new NoSuchElementException("No existe el libro con ese id. ID: " + libroId));
    }

    public Long createLibro(Libro libro) {
        LOGGER.info("Creando libro {} ", libro);
        boolean nombreExiste = libroRepository.existsByTituloAndAutor(libro.getTitulo(), libro.getAutor());
        if (nombreExiste) {
            LOGGER.warn("Titulo y autor {} ya existe");
            throw new IllegalArgumentException("Titulo " + libro.getTitulo() + " con autor " + libro.getAutor() + " ya existe.");

        }
        Long id = libroRepository.save(libro).getId();
        LOGGER.info("Libro con id {} fue creado con exito", id);
        return id;
    }

    @Transactional
    public Libro updateLibro(Long idLibro, Libro actualizarLibro) {
        Libro libroExiste = libroRepository.findById(idLibro)
                .orElseThrow(() -> new NoSuchElementException("No existe ese libro con id " + idLibro));

        // Check si el titulo y autor que no sea el id a actualizar, exista ya en la lista de repository.
        boolean libroEnLista = libroRepository.existsByTituloAndAutorAndIdIsNot(actualizarLibro.getTitulo(), actualizarLibro.getAutor(), idLibro);
        if (libroEnLista){
            throw new IllegalArgumentException("Libro " + actualizarLibro.getTitulo() + " con autor " +actualizarLibro.getAutor() + " ya existe.");
        }

        libroExiste.setTitulo(actualizarLibro.getTitulo());
        libroExiste.setAutor(actualizarLibro.getAutor());

        return libroExiste;
    }

    public void deleteLibro(Long idLibro) {
        boolean libroExiste = libroRepository.existsById(idLibro);
        if (!libroExiste){
            throw new IllegalArgumentException("No existe ese libro con id " + idLibro);
        }
        libroRepository.deleteById(idLibro);
    }
}
