package org.bukkit.craftbukkit.inventory;

import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.inventory.MerchantInventory;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;

public class CraftMerchantInventory extends CraftInventory implements MerchantInventory {

    private final IMerchant merchant;

    public CraftMerchantInventory(IMerchant merchant, MerchantInventory inventory) {
        super(inventory);
        this.merchant = merchant;
    }

    @Override
    public int getSelectedRecipeIndex() {
        return getInventory().selectedIndex;
    }

    @Override
    public MerchantRecipe getSelectedRecipe() {
        net.minecraft.item.MerchantOffer nmsRecipe = getInventory().getRecipe();
        return (nmsRecipe == null) ? null : nmsRecipe.asBukkit();
    }

    @Override
    public MerchantInventory getInventory() {
        return (MerchantInventory) inventory;
    }

    @Override
    public Merchant getMerchant() {
        return merchant.getCraftMerchant();
    }
}
