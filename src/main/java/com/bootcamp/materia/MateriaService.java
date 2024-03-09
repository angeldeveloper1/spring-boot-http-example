package com.bootcamp.materia;

import com.bootcamp.estudiante.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class MateriaService {

    private MateriaRepository materiaRepository;

    @Autowired
    public MateriaService(MateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
    }

    @Transactional(readOnly = true)
    public Page<Materia> findAllMaterias(Pageable pageable) {
        return materiaRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Materia getMateria(Long materiaId) {
        return materiaRepository.findById(materiaId)
                .orElseThrow(() -> new NoSuchElementException("Materia con ese ID no existe. ID incorrecto: " + materiaId));
    }

    public Long createMateria(Materia materia) {
        boolean nombreExiste = materiaRepository.existsByNombre(materia.getNombre());
        if (nombreExiste) {
            throw new IllegalArgumentException("Materia: " +materia.getNombre() + " ya existe.");
        }

        return materiaRepository.save(materia).getId();
    }

    @Transactional
    public Materia updateMateria(Long materiaId, Materia materiaActualizar) {
        Materia materiaExiste = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new NoSuchElementException("No existe esa materia. ID: " + materiaId));

        boolean nombreExiste = materiaRepository.existsByNombreAndIdIsNot(materiaActualizar.getNombre(),materiaId);
        if (nombreExiste) {
            throw new IllegalArgumentException("Materia: " +materiaActualizar.getNombre() + " ya existe.");
        }
        materiaExiste.setNombre(materiaActualizar.getNombre());
        materiaExiste.setCreditos(materiaActualizar.getCreditos());

        return materiaExiste;
    }

    public void deleteMateria(Long materiaId) {
        boolean materiaExiste = materiaRepository.existsById(materiaId);
        if (!materiaExiste){
            throw new NoSuchElementException("Materia con id: " +materiaId +" no existe. No se puede borrar algo que no existe");
        }
        materiaRepository.deleteById(materiaId);
    }
}
