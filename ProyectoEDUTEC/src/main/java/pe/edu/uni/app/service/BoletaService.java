package pe.edu.uni.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import pe.edu.uni.app.dto.BoletaDto;

import java.util.List;

@Service
public class BoletaService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<BoletaDto> generarBoletaPorEstudiante(String idAlumno) {
        String sql = "SELECT m.IdCursoProg, m.IdAlumno, cp.PreCursoProg " +
                     "FROM Matricula m " +
                     "JOIN CursoProgramado cp ON m.IdCursoProg = cp.IdCursoProg " +
                     "WHERE m.IdAlumno = ?";

        	RowMapper<BoletaDto> rowMapper = (rs, rowNum) -> {
            BoletaDto boleta = new BoletaDto();
            boleta.setIdCursoProg(rs.getString("IdCursoProg"));
            boleta.setIdAlumno(rs.getString("IdAlumno"));

            double montoPagar = rs.getDouble("PreCursoProg");
            boleta.setMontoPagar(montoPagar);

            return boleta;
        };

        // Cambiar a query para obtener una lista de resultados
        List<BoletaDto> boletas = jdbcTemplate.query(sql, rowMapper, idAlumno);

        return boletas;
    }
}



