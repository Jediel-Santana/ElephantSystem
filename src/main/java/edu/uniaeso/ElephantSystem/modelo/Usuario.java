package edu.uniaeso.ElephantSystem.modelo;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;


@DynamicUpdate
@Entity
@Table(name = "usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Email é obrigatório")
	@Email(message = "Insira um email correto")
	private String email;
	
	private String senha;
	
//	@OneToMany(mappedBy = "id.usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<UsuarioGrupo> usuarioGrupos = new ArrayList<>();
	
	@Size(min = 1, message = "Selecione pelo menos um grupo")
	@ManyToMany
	@JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "id_usuario"), inverseJoinColumns = @JoinColumn(name = "id_grupo"))
	private List<Grupo> grupos;
	
	private Boolean ativo = Boolean.TRUE;
	
	@Valid
	@OneToOne (cascade = CascadeType.ALL)
	@JoinColumn(name = "id_pessoa")
	private Pessoa pessoa;

//	@Size(min = 1, message="Selecione pelo menos um grupo")
//	@Transient
//	private List<Grupo> grupos = new ArrayList<>();
	
//	@PostLoad
//	public void postLoad() {
//		this.grupos = this.usuarioGrupos.stream().map(ug -> ug.getId().getGrupo()).collect(Collectors.toList());
//	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

//	public List<UsuarioGrupo> getUsuarioGrupos() {
//		return usuarioGrupos;
//	}
//
//	public void setUsuarioGrupos(List<UsuarioGrupo> usuarioGrupos) {
//		this.usuarioGrupos = usuarioGrupos;
//	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public boolean isNovo() {
		return this.id == null;
	}

//	public void adicionarGrupo(Grupo grupo) {
//		this.usuarioGrupos.add(new UsuarioGrupo(grupo, this));
//	}

	public List<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(List<Grupo> grupos) {
		this.grupos = grupos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}

	public void adicionarGrupo(Grupo grupoCliente) {
		this.grupos.add(grupoCliente);
	}
	
}
