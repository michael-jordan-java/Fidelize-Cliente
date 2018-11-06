package fidelizacao.br.com.fidelizacao.Model;

import java.util.Date;

public class ProgramaFidelizacao {
    private Long programaFidelizacaoId;
    private Date tempoExpiracao;
    private Adm usuarioCadastro;
    private boolean status;
    private double qtdPremio;
    private TipoFidelizacao tipoFidelizacao;
    private Date dataCadastro;

    public Long getProgramaFidelizacaoId() {
        return programaFidelizacaoId;
    }

    public void programaFidelizacaoId(Long id) {
        this.programaFidelizacaoId = id;
    }

    public Date getTempoExpiracao() {
        return tempoExpiracao;
    }

    public void setTempoExpiracao(Date tempoExpiracao) {
        this.tempoExpiracao = tempoExpiracao;
    }

    public Adm getUsuariCadastro() {
        return usuarioCadastro;
    }

    public void setUsuarioCadastro(Adm usuario_cadastro) {
        this.usuarioCadastro = usuario_cadastro;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getQtdPremio() {
        return qtdPremio;
    }

    public void setQtdPremio(double qtd_premio) {
        this.qtdPremio = qtd_premio;
    }

    public TipoFidelizacao getTipoFidelizacao() {
        return tipoFidelizacao;
    }

    public void setTipoFidelizacao(TipoFidelizacao tipoFidelizacao) {
        this.tipoFidelizacao = tipoFidelizacao;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
