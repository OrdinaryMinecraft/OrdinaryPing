package ru.flametaichou.ordinaryping;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import ru.flametaichou.ordinaryping.Handlers.*;


@Mod(modid = OrdinaryPing.ID, name = "Ordinary Ping", version = "1.1", acceptableRemoteVersions = "*")
public final class OrdinaryPing {

    public final static String ID = "ordinaryping";

    @Instance(value = ID)
    public static OrdinaryPing instance;

    @SidedProxy(clientSide = "ru.flametaichou.ordinaryping.OrdinaryPingClientProxy", serverSide = "ru.flametaichou.ordinaryping.OrdinaryPingProxy")
    public static OrdinaryPingProxy proxy;

    public static FMLEventChannel pingChannel;
    public static PingPacketHandler sk = new PingPacketHandler();

    public final static long pingInterval = 2000;

    private long latestPingTime = 0;
    private Long ping = -1L;
    private Integer fps = 0;
    private Long lagg = -1L;

    @EventHandler
    public void load(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
        pingChannel = NetworkRegistry.INSTANCE.newEventDrivenChannel(PacketChannel.PING.name());
        pingChannel.register(sk);
        proxy.registerGui();
    }

    @EventHandler
    public void load(FMLPreInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(FMLEventHandler.INSTANCE);
    }

    public void clearPings() {
        ping = -1L;
        lagg = -1L;
        latestPingTime = 0;
    }

    public Long getPing() {
        return ping;
    }

    public void setPing(Long ping) {
        this.ping = ping;
    }

    public void setFps() {
        try {
            this.fps = Integer.parseInt(Minecraft.getMinecraft().debug.split(" ")[0]);
        } catch (Exception e) {
            this.fps = 0;
        }
    }

    public Integer getFps() {
        return fps;
    }

    public Long getLagg() {
        return lagg;
    }

    public void setLagg(Long lagg) {
        this.lagg = lagg;
    }

    public long getLatestPingTime() {
        return latestPingTime;
    }

    public void setLatestPingTime(long latestPingTime) {
        this.latestPingTime = latestPingTime;
    }
}
