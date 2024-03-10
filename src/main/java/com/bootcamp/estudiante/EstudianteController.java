package com.bootcamp.estudiante;

import com.bootcamp.libro.Libro;
import com.bootcamp.materia.Materia;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/estudiante")
public class EstudianteController {

    private EstudianteService estudianteService;
    @Autowired
    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
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
    @PostMapping("{estudianteId}/libros")
    public Estudiante agregarLibroEstudiante(@PathVariable Long estudianteId, @RequestBody Libro libro){
        return estudianteService.agregarLibroEstudiante(estudianteId,libro);
    }

    @PostMapping("{estudianteId}/materias")
    public Estudiante agregarMateriaEstudiante(@PathVariable Long estudianteId, @RequestBody Materia materia){
        return estudianteService.agregarMateriaEstudiante(estudianteId,materia);
    }

}
