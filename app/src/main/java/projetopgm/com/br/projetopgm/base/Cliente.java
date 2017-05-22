package projetopgm.com.br.projetopgm.base;

import java.io.Serializable;


public class Cliente implements Serializable{

	private Long id;
	private String nome;
	private String email;
	private String aplicativoToken;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAplicativoToken() {
		return aplicativoToken;
	}
	public void setAplicativoToken(String aplicativoToken) {
		this.aplicativoToken = aplicativoToken;
	}
}
