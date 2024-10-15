import Model.Candidato;
import Model.Sessao;
import Model.Voto;
import Service.CandidatoService;
import Service.SessaoService;
import Util.LeituraDados;
import View.View;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        List<Candidato> candidatos = new ArrayList<>();
        List<Voto> votos = new ArrayList<>();
        List<Sessao> sessoes = new ArrayList<>();

        LeituraDados leituraDados = new LeituraDados(candidatos,votos,sessoes);

        leituraDados.lerDadosCandidato("resultado-votacao-secao-editado.txt");

        Set<String> generico = new HashSet<>();
        candidatos.removeIf(candidato -> !generico.add(candidato.getNome()));

        leituraDados.lerDadosSessao("zona_eleitoral.csv");

        leituraDados.lerDadosVotos("resultado-votacao-secao-editado.txt");

        SessaoService sessaoService = new SessaoService(candidatos,votos,sessoes);
        CandidatoService candidatoService = new CandidatoService(candidatos,votos,sessoes);

        candidatoService.votosPorSessao("UNIVERSIDADE DE MOGI DAS CRUZES ","22222");
        //candidatoService.sessoesMaisVotadasDeUmCandidato("22222");
        candidatoService.rankCandidatoVotadoBairro("Centro");
    }
}
