package Model;

public class Sessao {
    private String sessao;
    private String colegio;
    private String logadouro;
    private String bairro;
    private String telefone;
    private String zonaEleitoral;

    public Sessao(String sessao, String colegio, String logadouro, String bairro, String telefone, String zonaEleitoral) {
        this.sessao = sessao;
        this.colegio = colegio;
        this.logadouro = logadouro;
        this.bairro = bairro;
        this.telefone = telefone;
        this.zonaEleitoral = zonaEleitoral;
    }

    public String getSessao() {
        return sessao;
    }

    public void setSessao(String sessao) {
        this.sessao = sessao;
    }

    public String getColegio() {
        return colegio;
    }

    public void setColegio(String colegio) {
        this.colegio = colegio;
    }

    public String getLogadouro() {
        return logadouro;
    }

    public void setLogadouro(String logadouro) {
        this.logadouro = logadouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getZonaEleitoral() {
        return zonaEleitoral;
    }

    public void setZonaEleitoral(String zonaEleitoral) {
        this.zonaEleitoral = zonaEleitoral;
    }

    @Override
    public String toString() {
        return "SESSAO: " +
                "Sessao: " + sessao + '\'' +
                ", Colegio: " + colegio + '\'' +
                ", Logadouro: " + logadouro + '\'' +
                ", Bairro: " + bairro + '\'' +
                ", Telefone: " + telefone + '\'';
    }
}
