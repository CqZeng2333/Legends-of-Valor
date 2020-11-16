
public class Market extends AccessibleTile {
	private BuyableFactory bf;
	
	public Market() {
		super('M', "market");
		bf = new BuyableFactory();
	}

	public BuyableObject sellBuyableObject(String type, String name, int level, int money) {
		BuyableObject bo = bf.createBuyableObject(type, name);
		if (bo.buyable(level, money) > 0) return bo;
		else return null;
	}
	
	public String displayObject() {
		return this.bf.display();
	}
	
	public BuyableFactory getBF() {
		return bf;
	}
}
