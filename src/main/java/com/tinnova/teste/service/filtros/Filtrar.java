package com.tinnova.teste.service.filtros;

import com.tinnova.teste.model.Veiculo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class Filtrar {

    public Map<Integer, Integer> filtrarVeiculosDecadas(List<Veiculo> veiculos) {
        Map<Integer, Integer> decadasQuantidade = new HashMap<>();
        LocalDate date = LocalDate.now();
        int anoAtualDividido = date.getYear() / 10;
        int anoCarrodividido;
        int qtdTotal = 0;

        int min = veiculos.stream()
                .mapToInt(Veiculo::getAno)
                .min().getAsInt() / 10;

        while (min <= anoAtualDividido) {

            for (Veiculo veiculo : veiculos) {
                anoCarrodividido = veiculo.getAno() / 10;

                if (anoAtualDividido == anoCarrodividido) {
                    qtdTotal += 1;
                    decadasQuantidade.put((anoAtualDividido * 10), qtdTotal);
                }
            }
            qtdTotal = 0;
            anoAtualDividido -= 1;
        }
        return decadasQuantidade;
    }

    public Map<String, Long> filtrarPorMarcas(List<Veiculo> veiculos) {
        List<String> listaNomes = veiculos.stream()
                .map(v -> v.getMarca())
                .collect(Collectors.toList());
        Map<String, Long> totalQuantidadeMarcas = new HashMap<>();

        for (String marca : listaNomes) {
            if (totalQuantidadeMarcas.containsKey(marca)) {
                totalQuantidadeMarcas.put(marca, totalQuantidadeMarcas.get(marca) + 1L);
            } else {
                totalQuantidadeMarcas.put(marca, 1L);
            }
        }
        return totalQuantidadeMarcas;
    }

    public List<Veiculo> filtrarPelosUltimosSeteDias(List<Veiculo> veiculos) {
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataSeteDiasAtras = dataAtual.minusDays(7);
        List<Veiculo> veiculosFiltrados = veiculos
                .stream()
                .filter(p -> p.getCreated().isAfter(dataSeteDiasAtras))
                .collect(Collectors.toList());
        return veiculosFiltrados;
    }
}
