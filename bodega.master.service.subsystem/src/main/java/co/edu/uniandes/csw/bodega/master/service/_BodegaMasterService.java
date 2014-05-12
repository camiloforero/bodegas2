package co.edu.uniandes.csw.bodega.master.service;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.inject.Inject;

import co.edu.uniandes.csw.bodega.master.logic.api.IBodegaMasterLogicService;
import co.edu.uniandes.csw.bodega.master.logic.dto.BodegaMasterDTO;

public abstract class _BodegaMasterService {

    @Inject
    protected IBodegaMasterLogicService bodegaLogicService;

    @POST
    public BodegaMasterDTO createBodega(BodegaMasterDTO bodega) {
        return bodegaLogicService.createMasterBodega(bodega);
    }

    @DELETE
    @Path("{id}")
    public void deleteBodega(@PathParam("id") Long id) {
        bodegaLogicService.deleteMasterBodega(id);
    }
    
    @GET
    @Path("{id}")
    public BodegaMasterDTO getBodega(@PathParam("id") Long id) {
        return bodegaLogicService.getMasterBodega(id);
    }

    @PUT
    @Path("{id}")
    public void updateBodega(@PathParam("id") Long id, BodegaMasterDTO bodega) {
        bodegaLogicService.updateMasterBodega(bodega);
    }

}
