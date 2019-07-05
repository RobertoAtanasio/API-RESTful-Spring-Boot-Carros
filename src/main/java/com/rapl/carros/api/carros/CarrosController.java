package com.rapl.carros.api.carros;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rapl.carros.domain.Carro;
import com.rapl.carros.domain.CarroService;
import com.rapl.carros.domain.dto.CarroDTO;

@RestController
@RequestMapping("/api/v1/carros")
public class CarrosController {

	@Autowired
    private CarroService service;
	
	@GetMapping()
    public ResponseEntity<List<CarroDTO>> get() {
        List<CarroDTO> carros = service.getCarros();
        return !carros.isEmpty() ? ResponseEntity.status(HttpStatus.OK).body(carros) : ResponseEntity.noContent().build();
//        return ResponseEntity.ok(carros);
//        return new ResponseEntity<>(carros, HttpStatus.OK);
    }
	
	@GetMapping("/todos")
    public ResponseEntity<List<Carro>> getTodos() {
        List<Carro> carros = service.getCarrosTodos();
        return ResponseEntity.status(HttpStatus.OK).body(carros);
    }
	
	@GetMapping("/todos/dto")
    public ResponseEntity<List<CarroDTO>> getTodosDTO() {
        List<CarroDTO> carros = service.getCarrosTodosDTO();
        return ResponseEntity.status(HttpStatus.OK).body(carros);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<CarroDTO> getCarroPorIdDTO(@PathVariable("id") Long id) {
        CarroDTO carro = service.getCarroById(id);
        return ResponseEntity.ok(carro);
    }
	
	@GetMapping("/{id}/completo")
	public ResponseEntity<Carro> getCarroCompletoById(@PathVariable("id") Long id) {
        Carro carro = service.getCarroCompletoById(id);
        return ResponseEntity.ok(carro);
    }
	
	@GetMapping("/optional/{id}")
	public ResponseEntity<Carro> getOptional(@PathVariable("id") Long id) {
		Optional<Carro> carro = service.getCarroByIdOptional(id);
		return carro
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
//		return carro
//				.map(c -> ResponseEntity.ok(c))
//				.orElse(ResponseEntity.notFound().build());
//		return carro.isPresent() ? ResponseEntity.ok(carro.get()) : ResponseEntity.notFound().build();
//		if (carro.isPresent()) {
//			return ResponseEntity.ok(carro.get());        					
//		} else {
//			return ResponseEntity.notFound().build();
//		}
    }
	
	@GetMapping("/tipo/{tipo}")
	public ResponseEntity<List<Carro>> getByTipo(@PathVariable("tipo") String tipo) {
        List<Carro> carros = service.getCarrosByTipo(tipo);
        return carros.isEmpty() ? 
        		ResponseEntity.noContent().build() :
        		ResponseEntity.status(HttpStatus.OK).body(carros);        		
    }
	
	@GetMapping("/tipo/dto/{tipo}")
	public ResponseEntity<List<CarroDTO>> getByTipoDTO(@PathVariable("tipo") String tipo) {
        List<CarroDTO> carros = service.getCarrosPorTipoDTO(tipo);
        return carros.isEmpty() ? 
        		ResponseEntity.noContent().build() :
        		ResponseEntity.status(HttpStatus.OK).body(carros);        		
    }
	
	// para utilizar o @Secured será preciso:
	// 1. desabilitar as propriedades do usuário e senha em application.properties: spring.security.user.name=user
	//    e spring.security.user.password=user
	// 2. configurar o SecurityConfig.java, injetar a dependência @EnableGlobalMethodSecurity(securedEnabled = true)
	//    onde inclui-se a segurança por método.
	@PostMapping
	@Secured("ROLE_ADMIN")
    public ResponseEntity<URI> insert(@RequestBody Carro carro) {
		Carro c = service.insert(carro);
		URI location = getUri(c.getId());
		return ResponseEntity.created(location).build();
    }
	
	@PostMapping("/dto")
	@Secured("ROLE_ADMIN")
    public ResponseEntity<URI> insertDTO(@RequestBody Carro carro) {
		try {
			CarroDTO c = service.insertDTO(carro);
			URI location = getUri(c.getId());			
			return ResponseEntity.created(location).build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
    }
	
	@Secured("ROLE_ADMIN")
	@PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") Long id, @RequestBody Carro carro) {
        carro.setId(id);
        Carro c = service.update(carro, id);
        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }
	
	@Secured("ROLE_ADMIN")
	@PutMapping("/dto/{id}")
    public ResponseEntity<Object> atualizarDTO(@PathVariable("id") Long id, @RequestBody Carro carro) {
        carro.setId(id);
        CarroDTO c = service.updateDTO(carro, id);
        return c != null ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }
	
	@Secured("ROLE_ADMIN")
	@DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
	
	private URI getUri(Long id) {
    	// este método cria o endereço para a pesquisa do carro inserido a partir da URL da inserção
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }
}
