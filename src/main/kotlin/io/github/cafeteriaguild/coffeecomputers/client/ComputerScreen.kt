package io.github.cafeteriaguild.coffeecomputers.client

import io.github.cafeteriaguild.coffeecomputers.CoffeeComputersClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.LiteralText
import net.minecraft.util.Identifier
import kotlin.math.min

class ComputerScreen: Screen(LiteralText("")) {

    val cornerTexture = Identifier("coffeecomputers:textures/gui/corners.png")

    //must be between 0-6
    var borderSize = 6

    var innerWidth = 0
    var innerHeight = 0

    var backgroundHeight = (borderSize*2)+8+innerHeight
    var backgroundWidth = (borderSize*2)+8+innerWidth

    var x = 0
    var y = 0

    override fun isPauseScreen() = false

    override fun init() {
        super.init()
        innerWidth = CoffeeComputersClient.emptyScreenNI.width
        innerHeight = CoffeeComputersClient.emptyScreenNI.height
        backgroundHeight = (borderSize*2)+8+innerHeight
        backgroundWidth = (borderSize*2)+8+innerWidth
        x = (width - backgroundWidth) / 2
        y = (height - backgroundHeight) / 2
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
        drawBackground(matrices)
        drawScreen(matrices)
    }

    private fun drawShaft(matrices: MatrixStack, x: Int, y: Int, u: Int, v:Int, width: Int, height: Int, limitWidth: Int, limitHeight: Int) {
        if(width > limitWidth || height > limitHeight) {
            if(width > limitWidth && height <= limitHeight) {
                var missingWidth = width
                while(missingWidth > 0) {
                    val widthToDraw = min(missingWidth, limitWidth)
                    drawTexture(matrices, x+(width-missingWidth), y, u, v, widthToDraw, height)
                    missingWidth -= widthToDraw
                }
            }else if(width <= limitWidth && height > limitHeight) {
                var missingHeight = height
                while(missingHeight > 0) {
                    val heightToDraw = min(missingHeight, limitHeight)
                    drawTexture(matrices, x, y+(height-heightToDraw), u, v, width, heightToDraw)
                    missingHeight -= heightToDraw
                }
            }
        }else{
            drawTexture(matrices, x, y, u, v, width, height)
        }
    }

    private fun drawBackground(matrices: MatrixStack) {
        client!!.textureManager.bindTexture(cornerTexture)

        drawTexture(matrices, x, y, 0, 0, 4+borderSize, 4+borderSize) //Draw the Top-Left corner
        drawTexture(matrices, x, y+6+borderSize+innerHeight, 0, 252-borderSize, 4+borderSize, 4+borderSize) //Draw the Bottom-Left corner
        drawTexture(matrices, x+6+borderSize+innerWidth, y, 252-borderSize, 0, 4+borderSize, 4+borderSize) //Draw the Top-Right corner
        drawTexture(matrices, x+6+borderSize+innerWidth,y+6+borderSize+innerHeight, 252-borderSize, 252-borderSize, 4+borderSize, 4+borderSize) //Draw the Bottom-Right corner

        drawShaft(matrices, x+4+borderSize, y, 11, 0, innerWidth+2, 4+borderSize, 234, 4+borderSize) //Draw the Outer-Top shaft
        drawShaft(matrices, x, y+4+borderSize, 0, 11, 4+borderSize, innerHeight+2, 4+borderSize, 234) //Draw the Outer-Left shaft
        drawShaft(matrices, x+4+borderSize, y+6+borderSize+innerHeight, 11, 252-borderSize, innerWidth+2, 4+borderSize, 234, 4+borderSize) //Draw the Outer-Bottom shaft
        drawShaft(matrices, x+6+borderSize+innerWidth, y+4+borderSize, 252-borderSize, 11, 4+borderSize, innerHeight+2, 4+borderSize, 234) //Draw the Outer-Right shaft

        drawShaft(matrices,x+4+borderSize, y+4+borderSize, 10, 10, innerWidth+2, 1, 236, 1) //Draw the Inner-Top shaft
        drawShaft(matrices, x+4+borderSize, y+5+borderSize, 10, 11, 1, innerHeight, 1, 234) //Draw the Inner-Left shaft
        drawShaft(matrices, x+4+borderSize, y+5+borderSize+innerHeight, 10, 245, innerWidth+2, 1, 236, 1) //Draw the Inner-Bottom shaft
        drawShaft(matrices, x+5+borderSize+innerWidth, y+5+borderSize, 245, 11, 1, innerHeight, 1, 234) //Draw the Inner-Right shaft

    }

    private fun drawScreen(matrices: MatrixStack) {
        //DrawableHelper.fill(matrices, x+4+borderSize+1, y+4+borderSize+1, x+4+borderSize+1+innerWidth, y+4+borderSize+1+innerHeight, 0xFFFFFF00.toInt())
        client!!.textureManager.bindTexture(CoffeeComputersClient.emptyScreen)
        drawTexture(matrices, x+4+borderSize+1, y+4+borderSize+1, 0, 0, innerWidth, innerHeight)
    }
}