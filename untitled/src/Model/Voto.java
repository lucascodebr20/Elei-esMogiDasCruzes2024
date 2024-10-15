package Model;

public class Voto {
    private Sessao Sessao;
    private int NumeroVotos;
    private String zonaEleitoral;

    public Voto(Sessao sessao, int numeroVotos, String zonaEleitoral) {
        this.Sessao = sessao;
        this.NumeroVotos = numeroVotos;
        this.zonaEleitoral = zonaEleitoral;
    }

    public Sessao getSessao() {
        return Sessao;
    }

    public void setSessao(Model.Sessao sessao) {
        Sessao = sessao;
    }

    public int getNumeroVotos() {
        return NumeroVotos;
    }

    public void setNumeroVotos(int numeroVotos) {
        NumeroVotos = numeroVotos;
    }

    public String getZonaEleitoral() {
        return zonaEleitoral;
    }

    public void setZonaEleitoral(String zonaEleitoral) {
        this.zonaEleitoral = zonaEleitoral;
    }

    @Override
    public String toString() {
        return
                        "=============================\n" +
                        "       DETALHES DA SESSÃO      \n" +
                        "=============================\n" +
                        " Sessão:          " + Sessao.getSessao() + "\n" +
                        " Número de Votos: " + NumeroVotos + "\n" +
                        " Zona Eleitoral:  " + zonaEleitoral + "\n" +
                        " Bairro:          " + Sessao.getBairro() + "\n" +
                        "-----------------------------\n";
    }
}
