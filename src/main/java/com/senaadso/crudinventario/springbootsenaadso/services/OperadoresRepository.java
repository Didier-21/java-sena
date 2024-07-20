package com.senaadso.crudinventario.springbootsenaadso.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senaadso.crudinventario.springbootsenaadso.models.Operador;

public interface OperadoresRepository extends JpaRepository<Operador,Integer> {

}
