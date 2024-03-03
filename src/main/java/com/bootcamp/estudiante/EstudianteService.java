package com.bootcamp.estudiante;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;


@Service
public class EstudianteService {

    private EstudianteRepositoryMentiras estudianteRepositoryMentiras;
    private EstudianteRepository estudianteRepository;

    @Autowired
    public EstudianteService(EstudianteRepositoryMentiras estudianteRepositoryMentiras, EstudianteRepository estudianteRepository) {
        this.estudianteRepositoryMentiras = estudianteRepositoryMentiras;
        this.estudianteRepository = estudianteRepository;
    }

    public List<Estudiante> getAllEstudiantes() {
        List<Estudiante> estudiante = estudianteRepository.findAll();
        //Logica de negocio

        return estudiante;
    }

    public void createEstudiante(Estudiante e) {
        // check si el email es valido
        if (!checkValidezEmail(e.getEmail())) {
            throw new IllegalArgumentException("Email " + e.getEmail() + " no es valido");
        }
        // check si el email existe
        boolean emailExiste = estudianteRepository.existsByEmail(e.getEmail());
        if (emailExiste) {
            throw new IllegalArgumentException("Email " + e.getEmail() + " ya esta registrado");
        }

        estudianteRepository.save(e);
    }

    public void deleteEstudiante(Long estudianteId) {
        // check si Id existe, si no imprimimos Warining
//        boolean existe = false;
//        for (Estudiante e : getEstudiantes()) {
//            if (e.getId().equals(estudianteId)) {
//                existe = true;
//                break;
//            }
//        }
//        boolean existe = getAllEstudiantes().stream().anyMatch(e -> e.getId().equals(estudianteId));

        // usar un metodo de repositorio
        boolean estudianteExiste = estudianteRepository.existsById(estudianteId);
        if (!estudianteExiste){
            throw new NoSuchElementException("Estudiante con id " + estudianteId + " no existe.");
        }
        estudianteRepository.deleteById(estudianteId);
    }

    public Estudiante updateEstudiante(Long id, Estudiante estudianteActualizar){

        // check si estudiante con ese id existe, si no botamos un error

        Estudiante estudianteExistente = estudianteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Estudiante con ese id no existe, id: " + id));

        // check si el email es valido

        if (!checkValidezEmail(estudianteActualizar.getEmail())) {
            throw new IllegalArgumentException("Email " + estudianteActualizar.getEmail() + " no es valido");
        }

        // check si el email que se quiere actualizar ya existe
//        if (!estudianteActualizar.getEmail().equals(estudianteExistente.getEmail())) {
            boolean emailExiste = estudianteRepository.existsByEmailAndIdIsNot(estudianteActualizar.getEmail(),estudianteExistente.getId());
            if (emailExiste) {
                throw new IllegalArgumentException("Email " + estudianteActualizar.getEmail() + " ya esta registrado");
            }
//        }

        // actualizar estudiante

        estudianteExistente.setPrimerNombre(estudianteActualizar.getPrimerNombre());
        estudianteExistente.setSegundoNombre(estudianteActualizar.getSegundoNombre());
        estudianteExistente.setPrimerApellido(estudianteActualizar.getPrimerApellido());
        estudianteExistente.setSegundoApellido(estudianteActualizar.getSegundoApellido());
        estudianteExistente.setFechaNacimiento(estudianteActualizar.getFechaNacimiento());
        estudianteExistente.setEmail(estudianteActualizar.getEmail());

        return estudianteRepository.save(estudianteExistente);

    }

    public Estudiante getEstudiante(Long id) {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Estudiante con ese id no existe, id: " + id));
    }

    private boolean checkValidezEmail(String email) {
        return Pattern.compile(
                "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE
        ).asPredicate().test(email);
    }
}