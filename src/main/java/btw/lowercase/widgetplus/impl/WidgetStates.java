package btw.lowercase.widgetplus.impl;

import btw.lowercase.widgetplus.impl.states.WidgetState;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;

public class WidgetStates {
    private static final ExtraCodecs.LateBoundIdMapper<Identifier, MapCodec<? extends WidgetState.Unbaked>> ID_MAPPER = new ExtraCodecs.LateBoundIdMapper<>();
    public static final Codec<WidgetState.Unbaked> CODEC = ID_MAPPER.codec(Identifier.CODEC).dispatch(WidgetState.Unbaked::type, c -> c);

    public static void bootstrap() {
    }
}
