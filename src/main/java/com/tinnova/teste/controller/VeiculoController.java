package com.tinnova.teste.controller;

import com.tinnova.teste.model.Veiculo;
import com.tinnova.teste.service.ConferirValoresNullVeiculoServicePatch;
import com.tinnova.teste.service.VeiculoService;
import com.tinnova.teste.service.filtros.Filtrar;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/veiculo")
@CrossOrigin(origins = "http://localhost:4200")
@Api(tags = "Veiculo-Controller ")
public class VeiculoController {


    private VeiculoService veiculoService;
    private Filtrar filtrar;
    private ConferirValoresNullVeiculoServicePatch beanUtilsBean;

    public VeiculoController(VeiculoService veiculoService, Filtrar filtrar,
                             ConferirValoresNullVeiculoServicePatch beanUtilsBean) {
        this.veiculoService = veiculoService;
        this.filtrar = filtrar;
        this.beanUtilsBean = beanUtilsBean;
    }

    @GetMapping
    public ResponseEntity<List<Veiculo>> findAll(@RequestParam(value = "marca", required = false) String marca,
                                                 @RequestParam(value = "ano", required = false) Integer ano,
                                                 @RequestParam(value = "cor", required = false) String cor) {

        if (marca != null && ano != null && cor != null) {
            List<Veiculo> listaVeiculos = veiculoService.filtrarVeiculosNomeAnoCor(marca, ano, cor);
            return ResponseEntity.ok().body(listaVeiculos);
        }

        List<Veiculo> listaVeiculos = veiculoService.findAll();
        return ResponseEntity.ok().body(listaVeiculos);
    }

    @GetMapping("{id}")
    public ResponseEntity<Veiculo> findById(@PathVariable("id") Long id) throws Exception {
        Veiculo veiculo = veiculoService.findById(id);
        return ResponseEntity.ok().body(veiculo);
    }

    @GetMapping("/quantidade")
    public ResponseEntity<Integer> findQuantidadeVeiculosVendidos() {
        Integer qtdVendidos = veiculoService.findVeiculosNaoVendido();
        return ResponseEntity.ok().body(qtdVendidos);
    }

    @GetMapping("/fabricante")
    public ResponseEntity<Map<String, Long>> findQuantidadePorMarca() {
        List<Veiculo> veiculos = veiculoService.findAll();
        return ResponseEntity.ok().body(filtrar.filtrarPorMarcas(veiculos));
    }

    @GetMapping("/decada")
    public ResponseEntity<Map<Integer, Integer>> findVeiculosDecadas() {
        List<Veiculo> veiculos = veiculoService.findAll();
        Map<Integer, Integer> decadaQuantidade = filtrar.filtrarVeiculosDecadas(veiculos);
        return ResponseEntity.ok().body(decadaQuantidade);
    }

    @GetMapping("/ultimaSemana")
    public ResponseEntity<List<Veiculo>> findVeiculosUltimaSemana() {
        List<Veiculo> veiculos = veiculoService.findAll();
        List<Veiculo> veiculosUltimaSemana = filtrar.filtrarPelosUltimosSeteDias(veiculos);
        return ResponseEntity.ok().body(veiculosUltimaSemana);
    }

    @PostMapping
    public ResponseEntity<Veiculo> cadastrarVeiculo(@RequestBody Veiculo veiculo) {
        veiculoService.salvarVeiculo(veiculo);
        return ResponseEntity.ok(veiculo);
    }

    @PutMapping("{id}")
    public ResponseEntity<Veiculo> atualizarTodosDadosVeiculo(@RequestBody Veiculo veiculo, @PathVariable("id") Long id) throws Exception {

            Veiculo veiculoAtualizado = veiculoService.atualizarVeiculo(veiculo, id);
            return ResponseEntity.ok(veiculoAtualizado);



    }

    @PatchMapping("{id}")
    public ResponseEntity<Veiculo> atualizarAlgunsDadosInformacoes(@RequestBody Veiculo veiculaAtualizar, @PathVariable("id") Long id) throws Exception {

        Veiculo veiculoAnterior = veiculoService.findById(id);
        beanUtilsBean.copyProperties(veiculoAnterior, veiculaAtualizar);

        veiculoService.atualizarVeiculo(veiculoAnterior, id);
        return ResponseEntity.ok(veiculoAnterior);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Veiculo> deletarVeiculo(@PathVariable("id") Long id) throws Exception {
            veiculoService.excluirVeiculo(id);
            return ResponseEntity.ok().build();
    }
}
