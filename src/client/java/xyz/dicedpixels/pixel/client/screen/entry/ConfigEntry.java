package xyz.dicedpixels.pixel.client.screen.entry;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.DirectionalLayoutWidget;

public class ConfigEntry extends AbstractEntry {
    private final List<ClickableWidget> children = Lists.newArrayList();
    private final DirectionalLayoutWidget layout;

    public ConfigEntry(List<ClickableWidget> widgets) {
        layout = DirectionalLayoutWidget.horizontal().spacing(5);
        widgets.forEach(widget -> {
            children.add(widget);
            layout.add(widget);
        });
        layout.refreshPositions();
    }

    public static ConfigEntry of(ClickableWidget first, ClickableWidget second) {
        first.setDimensions(ButtonWidget.DEFAULT_WIDTH, 20);
        second.setDimensions(ButtonWidget.DEFAULT_WIDTH, 20);
        return new ConfigEntry(List.of(first, second));
    }

    public static ConfigEntry of(ClickableWidget widget) {
        widget.setDimensions((ButtonWidget.DEFAULT_WIDTH * 2) + 5, 20);
        return new ConfigEntry(List.of(widget));
    }

    @Override
    public List<? extends Selectable> selectableChildren() {
        return children;
    }

    @Override
    public List<? extends Element> children() {
        return children;
    }

    @Override
    public void render(
            DrawContext context,
            int index,
            int y,
            int x,
            int entryWidth,
            int entryHeight,
            int mouseX,
            int mouseY,
            boolean hovered,
            float delta) {
        layout.setPosition((entryWidth / 2) - (layout.getWidth() / 2), y);
        layout.forEachChild(child -> child.render(context, mouseX, mouseY, delta));
    }
}
