package com.bootcamp.estudiante;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EstudianteRepository {

    private final List<Estudiante> estudiante;

    public EstudianteRepository() {
        this.estudiante = new ArrayList<Estudiante>();
                estudiante.add(new Estudiante(1L,"Angel"));
                estudiante.add(new Estudiante(2L,"Maria"));
                estudiante.add(new Estudiante(3L,"Juan"));
                estudiante.add(new Estudiante(4L,"Jefferson"));
    }

    public List<Estudiante> getEstudiantes() {
        return estudiante;
    }

    public void createEstudiante(Estudiante e) {
        System.out.println("Repository create estudiante entered");
        estudiante.add(e);
        System.out.println("Repository create estudiante exited");
    }


}
