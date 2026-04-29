package pgc.carry.fix.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import tschipp.carryon.Constants;
import tschipp.carryon.common.carry.CarryOnData;
import tschipp.carryon.common.carry.CarryOnDataManager;
import tschipp.carryon.client.render.CarryRenderHelper;

public class CarriedObjectLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public CarriedObjectLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, AbstractClientPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

        CarryOnData carry = CarryOnDataManager.getCarryData(player);
        if (!carry.isCarrying()) return; // ถ้าไม่ได้ถืออะไรเลย ให้ข้ามไป

        poseStack.pushPose();

        // ท่าไม้ตายคณิตศาสตร์: พลิกแกนกลับและย้ายจุดเริ่มต้นลงมาที่เท้า เพื่อให้อ้างอิงโลกจริง
        poseStack.mulPose(Axis.ZP.rotationDegrees(180f));
        poseStack.translate(0.0, -1.5, 0.0);

        if (carry.isCarrying(CarryOnData.CarryType.ENTITY)) {
            // === โซนเรนเดอร์ ENTITY ===
            Entity entity = CarryRenderHelper.getRenderEntity(player);
            if (entity != null) {
                float height = entity.getBbHeight();
                float width = entity.getBbWidth();
                float multiplier = height * width;
                float scale = (10 - multiplier) * 0.08f;
                float scaledHeight = height * scale;

                float offsetY = 0.9f - (scaledHeight / 2.0f);

                poseStack.translate(0.0, offsetY, -0.6);
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
                poseStack.scale(scale, scale, scale);

                // แช่แข็งกันสั่น
                entity.setPos(0, 0, 0);
                entity.xOld = 0; entity.yOld = 0; entity.zOld = 0;
                entity.xo = 0; entity.yo = 0; entity.zo = 0;
                entity.setXRot(0); entity.setYRot(0);
                entity.xRotO = 0; entity.yRotO = 0;

                if (entity instanceof LivingEntity le) {
                    le.yBodyRot = 0; le.yBodyRotO = 0;
                    le.yHeadRot = 0; le.yHeadRotO = 0;
                    le.hurtTime = 0;
                }

                EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
                dispatcher.setRenderShadow(false);
                try {
                    dispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0f, 0.0f, poseStack, buffer, packedLight);
                } catch (Exception ignored) {}
                dispatcher.setRenderShadow(true);
            }
        }
        else if (carry.isCarrying(CarryOnData.CarryType.BLOCK)) {
            // === โซนเรนเดอร์ BLOCK (หีบ, เตาเผา ฯลฯ) ===
            BlockState state = CarryRenderHelper.getRenderState(player);
            ItemStack stack = new ItemStack(state.getBlock().asItem());
            BakedModel model = CarryRenderHelper.getRenderBlock(player);

            float heightOffset = CarryRenderHelper.getRenderHeight(player);
            float customOffset = (heightOffset - 1f) / 1.2f;

            // จัดตำแหน่งบล็อกให้อยู่ตรงอก
            poseStack.translate(0.0, 0.9 - customOffset, -0.6);

            // เช็คว่าต้องหันหน้าบล็อกเข้าหาคนอุ้มหรือไม่ (เช่น หีบ)
            if (Constants.CLIENT_CONFIG.facePlayer != CarryRenderHelper.isChest(state.getBlock())) {
                poseStack.mulPose(Axis.YP.rotationDegrees(180));
            }

            // ลดขนาดบล็อกให้อยู่ในสเกลการอุ้มปกติ
            poseStack.scale(0.6f, 0.6f, 0.6f);

            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            try {
                // เรนเดอร์เป็นไอเทม โดยใช้ Buffer ของ Layer ไม่ทำให้ Shader พังแน่นอน
                itemRenderer.render(stack, ItemDisplayContext.NONE, false, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, model);
            } catch (Exception ignored) {}
        }

        poseStack.popPose();
    }
}