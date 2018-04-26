package com.rivdu.servicio;
import com.rivdu.entidades.Plandecuentas;
import com.rivdu.util.BusquedaPaginada;

/**
 *
 * @author PCQUISPE
 */
public interface PlanCuentaServicio  extends GenericoServicio<Plandecuentas, Long>{
     
    public Plandecuentas actualizar(Plandecuentas entidad);
    public Plandecuentas crear(Plandecuentas entidad);
     public BusquedaPaginada busquedaPaginada(Plandecuentas entidadBuscar, BusquedaPaginada busquedaPaginada, String codigo);
}
