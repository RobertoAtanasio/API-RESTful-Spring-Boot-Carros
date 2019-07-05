package com.rapl.carros.domain;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.rapl.carros.api.exception.ObjectNotFoundException;
import com.rapl.carros.domain.dto.CarroDTO;

// o map(CarroDTO::criarDTO abaixo funciona devido a dependência <artifactId>modelmapper</artifactId> inserida no pom .xml
// e do public static CarroDTO criarDTO(Carro carro) { em CarroDTO.java.
// Se retirar essa dependência, só funciona map(c -> new CarroDTO(c))
// Obs.: o ::criarDTO se refere ao nome do método definido na classe CarroDTO
// Obs.: o map(c -> new CarroDTO(c)) utiliza o construtor da classe CarroDTO para inicializar os atributos, logo se o
//       construtor se referenciar todos os atributos, esses ficarão nulos! Daí a vantagem de se utilizar o
//       CarroDTO::criarDTO definido pela dependência 'modelmapper';
//		 Se informar map(CarroDTO::new), o new vai chamar o construtor e funcionará do map(c -> new CarroDTO(c))

@Service
public class CarroService {

	@Autowired
    private CarroRepository rep;
	
	public List<Carro> getCarrosTodos() {
		return rep.findAll();
    }

	public List<CarroDTO> getCarrosTodosDTO() {	
		    
		List<CarroDTO> lista = rep.findAll().stream().map(CarroDTO::criarDTO).collect(Collectors.toList());    
//		List<CarroDTO> lista = rep.findAll().stream().map(CarroDTO::new).collect(Collectors.toList());    
//		List<CarroDTO> lista = rep.findAll().stream().map(c -> new CarroDTO(c)).collect(Collectors.toList());    
//		List<Carro> carros = rep.findAll();
//		List<CarroDTO> lista = new ArrayList<>();
//		for (Carro carro : carros) {
//			lista.add(new CarroDTO(carro));
//		}
		return lista;
    }
		
	public CarroDTO getCarroById(Long id) {
		Optional<Carro> carro = rep.findById(id);
		return carro.map(CarroDTO::criarDTO).orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado"));
		
//		return carro.map(CarroDTO::criarDTO).orElse(new CarroDTO());

		// nos exemplos abaixo são utilizados o construtor
//		return carro.map(c -> Optional.of(new CarroDTO(c))).get().orElse(null);
//		if (carro.isPresent()) {
//			return Optional.of(new CarroDTO(carro.get())).get();
//		} else {
//			return null;
//		}		
	}

	public Carro getCarroCompletoById(Long id) {
		Optional<Carro> carro = rep.findById(id);
		if (carro.isPresent()) {
			return carro.get();
		} else {
			throw new ObjectNotFoundException("Carro não encontrado");
		}
	}
	
	public Optional<Carro> getCarroByIdOptional(Long id) {
	    return rep.findById(id);
	}
	
	public List<Carro> getCarrosByTipo(String tipo) {
		return rep.findByTipo(tipo);
	}
	
	public List<CarroDTO> getCarrosPorTipoDTO(String tipo) {
//		List<CarroDTO> carros = rep.findByTipo(tipo).stream().map(c -> new CarroDTO(c)).collect(Collectors.toList()); 
		List<CarroDTO> carros = rep.findByTipo(tipo).stream().map(CarroDTO::criarDTO).collect(Collectors.toList()); 
		return carros;
	}
	
	public List<CarroDTO> getCarros() {
//		List<CarroDTO> list = rep.findAll().stream().map(c -> new CarroDTO(c)).collect(Collectors.toList());
		List<CarroDTO> list = rep.findAll().stream().map(CarroDTO::criarDTO).collect(Collectors.toList());
        return list;
	}

	public Carro insert(Carro carro) {
		Assert.isNull(carro.getId(),"Não foi possível inserir o registro");
        return rep.save(carro);
	}
	
	public CarroDTO insertDTO(Carro carro) {
		Assert.isNull(carro.getId(),"Não foi possível inserir o registro");
		return new CarroDTO(rep.save(carro));
//        return CarroDTO.create(rep.save(carro));
	}

	public Carro update(Carro carro, Long id) {
		// Busca o carro no banco de dados
        Optional<Carro> optional = rep.findById(id);
        if(optional.isPresent()) {
            Carro db = optional.get();
            // Copiar as propriedades
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());
            return rep.save(db);
        } else {
            return null;
            //throw new RuntimeException("Não foi possível atualizar o registro");
        }
	}
	
	public CarroDTO updateDTO(Carro carro, Long id) {
		// Busca o carro no banco de dados
        Optional<Carro> optional = rep.findById(id);
        if(optional.isPresent()) {
            Carro db = optional.get();
            // Copiar as propriedades
            db.setNome(carro.getNome());
            db.setTipo(carro.getTipo());
            rep.save(db);
            return CarroDTO.criarDTO(db);
        } else {
            return null;
            //throw new RuntimeException("Não foi possível atualizar o registro");
        }
	}
	
	public void delete(Long id) {
		rep.deleteById(id);
	}

//	public boolean delete(Long id) {
//		if (rep.findById(id).isPresent()) {
//			rep.deleteById(id);
//			return true;
//		}
//		return false;
//	}
}
