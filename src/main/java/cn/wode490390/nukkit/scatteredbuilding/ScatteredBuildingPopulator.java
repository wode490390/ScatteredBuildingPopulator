package cn.wode490390.nukkit.scatteredbuilding;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.level.ChunkPopulateEvent;
import cn.nukkit.event.level.LevelLoadEvent;
import cn.nukkit.event.level.LevelUnloadEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.generator.Generator;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Logger;
import cn.wode490390.nukkit.scatteredbuilding.populator.PopulatorDesertPyramid;
import cn.wode490390.nukkit.scatteredbuilding.populator.PopulatorJungleTemple;
import cn.wode490390.nukkit.scatteredbuilding.populator.PopulatorSwampHut;
import cn.wode490390.nukkit.scatteredbuilding.util.MetricsLite;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class ScatteredBuildingPopulator extends PluginBase implements Listener {

    private static Logger LOGGER;
    private static boolean DEBUG = false;

    private final Map<Level, List<Populator>> populators = Maps.newHashMap();

    @Override
    public void onLoad() {
        LOGGER = this.getLogger();
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        try {
            new MetricsLite(this);
        } catch (Throwable ignore) {

        }
    }

    @EventHandler
    public void onLevelLoad(LevelLoadEvent event) {
        List<Populator> populators = Lists.newArrayList();
        Level level = event.getLevel();
        Generator generator = level.getGenerator();
        if (generator.getId() != Generator.TYPE_FLAT && generator.getDimension() == Level.DIMENSION_OVERWORLD) {
            PopulatorSwampHut witchHut = new PopulatorSwampHut();
            populators.add(witchHut);

            PopulatorDesertPyramid pyramid = new PopulatorDesertPyramid();
            populators.add(pyramid);

            PopulatorJungleTemple temple = new PopulatorJungleTemple();
            populators.add(temple);
        }
        this.populators.put(level, populators);
    }

    @EventHandler
    public void onChunkPopulate(ChunkPopulateEvent event) {
        Level level = event.getLevel();
        List<Populator> populators = this.populators.get(level);
        if (populators != null) {
            FullChunk chunk = event.getChunk();
            int chunkX = chunk.getX();
            int chunkZ = chunk.getZ();
            NukkitRandom random = new NukkitRandom(0xdeadbeef ^ (chunkX << 8) ^ chunkZ ^ level.getSeed());
            populators.forEach(populator -> {
                populator.populate(level, chunkX, chunkZ, random, chunk);
            });
        }
    }

    @EventHandler
    public void onLevelUnload(LevelUnloadEvent event) {
        this.populators.remove(event.getLevel());
    }

    public static void debug(Object... objs) {
        if (DEBUG) {
            StringJoiner joiner = new StringJoiner(" ");
            for (Object obj : objs) {
                joiner.add(String.valueOf(obj));
            }
            LOGGER.warning(joiner.toString());
        }
    }
}
