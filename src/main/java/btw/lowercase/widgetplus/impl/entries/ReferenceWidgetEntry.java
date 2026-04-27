package btw.lowercase.widgetplus.impl.entries;

import btw.lowercase.widgetplus.impl.WidgetState;
import btw.lowercase.widgetplus.impl.util.Utils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

public record ReferenceWidgetEntry(Identifier id) implements WidgetEntry {
    @Override
    public WidgetState resolve(final AbstractWidget widget, final @Nullable Screen screen, final @Nullable Player player) {
        return new WidgetState.Reference(this.id);
    }

    public record Unbaked(Identifier id) implements WidgetEntry.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Utils.IDENTIFIER_CODEC.fieldOf("id").forGetter(Unbaked::id)
        ).apply(instance, Unbaked::new));

        @Override
        public MapCodec<? extends Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public WidgetEntry bake() {
            return new ReferenceWidgetEntry(this.id);
        }
    }
}
