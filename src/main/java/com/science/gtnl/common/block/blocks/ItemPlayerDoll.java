package com.science.gtnl.common.block.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ItemPlayerDoll extends ItemBlock {

    public ItemPlayerDoll(Block block) {
        super(block);
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ, int metadata) {
        boolean placed = super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata);
        if (placed) {
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof PlayerDollTileEntity) {
                PlayerDollTileEntity playerDollTileEntity = (PlayerDollTileEntity) tileEntity;
                if (stack.hasTagCompound() && stack.getTagCompound()
                    .hasKey("PlayerName")) {
                    playerDollTileEntity.setSkullOwner(
                        stack.getTagCompound()
                            .getString("PlayerName"));
                } else {
                    playerDollTileEntity.setSkullOwner(player.getCommandSenderName());
                }
            }
        }
        return placed;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
        float hitX, float hitY, float hitZ) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        stack.getTagCompound()
            .setString("PlayerName", player.getCommandSenderName());
        return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }
}
