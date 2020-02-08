package cn.wode490390.nukkit.scatteredbuilding.loot;

import cn.nukkit.inventory.InventoryType;
import cn.nukkit.item.Item;
import com.google.common.collect.Maps;

public class JungleTempleDispenser extends RandomizableContainer {

    private static final JungleTempleDispenser INSTANCE = new JungleTempleDispenser();

    public static JungleTempleDispenser get() {
        return INSTANCE;
    }

    private JungleTempleDispenser() {
        super(Maps.newHashMap(), 9); //InventoryType.DISPENSER.getDefaultSize()

        PoolBuilder pool1 = new PoolBuilder()
                .register(new ItemEntry(Item.ARROW, 0, 7, 2, 1));
        this.pools.put(pool1.build(), new RollEntry(2, 0));
    }
}
