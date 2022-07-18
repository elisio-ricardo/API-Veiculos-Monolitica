package com.tinnova.teste.service;


import com.tinnova.teste.model.Veiculo;
import com.tinnova.teste.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;


    public void salvarVeiculo(Veiculo veiculo) {
        veiculoRepository.save(veiculo);
    }

    public Veiculo findById(Long id) throws Exception {
        Optional<Veiculo> veiculo = veiculoRepository.findById(id);
        return veiculo.orElseThrow(() -> new Exception("Veiculo não encontrado"));
    }

    public List<Veiculo> findAll() {
        List<Veiculo> listaVeiculos = veiculoRepository.findAll();
        return listaVeiculos;
    }

    public Integer findVeiculosNaoVendido() {
        Integer qtd = veiculoRepository.findVeiculosNaoVendidos().intValue();
        return qtd;
    }

    public List<Veiculo> filtrarVeiculosNomeAnoCor(String marca, Integer ano, String cor) {
        List<Veiculo> veiculos = veiculoRepository.findByMarcaAndAnoAndCor(marca, ano, cor);
        return veiculos;
    }

    public Veiculo atualizarVeiculo(Veiculo veiculoAtualizado, Long id) throws Exception {
        Veiculo veiculoAnterior = this.findById(id);
        veiculoAtualizado.setId(veiculoAnterior.getId());
        veiculoRepository.save(veiculoAtualizado);
        return veiculoAtualizado;
    }

    public void excluirVeiculo(Long id) throws Exception {
        Veiculo veiculo = this.findById(id);
        veiculoRepository.delete(veiculo);
    }


}
