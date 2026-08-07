package btw.lowercase.widgetplus.impl.management;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public final class VisualItem {
    public static final Codec<VisualItem> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("id").forGetter(VisualItem::id),
            DataComponentPatch.CODEC.optionalFieldOf("components", DataComponentPatch.EMPTY).forGetter(VisualItem::patch),
            Codec.INT.optionalFieldOf("seed", 0).forGetter(VisualItem::seed),
            Codec.INT.optionalFieldOf("count", 1).forGetter(VisualItem::count)
    ).apply(instance, VisualItem::new));

    private final Identifier id;
    private final DataComponentPatch patch;
    private final int seed;
    private final int count;

    private ItemStack stack = null;

    public VisualItem(final Identifier id, final DataComponentPatch patch, final int seed, final int count) {
        this.id = id;
        this.patch = patch;
        this.seed = seed;
        this.count = count;
    }

    public void submit(final WidgetRenderContext renderContext) {
        if (this.stack == null) {
            this.stack = new ItemStack(
                    Holder.direct(
                            BuiltInRegistries.ITEM.getValue(this.id),
                            DataComponentMap.builder()
                                    .set(DataComponents.MAX_STACK_SIZE, 64)
                                    .set(DataComponents.ITEM_MODEL, this.id)
                                    .build()
                    ),
                    this.count,
                    this.patch
            );
        }

        renderContext.guiGraphics().item(this.stack, renderContext.x0(), renderContext.y0(), this.seed);
    }

    public Identifier id() {
        return this.id;
    }

    public DataComponentPatch patch() {
        return this.patch;
    }

    public int seed() {
        return this.seed;
    }

    public int count() {
        return this.count;
    }
}
