// ReporteController.java
package pe.edu.uni.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.uni.app.dto.ReporteDto;
import pe.edu.uni.app.service.ReporteService;

import java.util.List;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/estudiante/{idAlumno}")
    public List<ReporteDto> generarReportePorEstudiante(@PathVariable String idAlumno) {
        return reporteService.generarReportePorEstudiante(idAlumno);
    }
}

