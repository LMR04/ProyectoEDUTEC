package pe.edu.uni.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.app.dto.MatriculaDto;

@Service
public class MatriculaService {


	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional(propagation = Propagation.REQUIRES_NEW, 
			rollbackFor = Exception.class)
	public MatriculaDto crearMatricula(MatriculaDto dto) {
		//VERIFICAR AL ALUMNO
        String sql = "SELECT COUNT(1) FROM Alumno WHERE idAlumno = ?";
        int filas = jdbcTemplate.queryForObject(sql, Integer.class, dto.getIdAlumno());

        if (filas == 0) {
			throw new RuntimeException("Codigo del alumno incorrecto.");
		}
        //VERIFICAR AL CURSO
        sql = "SELECT count(1) cont from CursoProgramado ";
        sql += "Where idCurso = ? AND idCiclo = ? AND Activo = 1 AND Matriculados < Vacantes ";
        filas = jdbcTemplate.queryForObject(sql, Integer.class, dto.getIdCurso(), dto.getIdCiclo());
		if (filas == 0) {
			throw new RuntimeException("Curso no disponible");
		}
		// CONTADOR MATRICULADO
		sql = "update CursoProgramado ";
		sql += "SET Matriculados = Matriculados + 1,Vacantes = Vacantes - 1 ";
		sql += "WHERE IdCiclo = ? AND IdCurso = ?";
		jdbcTemplate.update(sql, dto.getIdCiclo(), dto.getIdCurso());
		
		// REGISTRAR
		// Obtener IdCursoProg
		String obtenerIdCursoProgSql = "SELECT IdCursoProg FROM CursoProgramado WHERE IdCurso = ? AND IdCiclo = ?";
		int idCursoProg = jdbcTemplate.queryForObject(obtenerIdCursoProgSql, Integer.class, dto.getIdCurso(), dto.getIdCiclo());

		// GENERAR CODIGO MATRICULA
		String ultimoCodigo = jdbcTemplate.queryForObject(
		        "SELECT MAX(CodMatricula) FROM Matricula", String.class);
		int ultimoNumero = Integer.parseInt(ultimoCodigo.substring(1));
		int nuevoNumero = ultimoNumero + 1;
		String nuevoCodigo = "M" + String.format("%04d", nuevoNumero);
		// Asignar a MatriculaDto
		dto.setCodMatricula(nuevoCodigo);
		dto.setIdCursoProg(idCursoProg);
		sql = "insert into Matricula(IdCursoProg, IdAlumno,";
		sql += "FecMatricula,ExaParcial,ExaFinal,";
		sql += "Subsanacion,PC1,PC2,";
		sql += "PC3,PC4, Estado, CodMatricula)";
		sql += "values (?, ?, getdate(), 0, 0, 0, 0, 0, 0, 0, 0, ?)";
		jdbcTemplate.update(sql, dto.getIdCursoProg(), dto.getIdAlumno(), dto.getCodMatricula());
		return dto;		
	}
}