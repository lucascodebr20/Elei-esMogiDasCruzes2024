package View;

import Model.Candidato;
import Model.Sessao;
import Model.Voto;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class View {

    private List <Candidato> candidatos;
    private List <Voto> votos;
    private List <Sessao> sessoes;

    public View(List<Candidato> candidatos, List<Voto> votos, List<Sessao> sessoes) {
        this.candidatos = candidatos;
        this.votos = votos;
        this.sessoes = sessoes;
    }
    public void menuPrincipal () {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 4) {
            System.out.println("Menu:");
            System.out.println("1 - Analisar Model.CandidatoService");
            System.out.println("2 - Consultar por Sessão");
            System.out.println("3 - Consultar por Região");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    analisarCandidato();
                    break;
                case 2:
                    analisarSessao();
                    break;
                case 3:
                    analisarRegiao();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }

        scanner.close();
    }

    public void analisarCandidato() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o numero do candidato: ");
        String numeroCandidato = sc.nextLine();

        Candidato candidatoAtutal = candidatos.stream()
                .filter(c -> c.getNumeroCandidato().equalsIgnoreCase(numeroCandidato))
                .findFirst()
                .orElse(null);

        System.out.println("Nome candidato: " + candidatoAtutal.getNome());
        System.out.println("Numero Model.CandidatoService: " + candidatoAtutal.getNumeroCandidato());
        System.out.println("Total de votos: " + candidatoAtutal.getTotalVotos());
        System.out.println("");
        System.out.println(candidatoAtutal.getTop5Locais());
        System.out.println(candidatoAtutal.getTop5Bairros());
        System.out.println(candidatoAtutal.listarCandidatosProximosNosTop5Bairros(candidatos));

    }

    public void analisarSessao() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o numero da Sessão: ");
        String sessao = sc.nextLine();
        System.out.println("Digite o numero da Zona Eleitoral ");
        String zonaEleitoral = sc.nextLine();

        try {
            List<Candidato> top5Candidatos = Candidato.getTop5CandidatosMaisVotadosNoColegio(sessao, zonaEleitoral,sessoes,candidatos);

            System.out.println("Top 5 Candidatos mais votados no colégio associado:");
            top5Candidatos.forEach(candidato -> {
                int totalVotos = candidato.getTotalVotosPorSessoes(
                        sessoes.stream().filter(s -> s.getColegio().equals(
                                        sessoes.stream()
                                                .filter(s1 -> s1.getSessao().equals(sessao) && s1.getZonaEleitoral().equals(zonaEleitoral))
                                                .findFirst().get().getColegio()))
                                .collect(Collectors.toList()));
                System.out.println(candidato + " - Total de Votos: " + totalVotos);
            });

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

    }


    public void analisarRegiao() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Bairros disponíveis:");
        List<String> bairros = Candidato.listarBairros(sessoes);
        bairros.forEach(System.out::println);

        System.out.println("Digite o nome do bairro: ");
        String bairroSelecionado = sc.nextLine();

        if (!bairros.contains(bairroSelecionado)) {
            System.out.println("Bairro não encontrado.");
            return;
        }

        List<Candidato> top30Candidatos = Candidato.getTop30CandidatosPorBairro(bairroSelecionado,sessoes,candidatos);

        System.out.println("\nTop 30 Candidatos mais votados no bairro " + bairroSelecionado + ":");
        top30Candidatos.forEach(candidato -> {
            int totalVotos = candidato.getTotalVotosPorBairro(
                    sessoes.stream()
                            .filter(s -> s.getBairro().equalsIgnoreCase(bairroSelecionado))
                            .collect(Collectors.toList())
            );
            System.out.println("Nome: " + candidato.getNome() +
                    " - Número: " + candidato.getNumeroCandidato() +
                    " - Total de Votos: " + totalVotos);
        });


    }





}
