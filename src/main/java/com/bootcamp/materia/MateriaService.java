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
        if (materiaRepository.existsByNombre(materia.getNombre())) {
            throw new IllegalArgumentException("Materia: " +materia.getNombre() + " ya existe.");
        }

        return materiaRepository.save(materia).getId();
    }
}
