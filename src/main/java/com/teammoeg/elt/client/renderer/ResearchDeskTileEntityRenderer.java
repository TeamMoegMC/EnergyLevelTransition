/*
 *  Copyright (c) 2020. TeamMoeg
 *
 *  This file is part of Energy Level Transition.
 *
 *  Energy Level Transition is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, version 3.
 *
 *  Energy Level Transition is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Energy Level Transition.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.teammoeg.elt.client.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.teammoeg.elt.block.ResearchDeskBlock;
import com.teammoeg.elt.tileentity.ResearchDeskTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.BookModel;
import net.minecraft.client.renderer.tileentity.EnchantmentTableTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;

public class ResearchDeskTileEntityRenderer extends TileEntityRenderer<ResearchDeskTileEntity> {
    private final BookModel bookModel = new BookModel();

    public ResearchDeskTileEntityRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
<<<<<<< HEAD
    public void render(ResearchDeskTileEntity te, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockState blockstate = te.getBlockState();
        int i = 0;
        if (blockstate.getValue(ResearchDeskBlock.BOOK)){
=======
    public void render(ResearchDeskTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        BlockState blockstate = tileEntityIn.getBlockState();
        if (blockstate.getValue(ResearchDeskBlock.BOOK)) {
>>>>>>> cd9fdce97d367854fc70c52eed5e8453dd976935
            matrixStackIn.pushPose();
            
            matrixStackIn.translate(0.5D, 1.04, 0.5D);
<<<<<<< HEAD
            Direction d = blockstate.getValue(ResearchDeskBlock.FACING);
            if (d == Direction.WEST || d==Direction.EAST)
                 i = -180;
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(d.toYRot()+i));
=======
            float f = blockstate.getValue(ResearchDeskBlock.FACING).getCounterClockWise().toYRot();
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(f + 90));
>>>>>>> cd9fdce97d367854fc70c52eed5e8453dd976935
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(90F));
            matrixStackIn.translate(0.0D, -0.125D, 0.0D);
            this.bookModel.setupAnim(0.0F, 0F,te.leftPages, 1.3F);
            IVertexBuilder ivertexbuilder = EnchantmentTableTileEntityRenderer.BOOK_LOCATION.buffer(bufferIn, RenderType::entitySolid);
            this.bookModel.render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);


            matrixStackIn.popPose();
        }
    }
}
