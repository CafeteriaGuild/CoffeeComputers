package io.github.cafeteriaguild.coffeecomputers.block

import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.HorizontalFacingBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World


class ComputerBlock(settings: Settings) : HorizontalFacingBlock(settings), BlockEntityProvider {
    companion object {
        val STATE = EnumProperty.of("state", State::class.java)
    }

    enum class State : StringIdentifiable {
        OFF, ON, BLINK;

        override fun asString() = name.toLowerCase()
    }

    override fun onUse(
        state: BlockState, world: World, pos: BlockPos,
        player: PlayerEntity, hand: Hand, hit: BlockHitResult
    ): ActionResult? {
        player.openHandledScreen(state.createScreenHandlerFactory(world, pos))
        return ActionResult.SUCCESS
    }

    override fun createScreenHandlerFactory(state: BlockState, world: World, pos: BlockPos): NamedScreenHandlerFactory {
        return world.getBlockEntity(pos) as ComputerBlockEntity
    }

    override fun appendProperties(stateManager: StateManager.Builder<Block?, BlockState?>) {
        stateManager.add(
            Properties.HORIZONTAL_FACING,
            STATE
        )
    }

    init {
        defaultState = stateManager.defaultState
            .with(Properties.HORIZONTAL_FACING, Direction.NORTH)
            .with(
                STATE,
                State.OFF
            )
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState {
        return defaultState.with(FACING, ctx.playerFacing.opposite)
    }

    override fun createBlockEntity(blockView: BlockView?): BlockEntity {
        return ComputerBlockEntity()
    }
}