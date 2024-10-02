package com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.repositories;

import com.sistema_de_vacunacion.sistemaVacunacion.domain.entities.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

    List<Child> findByMunicipalityId(Long municipalityId);

    @Query("SELECT AVG(YEAR(CURRENT_DATE) - YEAR(c.birthDate)) FROM Child c WHERE c.municipality.id = :municipalityId")
    Double getAverageAgeByMunicipality(Long municipalityId);

    @Query("SELECT c FROM Child c JOIN c.vaccines v WHERE c.municipality.id = :municipalityId")
    List<Child> findVaccinatedChildrenByMunicipality(Long municipalityId);

    @Query("SELECT CASE WHEN COUNT(cv) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Child c JOIN c.vaccines cv " +
            "WHERE c.id = :childId AND cv.id = :vaccineId")
    boolean existsByChildIdAndVaccineId(@Param("childId") Long childId, @Param("vaccineId") Long vaccineId);
}
