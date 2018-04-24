package com.rivdu.servicio.impl;

import com.rivdu.dao.GenericoDao;
import com.rivdu.entidades.Plandecuentas;
import com.rivdu.excepcion.GeneralException;
import com.rivdu.servicio.PlanCuentaServicio;
import com.rivdu.util.BusquedaPaginada;
import com.rivdu.util.Criterio;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author PCQUISPE
 */
@Service
public class PlanCuentaServicioImpl extends GenericoServicioImpl<Plandecuentas, Long> implements PlanCuentaServicio{
    //show data console
     private final Logger loggerServicio = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private GenericoDao<Plandecuentas, Long> plandecuentadao;
    
    public PlanCuentaServicioImpl(GenericoDao<Plandecuentas , Long> genericoHibernateDao) {
        super(genericoHibernateDao);
    }

    @Override
    public Plandecuentas actualizar(Plandecuentas entidad) {
      //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return plandecuentadao.actualizar(entidad);
   
    }

    @Override
    public Plandecuentas crear(Plandecuentas entidad) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        verificarCuentaRepetidad(entidad);
        entidad.setEstado(true);
        return plandecuentadao.insertar(entidad);
    }
    //verificar 
    private void verificarCuentaRepetidad(Plandecuentas plancuenta) {
        Criterio filtro;
        filtro = Criterio.forClass(Plandecuentas.class);
        filtro.add(Restrictions.eq("estado", Boolean.TRUE));
        if (plancuenta.getId() != null) {
            filtro.add(Restrictions.ne("id", plancuenta.getCodigo()));
        }
        filtro.add(Restrictions.eq("codigo", plancuenta.getCodigo()));
        Plandecuentas u = plandecuentadao.obtenerPorCriteriaSinProyecciones(filtro);
        if (u != null) {
            throw new GeneralException("Ya codigo", "Guardar retorno nulo", loggerServicio);
        }
    }
    
    //buscar por crterios
    @Override
    public BusquedaPaginada busquedaPaginada(Plandecuentas entidadBuscar, BusquedaPaginada busquedaPaginada, String codigo) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Criterio filtro;
        filtro = Criterio.forClass(Plandecuentas.class);
        if (codigo != null && !codigo.equals("")) {
            filtro.add(Restrictions.ilike("codigo", '%' + codigo + '%'));
        }
        //cuenta by id
        busquedaPaginada.setTotalRegistros(plandecuentadao.cantidadPorCriteria(filtro, "id"));
        busquedaPaginada.calcularCantidadDePaginas();
        busquedaPaginada.validarPaginaActual();
        filtro.calcularDatosParaPaginacion(busquedaPaginada);
        filtro.addOrder(Order.desc("id"));
        List<Plandecuentas> u = plandecuentadao.buscarPorCriteriaSinProyecciones(filtro);
        busquedaPaginada.setRegistros(u);
        return busquedaPaginada;
    }
}
