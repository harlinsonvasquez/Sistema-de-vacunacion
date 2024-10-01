package com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.repositories;

import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccineRepository extends JpaRepository<Vaccine, Long> {
}
