package path_finder.misc;

public class Pair<T1, T2> implements Comparable<Pair<Double, T2>>{

	private Double first;
	private T2 second;
	
	public Pair(Double f, T2 s) {
		this.first = f;
		this.second = s;
	}
	
	public Double getFirst() {
		return this.first;
	}
	
	public T2 getSecond() {
		return this.second;
	}
	
	@Override
	public int compareTo(Pair<Double,T2> p) { 
		if(this.first == p.getFirst()) {
			return 0;
		}
		else if(this.first < p.getFirst()) {
			return -1;
		}
		else {
			return 1;
		}
	}
	
	
}
