package io.github.cafeteriaguild.coffeecomputers.client

import io.github.cafeteriaguild.coffeecomputers.framebuffer.FrameBuffer.Companion.IMAGE_HEIGHT
import io.github.cafeteriaguild.coffeecomputers.framebuffer.FrameBuffer.Companion.IMAGE_WIDTH
import io.github.cafeteriaguild.coffeecomputers.framebuffer.FrameBuffer.Companion.OP01_SKIP_NTH
import io.github.cafeteriaguild.coffeecomputers.framebuffer.FrameBuffer.Companion.OP02_SKIP_NTH_LINES
import io.github.cafeteriaguild.coffeecomputers.framebuffer.FrameBuffer.Companion.OP03_SET_COLOR
import io.github.cafeteriaguild.coffeecomputers.framebuffer.FrameBuffer.Companion.OP04_DRAW_NTH
import io.github.cafeteriaguild.coffeecomputers.framebuffer.FrameBuffer.Companion.OP05_DRAW_NTH_LINES
import io.github.cafeteriaguild.coffeecomputers.framebuffer.FrameBuffer.Companion.OP06_DRAW_ONCE
import net.minecraft.client.texture.NativeImage

@OptIn(ExperimentalUnsignedTypes::class)
object FramebufferRenderer {
    val colors = arrayOf<UInt>(
        0xFFF0F0F0u,
        0xFFF2B233u,
        0xFFE57FD8u,
        0xFF99B2F2u,
        0xFFDEDE6Cu,
        0xFF7FCC19u,
        0xFFF2B2CCu,
        0xFF4C4C4Cu,
        0xFF999999u,
        0xFF4C99B2u,
        0xFFB266E5u,
        0xFF3366CCu,
        0xFF7F664Cu,
        0xFF57A64Eu,
        0xFFCC4C4Cu,
        0xFF111111u
    )

    fun initialFrame(): NativeImage {
        val image = NativeImage(IMAGE_WIDTH, IMAGE_HEIGHT, false)
        image.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, colors[15].toInt())
        return image
    }

    fun nextFrame(previousFrame: NativeImage?, raw: UByteArray): NativeImage {
        val image = NativeImage(IMAGE_WIDTH, IMAGE_HEIGHT, false)
        previousFrame?.let(image::copyFrom)
        var at = 0 // Where we are at the array
        var p = 0 // Where we are at the image
        var c = 15 // Set color

        // Utility
        fun x() = p % image.width
        fun y() = p / image.width

        while (at < raw.size) {
            val b = raw[at++]
            val v = b and 0b00001111u
            when (b and 0b11110000u) {
                OP01_SKIP_NTH -> p += v.toInt() + 1
                OP02_SKIP_NTH_LINES -> p = (y() + v.toInt() + 1) * IMAGE_WIDTH
                OP03_SET_COLOR -> c = v.toInt()
                OP04_DRAW_NTH -> repeat(v.toInt() + 1) {
                    image.setPixelColor(x(), y(), colors[c].toInt())
                    p++
                }
                OP05_DRAW_NTH_LINES -> repeat(v.toInt() + 1) {
                    val line = y()
                    while (line == y()) {
                        image.setPixelColor(x(), y(), colors[c].toInt())
                        p++
                    }
                }
                OP06_DRAW_ONCE -> {
                    image.setPixelColor(x(), y(), colors[v.toInt()].toInt())
                    p++
                }
            }
        }

        return image
    }

    fun nextFrame(previousFrame: NativeImage?, raw: ByteArray): NativeImage {
        return nextFrame(previousFrame, raw.toUByteArray())
    }
}