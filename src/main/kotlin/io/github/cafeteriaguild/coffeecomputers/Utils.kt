package io.github.cafeteriaguild.coffeecomputers

import io.github.cafeteriaguild.coffeecomputers.CoffeeComputers.mainGroup
import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

fun mcIdentifier(path: String) = Identifier("minecraft", path)

fun identifier(path: String) = Identifier("coffeecomputers", path)

inline fun identifier(path: String, block: Identifier.() -> Unit) = identifier(path).run(block)

fun Identifier.item(item: Item) = apply {
    Registry.register(Registry.ITEM, this, item)
}

fun itemSettings(): Item.Settings = Item.Settings().group(mainGroup)

fun Identifier.blockAndItem(block: Block, settings: Item.Settings = itemSettings()) = apply {
    Registry.register(Registry.BLOCK, this, block)
    Registry.register(Registry.ITEM, this, BlockItem(block, settings))
}

fun Identifier.blockEntity(blockEntity: BlockEntityType<*>) = apply {
    Registry.register(Registry.BLOCK_ENTITY_TYPE, this, blockEntity)
}

