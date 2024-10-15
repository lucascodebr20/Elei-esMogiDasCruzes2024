package View;

import Model.Candidato;
import Model.Sessao;
import Model.Voto;
import Service.CandidatoService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class View {

    private List<Candidato> candidatos;
    private List<Voto> votos;
    private List<Sessao> sessoes;

    public View(List<Candidato> candidatos, List<Voto> votos, List<Sessao> sessoes) {
        this.candidatos = candidatos;
        this.votos = votos;
        this.sessoes = sessoes;

    }

    public void menuPrincipal() {
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
        CandidatoService candidatoService = new CandidatoService(candidatos, votos, sessoes);
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o numero do candidato: ");
        String numeroCandidato = sc.nextLine();

        Map<Sessao, Integer> sessaoIntegerMap = candidatoService.colegioMaisVotadasDeUmCandidato(numeroCandidato);

        System.out.println();
        System.out.println("SESSÂO MAIS VOTADAS");

        int contador = 0;

        for (Map.Entry<Sessao, Integer> entry : sessaoIntegerMap.entrySet()) {
            if (contador == 5) {
                break;
            }
            Sessao sessao = entry.getKey();
            Integer votos = entry.getValue();
            System.out.println();
            System.out.println(
                            "----------------------------" + "\n" +
                            " ESCOLA: " + sessao.getColegio() + "\n" +
                            " LOGADOURO: " + sessao.getLogadouro() + "\n" +
                            " BAIRRO: " + sessao.getBairro() + "\n" +
                            " TOTAL DE VOTOS: " + votos);
            contador++;
        }

        System.out.println();

        Map<Sessao, Integer> bairrosMaisVotado = candidatoService.bairrosMaisVotadasDeUmCandidato(numeroCandidato);

        System.out.println("BAIRRO MAIS VOTADOS!");

        int contador2 = 0;

        for (Map.Entry<Sessao, Integer> entry : bairrosMaisVotado.entrySet()) {
            if (contador2 == 10) {
                break;
            }
            Sessao sessao = entry.getKey();
            Integer votos = entry.getValue();
            System.out.println(
                    "----------------------------" + "\n" +
                            " BAIRRO: " + sessao.getBairro() + "\n" +
                            " TOTAL DE VOTOS: " + votos);
            contador2++;
        }

        System.out.println();

    }

    public void analisarSessao() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o numero da Sessão: ");
        String sessao = sc.nextLine();
        System.out.println("Digite o numero da Zona Eleitoral ");
        String zonaEleitoral = sc.nextLine();
    }


    public void analisarRegiao() {
        CandidatoService candidatoService = new CandidatoService(candidatos, votos, sessoes);
        Scanner sc = new Scanner(System.in);
        System.out.println("Bairros disponíveis:");
        List<String> bairros = candidatoService.listaBairros();

        bairros.forEach(System.out::println);

        System.out.println("Digite o nome do bairro: ");
        String bairroSelecionado = sc.nextLine();

        Map<Candidato, Integer> candidatoVotadoBairro = candidatoService.rankCandidatoVotadoBairro(bairroSelecionado);

        System.out.println("RANKING DE CANDIDATOS");
        System.out.println("BAIRRO: " + bairroSelecionado);

        int contador = 0;
        for (Map.Entry<Candidato, Integer> entry : candidatoVotadoBairro.entrySet()) {
            if (contador == 50) {
                break;
            }
            Candidato candidato = entry.getKey();
            Integer votos = entry.getValue();
            System.out.println(

                    " | " + (contador + 1) +
                            " | CANDIDATO: " + candidato.getNome() +
                            " | NUMERO CANDIDATO: " + candidato.getNumeroCandidato() +
                            " | TOTAL DE VOTOS: " + votos);

            contador++;
        }
    }

}






