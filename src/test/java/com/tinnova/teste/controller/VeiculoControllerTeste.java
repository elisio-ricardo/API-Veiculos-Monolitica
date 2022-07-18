package com.tinnova.teste.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinnova.teste.model.Veiculo;
import com.tinnova.teste.service.ConferirValoresNullVeiculoServicePatch;
import com.tinnova.teste.service.VeiculoService;
import com.tinnova.teste.service.filtros.Filtrar;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.*;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
@DisplayName("VeiculoControllerTest")
public class VeiculoControllerTeste {


    @MockBean
    private VeiculoService veiculoService;

    @MockBean
    private Filtrar filtrar;

    @MockBean
    private ConferirValoresNullVeiculoServicePatch beanUtilsBean;

    @Autowired
    private VeiculoController veiculoController;

    @Autowired
    MockMvc mockMvc;

    Veiculo veiculo = new Veiculo();

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        //Esse metodo garante que só vai ser chamado o controller de filmes
        standaloneSetup(veiculoController);
        veiculo = Veiculo.builder()
                .id(1L)
                .veiculo("Punto")
                .marca("Fiat")
                .ano(2010)
                .cor("Preto")
                .descricao("Semi-novo")
                .vendido(true)
                .created(LocalDate.ofEpochDay(2020 - 07 - 17))
                .updated(LocalDate.ofEpochDay(2020 - 07 - 18))
                .build();
    }


    @Test
    @DisplayName("Deve retornar um veiculo de acordo com o ID")
    public void testFindById() throws Exception {


        Mockito.when(veiculoService.findById(1L))
                .thenReturn(veiculo);

        given().accept(ContentType.JSON)
                .when()
                .get("/veiculo/{id}", 1L)
                .then()
                .statusCode(HttpStatus.OK.value());

        Mockito.verify(veiculoService, Mockito.times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar uma Lista com todos os veiculos")
    public void testeFindAllSemParametros() {
        List<Veiculo> veiculosFake = new ArrayList<>();
        Veiculo veiculo1 = Veiculo.builder().build();
        Veiculo veiculo2 = Veiculo.builder().build();
        Veiculo veiculo3 = Veiculo.builder().build();
        veiculosFake.addAll(Arrays.asList(veiculo1, veiculo2, veiculo3));

        Mockito.when(veiculoService.findAll()).thenReturn(veiculosFake);

        ResponseEntity<List<Veiculo>> veiculosTeste = veiculoController.findAll(null, null, null);

        assertEquals(veiculosTeste.getBody(), veiculosFake);
        assertEquals(veiculosTeste.getStatusCodeValue(), 200);

        Mockito.verify(veiculoService, Mockito.times(1)).findAll();

    }

    @Test
    @DisplayName("Deve retornar uma Lista de veiculos de acordo com os parametros")
    public void testaFindAllComParametros() {
        List<Veiculo> veiculosFake = new ArrayList<>();

        veiculosFake.add(veiculo);
        Mockito.when(veiculoService.filtrarVeiculosNomeAnoCor("Fiat", 2010, "Preto")).thenReturn(veiculosFake);

        ResponseEntity<List<Veiculo>> veiculosTeste = veiculoController.findAll("Fiat", 2010, "Preto");

        assertEquals(veiculosTeste.getBody(), veiculosFake);
        assertEquals(veiculosTeste.getStatusCodeValue(), 200);

        Mockito.verify(veiculoService, Mockito.times(1)).filtrarVeiculosNomeAnoCor("Fiat", 2010, "Preto");

    }

    @Test
    @DisplayName("Deve retornar a Qauntidade de veiculos vendidos (flag true)")
    public void deveRetornarQuantidadeDeVeiculosVendidos() {

        int resultadoEsperado = 5;

        Mockito.when(veiculoService.findVeiculosNaoVendido()).thenReturn(5);

        ResponseEntity<Integer> quantidadeVeiculosVendidos = veiculoController.findQuantidadeVeiculosVendidos();

        assertEquals(quantidadeVeiculosVendidos.getBody(), resultadoEsperado);

        Mockito.verify(veiculoService, Mockito.times(1)).findVeiculosNaoVendido();
    }

    @Test
    @DisplayName("Deve retornar um map com a quantidade de carros por marca")
    public void deveRetornarMapQuantidadeDeVeiculosPorCadaMarca() {
        Map<String, Long> veiculosFake = new HashMap<>();
        veiculosFake.put("Fiat", 2l);
        veiculosFake.put("Nissan", 10l);
        veiculosFake.put("Jeep", 8l);
        veiculosFake.put("Ford", 15l);

        List<Veiculo> veiculo = new ArrayList<>();

        Mockito.when(filtrar.filtrarPorMarcas(veiculo)).thenReturn(veiculosFake);

        ResponseEntity<Map<String, Long>> quantidadePorMarca = veiculoController.findQuantidadePorMarca();

        assertEquals(quantidadePorMarca.getBody(), veiculosFake);
        assertEquals(quantidadePorMarca.getStatusCodeValue(), 200);

        Mockito.verify(filtrar, Mockito.times(1)).filtrarPorMarcas(veiculo);
    }


    @Test
    @DisplayName("Deve retornar um map com a quantidade de carros por decada")
    public void deveRetornarMapQuantidadeDeVeiculosPorDecadas() {
        Map<Integer, Integer> veiculosFake = new HashMap<>();
        veiculosFake.put(1990, 2);
        veiculosFake.put(2000, 10);
        veiculosFake.put(2020, 8);
        veiculosFake.put(2010, 15);

        List<Veiculo> veiculo = new ArrayList<>();

        Mockito.when(filtrar.filtrarVeiculosDecadas(veiculo)).thenReturn(veiculosFake);

        ResponseEntity<Map<Integer, Integer>> quantidadePorMarca = veiculoController.findVeiculosDecadas();

        assertEquals(quantidadePorMarca.getBody(), veiculosFake);
        assertEquals(quantidadePorMarca.getStatusCodeValue(), 200);

        Mockito.verify(filtrar, Mockito.times(1)).filtrarVeiculosDecadas(veiculo);
    }


    @Test
    @DisplayName("Deve retornar uma lista de veiculos cadastrados na ultima semana")
    public void deveRetornar_UmaListaDeVeiculos_Cadastrados_NosUltimosSeteDias() {

        List<Veiculo> listaveiculosFake = new ArrayList<>();

        Veiculo veiculo1 = Veiculo.builder()
                .created(LocalDate.ofEpochDay(2020 - 07 - 10))
                .build();

        Veiculo veiculo2 = Veiculo.builder()
                .created(LocalDate.ofEpochDay(2020 - 07 - 15))
                .build();

        listaveiculosFake.addAll(Arrays.asList(veiculo1, veiculo2));
        List<Veiculo> veiculo = new ArrayList<>();

        Mockito.when(filtrar.filtrarPelosUltimosSeteDias(veiculo)).thenReturn(listaveiculosFake);

        ResponseEntity<List<Veiculo>> veiculosTeste = veiculoController.findVeiculosUltimaSemana();

        assertEquals(veiculosTeste.getBody(), listaveiculosFake);
        assertEquals(veiculosTeste.getStatusCodeValue(), 200);

        Mockito.verify(filtrar, Mockito.times(1)).
                filtrarPelosUltimosSeteDias(veiculo);

    }

    @Test
    @DisplayName("Deve cadastrar um veiculo")
    public void deveCadastrarUmVeiculo() throws Exception {

        mockMvc.perform(post("/veiculo")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(veiculo)))
                .andExpect(status().isOk());

        Mockito.verify(veiculoService, Mockito.times(1))
                .salvarVeiculo(veiculo);
    }

    @Test
    @DisplayName("Deve Atualizar um veiculo usando Put")
    public void deveAtualizarUmVeiculoUsandoPut() throws Exception {

        Long id = 10l;

        mockMvc.perform(MockMvcRequestBuilders.put("/veiculo/" + id, veiculo, 10l)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(veiculo)))
                .andExpect(status().isOk());

        Mockito.verify(veiculoService, Mockito.times(1))
                .atualizarVeiculo(veiculo, 10l);
    }

    @Test
    @DisplayName("Deve Atualizar um veiculo usando Patch")
    public void deveAtualizarUmVeiculoUsantoPatch() throws Exception {
        Veiculo veiculo = Veiculo.builder().id(1l)
                .marca("Gol")
                .ano(2010)
                .descricao("Semi-novo")
                .build();

        Mockito.when(veiculoService.findById(1l)).thenReturn(veiculo);

        Long id = 1l;

        mockMvc.perform(MockMvcRequestBuilders.patch("/veiculo/" + id, veiculo, 1l)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(veiculo)))
                .andExpect(status().isOk());

        Mockito.verify(veiculoService, Mockito.times(1))
                .atualizarVeiculo(veiculo, 1l);
    }

    @Test
    @DisplayName("Deve deletar um veiculo")
    public void deveDeletarUmVeiculo() throws Exception {

        Mockito.when(veiculoService.findById(1l)).thenReturn(veiculo);

        Long id = 1l;

        mockMvc.perform(MockMvcRequestBuilders.delete("/veiculo/" + id, 1l)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(veiculo)))
                .andExpect(status().isOk());

        Mockito.verify(veiculoService, Mockito.times(1))
                .excluirVeiculo(1l);
    }


}
