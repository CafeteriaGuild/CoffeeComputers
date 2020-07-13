package io.github.cafeteriaguild.coffeecomputers.block

import io.github.cafeteriaguild.coffeecomputers.CoffeeComputers
import io.github.cafeteriaguild.coffeecomputers.client.ComputerGuiDescription
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.text.LiteralText
import net.minecraft.text.Text


class ComputerBlockEntity : BlockEntity(CoffeeComputers.computerBlockEntity), NamedScreenHandlerFactory {
    override fun getDisplayName(): Text {
        return LiteralText.EMPTY
    }

    override fun createMenu(syncId: Int, inventory: PlayerInventory, player: PlayerEntity): ScreenHandler? {
        return ComputerGuiDescription(syncId, inventory, ScreenHandlerContext.create(world, pos))
    }
}