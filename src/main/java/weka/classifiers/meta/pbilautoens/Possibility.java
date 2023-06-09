package weka.classifiers.meta.pbilautoens;

import java.util.ArrayList;

import com.google.gson.Gson;

public class Possibility {

	private String father;
	
	private String key;
	private String drawnValue;
	private float weight;
	private float totalWeight;
	private ArrayList<Possibility> possibilities;
	
	public Possibility(String key) {
		this.father = new String("NIL");
		this.key = key;
		this.possibilities = new ArrayList<Possibility>();
		this.weight = 1;
	}

	public Possibility() {
		this.father = new String("NIL");
		this.possibilities = new ArrayList<Possibility>();
		this.weight = 0;
	}
	
	public void addPossibility(Possibility possibility) {
		possibility.setFather(this.key);
		possibilities.add(possibility);
		this.totalWeight += possibility.getWeight();
	}
	
	public void increaseWeight (float value) {
		this.weight += value;
	}
	
	public void updateWeights() {
		this.totalWeight = 0;
		for(Possibility p: this.possibilities) {
			this.totalWeight += p.getWeight();
		}
	}
	
	public String possibilityAsString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{" + this.father + ", " + this.key + ", " + this.weight + ", " + this.totalWeight + "}" +" @ ");
		for(Possibility p : possibilities) {
			sb.append(p.possibilityAsString());
		}
		return sb.toString();
	}
	
	public String possibilityToString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{" + this.father + ", " + this.key + ", " + this.weight + ", " + this.totalWeight + "}" +" --> \n");
		for(Possibility p : possibilities) {
			sb.append("\t");
			sb.append(p.possibilityToString());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public String possibilityAsGsonString() {
		Gson gson = new Gson();
		return gson.toJson(this, Possibility.class);
	}

	public Possibility findChildPossibility(String name) {
		for(Possibility p : possibilities) {
			if(p.getKey().equals(name)) {
				return p;
			}
		}
		return null;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(float totalWeight) {
		this.totalWeight = totalWeight;
	}

	public ArrayList<Possibility> getPossibilities() {
		return possibilities;
	}

	public void setPossibilities(ArrayList<Possibility> possibilities) {
		this.possibilities = possibilities;
	}
	
	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father;
	}

	public String getDrawnValue() {
		return drawnValue;
	}

	public void setDrawnValue(String drawnValue) {
		this.drawnValue = drawnValue;
	}

	
}
