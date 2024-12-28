package pe.edu.uni.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MatriculaDto {
	private int idCursoProg;
	private String idCurso;
	private String idCiclo;
	private String idAlumno;
	private String CodMatricula;
}
