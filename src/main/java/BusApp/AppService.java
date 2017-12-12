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

    public List<Double> estadisticas(){

        List<Double> lista = new LinkedList<Double>();
        String file = "src/main/resources/static/rdf/stopsautobus-updated.ttl";
        Model model = ModelFactory.createDefaultModel();
        model.read(file);

        // Get the total number of Bus stops
        String queryString = "PREFIX geo: " + " <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
                "PREFIX : " + "<http://www.semanticweb.org/group12/ontology#> " +
                "PREFIX dbpedia: " + "<http://dbpedia.org/page/> "  +
                "SELECT (count(*) as ?total) WHERE {" +
                "" +
                "   ?busStop a dbpedia:Bus_stop ;" +
                "   :stopName ?stopName ;" +
                "   :stopAddress ?stopAddress ;" +
                "   geo:lat ?geoLat ;" +
                "   geo:long ?geoLong ;" +
                "   :stopZone ?stopZone ." +
                "" +
                "}";
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.create(query, model);
        ResultSet resultSet = qexec.execSelect();
        QuerySolution binding = resultSet.nextSolution();
        Literal total = (Literal) binding.get("total");
        double t = total.getInt(); // With this we get all percents


        // Get the total "A" Bus Stops
        queryString = "PREFIX geo: " + " <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
                "PREFIX : " + "<http://www.semanticweb.org/group12/ontology#> " +
                "PREFIX dbpedia: " + "<http://dbpedia.org/page/> "  +
                "SELECT (count(*) as ?total) WHERE {" +
                "" +
                "   ?busStop a dbpedia:Bus_stop ;" +
                "   :stopName ?stopName ;" +
                "   :stopAddress ?stopAddress ;" +
                "   geo:lat ?geoLat ;" +
                "   geo:long ?geoLong ;" +
                "   :stopZone \"A\" ." +
                "" +
                "}";
        query = QueryFactory.create(queryString);
        qexec = QueryExecutionFactory.create(query, model);
        resultSet = qexec.execSelect();
        binding = resultSet.nextSolution();
        Literal zonaA = (Literal) binding.get("total");
        int a = zonaA.getInt();
        double aPercent = (a/t) * 100;
        lista.add(aPercent);

        // Get the total "B1" Bus Stops
        queryString = "PREFIX geo: " + " <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
                "PREFIX : " + "<http://www.semanticweb.org/group12/ontology#> " +
                "PREFIX dbpedia: " + "<http://dbpedia.org/page/> "  +
                "SELECT (count(*) as ?total) WHERE {" +
                "" +
                "   ?busStop a dbpedia:Bus_stop ;" +
                "   :stopName ?stopName ;" +
                "   :stopAddress ?stopAddress ;" +
                "   geo:lat ?geoLat ;" +
                "   geo:long ?geoLong ;" +
                "   :stopZone \"B1\" ." +
                "" +
                "}";
        query = QueryFactory.create(queryString);
        qexec = QueryExecutionFactory.create(query, model);
        resultSet = qexec.execSelect();
        binding = resultSet.nextSolution();
        Literal zonaB1 = (Literal) binding.get("total");
        int b1 = zonaB1.getInt();
        double b1Percent = (b1/t) * 100;
        lista.add(b1Percent);

        // Get the total "B2" Bus Stops
        queryString = "PREFIX geo: " + " <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
                "PREFIX : " + "<http://www.semanticweb.org/group12/ontology#> " +
                "PREFIX dbpedia: " + "<http://dbpedia.org/page/> "  +
                "SELECT (count(*) as ?total) WHERE {" +
                "" +
                "   ?busStop a dbpedia:Bus_stop ;" +
                "   :stopName ?stopName ;" +
                "   :stopAddress ?stopAddress ;" +
                "   geo:lat ?geoLat ;" +
                "   geo:long ?geoLong ;" +
                "   :stopZone \"B2\" ." +
                "" +
                "}";
        query = QueryFactory.create(queryString);
        qexec = QueryExecutionFactory.create(query, model);
        resultSet = qexec.execSelect();
        binding = resultSet.nextSolution();
        Literal zonaB2 = (Literal) binding.get("total");
        int b2 = zonaB2.getInt();
        double b2Percent = (b2/t) * 100;
        lista.add(b2Percent);


        // Get the total "B3" Bus Stops
        queryString = "PREFIX geo: " + " <http://www.w3.org/2003/01/geo/wgs84_pos#> " +
                "PREFIX : " + "<http://www.semanticweb.org/group12/ontology#> " +
                "PREFIX dbpedia: " + "<http://dbpedia.org/page/> "  +
                "SELECT (count(*) as ?total) WHERE {" +
                "" +
                "   ?busStop a dbpedia:Bus_stop ;" +
                "   :stopName ?stopName ;" +
                "   :stopAddress ?stopAddress ;" +
                "   geo:lat ?geoLat ;" +
                "   geo:long ?geoLong ;" +
                "   :stopZone \"B3\" ." +
                "" +
                "}";
        query = QueryFactory.create(queryString);
        qexec = QueryExecutionFactory.create(query, model);
        resultSet = qexec.execSelect();
        binding = resultSet.nextSolution();
        Literal zonaB3 = (Literal) binding.get("total");
        int b3 = zonaB3.getInt();
        double b3Percent = (b3/t) * 100;
        lista.add(b3Percent);

        return lista;
    }


}
