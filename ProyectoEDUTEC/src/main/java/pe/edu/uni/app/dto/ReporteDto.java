// ReporteDto.java
package pe.edu.uni.app.dto;

import lombok.Data;

@Data
public class ReporteDto {
    private String nombreCurso;
    private String idCiclo; 
    private double promedio;
    private boolean aprobado;
}
