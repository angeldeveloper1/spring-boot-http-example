package com.bootcamp.libro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/libros")
@PreAuthorize("hasAnyRole('BIBL','ADMIN')")
public class LibroController {

    private final LibroService libroService;

    @Autowired
    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('COOR', 'BIBL','ADMIN')")
    public Page<Libro> getLibros(@PageableDefault(size = 3, page = 0)Pageable pageable) {
        return libroService.getAllLibros(pageable);
    }
    @GetMapping("{libroId}")
    @PreAuthorize("hasAnyRole('COOR', 'BIBL','ADMIN')")
    public Libro getLibro(@PathVariable Long libroId){
        return libroService.getLibro(libroId);
    }

    @PostMapping
    public ResponseEntity<Long> createLibro(@RequestBody Libro libro){
        Long idLibro = libroService.createLibro(libro);
        return new ResponseEntity<>(idLibro, HttpStatus.CREATED);
    }

    @PutMapping("{idLibro}")
    public Libro updateLibro(@PathVariable("idLibro") Long idLibro, @RequestBody Libro actualizarLibro){
        return libroService.updateLibro(idLibro, actualizarLibro);
    }

    @DeleteMapping("{idLibro}")
    public ResponseEntity<?> deleteLibro(@PathVariable("idLibro") Long idLibro){
        libroService.deleteLibro(idLibro);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
