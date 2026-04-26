package btw.lowercase.widgetplus.impl.property;

import btw.lowercase.widgetplus.impl.base.SelectWidget;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface SelectWidgetProperty<T> {
    // TODO: @Nullable T get(...)

    Codec<T> valueCodec();

    SelectWidgetProperty.Type<? extends SelectWidgetProperty<T>, T> type();

    record Type<P extends SelectWidgetProperty<T>, T>(MapCodec<SelectWidget.UnbakedSwitch<P, T>> switchCodec) {
        public static <P extends SelectWidgetProperty<T>, T> SelectWidgetProperty.Type<P, T> create(final MapCodec<P> propertyMapCodec, final Codec<T> valueCodec) {
            final MapCodec<SelectWidget.UnbakedSwitch<P, T>> switchCodec = RecordCodecBuilder.mapCodec((i) -> i.group(propertyMapCodec.forGetter(SelectWidget.UnbakedSwitch::property), createCasesFieldCodec(valueCodec).forGetter(SelectWidget.UnbakedSwitch::cases)).apply(i, SelectWidget.UnbakedSwitch::new));
            return new SelectWidgetProperty.Type<>(switchCodec);
        }

        public static <T> MapCodec<List<SelectWidget.SwitchCase<T>>> createCasesFieldCodec(final Codec<T> valueCodec) {
            return SelectWidget.SwitchCase.codec(valueCodec).listOf().validate(SelectWidgetProperty.Type::validateCases).fieldOf("cases");
        }

        private static <T> DataResult<List<SelectWidget.SwitchCase<T>>> validateCases(final List<SelectWidget.SwitchCase<T>> cases) {
            if (cases.isEmpty()) {
                return DataResult.error(() -> "Empty case list");
            } else {
                final Multiset<T> counts = HashMultiset.create();
                for (final SelectWidget.SwitchCase<T> c : cases) {
                    counts.addAll(c.values());
                }

                return counts.size() != counts.entrySet().size() ? DataResult.error(() -> {
                    final Stream<String> var10000 = counts.entrySet().stream().filter((e) -> e.getCount() > 1).map((e) -> e.getElement().toString());
                    return "Duplicate case conditions: " + var10000.collect(Collectors.joining(", "));
                }) : DataResult.success(cases);
            }
        }
    }
}
