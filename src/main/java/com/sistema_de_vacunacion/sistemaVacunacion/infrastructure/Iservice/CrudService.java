package com.sistema_de_vacunacion.sistemaVacunacion.infrastructure.Iservice;

import org.springframework.data.domain.Page;

public interface CrudService <RQ, RS, ID>{
    public RS create(RQ request);
    public RS update(ID id, RQ request);
    public Page<RS> getAll(int page, int size);
}
