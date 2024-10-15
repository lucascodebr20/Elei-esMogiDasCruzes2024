package Model;

import java.util.*;
import java.util.stream.Collectors;

public class Candidato {
    private String Nome;
    private String NumeroCandidato;
    List<Voto> votos;

    public Candidato(String nome, String numeroCandidato) {
        Nome = nome;
        NumeroCandidato = numeroCandidato;
        this.votos = new ArrayList<>();
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getNumeroCandidato() {
        return NumeroCandidato;
    }

    public void addVoto(Voto voto) {
        votos.add(voto);
    }

    public List<Voto> getVotos() {
        return votos;
    }

    public String getTop5Locais() {
        StringBuilder sb = new StringBuilder();
        sb.append("===========================\n")
                .append("    TOP 5 LOCAIS VOTADOS   \n")
                .append("===========================\n");

        votos.stream()
                .sorted(Comparator.comparingInt(Voto::getNumeroVotos).reversed())
                .limit(5)
                .forEach(voto -> sb.append(" Sessão: ").append(voto.getSessao().getSessao()).append("\n")
                        .append(" Bairro: ").append(voto.getSessao().getBairro()).append("\n")
                        .append(" Número de Votos: ").append(voto.getNumeroVotos()).append("\n")
                        .append(" Zona Eleitoral: ").append(voto.getZonaEleitoral()).append("\n")
                        .append("---------------------------\n"));

        return sb.toString();
    }

    public String getTop5Bairros() {
        StringBuilder sb = new StringBuilder();
        sb.append("===========================\n")
                .append("    TOP 5 BAIRROS VOTADOS   \n")
                .append("===========================\n");

        votos.stream()
                .collect(Collectors.groupingBy(voto -> voto.getSessao().getBairro(), Collectors.summingInt(Voto::getNumeroVotos)))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> sb.append(" Bairro: ").append(entry.getKey()).append("\n")
                        .append(" Total de Votos: ").append(entry.getValue()).append("\n")
                        .append("---------------------------\n"));

        return sb.toString();

    }

    public int getTotalVotos() {
        return votos.stream().mapToInt(Voto::getNumeroVotos).sum();
    }

    private List<String> getTop5BairrosComMaisVotos() {
        return votos.stream()
                .collect(Collectors.groupingBy(v -> v.getSessao().getBairro(), Collectors.summingInt(Voto::getNumeroVotos)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // Método para obter o total de votos do candidato em um bairro específico
    private int getTotalVotosPorBairro(String bairro) {
        return votos.stream()
                .filter(v -> v.getSessao().getBairro().equals(bairro))
                .mapToInt(Voto::getNumeroVotos)
                .sum();
    }

    // Método para listar os 5 candidatos com votos mais próximos ao do candidato atual nos 5 bairros mais votados
    public String listarCandidatosProximosNosTop5Bairros(List<Candidato> todosCandidatos) {
        StringBuilder sb = new StringBuilder();

        // Obter os 5 bairros com mais votos do candidato atual
        List<String> topBairros = getTop5BairrosComMaisVotos();

        if (topBairros.isEmpty()) {
            return "O candidato atual não possui votos registrados.";
        }

        for (String bairro : topBairros) {
            int totalVotosBairroAtual = getTotalVotosPorBairro(bairro);

            sb.append("Bairro: ").append(bairro)
                    .append(" - Total de Votos do Model.CandidatoService Atual: ").append(totalVotosBairroAtual).append("\n")
                    .append("Candidatos com votos próximos:\n");

            // Encontrar os 5 candidatos com votos mais próximos no mesmo bairro
            todosCandidatos.stream()
                    .filter(c -> c.votos.stream().anyMatch(v -> v.getSessao().getBairro().equals(bairro)))
                    .sorted(Comparator.comparingInt(c -> Math.abs(c.getTotalVotosPorBairro(bairro) - totalVotosBairroAtual)))
                    .limit(5)
                    .forEach(c -> sb.append(" Nome: ").append(c.getNome())
                            .append(" - Número Model.CandidatoService: ").append(c.getNumeroCandidato())
                            .append(" - Total de Votos no Bairro: ").append(c.getTotalVotosPorBairro(bairro)).append("\n"));

            sb.append("---------------------------\n");
        }

        return sb.toString();
    }


    public int getTotalVotosPorSessoes(List<Sessao> sessoesColégio) {
        return votos.stream()
                .filter(v -> sessoesColégio.contains(v.getSessao()))
                .mapToInt(Voto::getNumeroVotos)
                .sum();
    }

    public static List<Candidato> getTop5CandidatosMaisVotadosNoColegio(String numeroSessao, String zonaEleitoral,
                                                                        List<Sessao> sessoes, List<Candidato> todosCandidatos) {

        Optional<Sessao> sessaoInicial = sessoes.stream()
                .filter(s -> s.getSessao().equals(numeroSessao) && s.getZonaEleitoral().equals(zonaEleitoral))
                .findFirst();

        if (sessaoInicial.isEmpty()) {
            throw new IllegalArgumentException("Sessão não encontrada.");
        }

        String colegio = sessaoInicial.get().getColegio();

        List<Sessao> sessoesColegio = sessoes.stream()
                .filter(s -> s.getColegio().equals(colegio))
                .collect(Collectors.toList());

        return todosCandidatos.stream()
                .sorted(Comparator.comparingInt(c -> -c.getTotalVotosPorSessoes(sessoesColegio)))
                .limit(5)
                .collect(Collectors.toList());
    }

    public int getTotalVotosPorBairro(List<Sessao> sessoesBairro) {
        return votos.stream()
                .filter(v -> sessoesBairro.contains(v.getSessao()))
                .mapToInt(Voto::getNumeroVotos)
                .sum();
    }

    public static List<String> listarBairros(List<Sessao> sessoes) {
        return sessoes.stream()
                .map(Sessao::getBairro)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public static List<Candidato> getTop30CandidatosPorBairro(String bairroSelecionado, List<Sessao> sessoes, List<Candidato> todosCandidatos) {
        List<Sessao> sessoesBairro = sessoes.stream()
                .filter(s -> s.getBairro().equalsIgnoreCase(bairroSelecionado))
                .collect(Collectors.toList());

        return todosCandidatos.stream()
                .sorted(Comparator.comparingInt(c -> -c.getTotalVotosPorBairro(sessoesBairro)))
                .limit(30)
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Candidato " +
                " - Nome: " + Nome + '\'' +
                " - NumeroCandidato: '" + NumeroCandidato + '\'';
    }


}
