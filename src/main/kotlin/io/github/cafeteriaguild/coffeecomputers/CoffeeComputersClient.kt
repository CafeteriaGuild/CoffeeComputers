package io.github.cafeteriaguild.coffeecomputers

import io.github.cafeteriaguild.coffeecomputers.CoffeeComputersNetworking.framebuffer
import io.github.cafeteriaguild.coffeecomputers.client.FramebufferRenderer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry
import net.fabricmc.fabric.api.network.PacketContext
import net.minecraft.network.PacketByteBuf


object CoffeeComputersClient : ClientModInitializer {
    override fun onInitializeClient() {
        ClientSidePacketRegistry.INSTANCE.register(framebuffer) { ctx: PacketContext, data: PacketByteBuf ->
            val size = data.readInt()
            val bytes = data.readByteArray(size)
            val image = FramebufferRenderer.nextFrame(null, bytes)

            // TODO Enviar isso para a BlockEntity
        }
    }
}