package pe.edu.uni.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class AlumnoDto {

	private String codigo;
	private String apellido;
	private String nombre;
	private String direccion;
	private String telefono;
	private String email;
	private int SaldoDisponible;
	
}
