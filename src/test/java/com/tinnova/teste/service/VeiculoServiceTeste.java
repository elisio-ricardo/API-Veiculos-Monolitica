package com.tinnova.teste.service;


import com.tinnova.teste.model.Veiculo;
import com.tinnova.teste.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@DisplayName("VeiculoServiceTest")
public class VeiculoServiceTeste {


    @MockBean
    private VeiculoRepository veiculoRepository;

    @Autowired
    private VeiculoService veiculoService;

    Veiculo veiculo = new Veiculo();

    @BeforeEach
    public void setup() {
        LocalDate created1 = LocalDate.of(2022, 07, 15);
        LocalDate created2 = LocalDate.of(2022, 07, 17);
        veiculo = Veiculo.builder()
                .id(1l)
                .veiculo("Punto")
                .marca("Fiat")
                .ano(2010)
                .cor("Preto")
                .descricao("Semi-novo")
                .vendido(true)
                .created(created1)
                .updated(created2)
                .build();
    }


    @Test
    @DisplayName("Deve salvar um veiculo")
    public void salvandoVeiculo() {

        veiculoService.salvarVeiculo(veiculo);
        Mockito.verify(veiculoRepository, Mockito.times(1))
                .save(ArgumentMatchers.any(Veiculo.class));
    }

    @Test
    @DisplayName("Deve retornar um veiculo pelo id")
    public void trazerVeiculoPeloId() throws Exception {

        Mockito.when(veiculoRepository.findById(1l))
                .thenReturn(Optional.ofNullable(veiculo));

        Veiculo veiculoFindById = veiculoService.findById(1l);

        assertEquals(veiculoFindById, veiculo);

        Mockito.verify(veiculoRepository, Mockito.times(1)).findById(1l);
    }

    @Test
    @DisplayName("Deve retornar uma listade veiculos")
    public void trazerListaComTodosVeiculos() {
        List<Veiculo> veiculosFake = new ArrayList<>();
        Veiculo veiculo1 = Veiculo.builder().build();
        Veiculo veiculo2 = Veiculo.builder().build();
        Veiculo veiculo3 = Veiculo.builder().build();

        veiculosFake.addAll(Arrays.asList(veiculo1, veiculo2, veiculo3));

        Mockito.when(veiculoRepository.findAll()).thenReturn(veiculosFake);

        List<Veiculo> listaVeiculo = veiculoService.findAll();

        assertEquals(listaVeiculo, veiculosFake);

        Mockito.verify(veiculoRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Deve excluir um veiculo")
    public void excluirUmVeiculo() throws Exception {
        Mockito.when(veiculoRepository.findById(1l))
                .thenReturn(Optional.ofNullable(veiculo));

        veiculoService.excluirVeiculo(1l);

        Mockito.verify(veiculoRepository, Mockito.times(1)).findById(1l);
    }

    @Test
    @DisplayName("Deve retornar a quantidade de veiculos com a flag true(não vendido)")
    public void quantidadeDeVeiculosNaoVendido() {
        int qtd = 2;
        Mockito.when(veiculoRepository.findVeiculosNaoVendidos()).thenReturn(qtd);
        Integer veiculosNaoVendido = veiculoService.findVeiculosNaoVendido();

        assertEquals(veiculosNaoVendido, qtd);
        Mockito.verify(veiculoRepository, Mockito.times(1))
                .findVeiculosNaoVendidos();
    }


    @Test
    @DisplayName("Deve retornar uma lista de veiculos filtrados Pelos parametros nome, ano, cor")
    public void filtrarVeiculosPelosParametros() {
        List<Veiculo> listaVeiculosFake = new ArrayList<>();
        Veiculo veiculo1 = Veiculo.builder().build();
        Veiculo veiculo2 = Veiculo.builder().build();
        Veiculo veiculo3 = Veiculo.builder().build();

        listaVeiculosFake.addAll(Arrays.asList(veiculo1, veiculo2, veiculo3));

        Mockito.when(veiculoRepository.findByMarcaAndAnoAndCor("Punto", 2010, "Preto"))
                .thenReturn(listaVeiculosFake);

        List<Veiculo> veiculoList = veiculoService.filtrarVeiculosNomeAnoCor("Punto", 2010, "Preto");

        assertEquals(veiculoList, listaVeiculosFake);

        Mockito.verify(veiculoRepository, Mockito.times(1))
                .findByMarcaAndAnoAndCor("Punto", 2010, "Preto");
    }

    @Test
    @DisplayName("Deve atualizar um veiculo")
    public void deveAtualizarUmVeiculo() throws Exception {
        Mockito.when(veiculoRepository.findById(1l))
                .thenReturn(Optional.ofNullable(veiculo));
        Veiculo veiculoTeste = veiculoService.atualizarVeiculo(this.veiculo, 1l);

        assertEquals(veiculoTeste, veiculo);

        Mockito.verify(veiculoRepository, Mockito.times(1))
                .findById(1l);
    }

}
