package com.bootcamp.estudiante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EstudianteController {

    private EstudianteService estudianteService;
    @Autowired
    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping("/estudiante")
    public List<Estudiante> getEstudiantes() {
        return estudianteService.getEstudiantes();
    }

    @PostMapping("/crear-estudiante")
    public void createEstudiante(@RequestBody Estudiante e) {
        System.out.println("Controller create estudiante entered");
        estudianteService.createEstudiante(e);
        System.out.println("Controller create estudiante exited");
    }
}
