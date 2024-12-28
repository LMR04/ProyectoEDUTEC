package pe.edu.uni.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.app.dto.AlumnoDto;

@Service
public class AlumnoService {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, 
			rollbackFor = Exception.class)	
	
	//OBTENER TODOS LOS ALUMNOS
	public List<Map<String, Object>> getAlumnos(){
		String sql = "select IdAlumno codigo, ApeAlumno apellido,";
		sql += "NomAlumno nombre, DirAlumno direccion,";
		sql += "TelAlumno telefono, EmailAlumno email, SaldoDisponible ";
		sql += "from Alumno";
		return jdbcTemplate.queryForList(sql); 
	}
	
	// CREAR ALUMNO
	public AlumnoDto crearAlumno(AlumnoDto dto) {
		// GENERAR CODIGO
		String ultimoCodigo = jdbcTemplate.queryForObject(
		        "SELECT MAX(IdAlumno) FROM Alumno", String.class);
		int ultimoNumero = Integer.parseInt(ultimoCodigo.substring(1));
		int nuevoNumero = ultimoNumero + 1;
		String nuevoCodigo = "A" + String.format("%04d", nuevoNumero);
		
		// INSERTAR ALUMNO
		String sql = "insert into Alumno(IdAlumno, ApeAlumno,";
		sql += "NomAlumno,DirAlumno,TelAlumno,";
		sql += "EmailAlumno, SaldoDisponible) ";
		sql += "values(?,?,?,?,?,?,?)";
		jdbcTemplate.update(sql, nuevoCodigo, dto.getApellido(), dto.getNombre(), dto.getDireccion(),
				dto.getTelefono(), dto.getEmail(), dto.getSaldoDisponible());
		dto.setCodigo(nuevoCodigo);
		return dto;
	}
	
	// ELIMINAR ALUMNO
	public void eliminarAlumno(String codigo) {
        String sql = "DELETE FROM Alumno WHERE IdAlumno = ?";
        jdbcTemplate.update(sql, codigo);
    }
}
