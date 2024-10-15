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

    public Map<Sessao, Integer> sessoesMaisVotadasDeUmCandidato(String numeroCandidato) {
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

        resultadoOrdenado.forEach((sessao, votos) ->
                System.out.println("Sessão: " + sessao.getColegio() + ", Votos: " + votos)
        );

        return resultadoOrdenado;
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


        resultadoOrdenado.forEach((candidato, votos) ->
                System.out.println("Candidato: " + candidato.getNome() + ", Votos: " + votos)
        );

        return resultadoOrdenado;
    }

    




    public Candidato localizarCandidato(String numeroCandidato) {
        return candidatos.stream()
                .filter(candidato -> candidato.getNumeroCandidato().equalsIgnoreCase(numeroCandidato))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("CANDIDATO NÃO ENCONTRADO"));
    }

    private String localizarBairro(String sessaoId) {
        return sessoes.stream()
                .filter(sessao -> sessao.getSessao().equalsIgnoreCase(sessao.getSessao()))
                .map(Sessao::getBairro)
                .findFirst()
                .orElse("Bairro desconhecido");
    }



}






