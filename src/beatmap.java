
public class beatmap implements Comparable{
	String name;
	int score;
	public beatmap(String _name, int _score){name = _name;score = _score;}
	@Override
	public int compareTo(Object o) {
		beatmap another = (beatmap) o;
		if(score > another.score)
			return 1;
		
		else if(score < another.score) return -1;
		
		else return 0;
	}
	
}
