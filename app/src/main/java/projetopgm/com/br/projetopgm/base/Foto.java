package projetopgm.com.br.projetopgm.base;


import java.io.Serializable;

public class Foto implements Serializable{
	private Long id;
	private byte[] arquivo;
	private String nome;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return nome;
	}
	public void setName(String nome) {
		this.nome = nome;
	}
	public byte[] getArquivo() {
		return arquivo;
	}
	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}
}
