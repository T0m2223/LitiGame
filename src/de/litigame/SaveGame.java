package de.litigame;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.awt.geom.Point2D;

import de.litigame.items.*;
import de.litigame.hotbar.*;

//Info: creates SaveGame; to save in xml file: saveGame(); to load from xml file: loadSavedGameFile()

@XmlRootElement(name = "savegame") 
public class SaveGame {
	
	//elements of xml file
	
	@XmlElement(name = "storage")
	private Item[] storage;
	
	@XmlElement(name = "hotbar")
	private Hotbar hotbar;
	
	@XmlElement(name = "money")
	private int money;
	
	@XmlElement(name = "level")
	private int level;
	
	@XmlElement(name = "xPos")
	private Point2D location;
	
	@XmlElement(name="health")
	private int health;
	
	//name of file
	@XmlElement(name = "name")
	private String name;
	
	//constructors
	
	public SaveGame() {
	}
	
	public SaveGame(/*final Item[] storage,*/ final Hotbar hotbar, final int money, final int level, final Point2D location, final int health, final String name) {
		//this.storage = storage;
		this.hotbar = hotbar;
		//this.money = money;
		this.level = level;
		this.location = location;
		this.health = health;
	}
	
	//getters (transient/not in file)
	
	@XmlTransient
	public Item[] getStorage() {
		return this.storage;
	}
	
	@XmlTransient
	public Hotbar getHotbar() {
		return this.hotbar;
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
		return this.location;
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
		this.hotbar = hotbar;
	}
	
	public void setMoney(int money) {
		this.money = money;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void setLocation(Point2D location) {
		this.location = location;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}

		
	

