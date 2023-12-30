package xyz.dicedpixels.pixel.client.screen;

import java.util.function.Consumer;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.TextWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public abstract class AbstractScreen extends Screen {
    private Identifier background = new Identifier("textures/block/stone.png");
    private Consumer<ButtonWidget> resetAction = button -> {};
    private Consumer<ButtonWidget> doneAction = button -> {};
    private boolean renderTitle = true;

    public AbstractScreen(Text title) {
        super(title);
    }

    public void setResetAction(Consumer<ButtonWidget> resetAction) {
        this.resetAction = resetAction;
    }

    public void setDoneAction(Consumer<ButtonWidget> doneAction) {
        this.doneAction = doneAction;
    }

    public void setRenderTitle(boolean renderTitle) {
        this.renderTitle = renderTitle;
    }

    public void setBackground(Identifier background) {
        this.background = background;
    }

    public void renderCommonWidgets() {
        var grid = new GridWidget().setSpacing(5);
        var adder = grid.createAdder(1);
        var buttons = DirectionalLayoutWidget.horizontal().spacing(5);
        if (renderTitle) {
            adder.add(new TextWidget(title, textRenderer));
        }
        buttons.add(ButtonWidget.builder(Text.empty(), button -> resetAction.accept(button))
                .width(200 / 4)
                .build());
        buttons.add(ButtonWidget.builder(Text.empty(), button -> doneAction.accept(button))
                .width((200 / 4 * 3) - 5)
                .build());
        adder.add(buttons);
        grid.refreshPositions();
        grid.setPosition(10, height - grid.getHeight() - 10);
        grid.forEachChild(this::addDrawableChild);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        context.setShaderColor(0.25F, 0.25F, 0.25F, 1.0F);
        context.drawTexture(background, 0, 0, 0, 0.0F, 0.0F, width, height, 40, 40);
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
