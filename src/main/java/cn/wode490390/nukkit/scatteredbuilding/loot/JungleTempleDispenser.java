package cn.wode490390.nukkit.scatteredbuilding.loot;

import cn.nukkit.inventory.Inventory;
import cn.nukkit.item.Item;
import cn.nukkit.math.NukkitRandom;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class JungleTempleDispenser extends RandomizableContainer {

    private JungleTempleDispenser() {

    }

    @Override
    public void create(Inventory inventory, NukkitRandom random) {
        for (int i = 0; i < 2; i++) {
            inventory.setItem(random.nextBoundedInt(inventory.getSize()), Item.get(Item.ARROW, 0, random.nextRange(2, 7)), false);
        }
    }

    @Override
    public Map<List<ItemEntry>, RollEntry> getPools() {
        return Collections.emptyMap();
    }

    public static final JungleTempleDispenser INSTANCE = new JungleTempleDispenser();
}
