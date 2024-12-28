package pe.edu.uni.app.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uni.app.service.AlumnoService;
import pe.edu.uni.app.dto.AlumnoDto;
import pe.edu.uni.app.dto.MensajeDto;

@CrossOrigin(origins = "http://localhost:3001/")
@RestController
@RequestMapping("/api")
public class AlumnoController {

	@Autowired
	private AlumnoService alumnoService;
	
	@GetMapping("/alumnos")
	public List<Map<String, Object>> getAlumnos(){
		return alumnoService.getAlumnos();
	}
	
	@PostMapping("/alumnos")
	public MensajeDto crearAlumno(@RequestBody AlumnoDto dto) {
	    MensajeDto mensaje = null; new MensajeDto(1, "Proceso ok." );
	    try {
	    	dto = alumnoService.crearAlumno(dto);
	        mensaje = new MensajeDto(1, "Alumno creado: " + dto.getCodigo());
	    } catch (Exception e) {
	        mensaje = new MensajeDto(-1, "Error: " + e.getMessage());
	    }
	    return mensaje;
	}
	
    @DeleteMapping("/eliminar/{codigo}")
    public MensajeDto eliminarAlumno(@PathVariable String codigo) {
        MensajeDto mensaje;
        try {
            alumnoService.eliminarAlumno(codigo);
            mensaje = new MensajeDto(1, "Alumno eliminado correctamente.");
        } catch (Exception e) {
            mensaje = new MensajeDto(-1, "Error al eliminar el alumno: " + e.getMessage());
        }
        return mensaje;
    }

	
}

