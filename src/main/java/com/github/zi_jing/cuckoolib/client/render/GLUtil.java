package com.github.zi_jing.cuckoolib.client.render;

import com.github.zi_jing.cuckoolib.CuckooLib;
import com.github.zi_jing.cuckoolib.util.math.ColorRGBA;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * Cuckoo图形库<br>
 * <strong>若没有特殊说明，所有用单个整数表示的颜色均使用RGBA格式，不指定不透明度可能会产生错误的颜色。</strong>
 */
@SuppressWarnings({ "deprecation" })
public class GLUtil {
	// 黑魔法，用来调用mc原生Gui方法
	private static final Screen screen = new EmptyScreen();
	public static Minecraft mc;
	public static FontRenderer fontRenderer;
	public static IResourceManager resourceManager;
	public static TextureManager textureManager;
	public static Tessellator tessellator;
	public static BufferBuilder bufferBuilder;
	private static int r;
	private static int g;
	private static int b;
	private static int renderStateR;
	private static int renderStateG;
	private static int renderStateB;
	private static int renderStateA;

	static {
		// 防止单元测试时出错
		mc = Minecraft.getInstance();
		try {
			fontRenderer = mc.font;
			resourceManager = mc.getResourceManager();
			textureManager = mc.textureManager;
			tessellator = Tessellator.getInstance();
			bufferBuilder = Tessellator.getInstance().getBuilder();
		} catch (NullPointerException e) {
			CuckooLib.getLogger().warn("CuckooLib: Can't find the Minecraft instance");
			CuckooLib.getLogger().warn("CuckooLib: If you are running JUnit tests, you can ignore this warning");
		}
	}

	/**
	 * 将RGB颜色合成24位RGB整数颜色。<br>
	 * 示例:<br>
	 * &emsp;&emsp; <code>rgbToHex(102, 204, 255) = 0x66CCFF</code>
	 *
	 * @see #hexRGBToRGB(int)
	 */
	public static int rgbToHex(int r, int g, int b) {
		r <<= 16;
		g <<= 8;
		return r | g | b;
	}

	/**
	 * 将24位RGB整数颜色转为RGB颜色。
	 *
	 * @see #rgbToHex(int, int, int)
	 * @see #hexRGBAToRGBA(int)
	 */
	public static ColorRGBA hexRGBToRGB(int hex) {
		int r = (hex & 0xFF0000) >> 16;
		int g = (hex & 0x00FF00) >> 8;
		int b = (hex & 0x0000FF);
		return new ColorRGBA(r, g, b);
	}

	/**
	 * 将32位RGBA整数颜色转为RGB颜色。
	 *
	 * @see #rgbToHex(int, int, int)
	 * @see #hexRGBToRGB(int)
	 */
	public static ColorRGBA hexRGBAToRGBA(int hex) {
		int r = (hex & 0xFF000000) >>> 24;
		int g = (hex & 0x00FF0000) >>> 16;
		int b = (hex & 0x0000FF00) >>> 8;
		int a = (hex & 0x000000FF);
		return new ColorRGBA(r, g, b, a);
	}

	/**
	 * 将十六进制颜色字符串转为24位RGB整数颜色。
	 *
	 * @param hex 像"3FFF3F"、"3fff3f"、"3FfF3f"这样的字符串。它也支持以'#'开头的字符串<br>
	 *            <br>
	 *            Strings like "3FFF3F", "3fff3f" or "3FfF3f". It also supports
	 *            strings what starts with '#'
	 * @see #hexStringToRGB(String)
	 */
	public static int hexStringToRGB24(String hex) {
		hex = hex.toUpperCase();
		int color = 0;
		for (char c : hex.toCharArray()) {
			switch (c) {
			case '#':
				continue;
			case '0':
				color = color << 4;
				break;
			case '1':
				color = color << 4 | 0x00000001;
				break;
			case '2':
				color = color << 4 | 0x00000002;
				break;
			case '3':
				color = color << 4 | 0x00000003;
				break;
			case '4':
				color = color << 4 | 0x00000004;
				break;
			case '5':
				color = color << 4 | 0x00000005;
				break;
			case '6':
				color = color << 4 | 0x00000006;
				break;
			case '7':
				color = color << 4 | 0x00000007;
				break;
			case '8':
				color = color << 4 | 0x00000008;
				break;
			case '9':
				color = color << 4 | 0x00000009;
				break;
			case 'A':
				color = color << 4 | 0x0000000A;
				break;
			case 'B':
				color = color << 4 | 0x0000000B;
				break;
			case 'C':
				color = color << 4 | 0x0000000C;
				break;
			case 'D':
				color = color << 4 | 0x0000000D;
				break;
			case 'E':
				color = color << 4 | 0x0000000E;
				break;
			case 'F':
				color = color << 4 | 0x0000000F;
				break;
			default:
				break;
			}
		}
		return color;
	}

	/**
	 * 将十六进制颜色字符串转为RGB颜色。
	 *
	 * @param hex 像"3FFF3F"、"3fff3f"、"3FfF3f"这样的字符串。它也支持以'#'开头的字符串<br>
	 *            <br>
	 *            Strings like "3FFF3F", "3fff3f" or "3FfF3f". It also supports
	 *            strings what starts with '#'
	 * @see #hexStringToRGB24(String)
	 */
	public static ColorRGBA hexStringToRGB(String hex) {
		return hexRGBToRGB(hexStringToRGB24(hex));
	}

	/**
	 * 获取正常字体大小下某个字符串的宽度(像素)。
	 *
	 * @see FontRenderer#getStringWidth(String)
	 * @see #splitStringWithWidth(String, int)
	 */
	public static int getStringWidth(String str) {
		return fontRenderer.width(str);
	}

	/**
	 * 将一个字符串按(正常字体大小的)的宽度分成多行。
	 *
	 * @see #getStringWidth(String)
	 */
	public static List<String> splitStringWithWidth(String str, int width) {
		List<String> lines = new ArrayList<>();
		int currWidth = 0;
		StringBuilder builder = new StringBuilder(str.length());
		while (!str.isEmpty()) {
			char c = str.charAt(0);
			int charWidth = getStringWidth(String.valueOf(c));
			if (charWidth > width) {
				return new ArrayList<>();
			}
			if (getStringWidth(builder.toString() + charWidth) > width) {
				lines.add(builder.toString());
				builder = new StringBuilder(str.length());
			} else {
				builder.append(c);
				str = str.substring(1);
			}
		}
		if (!builder.toString().isEmpty())
			lines.add(builder.toString());
		return lines;
	}

	/**
	 * 在指定位置绘制一个正常字体大小的字符串。
	 *
	 * @see #drawScaledString(MatrixStack, String, float, float, int, int)
	 * @see #drawStringWithShadow(MatrixStack, String, float, float, int)
	 * @see #drawScaledStringWithShadow(MatrixStack, String, float, float, int, int)
	 * @see #drawCenteredString(MatrixStack, String, float, float, int)
	 * @see #drawScaledCenteredString(MatrixStack, String, float, float, int, int)
	 * @see #drawCenteredStringWithShadow(MatrixStack, String, float, float, int)
	 * @see #drawScaledCenteredStringWithShadow(MatrixStack, String, float, float,
	 *      int, int)
	 */
	public static void drawString(MatrixStack matrixStack, String str, float x, float y, int color) {
		fontRenderer.draw(matrixStack, str, x, y, color);
	}

	/**
	 * 在指定位置绘制一个自定义字体大小的字符串。参数{@code height}为缩放后的字体高度(像素), 正常字体大小下字体高度为9像素。 <br>
	 * <strong>注意: 除非必要, 否则你不应该将其他绘图语句使用的{@link MatrixStack}传入该方法,
	 * 这个方法会污染传入的{@link MatrixStack}。</strong>
	 *
	 * @see #drawString(MatrixStack, String, float, float, int)
	 */
	public static void drawScaledString(MatrixStack matrixStack, String str, float x, float y, int color, int height) {
		float f = ((float) height) / 9;
		matrixStack.scale(f, f, 1f);
		drawString(matrixStack, str, x / f, y / f, color);
	}

	/**
	 * 在指定位置绘制一个正常字体大小的带阴影的字符串。
	 *
	 * @see #drawString(MatrixStack, String, float, float, int)
	 */
	public static void drawStringWithShadow(MatrixStack matrixStack, String str, float x, float y, int color) {
		fontRenderer.drawShadow(matrixStack, str, x, y, color);
	}

	/**
	 * 在指定位置绘制一个自定义字体大小的带阴影的字符串。参数{@code height}为缩放后的字体高度(像素), 正常字体大小下字体高度为9像素。 <br>
	 * <strong>注意: 除非必要, 否则你不应该将其他绘图语句使用的{@link MatrixStack}传入该方法,
	 * 这个方法会污染传入的{@link MatrixStack}。</strong>
	 *
	 * @see #drawString(MatrixStack, String, float, float, int)
	 */
	public static void drawScaledStringWithShadow(MatrixStack matrixStack, String str, float x, float y, int color,
			int height) {
		float f = ((float) height) / 9;
		matrixStack.scale(f, f, 1f);
		drawStringWithShadow(matrixStack, str, x / f, y / f, color);
	}

	/**
	 * 在指定位置绘制一个正常字体大小的居中的字符串。
	 *
	 * @see #drawString(MatrixStack, String, float, float, int)
	 */
	public static void drawCenteredString(MatrixStack matrixStack, String str, float x, float y, int color) {
		float width = getStringWidth(str);
		drawString(matrixStack, str, x - width / 2, y, color);
	}

	/**
	 * 在指定位置绘制一个自定义字体大小的居中的字符串。参数{@code height}为缩放后的字体高度(像素), 正常字体大小下字体高度为9像素。 <br>
	 * <strong>注意: 除非必要, 否则你不应该将其他绘图语句使用的{@link MatrixStack}传入该方法,
	 * 这个方法会污染传入的{@link MatrixStack}。</strong>
	 *
	 * @see #drawString(MatrixStack, String, float, float, int)
	 */
	public static void drawScaledCenteredString(MatrixStack matrixStack, String str, float x, float y, int color,
			int height) {
		float f = ((float) height) / 9;
		matrixStack.scale(f, f, 1f);
		drawCenteredString(matrixStack, str, x / f, y / f, color);
	}

	/**
	 * 在指定位置绘制一个正常字体大小的居中的带阴影的字符串。
	 *
	 * @see #drawString(MatrixStack, String, float, float, int)
	 */
	public static void drawCenteredStringWithShadow(MatrixStack matrixStack, String str, float x, float y, int color) {
		float width = getStringWidth(str);
		drawStringWithShadow(matrixStack, str, x - width / 2, y, color);
	}

	/**
	 * 在指定位置绘制一个自定义字体大小的居中带阴影的字符串。参数{@code height}为缩放后的字体高度(像素), 正常字体大小下字体高度为9像素。
	 * <br>
	 * <strong>注意: 除非必要, 否则你不应该将其他绘图语句使用的{@link MatrixStack}传入该方法,
	 * 这个方法会污染传入的{@link MatrixStack}。</strong>
	 *
	 * @see #drawString(MatrixStack, String, float, float, int)
	 */
	public static void drawScaledCenteredStringWithShadow(MatrixStack matrixStack, String str, float x, float y,
			int color, int height) {
		float f = ((float) height) / 9;
		matrixStack.scale(f, f, 1f);
		drawCenteredStringWithShadow(matrixStack, str, x / f, y / f, color);
	}

	/**
	 * 设置绘图时直线的粗细(像素)。
	 */
	public static void glLineWidth(float width) {
		GlStateManager._lineWidth(width);
	}

	/**
	 * 设置绘图时点的直径(像素)。
	 */
	public static void glPointSize(float size) {
		GL11.glPointSize(size);
	}

	/**
	 * 设置绘图使用的颜色, 四个整数参数取值范围均为[0, 255]。
	 *
	 * @see #color3i(int, int, int)
	 * @see #color4f(float, float, float, float)
	 */
	public static void color4i(int r, int g, int b, int a) {
		GlStateManager._color4f(r / 255F, g / 255F, b / 255F, a / 255F);
	}

	/**
	 * 设置绘图使用的颜色, 三个整数参数取值范围均为[0, 255], Alpha值为255(不透明)。
	 *
	 * @see #color4i(int, int, int, int)
	 */
	public static void color3i(int r, int g, int b) {
		color4i(r, g, b, 255);
	}

	/**
	 * 设置绘图使用的颜色, 四个浮点数参数取值范围均为[0.0F, 1.0F]。
	 *
	 * @see #color4i(int, int, int, int)
	 * @see #color3f(float, float, float)
	 */
	public static void color4f(float r, float g, float b, float a) {
		GlStateManager._color4f(r, g, b, a);
	}

	/**
	 * 设置绘图使用的颜色, 三个浮点数参数取值范围均为[0.0F, 1.0F], Alpha值为1.0F(不透明)。
	 *
	 * @see #color4f(float, float, float, float)
	 */
	public static void color3f(float r, float g, float b) {
		color4f(r, g, b, 1F);
	}

	/**
	 * 将指定纹理绑定到渲染器上, 参数为指向纹理所在的PNG文件的{@link ResourceLocation}。
	 *
	 * @see #bindTexture(int)
	 */
	public static void bindTexture(ResourceLocation textureLocation) {
		textureManager.bind(textureLocation);
	}

	/**
	 * 将指定纹理绑定到渲染器上, 参数为纹理的内部ID。
	 *
	 * @see #bindTexture(ResourceLocation)
	 */
	public static void bindTexture(int textureId) {
		GlStateManager._bindTexture(textureId);
	}

	/**
	 * 启用OpenGL混色功能, <strong>需要和{@link #disableBlend()}成对使用</strong><br>
	 * 。 要使其生效，你需要使用{@link GlStateManager#blendFunc(int, int)}自定义混色方式
	 * 或直接调用{@link #normalBlend()}启用混色并设置正常的混色方式。
	 *
	 * @see #normalBlend()
	 */
	public static void enableBlend() {
		GlStateManager._enableBlend();
	}

	/**
	 * 关闭OpenGL混色功能, 需要和{@link #enableBlend()}成对使用。
	 *
	 * @see #enableBlend()
	 */
	public static void disableBlend() {
		GlStateManager._disableBlend();
	}

	/**
	 * 启用2D纹理。
	 *
	 * @see #disableTexture()
	 */
	public static void enableTexture() {
		GlStateManager._enableTexture();
	}

	/**
	 * 禁用2D纹理, <strong>完成绘图操作后必须调用{@link #enableTexture()}启用2D纹理,
	 * 否则会引发严重后果!</strong>
	 */
	public static void disableTexture() {
		GlStateManager._disableTexture();
	}

	/**
	 * 启用混色并使用正常的混色方式, 需要和{@link #disableBlend()}成对使用。
	 *
	 * @see #enableBlend()
	 */
	public static void normalBlend() {
		GlStateManager._enableBlend();
		GlStateManager._blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	/**
	 * 必须与{@link #popMatrix()}成对使用, 否则会引发OpenGL错误。<br>
	 * 具体含义及用途请查阅OpenGL相关文档。
	 */
	public static void pushMatrix() {
		GlStateManager._pushMatrix();
	}

	/**
	 * 必须与{@link #pushMatrix()}成对使用, 否则会引发OpenGL错误。<br>
	 * 具体含义及用途请查阅OpenGL相关文档。
	 */
	public static void popMatrix() {
		GlStateManager._popMatrix();
	}

	/**
	 * 用于完成纯色绘图前的准备工作，包括设置颜色、调整混色和禁用贴图。<br>
	 * <strong>必须和{@link #postRenderNoTexture()}成对使用, 否则将引发OpenGL错误。</strong>
	 */
	public static void preRenderNoTexture(int color) {
		int r = color >> 24 & 255;
		int g = color >> 16 & 255;
		int b = color >> 8 & 255;
		int a = color & 255;
		pushMatrix();
		color4i(r, g, b, a);
		renderStateR = r;
		renderStateG = g;
		renderStateB = b;
		renderStateA = a;
		enableBlend();
		normalBlend();
		disableTexture();
	}

	/**
	 * 必须与{@link #preRenderNoTexture(int)}成对使用, 否则将引发OpenGL错误。
	 */
	public static void postRenderNoTexture() {
		disableBlend();
		enableTexture();
		popMatrix();
	}

	/**
	 * 用于完成贴图绘制前的准备工作，包括设置颜色、调整混色和启用贴图。<br>
	 * 需要和{@link #postRenderTexture()}成对使用。
	 */
	public static void preRenderTexture() {
		pushMatrix();
		enableBlend();
		normalBlend();
		enableTexture();
		color4f(1f, 1f, 1f, 1f);
	}

	/**
	 * 用于完成贴图绘制前的准备工作，包括设置颜色、调整混色和启用贴图。<br>
	 * 需要和{@link #postRenderTexture()}成对使用。
	 */
	public static void preRenderTexture(int color) {
		int r = color >> 24 & 255;
		int g = color >> 16 & 255;
		int b = color >> 8 & 255;
		int a = color & 255;
		pushMatrix();
		enableBlend();
		normalBlend();
		enableTexture();
		color4i(r, g, b, a);
	}

	/**
	 * 用于完成贴图绘制前的准备工作，包括设置颜色、调整混色和启用贴图。<br>
	 * 需要和{@link #postRenderTexture()}成对使用。
	 */
	public static void preRenderTexture(int r, int g, int b, int a) {
		pushMatrix();
		enableBlend();
		normalBlend();
		enableTexture();
		color4i(r, g, b, a);
	}

	/**
	 * 需要与{@link #preRenderTexture()}成对使用。
	 */
	public static void postRenderTexture() {
		disableBlend();
		enableTexture();
		popMatrix();
	}

	/**
	 * 用于绘制原版Gui在世界中的暗灰色半透明背景,
	 * 通常需要在{@link Screen#render(MatrixStack, int, int, float)}的开头调用。
	 *
	 * @see #drawDirtBackground()
	 */
	public static void drawDefaultBackground() {
		screen.renderBackground(new MatrixStack());
	}

	/**
	 * 用于绘制原版Gui不再在世界中时的泥土背景,
	 * 通常需要在{@link Screen#render(MatrixStack, int, int, float)}的开头调用。
	 *
	 * @see #drawDefaultBackground()
	 */
	public static void drawDirtBackground() {
		screen.renderDirtBackground(0);
	}

	/**
	 * 绘制纯色矩形, 颜色格式为RGBA整数颜色。
	 */
	public static void fill(MatrixStack matrixStack, int x, int y, int width, int height, int color) {
		preRenderNoTexture(color);
		bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		Matrix4f matrix4f = matrixStack.last().pose();
		bufferBuilder.vertex(matrix4f, x, y + height, 0).color(renderStateR, renderStateG, renderStateB, renderStateA)
				.endVertex();
		bufferBuilder.vertex(matrix4f, x + width, y + height, 0)
				.color(renderStateR, renderStateG, renderStateB, renderStateA).endVertex();
		bufferBuilder.vertex(matrix4f, x + width, y, 0).color(renderStateR, renderStateG, renderStateB, renderStateA)
				.endVertex();
		bufferBuilder.vertex(matrix4f, x, y, 0).color(renderStateR, renderStateG, renderStateB, renderStateA).endVertex();
		tessellator.end();
		postRenderNoTexture();
	}

	/**
	 * 绘制矩形贴图, 贴图大小必须为256*256, 否则会绘制出错误的图形。
	 *
	 * @see #drawTexturedRect(MatrixStack, int, int, int, int, int, int,
	 *      ResourceLocation)
	 * @see #drawTexturedRectWithDefaultUV(MatrixStack, int, int, int, int)
	 * @see #drawTexturedRectWithDefaultUV(MatrixStack, int, int, int, int,
	 *      ResourceLocation)
	 * @see #drawTexturedRectWithCustomSizedTexture(MatrixStack, int, int, int, int,
	 *      int, int, int, int, int, int)
	 * @see #drawTexturedRectWithCustomSizedTexture(MatrixStack, int, int, int, int,
	 *      int, int, int, int, int, int, ResourceLocation)
	 * @see #drawTexturedRectWithCustomSizedTextureWithDefaultUV(MatrixStack, int,
	 *      int, int, int, int, int, int, int)
	 * @see #drawTexturedRectWithCustomSizedTextureWithDefaultUV(MatrixStack, int,
	 *      int, int, int, int, int, int, int, ResourceLocation)
	 */
	public static void drawTexturedRect(MatrixStack matrixStack, int x, int y, int u, int v, int width, int height) {
		preRenderTexture();
		double m = 1D / 256D;
		bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		Matrix4f matrix4f = matrixStack.last().pose();
		bufferBuilder.vertex(matrix4f, x, y + height, 0).uv((float) (m * u), (float) (m * (v + height))).endVertex();
		bufferBuilder.vertex(matrix4f, x + width, y + height, 0).uv((float) (m * (u + width)), (float) (m * (v + height)))
				.endVertex();
		bufferBuilder.vertex(matrix4f, x + width, y, 0).uv((float) (m * (u + width)), (float) (m * v)).endVertex();
		bufferBuilder.vertex(matrix4f, x, y, 0).uv((float) (m * u), (float) (m * v)).endVertex();
		tessellator.end();
		postRenderTexture();
	}

	/**
	 * 绘制矩形贴图, 贴图大小必须为256*256, 否则会绘制出错误的图形。<br>
	 * 可指定{@link ResourceLocation}。
	 *
	 * @see #drawTexturedRect(MatrixStack, int, int, int, int, int, int)
	 */
	public static void drawTexturedRect(MatrixStack matrixStack, int x, int y, int u, int v, int width, int height,
			ResourceLocation textureLocation) {
		bindTexture(textureLocation);
		drawTexturedRect(matrixStack, x, y, u, v, width, height);
	}

	/**
	 * 绘制矩形贴图, 贴图大小必须为256*256, 否则会绘制出错误的图形。<br>
	 * UV为0, 即总是从贴图左上角开始绘制。
	 *
	 * @see #drawTexturedRect(MatrixStack, int, int, int, int, int, int)
	 */
	public static void drawTexturedRectWithDefaultUV(MatrixStack matrixStack, int x, int y, int width, int height) {
		drawTexturedRect(matrixStack, x, y, 0, 0, width, height);
	}

	/**
	 * 绘制矩形贴图, 贴图大小必须为256*256, 否则会绘制出错误的图形。<br>
	 * UV为0, 即总是从贴图左上角开始绘制。<br>
	 * 可指定{@link ResourceLocation}。
	 *
	 * @see #drawTexturedRect(MatrixStack, int, int, int, int, int, int)
	 */
	public static void drawTexturedRectWithDefaultUV(MatrixStack matrixStack, int x, int y, int width, int height,
			ResourceLocation textureLocation) {
		bindTexture(textureLocation);
		drawTexturedRect(matrixStack, x, y, 0, 0, width, height);
	}

	/**
	 * 绘制矩形贴图, 可缩放纹理, 可自行指定贴图大小。
	 *
	 * @see #drawTexturedRect(MatrixStack, int, int, int, int, int, int)
	 * @see #drawTexturedRectWithCustomSizedTexture(MatrixStack, int, int, int, int,
	 *      int, int, int, int, int, int, ResourceLocation)
	 * @see #drawTexturedRectWithCustomSizedTextureWithDefaultUV(MatrixStack, int,
	 *      int, int, int, int, int, int, int)
	 * @see #drawTexturedRectWithCustomSizedTextureWithDefaultUV(MatrixStack, int,
	 *      int, int, int, int, int, int, int, ResourceLocation)
	 */
	public static void drawTexturedRectWithCustomSizedTexture(MatrixStack matrixStack, int x, int y, int u, int v,
			int width, int height, int textureWidth, int textureHeight) {
		preRenderTexture();
		double mw = 1D / (double) textureWidth;
		double mh = 1D / (double) textureHeight;
		bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		Matrix4f matrix4f = matrixStack.last().pose();
		bufferBuilder.vertex(matrix4f, x, y + height, 0).uv((float) (mw * u), (float) (mh * (v + height))).endVertex();
		bufferBuilder.vertex(matrix4f, x + width, y + height, 0)
				.uv((float) (mw * (u + width)), (float) (mh * (v + height))).endVertex();
		bufferBuilder.vertex(matrix4f, x + width, y, 0).uv((float) (mw * (u + width)), (float) (mh * v)).endVertex();
		bufferBuilder.vertex(matrix4f, x, y, 0).uv((float) (mw * u), (float) (mh * v)).endVertex();
		tessellator.end();
		postRenderTexture();
	}

	/**
	 * 绘制矩形贴图, 可缩放纹理, 可自行指定贴图大小, 可指定{@link ResourceLocation}
	 */
	public static void drawTexturedRectWithCustomSizedTexture(MatrixStack matrixStack, int x, int y, int u, int v,
			int width, int height, int textureWidth, int textureHeight, ResourceLocation textureLocation) {
		bindTexture(textureLocation);
		drawTexturedRectWithCustomSizedTexture(matrixStack, x, y, u, v, width, height, textureWidth, textureHeight);
	}

	/**
	 * 绘制矩形贴图, 可缩放纹理, 可自行指定贴图大小。 <br>
	 * UV为0, 即总是从贴图左上角开始绘制。
	 */
	public static void drawTexturedRectWithCustomSizedTextureWithDefaultUV(MatrixStack matrixStack, int x, int y,
			int width, int height, int textureWidth, int textureHeight) {
		drawTexturedRectWithCustomSizedTexture(matrixStack, x, y, 0, 0, width, height, textureWidth, textureHeight);
	}

	/**
	 * 绘制矩形贴图, 可缩放纹理, 可自行指定贴图大小, 可指定{@link ResourceLocation}。<br>
	 * UV为0, 即总是从贴图左上角开始绘制。
	 */
	public static void drawTexturedRectWithCustomSizedTextureWithDefaultUV(MatrixStack matrixStack, int x, int y,
			int width, int height, int textureWidth, int textureHeight, ResourceLocation textureLocation) {
		bindTexture(textureLocation);
		drawTexturedRectWithCustomSizedTexture(matrixStack, x, y, 0, 0, width, height, textureWidth, textureHeight);
	}

	/**
	 * 将材质的指定部分缩放绘制到指定界面范围内
	 * 
	 * @param x             绘制在界面中的x坐标
	 * @param y             绘制在界面中的y坐标
	 * @param width         绘制在界面中的宽度
	 * @param height        绘制在界面中的高度
	 * @param textureX      材质绘制部分开始位置的x占比, 属于[0, 1]
	 * @param textureY      材质绘制部分开始位置的y占比, 属于[0, 1]
	 * @param textureWidth  材质绘制部分宽度的x占比, 属于[0, 1]
	 * @param textureHeight 材质绘制部分高度的x占比, 属于[0, 1]
	 */
	public static void drawScaledTexturedRect(MatrixStack transform, int x, int y, int width, int height,
			float textureX, float textureY, float textureWidth, float textureHeight) {
		preRenderTexture();
		bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		Matrix4f matrix4f = transform.last().pose();
		bufferBuilder.vertex(matrix4f, x, y + height, 0).uv(textureX, textureY + textureHeight).endVertex();
		bufferBuilder.vertex(matrix4f, x + width, y + height, 0).uv(textureX + textureWidth, textureY + textureHeight)
				.endVertex();
		bufferBuilder.vertex(matrix4f, x + width, y, 0).uv(textureX + textureWidth, textureY).endVertex();
		bufferBuilder.vertex(matrix4f, x, y, 0).uv(textureX, textureY).endVertex();
		tessellator.end();
		postRenderTexture();
	}

	private static final class EmptyScreen extends Screen {
		protected EmptyScreen() {
			super(new StringTextComponent(""));
		}
	}
}