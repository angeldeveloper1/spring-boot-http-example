package com.bootcamp.estudiante;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante,Long> {

    boolean existsByEmailAndIdIsNot(String email, Long id);

    boolean existsByEmail(String email);

    //Consulta derivada
    //List<Estudiante> findEstudianteByPrimerNombreOrPrimerApellido(String primerNombre, String primerApellido);

    //Consulta JPQL
//    @Query("SELECT e FROM Estudiante e WHERE e.primerNombre = ?1 OR e.primerApellido = ?2")
//    List<Estudiante> findEstudianteByPrimerNombreOrPrimerApellido(String primerNombre, String primerApellido);

    //Consulta SQL
    @Query(value = "SELECT * FROM estudiante WHERE primer_nombre = :primerNombre OR primer_apellido = :primerApellido", nativeQuery = true)
    List<Estudiante> findEstudianteByPrimerNombreOrPrimerApellido(
            @Param("primerNombre") String primerNombre,
            @Param("primerApellido") String primerApellido);
}