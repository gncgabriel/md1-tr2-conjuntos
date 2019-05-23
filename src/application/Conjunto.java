package application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Conjunto {

	private String nome; 
	private Set<Object>elementos; 
	
	public Conjunto(String nome) {
		this.nome = nome; 
		this.elementos = new HashSet<Object>(); 
	}
		
	public Set<Object> getElementos() {
		return elementos;
	}
	
	public String getNome() {
		return this.nome;
	}

	public void add(Object obj) {
		if(!(this.elementos.contains(obj))) {
			this.elementos.add(obj);
		}
	}
	
	public int getCardinalidade() {
		return this.elementos.size();
	}
	
	public Conjunto uniao(Conjunto b){
		Conjunto c = new Conjunto(this.nome + " âˆª " + b.getNome());
		for(Object obj : this.elementos) c.add(obj);
		for(Object obj : b.getElementos()) c.add(obj);
		return c;
	}
	
	public Conjunto intersecao(Conjunto b) {
		Conjunto c = new Conjunto(this.nome + " âˆ© " + b.getNome());
		Conjunto uniao = this.uniao(b);
		for(Object obj : uniao.getElementos()) {
			if(this.elementos.contains(obj) && b.getElementos().contains(obj))
				c.add(obj);
		}
		return c;
	}
	
	public Conjunto diferenca(Conjunto b) {
		Conjunto c = new Conjunto(this.nome + " - " + b.getNome());
		for(Object obj : this.getElementos()) {
			if(!(b.getElementos().contains(obj)))
				c.add(obj);
		}
		return c;
	}
	
	public static Conjunto universo(ArrayList<Conjunto> conj){
		Conjunto U = new Conjunto("U");
		for(Conjunto c : conj)
			for(Object elemento : c.getElementos())
				U.add(elemento);
		return U;
	}
	
	// Recebe o universo como parâmetro;
	public Conjunto complementar(Conjunto b) {
		Conjunto c = new Conjunto(this.nome + "\'");
		for(Object obj : this.getElementos()) {
			if(!(b.getElementos().contains(obj)))
				c.add(obj);
		}
		return  c;
	}
	
	public Conjunto partes() {
		Conjunto P = new Conjunto("P(" + this.nome +")");
		P.add("ø");
		for(Object e : this.getElementos()) {
			P.add(e);
		}
		// Falta pegar as combinações de elementos.
		return P;
	}
	
	@Override
	public String toString() {
		return nome + " = { " + elementos + " }";
	}

	@Override
	public boolean equals(Object obj) {
		if(this.getCardinalidade() != ((Conjunto)obj).getCardinalidade())
			return false; 
		else {
			for(Object e : this.elementos) {
				if(!((Conjunto)obj).getElementos().contains(e))
					return false;
			}
			return true;
		}
	}
	
	
	
}
