package xyz.dicedpixels.pixel.client.screen.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public abstract class AbstractListWidget<E extends ElementListWidget.Entry<E>> extends ElementListWidget<E> {
    private Identifier background = new Identifier("textures/block/stone.png");

    public AbstractListWidget(MinecraftClient client, int width, int height, int y, int itemHeight) {
        super(client, width, height, y, itemHeight);
        setRenderBackground(false);
    }

    public void setBackground(Identifier background) {
        this.background = background;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        context.setShaderColor(0.125F, 0.125F, 0.125F, 1.0F);
        var x = getX();
        var y = getY();
        var u = (float) getRight();
        var v = (float) (getBottom() + getScrollAmount());
        var right = getRight();
        var bottom = getBottom();
        context.drawTexture(background, x, y, u, v, width, height, 40, 40);
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        context.fillGradient(RenderLayer.getGuiOverlay(), x, y, right, y + 5, 0xFF000000, 0, 0);
        context.fillGradient(RenderLayer.getGuiOverlay(), x, bottom - 5, right, bottom, 0, 0xFF000000, 0);
        super.renderWidget(context, mouseX, mouseY, delta);
    }
}
