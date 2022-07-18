package com.tinnova.teste.service;


import com.tinnova.teste.model.Veiculo;
import com.tinnova.teste.service.filtros.Filtrar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@DisplayName("FiltrosTest")
public class FiltrosTeste {

    @Autowired
    public Filtrar filtrar;

    List<Veiculo> veiculosFake = new ArrayList<>();
    Veiculo veiculo = new Veiculo();
    Veiculo veiculo2 = new Veiculo();

    @BeforeEach
    public void setup() {
        LocalDate created1 = LocalDate.of(2022, 07, 15);
        LocalDate created2 = LocalDate.of(2022, 06, 15);
        veiculo = Veiculo.builder()
                .marca("Ford")
                .ano(2010)
                .created(created1)
                .build();

        veiculo2 = Veiculo.builder()
                .marca("Fiat")
                .ano(2020)
                .created(created2)
                .build();

        veiculosFake.addAll(Arrays.asList(veiculo, veiculo2));
    }

    @Test
    @DisplayName("Deve retornar um map com a quantidade de veiculos por decadas")
    public void veiculosPorDecadasDeFabricacao() {
        Map<Integer, Integer> quantidadePorDecadas = new HashMap<>();
        quantidadePorDecadas.put(2010, 1);
        quantidadePorDecadas.put(2020, 1);

        Map<Integer, Integer> listaMapTeste = filtrar.filtrarVeiculosDecadas(veiculosFake);
        assertEquals(listaMapTeste, quantidadePorDecadas);
    }

    @Test
    @DisplayName("Deve fazer o Filtro por Marcas")
    public void veiculosFiltradosPorDecadas() {
        Map<String, Long> quantidadePorMarca = new HashMap<>();
        quantidadePorMarca.put("Fiat", 1l);
        quantidadePorMarca.put("Ford", 1l);

        Map<String, Long> listPorMarcasTeste = filtrar.filtrarPorMarcas(veiculosFake);
        assertEquals(listPorMarcasTeste, quantidadePorMarca);
    }

    @Test
    @DisplayName("Deve trazer uma Lista com os veiculos adicionado nos ultimos 7 dias")
    public  void listaDeVeiculosAdicionado_nosUltimosSeteDias(){
        List<Veiculo> listaVeiculoTeste = new ArrayList<>();
        listaVeiculoTeste.add(veiculo);

        List<Veiculo> listaVeiculosFiltrado = filtrar.filtrarPelosUltimosSeteDias(veiculosFake);

        assertEquals(listaVeiculosFiltrado, listaVeiculoTeste);
    }
}
