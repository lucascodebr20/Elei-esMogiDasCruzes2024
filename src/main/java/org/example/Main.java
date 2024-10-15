package org.example;

import org.example.Model.Candidato;
import org.example.Model.Sessao;
import org.example.Model.Voto;
import org.example.Service.CandidatoService;
import org.example.Service.SessaoService;
import org.example.Util.LeituraDados;
import org.example.View.View;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        List<Candidato> candidatos = new ArrayList<>();
        List<Voto> votos = new ArrayList<>();
        List<Sessao> sessoes = new ArrayList<>();

        LeituraDados leituraDados = new LeituraDados(candidatos, votos, sessoes);

        leituraDados.lerDadosCandidato("resultado-votacao-secao-editado.txt");

        Set<String> generico = new HashSet<>();
        candidatos.removeIf(candidato -> !generico.add(candidato.getNome()));

        leituraDados.lerDadosSessao("zona_eleitoral.csv");

        leituraDados.lerDadosVotos("resultado-votacao-secao-editado.txt");

        SessaoService sessaoService = new SessaoService(candidatos, votos, sessoes);
        CandidatoService candidatoService = new CandidatoService(candidatos, votos, sessoes);
        View view = new View(candidatos, votos, sessoes);

        view.menuPrincipal();


    }
}
