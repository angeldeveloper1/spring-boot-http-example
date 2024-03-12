package com.bootcamp.estudiante;

import com.bootcamp.libro.Libro;
import com.bootcamp.materia.Materia;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/estudiante")
@PreAuthorize("hasAnyRole('COOR', 'ADMIN')")
public class EstudianteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EstudianteController.class);
    private EstudianteService estudianteService;
    @Autowired
    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('BIBL','COOR', 'ADMIN')")
    public List<Estudiante> getEstudiantes(
            @RequestParam(value = "primerNombre", required = false) String primerNombre,
            @RequestParam(value = "primerApellido", required = false) String primerApellido
    ) {
        if (primerNombre != null || primerApellido != null){
            return estudianteService.getEstudiantesByPrimerNombreOrPrimerApellido(primerNombre,primerApellido);
        }

        return estudianteService.getAllEstudiantes();
    }
    // este metodo podria reemplazar a la lista de Estudiante getEstudiantes
    @GetMapping("/paged")
    @PreAuthorize("hasAnyRole('BIBL','COOR', 'ADMIN')")
    public Page<Estudiante> getEstudiantes(@PageableDefault(size = 3, page = 0)Pageable pageable) {
        // size = tamano pagina
        // size = numero pagina
        // sort = orden sobre alguno de los atributos, podemos agregar direccion ASC, DESC
        return estudianteService.findAllEstudiantes(pageable);
    }

    @GetMapping("{id}")
    public Estudiante getEstudiante(@PathVariable Long id) {
        return estudianteService.getEstudiante(id);
    }

    @PostMapping
    public ResponseEntity<Long> createEstudiante(@RequestBody Estudiante e) {
        Long idEstudiante = estudianteService.createEstudiante(e);
        return new ResponseEntity<>(idEstudiante, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteEstudiante(@PathVariable("id") Long estudianteId) {
        estudianteService.deleteEstudiante(estudianteId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public Estudiante updateEstudiante(@PathVariable("id") Long estudianteId, @RequestBody Estudiante estudiante) {
        return estudianteService.updateEstudiante(estudianteId, estudiante);
    }

    @PutMapping("{estudianteId}/libros/{libroId}")
    @PreAuthorize("hasAnyRole('BIBL', 'ADMIN')")
    public Estudiante agregarLibroEstudiante(@PathVariable Long estudianteId, @PathVariable Long libroId){
        return estudianteService.agregarLibroEstudiante(estudianteId,libroId);
    }

    @PutMapping("{estudianteId}/materias/{materiaId}")
    public Estudiante agregarMateriaEstudiante(@PathVariable Long estudianteId, @PathVariable Long materiaId){
        return estudianteService.agregarMateriaEstudiante(estudianteId,materiaId);
    }
    @PutMapping("{estudianteId}/cuentas/{cuentaId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Estudiante agregarCuentaEstudiante(@PathVariable Long estudianteId, @PathVariable Long cuentaId){
        return estudianteService.agregarCuentaEstudiante(estudianteId,cuentaId);
    }

}
