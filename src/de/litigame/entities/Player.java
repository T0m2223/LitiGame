package de.litigame.entities;

import java.util.Set;
import java.util.TreeSet;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.Valign;
import de.gurkenlabs.litiengine.entities.AnimationInfo;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.util.geom.GeometricUtilities;
import de.litigame.GameManager;
import de.litigame.abilities.MeleeAttackAbility;
import de.litigame.abilities.RangeAttackAbility;
import de.litigame.hotbar.Hotbar;
import de.litigame.hp.PlayerHealthBar;
import de.litigame.input.PlayerController;
import de.litigame.items.Armor;
import de.litigame.items.Item;
import de.litigame.items.Weapon;
import de.litigame.shop.ShopEntry;
import de.litigame.utilities.GeometryUtilities;

@AnimationInfo(spritePrefix = "player")
@CollisionInfo(collision = true, collisionBoxWidth = 16, collisionBoxHeight = 6, valign = Valign.MIDDLE)
@EntityInfo(width = 16, height = 6)
@MovementInfo(velocity = 70)

public class Player extends Creature implements IUpdateable, IFighter {

	private static final Player instance = new Player();

	private static final double INTERACT_RANGE = 20;

	public static Player getInstance() {
		return instance;
	}

	private Armor currentArmor;

	public final PlayerHealthBar healthBar = new PlayerHealthBar(this);
	public final Hotbar hotbar = new Hotbar(this);

	private final MeleeAttackAbility melee = new MeleeAttackAbility(this);
	private int money = 0, lvl = 1;
	private final RangeAttackAbility range = new RangeAttackAbility(this);
	public final Set<Item> storage = new TreeSet<>((i1, i2) -> i1.getName().compareTo(i2.getName()));

	private Player() {
		super("player");
		addController(new PlayerController(this));

		Game.loop().attach(this);
	}

	public void attack() {
		if (hotbar.getSelectedItem() instanceof Weapon) {
			final Weapon weapon = (Weapon) hotbar.getSelectedItem();
			switch (weapon.type) {
			case MELEE:
				weapon.overrideAbility(melee);
				melee.cast();
				break;
			case RANGE:
				weapon.overrideAbility(range);
				range.cast();
				break;
			}
		}
	}

	public void buy(ShopEntry entry) {
		if (entry.equippable) storage.add(entry.getItem());
		hotbar.addItem(entry.getItem());
	}

	public boolean canBuy(ShopEntry entry) {
		return lvl >= entry.requiredLevel && money >= entry.price;
	}

	public void changeLvl(int shift) {
		lvl += shift;
	}

	public void changeMoney(int shift) {
		money += shift;
	}

	public void dropItem() {
		hotbar.dropSelectedItem();
	}

	public void equip(Armor armor) {
		if (currentArmor != null) getHitPoints().removeModifier(currentArmor.healthBuff());
		getHitPoints().addModifier(armor.healthBuff());
		currentArmor = armor;
	}

	@Override
	public double getStrength() {
		// TODO Auto-generated method stub
		return 10;
	}

	public void interact() {
		final IInteractEntity nearest = GeometryUtilities.getNearestEntity(this, GameManager.interactEntities);

		if (nearest != null && getCenter().distance(nearest.getCenter()) <= INTERACT_RANGE) {
			nearest.interact(this);
		} else {
			Game.world().environment().interact(this);
		}
	}

	@Override
	public void update() {
		if (hotbar.getSelectedItem() instanceof Weapon) {
			setTurnOnMove(false);
			setAngle(GeometricUtilities.calcRotationAngleInDegrees(getCenter(), Input.mouse().getMapLocation()));
		} else {
			setTurnOnMove(true);
		}
	}
}
