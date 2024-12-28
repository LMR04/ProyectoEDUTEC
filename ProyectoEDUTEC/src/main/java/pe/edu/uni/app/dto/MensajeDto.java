package pe.edu.uni.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class MensajeDto {
	private Integer codigo; 
	private String mensaje; 
}