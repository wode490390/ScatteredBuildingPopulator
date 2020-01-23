package cn.wode490390.nukkit.scatteredbuilding.populator;

import cn.nukkit.level.biome.EnumBiome;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.NukkitRandom;
import cn.wode490390.nukkit.scatteredbuilding.structure.piece.DesertPyramid;
import cn.wode490390.nukkit.scatteredbuilding.structure.piece.ScatteredStructurePiece;

public class PopulatorDesertPyramid extends PopulatorScatteredStructure {

    @Override
    protected boolean canGenerate(int chunkX, int chunkZ, NukkitRandom random, FullChunk chunk) {
        int biome = chunk.getBiomeId(7, 7);
        return (biome == EnumBiome.DESERT.id || biome == EnumBiome.DESERT_HILLS.id || biome == EnumBiome.DESERT_M.id) && super.canGenerate(chunkX, chunkZ, random, chunk);
    }

    @Override
    protected ScatteredStructurePiece getPiece(int chunkX, int chunkZ) {
        return new DesertPyramid(this.getStart(chunkX, chunkZ));
    }
}
