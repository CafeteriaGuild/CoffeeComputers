package io.github.cafeteriaguild.coffeecomputers.client

import io.github.cafeteriaguild.coffeecomputers.CoffeeComputers
import io.github.cafeteriaguild.coffeecomputers.CoffeeComputersClient
import io.github.cafeteriaguild.coffeecomputers.ScreenSizes
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.widget.WBox
import io.github.cottonmc.cotton.gui.widget.WSprite
import io.github.cottonmc.cotton.gui.widget.data.Axis
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandlerContext


class ComputerGuiDescription(
    syncId: Int,
    inv: PlayerInventory,
    private val ctx: ScreenHandlerContext
) : SyncedGuiDescription(CoffeeComputers.computerScreenHandler, syncId, inv) {
    init {
        val root = WBox(Axis.HORIZONTAL)
        setRootPanel(root)

        root.setSize(ScreenSizes.computerWidth, ScreenSizes.computerHeight)

        val screen = WSprite(CoffeeComputersClient.emptyScreen)
        root.add(screen, CoffeeComputersClient.emptyScreenNI.width, CoffeeComputersClient.emptyScreenNI.height)

        root.validate(this)
    }
}