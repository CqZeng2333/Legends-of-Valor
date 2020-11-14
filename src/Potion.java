/*
 * A concrete class of potion
 * Can increase a specific numeric number of certain skills
 */
import java.util.ArrayList;
import java.util.List;

public class Potion extends BuyableObject {
	private int numericIncreased;
	private List<String> skillsAffected;
	
	public Potion(String name, int cost, int level, int numericIncreased, String[] skillsAffected) {
		super(name, cost, level, "potion");
		this.numericIncreased = numericIncreased;
		this.skillsAffected = new ArrayList<>();
		for (String s : skillsAffected) {
			this.skillsAffected.add(new String(s));
		}
	}
	
	public int getNumericIncreased() {
		return numericIncreased;
	}
	public void setNumericIncreased(int numericIncreased) {
		this.numericIncreased = numericIncreased;
	}
	public List<String> getSkillsAffected() {
		return skillsAffected;
	}
	
}
