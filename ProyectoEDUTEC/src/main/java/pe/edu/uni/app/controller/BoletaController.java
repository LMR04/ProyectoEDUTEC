package pe.edu.uni.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.uni.app.dto.BoletaDto;
import pe.edu.uni.app.service.BoletaService;

import java.util.List;

@RestController
@RequestMapping("/boletas")
public class BoletaController {

    @Autowired
    private BoletaService boletaService;

    @GetMapping("/estudiante/{idAlumno}")
    public List<BoletaDto> generarBoletaPorEstudiante(@PathVariable String idAlumno) {
        return boletaService.generarBoletaPorEstudiante(idAlumno);
    }
}
