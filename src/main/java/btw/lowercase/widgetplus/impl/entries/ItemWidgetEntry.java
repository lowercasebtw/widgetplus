package btw.lowercase.widgetplus.impl.entries;

import btw.lowercase.widgetplus.impl.WidgetState;
import btw.lowercase.widgetplus.impl.management.VisualItem;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

public record ItemWidgetEntry(VisualItem item) implements WidgetEntry {
    @Override
    public WidgetState resolve(final AbstractWidget widget, final @Nullable Screen screen, final @Nullable Player player) {
        return new WidgetState.Item(this.item);
    }

    public record Unbaked(VisualItem item) implements WidgetEntry.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                VisualItem.CODEC.fieldOf("item").forGetter(Unbaked::item)
        ).apply(instance, Unbaked::new));

        @Override
        public MapCodec<? extends Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public WidgetEntry bake() {
            return new ItemWidgetEntry(this.item);
        }
    }
}
