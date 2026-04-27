package btw.lowercase.widgetplus.impl.properties.range_dispatch;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.jspecify.annotations.Nullable;

public class PlayerStats {
    public record Health(boolean normalize) implements RangeDispatchWidgetProperty {
        public static final MapCodec<Health> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.BOOL.optionalFieldOf("normalize", true).forGetter(Health::normalize)
        ).apply(instance, Health::new));

        @Override
        public float get(final AbstractWidget widget, final @Nullable Screen screen, final @Nullable Player player) {
            if (player != null) {
                final float health = player.getHealth();
                final float maxHealth = player.getMaxHealth();
                return this.normalize ? Mth.clamp(health / maxHealth, 0.0F, 1.0F) : Mth.clamp(health, 0.0F, maxHealth);
            } else {
                return 0.0F;
            }
        }

        @Override
        public MapCodec<? extends RangeDispatchWidgetProperty> type() {
            return MAP_CODEC;
        }
    }

    public record Hunger() implements RangeDispatchWidgetProperty {
        public static final MapCodec<Hunger> MAP_CODEC = MapCodec.unit(new Hunger());

        @Override
        public float get(final AbstractWidget widget, final @Nullable Screen screen, final @Nullable Player player) {
            return player != null ? player.getFoodData().getFoodLevel() : 0.0F;
        }

        @Override
        public MapCodec<? extends RangeDispatchWidgetProperty> type() {
            return MAP_CODEC;
        }
    }

    public record Saturation() implements RangeDispatchWidgetProperty {
        public static final MapCodec<Saturation> MAP_CODEC = MapCodec.unit(new Saturation());

        @Override
        public float get(final AbstractWidget widget, final @Nullable Screen screen, final @Nullable Player player) {
            return player != null ? player.getFoodData().getSaturationLevel() : 0.0F;
        }

        @Override
        public MapCodec<? extends RangeDispatchWidgetProperty> type() {
            return MAP_CODEC;
        }
    }

    public record ExperienceLevel() implements RangeDispatchWidgetProperty {
        public static final MapCodec<ExperienceLevel> MAP_CODEC = MapCodec.unit(new ExperienceLevel());

        @Override
        public float get(final AbstractWidget widget, final @Nullable Screen screen, final @Nullable Player player) {
            return player != null ? player.experienceLevel : 0.0F;
        }

        @Override
        public MapCodec<? extends RangeDispatchWidgetProperty> type() {
            return MAP_CODEC;
        }
    }

    public record ExperienceProgress() implements RangeDispatchWidgetProperty {
        public static final MapCodec<ExperienceProgress> MAP_CODEC = MapCodec.unit(new ExperienceProgress());

        @Override
        public float get(final AbstractWidget widget, final @Nullable Screen screen, final @Nullable Player player) {
            return player != null ? player.experienceProgress : 0.0F;
        }

        @Override
        public MapCodec<? extends RangeDispatchWidgetProperty> type() {
            return MAP_CODEC;
        }
    }

    public record Oxygen(boolean normalize) implements RangeDispatchWidgetProperty {
        public static final MapCodec<Oxygen> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.BOOL.optionalFieldOf("normalize", true).forGetter(Oxygen::normalize)
        ).apply(instance, Oxygen::new));

        @Override
        public float get(final AbstractWidget widget, final @Nullable Screen screen, final @Nullable Player player) {
            if (player != null) {
                final int airSupply = player.getAirSupply();
                final int maxAirSupply = player.getMaxAirSupply();
                return this.normalize ? Mth.clamp((float) airSupply / maxAirSupply, 0.0F, 1.0F) : Mth.clamp(airSupply, 0.0F, maxAirSupply);
            } else {
                return 0.0F;
            }
        }

        @Override
        public MapCodec<? extends RangeDispatchWidgetProperty> type() {
            return MAP_CODEC;
        }
    }

    public record HotbarSelected() implements RangeDispatchWidgetProperty {
        public static final MapCodec<HotbarSelected> MAP_CODEC = MapCodec.unit(new HotbarSelected());

        @Override
        public float get(final AbstractWidget widget, final @Nullable Screen screen, final @Nullable Player player) {
            return player != null ? player.getInventory().getSelectedSlot() : 0.0F;
        }

        @Override
        public MapCodec<? extends RangeDispatchWidgetProperty> type() {
            return MAP_CODEC;
        }
    }
}
