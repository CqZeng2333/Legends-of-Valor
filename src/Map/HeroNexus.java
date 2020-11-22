package Map;
import Avatars.Avatar;
import Avatars.Hero;
import Avatars.HeroFactory;

/*
 * The Nexus for hero with the function of hero born and revive
 */
public class HeroNexus extends Market implements Nexus {
	private HeroFactory hf = new HeroFactory();
	
	public HeroNexus() {
		super();
		this.setMark('N');
		this.setType("hero_nexus");
	}
	
	@Override
	public Avatar produceCharacter(String name) {
		return hf.createHero(hf.getTypeForHero(name), name);
	}
	
	public int revive(Hero hero) {
		 return hero.revive();
	}
}
