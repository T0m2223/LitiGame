package de.litigame.input;

import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

import de.gurkenlabs.litiengine.util.ListUtilities;
import de.litigame.hotbar.Hotbar;

public class HotbarController {

	private final Hotbar bar;
	public boolean invertMouseWheel = false;
	private final List<List<Integer>> slotKeys = new ArrayList<>();

	public HotbarController(Hotbar hotbar) {
		this(hotbar, KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5);
	}

	public HotbarController(Hotbar hotbar, int... hot) {
		bar = hotbar;

		if (hot.length != hotbar.size()) throw new RuntimeException(
				"Parameter size (" + hot.length + ") and hotbar slots (" + hotbar.size() + ") don't match");

		for (int i = 0; i < bar.size(); ++i) {
			slotKeys.add(new ArrayList<>());

			addHotKey(i, hot[i]);
		}
	}

	public void addHotKey(int index, int keyCode) {
		slotKeys.get(index).add(keyCode);
	}

	public List<Integer> getHotKeys(int index) {
		return slotKeys.get(index);
	}

	public void handleMovedWheel(MouseWheelEvent event) {
		if (invertMouseWheel) {
			bar.addToPosition(-event.getWheelRotation());
		} else {
			bar.addToPosition(event.getWheelRotation());
		}
	}

	public void handlePressedKey(KeyEvent event) {
		int key = event.getKeyCode();

		for (int slot = 0; slot < slotKeys.size(); ++slot) {
			List<Integer> keys = slotKeys.get(slot);

			if (keys.contains(key)) {
				bar.setToSlot(slot);
				break;
			}
		}
	}

	public void setHotKeys(int index, int... hot) {
		setHotKeys(index, ListUtilities.getIntList(hot));
	}

	public void setHotKeys(int index, List<Integer> hot) {
		slotKeys.set(index, hot);
	}
}
