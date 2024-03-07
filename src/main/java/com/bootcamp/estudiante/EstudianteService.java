package com.bootcamp.estudiante;

import com.bootcamp.libro.Libro;
import com.bootcamp.libro.LibroRepository;
import com.bootcamp.materia.Materia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;

@Transactional
@Service
public class EstudianteService {

    private EstudianteRepository estudianteRepository;
    private final LibroRepository libroRepository;

    @Autowired
    public EstudianteService(EstudianteRepository estudianteRepository,
                             LibroRepository libroRepository) {
        this.estudianteRepository = estudianteRepository;
        this.libroRepository = libroRepository;
    }

    @Transactional(readOnly = true)
    public List<Estudiante> getAllEstudiantes() {
        List<Estudiante> estudiante = estudianteRepository.findAll();
        //Logica de negocio

        return estudiante;
    }

    @Transactional(readOnly = true)
    public List<Estudiante> getEstudiantesByPrimerNombreOrPrimerApellido(String primerNombre, String primerApellido) {
        List<Estudiante> estudiante = estudianteRepository.findEstudianteByPrimerNombreOrPrimerApellido(primerNombre,primerApellido);
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


    @Transactional
    public Estudiante updateEstudiante(Long id, Estudiante estudianteActualizar){

        // check si estudiante con ese id existe, si no botamos un error

        Estudiante estudianteExistente = estudianteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Estudiante con ese id no existe, id: " + id));


        // Actualizar estudiante
        Nombre nombre = new Nombre();

        nombre.setPrimerNombre(estudianteActualizar.getNombre().getPrimerNombre());
        nombre.setSegundoNombre(estudianteActualizar.getNombre().getSegundoNombre());
        nombre.setPrimerApellido(estudianteActualizar.getNombre().getPrimerApellido());
        nombre.setSegundoApellido(estudianteActualizar.getNombre().getSegundoApellido());
        estudianteExistente.setNombre(nombre);
        estudianteExistente.setFechaNacimiento(estudianteActualizar.getFechaNacimiento());


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

        // actualizar email
        estudianteExistente.setEmail(estudianteActualizar.getEmail());

        return estudianteExistente;

    }

    @Transactional(readOnly = true)
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

    public Estudiante agregarLibroEstudiante(Long estudianteId, Libro libro) {
        // check si estudiante con ese id existe, si no botamos un error

        Estudiante estudianteExistente = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new NoSuchElementException("Estudiante con ese id no existe, id: " + estudianteId));

        libro.setEstudiante(estudianteExistente);
        libroRepository.save(libro);
        return estudianteExistente;
    }
    public Estudiante agregarMateriaEstudiante(Long estudianteId, Materia materia) {
        // check si estudiante con ese id existe, si no botamos un error

        Estudiante estudianteExistente = estudianteRepository.findById(estudianteId)
                .orElseThrow(() -> new NoSuchElementException("Estudiante con ese id no existe, id: " + estudianteId));

        estudianteExistente.addMateria(materia);
        return estudianteExistente;
    }
}