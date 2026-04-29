package pgc.carry.fix;

import net.fabricmc.api.ClientModInitializer;

public class CarryFixClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // ทุกอย่างทำงานผ่าน Mixin จึงไม่ต้องเขียนคำสั่งเริ่มต้นที่นี่
        System.out.println("[CarryFix] Initialized Shader Compatible Renderer for Carry On");
    }
}
