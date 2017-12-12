package BusApp;


import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.util.FileManager;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.XSD;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

@Service
public class AppService {


    public List<Route> listarRutas(){
        String file  = "src/main/resources/static/rdf/routesautobus-updated.ttl";
        // Create an empty model
        Model model = ModelFactory.createDefaultModel();
        List<Route> lista = new LinkedList<Route>();
        // Read the Turtle file
        model.read(file);

        String queryString = "PREFIX : " + "<http://www.semanticweb.org/group12/ontology#> " +
                "PREFIX rdf: " + "<" + RDF.getURI() + "> " +
                "PREFIX owl: " + "<" + OWL.getURI() + "> " +
                "PREFIX xsd: " + "<" + XSD.getURI() + "> " +
                "PREFIX dbpedia: " + "<http://dbpedia.org/page/> "  +
                "PREFIX foaf: " + "<" + FOAF.getURI() + "> " +
                "SELECT ?ruta ?routeLine ?journey ?routeURL WHERE{ " +
                        " ?ruta a dbpedia:Route ;" +
                        " :routeLine ?routeLine ;" +
                        " :journey ?journey ;" +
                        " :routeURL ?routeURL ." +
                        "}";
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet resultSet = qexec.execSelect();
        while (resultSet.hasNext()){
            QuerySolution binding = resultSet.nextSolution();
            Route route = new Route();
            Resource ruta = (Resource) binding.get("ruta");
            route.setIdRoute(ruta.toString());
            Literal routeLine = (Literal) binding.get("routeLine");
            route.setRouteLine(routeLine.toString());
            Literal journey = (Literal) binding.get("journey");
            route.setJourney(journey.toString());
            Literal routeURL = (Literal) binding.get("routeURL");
            route.setRouteURL(routeURL.toString());

            lista.add(route);

        }
        return lista;
    }
}
