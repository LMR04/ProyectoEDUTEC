package pe.edu.uni.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.app.service.PagarService;
import pe.edu.uni.app.dto.PagarDto;
import pe.edu.uni.app.dto.MensajeDto;

@RestController
@RequestMapping("/matricula")
public class PagarController {

	@Autowired
	private PagarService pagarService;

	@PostMapping("/pagar")
	public MensajeDto pagarMatricula(@RequestBody PagarDto dto) {
		MensajeDto mensaje = null; new MensajeDto(1, "Proceso ok." );
		try {
			dto = pagarService.pagarMatricula(dto);
			mensaje = new MensajeDto(1,"Deuda pagada ");
		} catch (Exception e) {
			mensaje = new MensajeDto(-1, "Error: " + e.getMessage());
		}
		return mensaje;
	}
}
