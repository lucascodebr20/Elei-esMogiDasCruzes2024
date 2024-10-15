import Model.Candidato;
import Model.Sessao;
import Model.Voto;
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




    }
}
