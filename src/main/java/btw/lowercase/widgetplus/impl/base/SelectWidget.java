package btw.lowercase.widgetplus.impl.base;

import btw.lowercase.widgetplus.impl.WidgetState;
import btw.lowercase.widgetplus.impl.property.SelectWidgetProperty;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.renderer.item.ItemModels;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.util.ExtraCodecs;

import java.util.List;

public class SelectWidget {
    public record UnbakedSwitch<P extends SelectWidgetProperty<T>, T>(P property,
                                                                      List<SelectWidget.SwitchCase<T>> cases) {
    }

    public record SwitchCase<T>(List<T> values, WidgetState state) {
        public static <T> Codec<SelectItemModel.SwitchCase<T>> codec(final Codec<T> valueCodec) {
            return RecordCodecBuilder.create(instance -> instance.group(
                    ExtraCodecs.nonEmptyList(ExtraCodecs.compactListCodec(valueCodec)).fieldOf("when").forGetter(SelectItemModel.SwitchCase::values),
                    ItemModels.CODEC.fieldOf("widget").forGetter(SelectItemModel.SwitchCase::model)
            ).apply(instance, SelectItemModel.SwitchCase::new));
        }
    }
}
