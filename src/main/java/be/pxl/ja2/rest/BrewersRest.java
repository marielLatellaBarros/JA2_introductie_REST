package be.pxl.ja2.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import be.pxl.ja2.dao.BrewerDao;
import be.pxl.ja2.dao.EntityManagerUtil;
import be.pxl.ja2.data.Brewer;
import be.pxl.ja2.rest.resource.BrewerResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Path("brewers")
public class BrewersRest {

	private static final Logger LOGGER = LogManager.getLogger(BrewersRest.class);
	
	@GET
	@Path("city/{city}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Brewer> getBrewersByCity(@PathParam("city") String city) {
		EntityManager em = EntityManagerUtil.createEntityManager();
		BrewerDao brewerDao = new BrewerDao(em);
		LOGGER.info("QueryParam: " + city);
		List<Brewer> brewers = brewerDao.findByCity(city);
		LOGGER.info("Aantal brouwers: " + brewers.size() + " for city " + city);
		em.close();
		return brewers;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createBrewer(BrewerResource brewerResource) {
		EntityManager em = EntityManagerUtil.createEntityManager();
		BrewerDao brewerDao = new BrewerDao(em);

		Brewer brewer = brewerDao.saveBrewer(mapBrewerResource(brewerResource));
		em.close();
		return Response.created(UriBuilder.fromPath("/api/brewers/" + brewer.getId()).build()).build();
	}

	private Brewer mapBrewerResource(BrewerResource brewerResource) {
		Brewer brewer = new Brewer();
		brewer.setAddress(brewerResource.getAddress());
		brewer.setCity(brewerResource.getCity());
		brewer.setName(brewerResource.getName());
		brewer.setTurnover(brewerResource.getTurnover());
		brewer.setZipCode(brewerResource.getZipCode());
		return brewer;
	}

}
