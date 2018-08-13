package movie;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import object.Movie;

import org.jboss.resteasy.spi.NoLogWebApplicationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Path("/movie")
public class MovieResource {

	public final static Map<Integer, Movie> movieDB = new HashMap<Integer, Movie>();

//	@GET
//	@Path("/{id}")
//	public Response dispMessage(@PathParam("id") String id) {
//		return Response.status(200).entity(id).build();
//
//	}

    /**
     * Retrieve a phonebook entry and return it as a streaming output, using an XML representation
     * @param id path parameter identifying the phonebook entry
     * @return a representation of the streaming output for the resource representation
     */
    @GET
    @Path( "{id}" )
    @Produces( MediaType.APPLICATION_XML )
    public StreamingOutput getEntryXML(@PathParam("id") Integer id) 
    {
        final Movie movie = movieDB.get(id);

        if (movie == null) {
	    // if you'd like to log this exception in JBoss log file, use WebApplicationException
	    // otherwise, use NoLogWebApplicationException, which will not log the exception
            //throw new WebApplicationException( Response.Status.NOT_FOUND );
            throw new NoLogWebApplicationException( Response.Status.NOT_FOUND );
        }

        return new StreamingOutput() {
            public void write(OutputStream output) throws IOException, NoLogWebApplicationException {
                outputPersonXML(output, movie);
            }
        };
    }
    
    private void outputPersonXML( OutputStream os, Movie movie ) 
            throws IOException 
    {
        PrintStream writer = new PrintStream(os);
        writer.println("<?xml version=\"1.0\"?>");
        writer.println("<movie>");
        writer.println("   <name>" + movie.getName() + "</name>");
        writer.println("   <number>" + movie.getGenres() + "</number>");
        writer.println("</movie>");
        writer.close();
    }
    
	public void readMovieXML() {
		try {

			String rootPath = "/Users/wingrove/javacode/movie/WebContent/";
			File file = new File(rootPath + "WEB-INF/movie.xml");

			DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();

			Document doc = dBuilder.parse(file);

			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());

			if (doc.hasChildNodes()) {

				storageNode(doc.getChildNodes().item(0).getChildNodes());

			}

			for (Entry<Integer, Movie> pair : movieDB.entrySet()) {
				System.out.println("Key : " + pair.getKey() + " Value : "
						+ pair.getValue());
				Movie m = pair.getValue();
				int id = pair.getKey();
				System.out.println(id);
				System.out.println(m.getName());
				System.out.println(m.getGenres());
				System.out.println(m.getRatings());
				System.out.println(m.getShowTime());
				System.out.println(m.getTheater());
				System.out.println(m.getTheaterAddress());
				System.out.println(m.getDescription());
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	public static void storageNode(NodeList nodeList) {

		for (int count = 0; count < nodeList.getLength(); count++) {

			Node movieNode = nodeList.item(count);

			// make sure it's element node.
			if (movieNode.getNodeType() == Node.ELEMENT_NODE) {

				if (movieNode.hasAttributes()) {

					// get attributes names and values
					NamedNodeMap nodeMap = movieNode.getAttributes();
					Movie movie = new Movie();
					int id = 0;
					for (int i = 0; i < nodeMap.getLength(); i++) {

						Node node = nodeMap.item(i);
						if (node.getNodeName().equals("id")) {
							id = Integer.parseInt(node.getNodeValue());
						}
						// System.out.println("attr name : " +
						// node.getNodeName());
						// System.out.println("attr value : "
						// + node.getNodeValue());

					}

					if (movieNode.hasChildNodes()) {

						for (int i = 0; i < movieNode.getChildNodes()
								.getLength(); i++) {
							Node movieAttributeNode = movieNode.getChildNodes()
									.item(i);
							String nodeName = movieAttributeNode.getNodeName();
							String nodeContent = movieAttributeNode
									.getTextContent();

							if (nodeName.equals("name")) {
								movie.setName(nodeContent);
							} else if (nodeName.equals("genres")) {
								movie.setGenres(nodeContent);
							} else if (nodeName.equals("ratings")) {
								movie.setRatings(nodeContent);
								;
							} else if (nodeName.equals("theater")) {
								movie.setTheater(nodeContent);
								;
							} else if (nodeName.equals("showTime")) {
								movie.setShowTime(nodeContent);
								;
							} else if (nodeName.equals("theaterAddress")) {
								movie.setTheaterAddress(nodeContent);
								;
							} else if (nodeName.equals("description")) {
								movie.setDescription(nodeContent);
								;
							}
							// System.out.println("\nNode Name =" + nodeName);
							// System.out.println("Node Value =" + nodeContent);
						}
					}

					movieDB.put(id, movie);

				}

			}

		}

	}
}
