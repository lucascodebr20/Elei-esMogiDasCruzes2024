package Service;

import Model.Candidato;
import Model.Sessao;
import Model.Voto;

import java.util.*;
import java.util.stream.Collectors;


public class CandidatoService {

    private List<Candidato> candidatos;
    private List<Voto> votos;
    private List<Sessao> sessoes;

    public CandidatoService(List<Candidato> candidatos, List<Voto> votos, List<Sessao> sessoes) {
        this.candidatos = candidatos;
        this.votos = votos;
        this.sessoes = sessoes;
    }

    public int votosPorSessao(String nomeColegio, String numeroCandidato) {
        return candidatos.stream()
                .filter(candidato -> candidato.getNumeroCandidato().equalsIgnoreCase(numeroCandidato))
                .flatMap(candidato -> candidato.getVotos().stream())
                .filter(voto -> voto.getSessao().getColegio().equalsIgnoreCase(nomeColegio))
                .mapToInt(Voto::getNumeroVotos)
                .sum();
    }

    public Map<Sessao, Integer> colegioMaisVotadasDeUmCandidato(String numeroCandidato) {
        Map<Sessao, Integer> quantidadeVotosSessao = new HashMap<>();

        for (Candidato candidato : candidatos) {
            if (candidato.getNumeroCandidato().equalsIgnoreCase(numeroCandidato)) {
                for (Voto voto : candidato.getVotos()) {
                    Sessao sessao = voto.getSessao();
                    quantidadeVotosSessao.put(sessao, quantidadeVotosSessao.getOrDefault(sessao, 0) + voto.getNumeroVotos());
                }
                break;
            }
        }

        // REMOVE SESSÃO DUPLICADA //
        Set<String> generico = new HashSet<>();
        quantidadeVotosSessao.keySet().removeIf(sessao -> !generico.add(sessao.getColegio()));

        // SOMA OS VOTOS POR COLEGIO //
        Map<Sessao, Integer> resultadoFinal = new HashMap<>();

        for (Map.Entry<Sessao, Integer> entry : quantidadeVotosSessao.entrySet()) {
            Sessao sessao = entry.getKey(); // PEGAR VALOR DA SESSÂO DENTRO DO MAP
            String nomeColegio = sessao.getColegio();
            int votos = votosPorSessao(nomeColegio, numeroCandidato);
            resultadoFinal.put(sessao, votos);
        }

        // ORDENA A LISTA EM ORDEM CRECENTE //
        Map <Sessao,Integer> resultadoOrdenado = resultadoFinal.entrySet().stream()
                        .sorted(Map.Entry.<Sessao,Integer>comparingByValue().reversed())
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue,
                                (e2, e1) -> e2, LinkedHashMap::new));

        return resultadoOrdenado;
    }

    public Map<Sessao, Integer> bairrosMaisVotadasDeUmCandidato(String numeroCandidato) {
        Map<Sessao, Integer> quantidadeVotosBairro = colegioMaisVotadasDeUmCandidato(numeroCandidato);
        Map<String, Integer> valorFinalSomado = new HashMap<>();
        List<String> bairrosUnicos = listaBairros();

        for (String bairro : bairrosUnicos) {
            valorFinalSomado.put(bairro.trim(), 0);
        }

        for (Map.Entry<Sessao, Integer> entry : quantidadeVotosBairro.entrySet()) {
            Sessao sessao = entry.getKey();
            Integer votos = entry.getValue();

            String bairro = sessao.getBairro();
            if (valorFinalSomado.containsKey(bairro)) {
                valorFinalSomado.put(bairro, valorFinalSomado.get(bairro) + votos);
            }
        }

        Map <String,Integer> resultadoOrdenado = valorFinalSomado.entrySet().stream()
                .sorted(Map.Entry.<String,Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e2, e1) -> e2, LinkedHashMap::new));

        return quantidadeVotosBairro;

    }

    public Map<Candidato, Integer> rankCandidatoVotadoBairro(String nomeBairro) {
        Map<Candidato, Integer> resultado = new HashMap<>();

        for (Candidato candidato : candidatos) {
            Map<Sessao, Integer> quantidadeVotosSessao = new HashMap<>();

                for (Voto voto : candidato.getVotos()) {
                    Sessao sessao = voto.getSessao();
                    if (sessao.getBairro().equalsIgnoreCase(nomeBairro)) {
                        quantidadeVotosSessao.put(sessao,
                                quantidadeVotosSessao.getOrDefault(sessao, 0) + voto.getNumeroVotos());
                    }
                }

                int totalVotos = quantidadeVotosSessao.values().stream()
                     .mapToInt(Integer::intValue)
                     .sum();

            resultado.put(candidato,totalVotos);
        }

        Map <Candidato,Integer> resultadoOrdenado = resultado.entrySet().stream()
                .sorted(Map.Entry.<Candidato,Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e2, e1) -> e2, LinkedHashMap::new));

        return resultadoOrdenado;
    }

    public List<String> listaBairros() {
        Set<String> nomesBairros = new HashSet<>();
        sessoes.removeIf(sessao -> !nomesBairros.add(sessao.getBairro()));
        return new ArrayList<>(nomesBairros);
    }



}






