package com.rapl.carros.domain.dto;

import org.modelmapper.ModelMapper;

import com.rapl.carros.domain.Carro;

//@Data
public class CarroDTO {
    private Long id;
    private String nome;
    private String tipo;
    private String descricao;

    public CarroDTO() {}
    
    public CarroDTO(Carro carro) {
        this.id = carro.getId();
        this.nome = carro.getNome();
        this.tipo = carro.getTipo();
    }

    // conteúdo do método gerenciado pela dependência <artifactId>modelmapper</artifactId> do pom.xml
	public static CarroDTO criarDTO(Carro carro) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(carro, CarroDTO.class);
    }

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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
    
}