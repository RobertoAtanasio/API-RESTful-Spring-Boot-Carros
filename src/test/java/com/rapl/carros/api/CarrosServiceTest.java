package com.rapl.carros.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.rapl.carros.api.exception.ObjectNotFoundException;
import com.rapl.carros.domain.Carro;
import com.rapl.carros.domain.CarroService;
import com.rapl.carros.domain.dto.CarroDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CarrosServiceTest {

	@Autowired
	private CarroService carroService;
	
	@Test
	public void testSave() {
		Carro carro = new Carro();
		carro.setNome("Ferrari");
		carro.setTipo("esportivo");
		 
		CarroDTO c = carroService.insertDTO(carro);
		assertNotNull(c);
		
		Long id = c.getId();
		assertNotNull(id);
		
		// Buscar o objeto
		c = carroService.getCarroById(id);
		assertNotNull(id);
		assertEquals("Ferrari", c.getNome());
		assertEquals("esportivo", c.getTipo());
		
		// Excluir objeto
		carroService.delete(id);
		
		// Verificar se excluiu
		try {
			carroService.getCarroById(id);
			fail("O carro não foi excluído");
        } catch (ObjectNotFoundException e) {
            // OK
        }
	}
	
	@Test
    public void testLista() {

        List<CarroDTO> carros = carroService.getCarros();

        assertEquals(30, carros.size());
    }
	
	@Test
    public void testListaPorTipo() {
        assertEquals(10, carroService.getCarrosByTipo("classicos").size());
        assertEquals(10, carroService.getCarrosByTipo("esportivos").size());
        assertEquals(10, carroService.getCarrosByTipo("luxo").size());
        assertEquals(0, carroService.getCarrosByTipo("x").size());
    }
	
	@Test
    public void testGet() {
        CarroDTO c = carroService.getCarroById(11L);
        assertNotNull(c);
        assertEquals("Ferrari FF", c.getNome());
    }

}
