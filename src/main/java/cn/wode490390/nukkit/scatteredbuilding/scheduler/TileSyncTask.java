package cn.wode490390.nukkit.scatteredbuilding.scheduler;

import cn.nukkit.blockentity.BlockEntity;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.scheduler.PluginTask;
import cn.wode490390.nukkit.scatteredbuilding.ScatteredBuildingPopulator;

public class TileSyncTask extends PluginTask<ScatteredBuildingPopulator> {

    public final String type;
    public final FullChunk chunk;
    public final CompoundTag nbt;

    public TileSyncTask(String type, FullChunk chunk, CompoundTag nbt) {
        super(ScatteredBuildingPopulator.getInstance());
        this.type = type;
        this.chunk = chunk;
        this.nbt = nbt;
    }

    @Override
    public void onRun(int currentTick) {
        BlockEntity.createBlockEntity(this.type, this.chunk, this.nbt);
    }
}
