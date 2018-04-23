package com.rivdu.servicio;
import com.rivdu.entidades.Plandecuentas;
import java.util.List;

/**
 *
 * @author PCQUISPE
 */
public interface PlanCuentaServicio  extends GenericoServicio<PlanCuentaServicio, Long>{
     
    public Plandecuentas actualizar(PlanCuentaServicio entidad);
    public Plandecuentas crear(PlanCuentaServicio entidad);
    public List<PlanCuentaServicio> listar();

}
