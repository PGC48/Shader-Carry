package pgc.carry.fix.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import tschipp.carryon.client.render.CarriedObjectRender;
import tschipp.carryon.common.carry.CarryOnData;

@Mixin(CarriedObjectRender.class)
public class DisableOldEntityRenderMixin {

    @Redirect(method = "drawThirdPerson", at = @At(value = "INVOKE", target = "Ltschipp/carryon/common/carry/CarryOnData;isCarrying(Ltschipp/carryon/common/carry/CarryOnData$CarryType;)Z"))
    private static boolean bypassOldRendering(CarryOnData instance, CarryOnData.CarryType type) {
        // ข้ามระบบเก่าทั้งหมด (ทั้ง BLOCK และ ENTITY) เพื่อให้ RenderLayer ของเราจัดการแทน
        return false;
    }
}