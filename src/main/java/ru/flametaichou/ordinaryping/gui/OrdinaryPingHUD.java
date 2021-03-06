package ru.flametaichou.ordinaryping.gui;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import ru.flametaichou.ordinaryping.OrdinaryPing;

import java.lang.reflect.Field;
import java.util.List;

public final class OrdinaryPingHUD extends Gui {
    public static final OrdinaryPingHUD INSTANCE = new OrdinaryPingHUD();

    public void addToText(List<String> left) {
        left.add(
                StatCollector.translateToLocal("gui.fps") +
                ": " +
                OrdinaryPing.instance.getFps() +
                ", " +
                StatCollector.translateToLocal("gui.ping") +
                ": " +
                (OrdinaryPing.instance.getPingFact() != -1L ? + OrdinaryPing.instance.getPingFact() + StatCollector.translateToLocal("gui.ms") : StatCollector.translateToLocal("gui.stabilizes")) +
                ", " +
                StatCollector.translateToLocal("gui.lagg") +
                ": " +
                (OrdinaryPing.instance.getEstablishedPing() != -1L ? "~" + (getLagg()) + StatCollector.translateToLocal("gui.ms") : StatCollector.translateToLocal("gui.stabilizes"))
        );
    }

    @SubscribeEvent
    public void renderLvlUpHUD(RenderGameOverlayEvent.Pre event) {
        if (OrdinaryPing.proxy.getPlayer() != null) {
            if (event.type == ElementType.TEXT)
                addToText(((RenderGameOverlayEvent.Text)event).right);
        }
    }

    private Long getLagg() {
        Long lagg = OrdinaryPing.instance.getEstablishedPing() - OrdinaryPing.instance.getPingFact();
        if (lagg < 0) {
            return 0L;
        }
        return lagg;
    }
}
