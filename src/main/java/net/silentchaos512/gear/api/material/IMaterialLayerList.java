package net.silentchaos512.gear.api.material;

import net.silentchaos512.gear.gear.part.PartTextureSet;

import javax.annotation.Nullable;
import java.util.List;

/**
 * An object with display properties used by {@link IMaterial}
 */
public interface IMaterialLayerList extends Iterable<MaterialLayer> {
    List<MaterialLayer> getLayers();

    @Nullable
    default MaterialLayer getFirstLayer() {
        List<MaterialLayer> layers = getLayers();
        return layers.isEmpty() ? null : layers.get(0);
    }

    /**
     * Gets the texture type
     *
     * @return The texture type
     */
    @Deprecated
    PartTextureSet getTexture();

    /**
     * Gets the color of the first layer
     *
     * @return The item color
     */
    @Deprecated
    int getPrimaryColor();
}
