package Util;

import Model.Candidato;
import Model.Sessao;
import Model.Voto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class LeituraDados {

    private List <Candidato> candidatos;
    private List <Voto> votos;
    private List <Sessao> sessoes;

    public LeituraDados(List<Candidato> candidatos, List<Voto> votos, List<Sessao> sessoes) {
        this.candidatos = candidatos;
        this.votos = votos;
        this.sessoes = sessoes;
    }

    public void lerDadosCandidato(String caminho) {

        try {
            FileReader fileReader = new FileReader(caminho);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha = "";
            while ((linha = bufferedReader.readLine()) != null) {

                if (linha.contains("Candidato:") || linha.contains("Partido:")) {
                    linha = linha.replace("Candidato:", "").trim();
                    String[] campoSeparado = linha.split("-");
                    Candidato novoCandidado = new Candidato(campoSeparado[1], campoSeparado[0].trim());
                    candidatos.add(novoCandidado);
                }
            }
            fileReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lerDadosSessao(String caminho) {

        try {
            FileReader fileReader = new FileReader(caminho);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha = "";
            String pularLinha = bufferedReader.readLine();
            while ((linha = bufferedReader.readLine()) != null) {
                String[] campoSeparado = linha.split(",");
                Sessao novasessao = new Sessao(campoSeparado[0], campoSeparado[1],
                        campoSeparado[2] + campoSeparado[3],campoSeparado[4],campoSeparado[5],campoSeparado[6]);
                sessoes.add(novasessao);
            }
            fileReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lerDadosVotos(String caminho) {

        try {
            FileReader fileReader = new FileReader(caminho);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String linha = "";
            Candidato candidatoAtual = null;
            Boolean localizarCandidato;
            Boolean localizarZonaEleitoral;
            String zonaEleitoralAtual = "";

            while ((linha = bufferedReader.readLine()) != null) {

                //System.out.println(contador);

                localizarCandidato = false;
                localizarZonaEleitoral = false;

                if (linha.contains("Candidato:")) {
                    linha = linha.replace("Candidato:", "").trim();
                    String[] campoSeparadoCandidatos = linha.split("-");
                    candidatoAtual = LeituraDados.retornarCandidato(candidatos, campoSeparadoCandidatos[1]);
                    localizarCandidato = true;
                }

                if (linha.contains("Partido")) {
                    linha = linha.replace("Partido:", "").trim();
                    String[] campoSeparadoCandidatos = linha.split("-");
                    candidatoAtual = LeituraDados.retornarCandidato(candidatos, campoSeparadoCandidatos[1]);
                    localizarCandidato = true;
                }

                if (linha.contains("Zona Eleitoral")) {
                    linha = linha.replace("Zona Eleitoral:", "").trim();
                    zonaEleitoralAtual = linha;
                    localizarZonaEleitoral = true;
                }


                if (!linha.contains("Total:") || !localizarCandidato
                        || !linha.contains("Branco") || !localizarZonaEleitoral ||
                        !linha.contains("MOGI DAS CRUZES")) {
                    String[] campoSeparadoVotos = linha.split(",");

                    for (int i = 0; i < campoSeparadoVotos.length; i++) {
                        campoSeparadoVotos[i] = campoSeparadoVotos[i].trim();
                    }

                    if (campoSeparadoVotos.length == 2) {
                        Sessao sessao = encontrarSessaoPorNumeroEZona(campoSeparadoVotos[0], zonaEleitoralAtual, sessoes);
                        Voto voto1 = new Voto(sessao, Integer.parseInt(campoSeparadoVotos[1]), zonaEleitoralAtual);
                        candidatoAtual.addVoto(voto1);
                    }

                    if (campoSeparadoVotos.length == 4) {
                        Sessao sessao = encontrarSessaoPorNumeroEZona(campoSeparadoVotos[0], zonaEleitoralAtual, sessoes);
                        Voto voto1 = new Voto(sessao, Integer.parseInt(campoSeparadoVotos[1]), zonaEleitoralAtual);
                        candidatoAtual.addVoto(voto1);
                        Sessao sessao1 = encontrarSessaoPorNumeroEZona(campoSeparadoVotos[2], zonaEleitoralAtual, sessoes);
                        Voto voto2 = new Voto(sessao1, Integer.parseInt(campoSeparadoVotos[3]), zonaEleitoralAtual);
                        candidatoAtual.addVoto(voto2);
                    }

                    if (campoSeparadoVotos.length == 6) {
                        Sessao sessao = encontrarSessaoPorNumeroEZona(campoSeparadoVotos[0], zonaEleitoralAtual, sessoes);
                        Voto voto1 = new Voto(sessao, Integer.parseInt(campoSeparadoVotos[1]), zonaEleitoralAtual);
                        candidatoAtual.addVoto(voto1);
                        Sessao sessao1 = encontrarSessaoPorNumeroEZona(campoSeparadoVotos[2], zonaEleitoralAtual, sessoes);
                        Voto voto2 = new Voto(sessao1, Integer.parseInt(campoSeparadoVotos[3]), zonaEleitoralAtual);
                        candidatoAtual.addVoto(voto2);
                        Sessao sessao2 = encontrarSessaoPorNumeroEZona(campoSeparadoVotos[4], zonaEleitoralAtual, sessoes);
                        Voto voto3 = new Voto(sessao2, Integer.parseInt(campoSeparadoVotos[5]), zonaEleitoralAtual);
                        candidatoAtual.addVoto(voto3);
                    }
                }
            }

            fileReader.close();
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Sessao encontrarSessaoPorNumeroEZona
            (String numeroSessao, String zonaEleitoralAtual, List<Sessao> sessoes) {
        // Remove o 001 ou qualquer numero 0 antes da sessão ou zona eleitoral
        int numeroSessaoInt = Integer.parseInt(numeroSessao);
        String numeroSessaoStr = String.valueOf(numeroSessaoInt);
        int zonaEleitoralInt = Integer.parseInt(zonaEleitoralAtual);
        String zonaEleitoralStr = String.valueOf(zonaEleitoralInt);
        return sessoes.stream()
                .filter(sessao -> sessao.getSessao().equals(numeroSessaoStr) &&
                        sessao.getZonaEleitoral().equals(zonaEleitoralStr))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("SESSÃO NÂO ENCONTRADA"));
    }

    public static Candidato retornarCandidato(List<Candidato> candidatos, String nome) {
        return candidatos.stream()
                .filter(c -> c.getNome().equalsIgnoreCase(nome))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("CANDIDATO NÃO ENCONTRADO"));
    }

}
