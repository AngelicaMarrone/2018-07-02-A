package it.polito.tdp.extflightdelays.model;

import java.util.Comparator;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class OrdinaAereoporti implements Comparator<Airport> {
	
	private Graph<Airport, DefaultWeightedEdge> grafo;
	private Airport scelto;
	
	public OrdinaAereoporti(Graph<Airport, DefaultWeightedEdge> grafo, Airport scelto) {
		this.grafo = grafo;
		this.scelto = scelto;
	}

	@Override
	public int compare(Airport a1, Airport a2) {
		return (int) (grafo.getEdgeWeight(grafo.getEdge(scelto, a2)) - grafo.getEdgeWeight(grafo.getEdge(scelto, a1)));
	}

}
