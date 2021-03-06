package de.litigame;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.awt.geom.Point2D;
import java.io.File;

import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.io.XmlUtilities;
import de.litigame.entities.Player;
import de.litigame.items.*;
import de.litigame.hotbar.*;

//Info: creates SaveGame; to save in xml file: saveGame(); to load from xml file: loadSavedGameFile()

@XmlRootElement(name = "savegame") 
public class SaveGame {

	private Hotbar hotb;
	//elements of xml file
	
	@XmlElement(name = "storage")
	private Item[] storage;
	
	@XmlElement(name = "hotbar")
	private String hotbar;

	@XmlElement(name = "slot")
	private int slot;
	
	@XmlElement(name = "money")
	private int money;
	
	@XmlElement(name = "level")
	private int level;
	
	@XmlElement(name = "xPos")
	private double xPos;

	@XmlElement(name = "yPos")
	private double yPos;

	@XmlElement(name="health")
	private int health;
	
	//name of file
	@XmlElement(name = "name")
	private String name;
	
	//constructors
	
	public SaveGame() {
	}
	
	public SaveGame(/*final Item[] storage, */final Hotbar hotbar, final int money, final int level, final Point2D location, final int health, final String name) {
		//this.storage = storage;
		this.hotb = hotbar;
		this.slot = hotb.getSelectedSlot();
		this.hotbar = hotbar.parse();
		this.money = money;
		this.level = level;
		this.xPos = location.getX();
		this.yPos = location.getY();
		this.health = health;
		this.name = name;
	}

	public File saveGame () {
		SaveGame game = new SaveGame(Player.getInstance().hotbar, Player.getInstance().getMoney(),
				Player.getInstance().getLvl(), Player.getInstance().getLocation(), Player.getInstance().getHitPoints().get(), "savegame");
		String saveGamePath = game.getName() + ".xml";
		return(XmlUtilities.save(game, saveGamePath));
	}

	public void restore () {
		final String[] initialItems = { "Trainingsschwert", "null", "null", "null", "null" };
		SaveGame game = new SaveGame(Player.getInstance().hotbar, Player.getInstance().getMoney(),
				Player.getInstance().getLvl(), Player.getInstance().getLocation(), Player.getInstance().getHitPoints().get(), "savegame");
	}
	//getters (transient/not in file)
	
	@XmlTransient
	public Item[] getStorage() {
		return this.storage;
	}
	
	@XmlTransient
	public String[] getHotbar() {
		return hotbar.split(", ");
	}

	@XmlTransient
	public int getSlot() {
		return this.slot;
	}
	
	@XmlTransient
	public int getMoney() {
		return this.money;
	}
	
	@XmlTransient
	public int getLevel() {
		return this.level;
	}
	
	@XmlTransient
	public Point2D getLocation() {
		return new Point2D() {
			@Override
			public double getX() {
				return xPos;
			}

			@Override
			public double getY() {
				return yPos;
			}

			@Override
			public void setLocation(double x, double y) {

			}
		};
	}
	
	@XmlTransient
	public int getHealth() {
		return this.health;
	}
	
	@XmlTransient
	public String getName() {
		return this.name;
	}
	
		
	//setters
	
	public void setStorage(Item[] storage) {
		this.storage = storage;
	}
	
	public void setHotbar(Hotbar hotbar) {
		this.hotb = hotbar;
	}
	
	public void setMoney(int money) {
		this.money = money;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void setLocation(Point2D location) {
		this.xPos = location.getX();
		this.yPos = location.getY();
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}

		
	

