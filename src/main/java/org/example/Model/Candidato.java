package org.example.Model;

import java.util.*;

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


    @Override
    public String toString() {
        return "Candidato " +
                " - Nome: " + Nome + '\'' +
                " - NumeroCandidato: '" + NumeroCandidato + '\'';
    }


}
