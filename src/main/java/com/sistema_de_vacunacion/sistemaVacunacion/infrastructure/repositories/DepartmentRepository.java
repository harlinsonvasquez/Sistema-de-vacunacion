package com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.repositories;

import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
