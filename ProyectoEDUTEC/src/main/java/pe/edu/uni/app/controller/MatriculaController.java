package pe.edu.uni.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.app.service.MatriculaService;
import pe.edu.uni.app.dto.MatriculaDto;
import pe.edu.uni.app.dto.MensajeDto;

@RestController
@RequestMapping("/matricula")
public class MatriculaController {

	@Autowired
	private MatriculaService matriculaService;

	@PostMapping("/reservar")
	public MensajeDto reservarMatricula(@RequestBody MatriculaDto dto) {
		MensajeDto mensaje = null; new MensajeDto(1, "Proceso ok." );
		try {
			dto = matriculaService.crearMatricula(dto);
			mensaje = new MensajeDto(1,"Vacantes Reservada ");
		} catch (Exception e) {
			mensaje = new MensajeDto(-1, "Error: " + e.getMessage());
		}
		return mensaje;
	}
}