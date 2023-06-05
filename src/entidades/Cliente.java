package entidades;

public class Cliente {
	private int id;
    private String nomeCliente;
    private String cpf;
    private String emailCliente;
    private SoftwareLicenca softwareLicenca;

    public Cliente() {
        
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public SoftwareLicenca getSoftwareLicenca() {
        return softwareLicenca;
    }

    public void setSoftwareLicenca(SoftwareLicenca softwareLicenca) {
        this.softwareLicenca = softwareLicenca;
    }
}