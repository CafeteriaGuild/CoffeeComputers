package io.github.cafeteriaguild.coffeecomputers.client

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text

class ComputerBlockScreen(
    gui: ComputerGuiDescription,
    inventory: PlayerInventory,
    title: Text
) : CottonInventoryScreen<ComputerGuiDescription>(gui, inventory.player, title)