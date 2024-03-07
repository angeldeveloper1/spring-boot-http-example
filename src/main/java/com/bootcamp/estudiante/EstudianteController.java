package com.bootcamp.estudiante;

import com.bootcamp.libro.Libro;
import com.bootcamp.materia.Materia;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("{id}")
    public Estudiante getEstudiante(@PathVariable Long id) {
        return estudianteService.getEstudiante(id);
    }

    @PostMapping
    public void createEstudiante(@RequestBody Estudiante e) {
        System.out.println("Controller create estudiante entered");
        estudianteService.createEstudiante(e);
        System.out.println("Controller create estudiante exited");
    }

    @DeleteMapping("{id}")
    public void deleteEstudiante(@PathVariable("id") Long estudianteId) {
        estudianteService.deleteEstudiante(estudianteId);
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
