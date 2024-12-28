// ReporteService.java
package pe.edu.uni.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import pe.edu.uni.app.dto.ReporteDto;

import java.util.List;

@Service
public class ReporteService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ReporteDto> generarReportePorEstudiante(String idAlumno) {

        String sql = "SELECT c.NomCurso AS NombreCurso, cp.IdCiclo AS Ciclo, "+
        			 "((m.PC1 +m.PC2 + m.PC3 + m.PC4)/4 + m.ExaParcial + m.ExaFinal) / 3 AS Promedio " +
                     "FROM Matricula m " +
                     "JOIN CursoProgramado cp ON m.IdCursoProg = cp.IdCursoProg " +
                     "JOIN Curso c ON cp.IdCurso = c.IdCurso " +
                     "WHERE m.IdAlumno = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ReporteDto reporte = new ReporteDto();
            reporte.setNombreCurso(rs.getString("NombreCurso"));
            reporte.setIdCiclo(rs.getString("Ciclo"));
            reporte.setPromedio(rs.getDouble("Promedio"));
            reporte.setAprobado(reporte.getPromedio() >= 10.0); // Ajusta según tu criterio de aprobación
            return reporte;
        }, idAlumno);
    }
}







