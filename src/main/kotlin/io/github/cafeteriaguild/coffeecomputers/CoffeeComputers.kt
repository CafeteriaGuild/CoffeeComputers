package io.github.cafeteriaguild.coffeecomputers

import io.github.cafeteriaguild.coffeecomputers.block.ComputerBlock
import io.github.cafeteriaguild.coffeecomputers.block.ComputerBlockEntity
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import java.util.function.Supplier

object CoffeeComputers : ModInitializer {
    val computer = ComputerBlock(FabricBlockSettings.of(Material.METAL))
    val computerBlockEntity = BlockEntityType.Builder.create(Supplier(::ComputerBlockEntity), computer).build(null)

    val mainGroup: ItemGroup = FabricItemGroupBuilder.create(identifier("main"))
        .icon { ItemStack(computer) }
        .build()

    override fun onInitialize() {
        identifier("computer").blockAndItem(computer).blockEntity(computerBlockEntity)
    }
}