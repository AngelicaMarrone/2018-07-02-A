package it.polito.tdp.extflightdelays.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	private Graph<Airport, DefaultWeightedEdge> grafo;
	private ExtFlightDelaysDAO dao;
	private Map<Integer, Airport> idMap;
	
	private List<Airport> best;
	private double disponibili;
	
	public Model() {
		dao = new ExtFlightDelaysDAO();
		idMap = new HashMap<>();
		dao.loadAllAirports(idMap);
	}

	public void creaGrafo(double mediaMin) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		for (Adiacenze a: dao.adiacenze(mediaMin)) {
			
			Airport partenza = idMap.get(a.getId1());
			if (!grafo.containsVertex(partenza))
				grafo.addVertex(partenza);
			
			Airport arrivo = idMap.get(a.getId2());
			if (!grafo.containsVertex(arrivo))
				grafo.addVertex(arrivo);
			
			if (!grafo.containsEdge(partenza, arrivo))
				Graphs.addEdge(grafo, partenza, arrivo, a.getDistanza());
			else { // aggiorna il peso dell'arco
				DefaultWeightedEdge arco = grafo.getEdge(partenza, arrivo);
				double peso = (a.getDistanza() + grafo.getEdgeWeight(arco))/2;
				grafo.setEdgeWeight(arco, peso);
			}
				
		}
		
	}
	
	public Set<Airport> getAereoporti() {
		return grafo.vertexSet();
	}
	
	public List<Airport> calcolaConnessi(Airport scelto) {
		List<Airport> result = Graphs.neighborListOf(grafo, scelto);
		
		Collections.sort(result, new OrdinaAereoporti(grafo, scelto));
		
		return result;
	}
	
	public List<Airport> calcolaItinerario(double disponibili, Airport scelto) {
		best = new LinkedList<Airport>();
		this.disponibili = disponibili;
		
		List<Airport> parziale = new LinkedList<Airport>();
		parziale.add(scelto);
		
		cerca(parziale);
		
		return best;
	}
	
	private void cerca(List<Airport> parziale) {
		
		if ( parziale.size() > best.size() )
			best = new LinkedList<>(parziale);
		
		for (Airport a: calcolaConnessi(parziale.get(parziale.size()-1)))
			if ( !parziale.contains(a) ) {
				parziale.add(a);
				
				if ( distanzaPercorsa(parziale) <= disponibili ) 
					cerca(parziale);
				
				parziale.remove(parziale.size()-1);
			}
		
	}
	
	public double distanzaPercorsa(List<Airport> parziale) {
		double somma = 0;
		
		for (int i=1; i<parziale.size(); i++)
			somma += grafo.getEdgeWeight(grafo.getEdge(parziale.get(i-1), parziale.get(i)));
		
		return somma;
	}
		
}