package meowmel.gtqtspace.common.items.behaviors;

import gregtech.api.items.metaitem.stats.IItemBehaviour;
import meowmel.gtqtspace.common.entity.EntityCustomVehicle;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import meowmel.gtqtspace.common.metatileentities.multiblock.standard.SpaceStation.SpaceDockSystem.DockUtils;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.List;

public class BaseShip implements IItemBehaviour {
    int type;
    public BaseShip(int type) {
        this.type = type;
    }

    public void addInformation(ItemStack stack, List<String> lines) {
        lines.add(I18n.format("舰船类型-"+DockUtils.getTypeByID(type)));
        if (stack.hasTagCompound()) {
            NBTTagCompound compound = stack.getTagCompound();
            //反应堆模块
            int a = compound.getInteger("enginModel");
            //推进器模块
            int b = compound.getInteger("generatorModel");
            //导航计算机模块
            int c = compound.getInteger("countModel");
            //探测器模块
            int d = compound.getInteger("senerModel");

            lines.add(I18n.format("反应堆模块-"+DockUtils.getEnginModelByID(a)));
            lines.add(I18n.format("推进器模块-"+DockUtils.getGeneratorModelByID(b)));
            lines.add(I18n.format("导航计算机模块-"+DockUtils.getCountModelByID(c)));
            lines.add(I18n.format("探测器模块-"+DockUtils.getSenerModelByID(d)));
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);

        if (!world.isRemote) {
            EntityCustomVehicle vehicle = new EntityCustomVehicle(world);
            vehicle.setPosition(player.posX, player.posY, player.posZ);
            world.spawnEntity(vehicle);

            if (!player.capabilities.isCreativeMode) {
                stack.shrink(1);
            }
        }

        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }
}
