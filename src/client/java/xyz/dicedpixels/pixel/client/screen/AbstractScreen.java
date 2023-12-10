package xyz.dicedpixels.pixel.client.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public abstract class AbstractScreen extends Screen {
    private static final Identifier BACKGROUND = new Identifier("textures/block/stone.png");

    public AbstractScreen(Text title) {
        super(title);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        context.setShaderColor(0.25F, 0.25F, 0.25F, 1.0F);
        context.drawTexture(BACKGROUND, 0, 0, 0, 0.0F, 0.0F, width, height, 40, 40);
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
