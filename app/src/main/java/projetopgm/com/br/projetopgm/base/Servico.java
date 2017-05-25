package projetopgm.com.br.projetopgm.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Servico implements Serializable{

	private Long id;
	private List<Foto> fotos;
	private Date dataAbertura;
	private Date dataAvaliacao;
	private Date dataFechamento;
	private String numero;
	private String descricao;
	private Double precoAvaliado;
	private Double precoFinal;
	private Double acrescimo;
	private Double desconto;
	private Tipo tipo;
	private Status status;
	private Cliente cliente;
	public Servico() {
		this.fotos = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*GETTERS AND SETTERS*/
	
	public List<Foto> getFotos() {
		return fotos;
	}

	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Long dataAbertura) {
        if(dataAbertura != null)
		    this.dataAbertura = new Date(dataAbertura);
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		if(Tipo.OS.toString().equalsIgnoreCase(tipo))
			this.tipo = Tipo.OS;
		else
			this.tipo = Tipo.ORCAMENTO;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getPrecoAvaliado() {
		return precoAvaliado;
	}

	public void setPrecoAvaliado(Double precoAvaliado) {
		this.precoAvaliado = precoAvaliado;
	}

	public Double getPrecoFinal() {
		return precoFinal;
	}

	public void setPrecoFinal(Double precoFinal) {
		this.precoFinal = precoFinal;
	}

	public Double getAcrescimo() {
		return acrescimo;
	}

	public void setAcrescimo(Double acrescimo) {
		this.acrescimo = acrescimo;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(String status) {
		if(Status.ANDAMENTO.toString().equalsIgnoreCase(status))
			this.status = Status.ANDAMENTO;
		else if(Status.FECHADO.toString().equalsIgnoreCase(status))
			this.status = Status.FECHADO;
		else if(Status.CANCELADO.toString().equalsIgnoreCase(status))
			this.status = Status.CANCELADO;
		else
			this.status = Status.ABERTO;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getDataAvaliacao() {
		return dataAvaliacao;
	}

	public void setDataAvaliacao(Long dataAvaliacao) {
        if(dataAvaliacao != null)
		    this.dataAvaliacao = new Date(dataAvaliacao);
	}

	public void setDataAvaliacao(Date dataAvaliacao) {
		this.dataAvaliacao = dataAvaliacao;
	}

	public Date getDataFechamento() {
		return dataFechamento;
	}

	public void setDataFechamento(Long dataFechamento) {
        if(dataFechamento != null)
		    this.dataFechamento = new Date(dataFechamento);
	}

	public void setDataFechamento(Date dataFechamento) {
		this.dataFechamento = dataFechamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public enum Status {
		ABERTO,
		ANDAMENTO,
		FECHADO,
		CANCELADO
	}
	public enum Tipo {
		ORCAMENTO,
		OS
	}


}
