package com.teammoeg.elt.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.teammoeg.elt.block.researchdesk.ResearchDeskTileEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public class ResearchDeskTileEntityRenderer extends TileEntityRenderer<ResearchDeskTileEntity> {
    public ResearchDeskTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }
    @Override
    public void render(ResearchDeskTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

    }
}
