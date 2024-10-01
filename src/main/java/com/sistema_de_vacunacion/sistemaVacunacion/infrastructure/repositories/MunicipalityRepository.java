package com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.repositories;

import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MunicipalityRepository extends JpaRepository<Municipality, Long> {
    List<Municipality> findByDepartmentId(Long departmentId);
}
