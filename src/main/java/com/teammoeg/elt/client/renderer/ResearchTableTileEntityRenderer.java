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
import com.teammoeg.elt.block.ELTTileEntityTypes;
import com.teammoeg.elt.block.researchtable.ResearchTableBlock;
import com.teammoeg.elt.block.researchtable.ResearchTablePart;
import com.teammoeg.elt.block.researchtable.ResearchTableTileEntity;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.tileentity.DualBrightnessCallback;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class ResearchTableTileEntityRenderer extends TileEntityRenderer<ResearchTableTileEntity> {
    private final ModelRenderer headPiece;
    private final ModelRenderer footPiece;
    private final ModelRenderer[] legs = new ModelRenderer[4];

    public ResearchTableTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher_) {
        super(tileEntityRendererDispatcher_);
        this.headPiece = new ModelRenderer(64, 64, 0, 0);
        this.headPiece.addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F, 0.0F);
        this.footPiece = new ModelRenderer(64, 64, 0, 22);
        this.footPiece.addBox(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F, 0.0F);
        this.legs[0] = new ModelRenderer(64, 64, 50, 0);
        this.legs[1] = new ModelRenderer(64, 64, 50, 6);
        this.legs[2] = new ModelRenderer(64, 64, 50, 12);
        this.legs[3] = new ModelRenderer(64, 64, 50, 18);
        this.legs[0].addBox(0.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F);
        this.legs[1].addBox(0.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F);
        this.legs[2].addBox(-16.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F);
        this.legs[3].addBox(-16.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F);
        this.legs[0].xRot = ((float)Math.PI / 2F);
        this.legs[1].xRot = ((float)Math.PI / 2F);
        this.legs[2].xRot = ((float)Math.PI / 2F);
        this.legs[3].xRot = ((float)Math.PI / 2F);
        this.legs[0].zRot = 0.0F;
        this.legs[1].zRot = ((float)Math.PI / 2F);
        this.legs[2].zRot = ((float)Math.PI * 1.5F);
        this.legs[3].zRot = (float)Math.PI;
    }

    public void render(ResearchTableTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        RenderMaterial rendermaterial = new RenderMaterial(Atlases.BED_SHEET, new ResourceLocation("entity/bed/white"));
        World world = tileEntityIn.getLevel();
        if (world != null) {
            BlockState blockstate = tileEntityIn.getBlockState();
            TileEntityMerger.ICallbackWrapper<? extends ResearchTableTileEntity> icallbackwrapper = TileEntityMerger.combineWithNeigbour(ELTTileEntityTypes.RESEARCH_TABLE.get(), ResearchTableBlock::getBlockType, ResearchTableBlock::getConnectedDirection, ChestBlock.FACING, blockstate, world, tileEntityIn.getBlockPos(), (world_, blockPos_) -> {
                return false;
            });
            int i = icallbackwrapper.<Int2IntFunction>apply(new DualBrightnessCallback<>()).get(combinedLightIn);
            this.renderPiece(matrixStackIn, bufferIn, blockstate.getValue(ResearchTableBlock.PART) == ResearchTablePart.HEAD, blockstate.getValue(ResearchTableBlock.FACING), rendermaterial, i, combinedOverlayIn, false);
        } else {
            this.renderPiece(matrixStackIn, bufferIn, true, Direction.SOUTH, rendermaterial, combinedLightIn, combinedOverlayIn, false);
            this.renderPiece(matrixStackIn, bufferIn, false, Direction.SOUTH, rendermaterial, combinedLightIn, combinedOverlayIn, true);
        }
//
//        matrixStackIn.pushPose();
//        matrixStackIn.translate(1, 0, 0);
//        BlockRendererDispatcher blockRenderer = Minecraft.getInstance().getBlockRenderer();
//        BlockState state = Blocks.CHEST.defaultBlockState();
//        blockRenderer.renderBlock(state, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, EmptyModelData.INSTANCE);
//        matrixStackIn.popPose();
//
//        matrixStackIn.pushPose();
//        matrixStackIn.translate(0, 1, 0);
//        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
//        ItemStack stack = new ItemStack(Items.DIAMOND);
//        IBakedModel ibakedmodel = itemRenderer.getModel(stack, tileEntityIn.getLevel(), null);
//        itemRenderer.render(stack, ItemCameraTransforms.TransformType.FIXED, true, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, ibakedmodel);
//        matrixStackIn.popPose();

    }

    private void renderPiece(MatrixStack matrixStack_, IRenderTypeBuffer renderTypeBuffer_, boolean boolean_, Direction direction_, RenderMaterial renderMaterial_, int int_, int int1_, boolean boolean1_) {
        this.headPiece.visible = boolean_;
        this.footPiece.visible = !boolean_;
        this.legs[0].visible = !boolean_;
        this.legs[1].visible = boolean_;
        this.legs[2].visible = !boolean_;
        this.legs[3].visible = boolean_;
        matrixStack_.pushPose();
        matrixStack_.translate(0.0D, 0.5625D, boolean1_ ? -1.0D : 0.0D);
        matrixStack_.mulPose(Vector3f.XP.rotationDegrees(90.0F));
        matrixStack_.translate(0.5D, 0.5D, 0.5D);
        matrixStack_.mulPose(Vector3f.ZP.rotationDegrees(180.0F + direction_.toYRot()));
        matrixStack_.translate(-0.5D, -0.5D, -0.5D);
        IVertexBuilder ivertexbuilder = renderMaterial_.buffer(renderTypeBuffer_, RenderType::entitySolid);
        this.headPiece.render(matrixStack_, ivertexbuilder, int_, int1_);
        this.footPiece.render(matrixStack_, ivertexbuilder, int_, int1_);
        this.legs[0].render(matrixStack_, ivertexbuilder, int_, int1_);
        this.legs[1].render(matrixStack_, ivertexbuilder, int_, int1_);
        this.legs[2].render(matrixStack_, ivertexbuilder, int_, int1_);
        this.legs[3].render(matrixStack_, ivertexbuilder, int_, int1_);
        matrixStack_.popPose();
    }
}
