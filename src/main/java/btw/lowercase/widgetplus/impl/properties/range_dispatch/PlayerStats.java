package btw.lowercase.widgetplus.impl.properties.range_dispatch;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class PlayerStats {
    public record Health(boolean normalize) implements RangeDispatchWidgetProperty {
        public static final MapCodec<Health> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.BOOL.optionalFieldOf("normalize", true).forGetter(Health::normalize)
        ).apply(instance, Health::new));

        @Override
        public float get(final AbstractWidget widget) {
            final Player player = Minecraft.getInstance().player;
            if (player != null) {
                return this.normalize ? Mth.clamp(player.getHealth() / player.getMaxHealth(), 0.0F, 1.0F) : player.getHealth();
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
        public float get(final AbstractWidget widget) {
            final Player player = Minecraft.getInstance().player;
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
        public float get(final AbstractWidget widget) {
            final Player player = Minecraft.getInstance().player;
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
        public float get(final AbstractWidget widget) {
            final Player player = Minecraft.getInstance().player;
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
        public float get(final AbstractWidget widget) {
            final Player player = Minecraft.getInstance().player;
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
        public float get(final AbstractWidget widget) {
            final Player player = Minecraft.getInstance().player;
            if (player != null) {
                return this.normalize ? Mth.clamp((float) player.getAirSupply() / player.getMaxAirSupply(), 0.0F, 1.0F) : player.getAirSupply();
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
        public float get(final AbstractWidget widget) {
            final Player player = Minecraft.getInstance().player;
            return player != null ? player.getInventory().getSelectedSlot() : 0.0F;
        }

        @Override
        public MapCodec<? extends RangeDispatchWidgetProperty> type() {
            return MAP_CODEC;
        }
    }
}
