package Service;

import Model.Candidato;
import Model.Sessao;
import Model.Voto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CandidatoService {

    private List<Candidato> candidatos;
    private List <Voto> votos;
    private List <Sessao> sessoes;

    public CandidatoService(List<Candidato> candidatos, List<Voto> votos, List<Sessao> sessoes) {
        this.candidatos = candidatos;
        this.votos = votos;
        this.sessoes = sessoes;
    }

    public Map<Candidato, Map<String, Long>> votosCandidatosPorBairro() {

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
