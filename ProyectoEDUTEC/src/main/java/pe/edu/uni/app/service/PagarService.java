package pe.edu.uni.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import pe.edu.uni.app.dto.PagarDto;

@Service
public class PagarService {


	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Transactional(propagation = Propagation.REQUIRES_NEW, 
			rollbackFor = Exception.class)
	public PagarDto pagarMatricula(PagarDto dto) {
		//VERIFICAR SI LA MATRICULA ESTA REGISTRADA
        String sql = "SELECT COUNT(1) FROM Matricula WHERE IdAlumno = ?";
        int numF = jdbcTemplate.queryForObject(sql, Integer.class, dto.getIdAlumno());
        if (numF == 0) {
			throw new RuntimeException("Alumno no registrado: " + dto.getIdAlumno());
		} 
        //VERIFICAR PAGO PENDIENTE
        sql = "SELECT count(1) cont from Matricula ";
        sql += "Where  IdAlumno = ? AND Estado = 0";
        int filas = jdbcTemplate.queryForObject(sql, Integer.class, dto.getIdAlumno());
		if (filas == 0) {
			throw new RuntimeException("No hay pagos pendientes");
		}
		// OBETENER SALDO
		String obtenerSaldo = "SELECT SaldoDisponible FROM Alumno WHERE IdAlumno = ?";
		Double saldo = jdbcTemplate.queryForObject(obtenerSaldo, Double.class, dto.getIdAlumno());
		dto.setSaldoDisponible(saldo);
		//OBTENER EL COSTO DEL CURSO MATRICULA
		String obtenerPrecioTotalCursos = "SELECT SUM(cp.PreCursoProg) " +
		        "FROM CursoProgramado cp " +
		        "JOIN Matricula m ON m.IdCursoProg = cp.IdCursoProg " +
		        "WHERE m.IdAlumno = ? AND m.Estado = 0";
		double precioTotalCursos = jdbcTemplate.queryForObject(obtenerPrecioTotalCursos, Double.class, dto.getIdAlumno());

		if (dto.getSaldoDisponible() < precioTotalCursos) {
		    throw new RuntimeException("Saldo insuficiente");
		}
		//PAGAR LA DEUDA
		sql = "update Alumno ";
		sql += "SET SaldoDisponible = SaldoDisponible - ? ";
		sql += "WHERE IdAlumno = ? ";
		jdbcTemplate.update(sql, precioTotalCursos, dto.getIdAlumno());
		sql = "update Matricula ";
		sql += "SET Estado = 1";
		sql += "WHERE IdAlumno = ? ";
		jdbcTemplate.update(sql,dto.getIdAlumno());
		
		return dto;
	}
}