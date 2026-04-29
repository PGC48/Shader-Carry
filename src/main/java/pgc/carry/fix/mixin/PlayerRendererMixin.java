package pgc.carry.fix.mixin;

import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pgc.carry.fix.render.CarriedObjectLayer;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(EntityRendererProvider.Context context, boolean bl, CallbackInfo ci) {
        PlayerRenderer renderer = (PlayerRenderer) (Object) this;
        renderer.addLayer(new CarriedObjectLayer(renderer));
    }
}
