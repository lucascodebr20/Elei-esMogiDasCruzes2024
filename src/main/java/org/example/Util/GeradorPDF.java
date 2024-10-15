package org.example.Util;

import org.example.Model.Candidato;
import org.example.Model.Sessao;
import org.example.Model.Voto;

import java.util.List;

public class GeradorPDF {

    private List<Candidato> candidatos;
    private List <Voto> votos;
    private List <Sessao> sessoes;

    public GeradorPDF(List<Candidato> candidatos, List<Voto> votos, List<Sessao> sessoes) {
        this.candidatos = candidatos;
        this.votos = votos;
        this.sessoes = sessoes;
    }





}
