package com.richardjuliano.restsample;

/** 
 * @author RichardJuliano
 */

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

@Path("/ddmmss")
public class DDMMSS {
	// http://localhost:8080/RichardRESTJerseySample/rest/ddmmss/32.21114
	// {"seconds":40.104000091552734,"minutes":12,"DDMMSS":"32Â°12'40.104","decimalDegrees":32.21114,"degrees":32}
	@Path("{decimalDegrees}")
	@GET
	@Produces("application/json")
	/**
	 * Returns a Degrees, Minutes, Seconds (DDMMSS) JSON Object.
	 * The JSON object contains properties: decimalDegrees, degrees,
	 * minutes, seconds, DDMMSS.
	 * <p>
	 * Converts Decimal Degrees to DDMMSS.
	 * 
	 * @param decimalDegrees a latitude or longitude, the where the values are bounded to ±90° and ±180°
	 * @return DDMMSS Object
	 * @throws JSONException
	 */
	public Response convertDecToDDMMSS(@PathParam("decimalDegrees") double decimalDegrees) throws JSONException {
	    final String DEGREE_SYMBOL = "\u00B0";
		JSONObject jsonObject = new JSONObject();
						
		// integer degrees is the integer for the decimal degrees
		int degrees = (int) Math.floor(decimalDegrees);
		
		// minutes is equal to the integer of decimal degrees minus integer degrees times 60
		int minutes = (int) Math.floor((decimalDegrees-degrees) * 60);
		
		// seconds is equal to decimal degrees minus integer degrees minus minutes divide by 60 times 3600
		// one degrees is equal to 60 minutes and is equal to 3600 seconds
		// double seconds = (decimalDegrees - degrees - (minutes/60) ) * 3600;
		double minDiv60 = (double) minutes/60;
		double degDif = (double) decimalDegrees - degrees;
		float seconds = (float) (degDif-minDiv60) * 3600;
				
		// "DDMMSS" property's value (example 30º 15' 50")
		StringBuilder sb = new StringBuilder("");	// initializes string buffer
		sb.append(degrees);
		sb.append(DEGREE_SYMBOL);	// degree symbol
		sb.append(minutes);
		sb.append("'");
		sb.append(seconds);
		
		// creates DDMMSS Object
		jsonObject.put("decimalDegrees", decimalDegrees);
		jsonObject.put("degrees", degrees);
		jsonObject.put("minutes", minutes);
		jsonObject.put("seconds", seconds);		
		jsonObject.put("DDMMSS", sb);
		
		String result = jsonObject.toString();
		
		// produces "application/json"
		return Response.status(200).entity(result).build();
	}
}
