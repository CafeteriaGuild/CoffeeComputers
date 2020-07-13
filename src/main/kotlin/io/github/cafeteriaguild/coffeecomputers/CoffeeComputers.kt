package io.github.cafeteriaguild.coffeecomputers

import io.github.cafeteriaguild.coffeecomputers.block.ComputerBlock
import io.github.cafeteriaguild.coffeecomputers.block.ComputerBlockEntity
import io.github.cafeteriaguild.coffeecomputers.client.ComputerGuiDescription
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.ScreenHandlerType
import java.util.function.Supplier

object CoffeeComputers : ModInitializer {
    val computer = ComputerBlock(FabricBlockSettings.of(Material.METAL))
    val computerBlockEntity = BlockEntityType.Builder.create(Supplier(::ComputerBlockEntity), computer).build(null)

    val computerScreenHandler: ScreenHandlerType<ComputerGuiDescription> =
        ScreenHandlerRegistry.registerSimple(identifier("computer")) { syncId, inv ->
            ComputerGuiDescription(syncId, inv, ScreenHandlerContext.EMPTY)
        }
    val mainGroup: ItemGroup = FabricItemGroupBuilder.create(identifier("main"))
        .icon { ItemStack(computer) }
        .build()

    override fun onInitialize() {

        identifier("computer").blockAndItem(computer).blockEntity(computerBlockEntity)
    }


}