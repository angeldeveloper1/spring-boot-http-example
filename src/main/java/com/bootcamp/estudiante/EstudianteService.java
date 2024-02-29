package com.bootcamp.estudiante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EstudianteService {

    private EstudianteRepository estudianteRepository;

    @Autowired
    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    public List<Estudiante> getEstudiantes() {
        List<Estudiante> estudiante = estudianteRepository.getEstudiantes();
        //Logica de negocio

        return estudiante;
    }

    public void createEstudiante(Estudiante e) {
        //Logica ...
        System.out.println("Service create estudiante entered");
        estudianteRepository.createEstudiante(e);
        System.out.println("Service create estudiante exited");
    }

    public void deleteEstudiante(Long estudianteId) {
        // check si Id existe, si no imprimimos Warining
        boolean existe = false;
        for (Estudiante e : getEstudiantes()) {
            if (e.getId().equals(estudianteId)) {
                existe = true;
                break;
            }
        }

        if (!existe){
            System.out.println("WARNING: El estudiante con ese id no existe.");
            return;
        }

        estudianteRepository.deleteEstudiante(estudianteId);
    }
}
