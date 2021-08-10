package me.pouille.mods.slimyfloor;

import net.fabricmc.api.ClientModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client implements ClientModInitializer {
    private static final Logger LOGGER = LogManager.getLogger("SlimyFloor");

    @Override
    public void onInitializeClient() {
        LOGGER.info("SlimyFloor is a server side only mod (... for now ...)");
    }

}
