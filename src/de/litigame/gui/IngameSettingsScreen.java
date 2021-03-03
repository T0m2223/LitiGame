package de.litigame.gui;

import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.graphics.Spritesheet;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.util.io.XmlUtilities;
import de.litigame.SaveGame;
import de.litigame.entities.Player;
import de.litigame.utilities.ImageUtilities;

public class IngameSettingsScreen extends Screen {


    public IngameSettingsScreen() {
        super("ingameSettings");

        String[] items = { "Save Game", "Done" };

        SaveGame saveGame = new SaveGame();

        ImageComponent bkgr = new ImageComponent(0, 0, Resources.images().get("menu"));

        BufferedImage buttonImg = Resources.images().get("menu_item");

        Spritesheet button = new Spritesheet(buttonImg, ImageUtilities.getPath("menu_item"), buttonImg.getWidth(),
                buttonImg.getHeight());

        Menu settings = new Menu(Game.window().getWidth() / 4, Game.window().getHeight() / 4, Game.window().getWidth() / 2,
                Game.window().getHeight() / 2, button, items);

        settings.onChange(index -> {
            if (index == 0) saveGame.saveGame();
            if (index == 1) Game.screens().display("ingameMenu");
        });
        Input.keyboard().onKeyPressed(this::handlePressedKey);
        getComponents().add(bkgr);
        getComponents().add(settings);
    }

    public void handlePressedKey(final KeyEvent keyCode) {
        if(keyCode.getKeyCode()==KeyEvent.VK_ESCAPE && isVisible()){
            System.out.println("hehe");
            Game.screens().display("ingameMenu");
        }
    }

}
