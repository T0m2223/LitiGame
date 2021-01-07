package de.litigame.input;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import de.gurkenlabs.litiengine.input.IKeyboard.KeyPressedListener;
import de.gurkenlabs.litiengine.input.KeyboardEntityController;
import de.gurkenlabs.litiengine.util.ListUtilities;
import de.litigame.entities.Player;

public class PlayerController extends KeyboardEntityController<Player> implements KeyPressedListener {

	private final KeyPressedListener hotbarController;
	private List<Integer> interactKeys;
	private final Player player;

	public PlayerController() {
		this(Player.getInstance(), KeyEvent.VK_F);
	}

	public PlayerController(Player player, int interact) {
		super(player);
		this.player = player;
		hotbarController = new HotbarController(player.hotbar);
		interactKeys = new ArrayList<>();

		interactKeys.add(interact);
		player.addController(this);
	}

	public void addInteractKey(int keyCode) {
		if (!interactKeys.contains(keyCode)) interactKeys.add(keyCode);
	}

	public List<Integer> getInteractKeys() {
		return interactKeys;
	}

	@Override
	public void handlePressedKey(KeyEvent event) {
		// prevents uncontrollable behavior from built-in method
	}

	@Override
	public void keyPressed(KeyEvent event) {
		super.handlePressedKey(event);

		if (interactKeys.contains(event.getKeyCode())) player.interact();

		hotbarController.keyPressed(event);
		event.consume();
	}

	public void setInteractKeys(int... interact) {
		interactKeys = ListUtilities.getIntList(interact);
	}
}