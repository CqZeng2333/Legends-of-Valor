/*
 * The Nexus for hero with the function of hero born and revive
 */
public class HeroNexus extends Market implements Nexus {
	private HeroFactory hf = new HeroFactory();
	
	@Override
	public Avatar produceCharacter(String name) {
		return hf.createHero(hf.getTypeForHero(name), name);
	}
	
	public int revive(Hero hero) {
		 return hero.revive();
	}
}
