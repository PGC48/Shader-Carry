package pgc.carry.fix;

import net.fabricmc.api.ClientModInitializer;

public class CarryFixClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        System.out.println("[CarryFix] Initialized Shader Compatible Renderer for Carry On");
    }
}
