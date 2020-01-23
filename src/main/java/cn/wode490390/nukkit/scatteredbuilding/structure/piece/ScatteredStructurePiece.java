package cn.wode490390.nukkit.scatteredbuilding.structure.piece;

import cn.nukkit.block.Block;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.math.BlockVector3;
import cn.nukkit.math.NukkitRandom;
import cn.wode490390.nukkit.scatteredbuilding.structure.StructureBoundingBox;

public abstract class ScatteredStructurePiece implements StructurePiece {

    protected StructureBoundingBox boundingBox;
    private int horizPos = -1;

    /**
     * Creates a scattered structure piece.
     *
     * @param pos the root location
     * @param size the size as a width-height-depth vector
     */
    public ScatteredStructurePiece(BlockVector3 pos, BlockVector3 size) {
        this.boundingBox = new StructureBoundingBox(new BlockVector3(pos.x, pos.y, pos.z), new BlockVector3(pos.x + size.x - 1, pos.y + size.y - 1, pos.z + size.z - 1));
    }

    @Override
    public StructureBoundingBox getBoundingBox() {
        return this.boundingBox;
    }

    protected void adjustHorizPos(ChunkManager level) {
        if (this.horizPos >= 0) {
            return;
        }

        int sumY = 0;
        int blockCount = 0;
        for (int x = this.boundingBox.getMin().x; x <= this.boundingBox.getMax().x; x++) {
            for (int z = this.boundingBox.getMin().z; z <= this.boundingBox.getMax().z; z++) {
                int y = level.getChunk(x >> 4, z >> 4).getHighestBlockAt(x & 0xf, z & 0xf);
                int id = level.getBlockIdAt(x, y - 1, z);
                while ((id == Block.LEAVES || id == Block.LEAVES2 || id == Block.LOG || id == Block.LOG2 || id == Block.VINE) && y > 1) {
                    y--;
                    id = level.getBlockIdAt(x, y - 1, z);
                }
                sumY += Math.max(64, y + 1);
                blockCount++;
            }
        }

        this.horizPos = sumY / blockCount;
        this.boundingBox.offset(new BlockVector3(0, this.horizPos - this.boundingBox.getMin().y, 0));
    }

    public abstract void generate(ChunkManager world, NukkitRandom random);
}
