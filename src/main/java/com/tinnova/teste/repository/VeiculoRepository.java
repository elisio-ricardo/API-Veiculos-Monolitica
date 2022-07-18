package com.tinnova.teste.repository;

import com.tinnova.teste.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {

    @Query(value = "select COUNT(vendido) FROM veiculo WHERE vendido = '1'", nativeQuery = true)
    Integer findVeiculosNaoVendidos();

    List<Veiculo> findByMarcaAndAnoAndCor(String marca, Integer ano, String cor);
}
