package org.bukkit.craftbukkit.inventory.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.inventory.IInventory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.BlastFurnaceTileEntity;
import net.minecraft.tileentity.BrewingStandTileEntity;
import net.minecraft.tileentity.DispenserTileEntity;
import net.minecraft.tileentity.DropperTileEntity;
import net.minecraft.tileentity.AbstractFurnaceTileEntity;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.tileentity.LecternTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.SmokerTileEntity;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.inventory.CraftInventoryBrewer;
import org.bukkit.craftbukkit.inventory.CraftInventoryFurnace;
import org.bukkit.craftbukkit.util.CraftChatMessage;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class CraftSimpleNamedContainerProviderConverter implements CraftInventoryCreator.InventoryConverter {

    public abstract IInventory getTileEntity();

    @Override
    public Inventory createInventory(InventoryHolder holder, InventoryType type) {
        return getInventory(getTileEntity());
    }

    @Override
    public Inventory createInventory(InventoryHolder holder, InventoryType type, String title) {
        IInventory te = getTileEntity();
        if (te instanceof TileEntityLootable) {
            ((TileEntityLootable) te).setCustomName(CraftChatMessage.fromStringOrNull(title));
        }

        return getInventory(te);
    }

    public Inventory getInventory(IInventory tileEntity) {
        return new CraftInventory(tileEntity);
    }

    public static class Furnace extends CraftSimpleNamedContainerProviderConverter {

        @Override
        public IInventory getTileEntity() {
            TileEntityFurnace furnace = new TileEntityFurnaceFurnace();
            furnace.setLocation(MinecraftServer.getServer().getServerWorld(DimensionManager.OVERWORLD), BlockPos.ZERO); // TODO: customize this if required
            return furnace;
        }

        @Override
        public Inventory createInventory(InventoryHolder owner, InventoryType type, String title) {
            IInventory tileEntity = getTileEntity();
            ((TileEntityFurnace) tileEntity).setCustomName(CraftChatMessage.fromStringOrNull(title));
            return getInventory(tileEntity);
        }

        @Override
        public Inventory getInventory(IInventory tileEntity) {
            return new CraftInventoryFurnace((TileEntityFurnace) tileEntity);
        }
    }

    public static class BrewingStand extends CraftSimpleNamedContainerProviderConverter {

        @Override
        public IInventory getTileEntity() {
            return new TileEntityBrewingStand();
        }

        @Override
        public Inventory createInventory(InventoryHolder holder, InventoryType type, String title) {
            // BrewingStand does not extend TileEntityLootable
            IInventory tileEntity = getTileEntity();
            if (tileEntity instanceof TileEntityBrewingStand) {
                ((TileEntityBrewingStand) tileEntity).setCustomName(CraftChatMessage.fromStringOrNull(title));
            }
            return getInventory(tileEntity);
        }

        @Override
        public Inventory getInventory(IInventory tileEntity) {
            return new CraftInventoryBrewer(tileEntity);
        }
    }

    public static class Dispenser extends CraftSimpleNamedContainerProviderConverter {

        @Override
        public IInventory getTileEntity() {
            return new DispenserTileEntity();
        }
    }

    public static class Dropper extends CraftSimpleNamedContainerProviderConverter {

        @Override
        public IInventory getTileEntity() {
            return new DropperTileEntity();
        }
    }

    public static class Hopper extends CraftSimpleNamedContainerProviderConverter {

        @Override
        public IInventory getTileEntity() {
            return new TileEntityHopper();
        }
    }

    public static class BlastFurnace extends CraftSimpleNamedContainerProviderConverter {

        @Override
        public IInventory getTileEntity() {
            return new BlastFurnaceTileEntity();
        }
    }

    public static class Lectern extends CraftSimpleNamedContainerProviderConverter {

        @Override
        public IInventory getTileEntity() {
            return new TileEntityLectern().inventory;
        }
    }

    public static class Smoker extends CraftSimpleNamedContainerProviderConverter {

        @Override
        public IInventory getTileEntity() {
            return new SmokerTileEntity();
        }
    }
}
