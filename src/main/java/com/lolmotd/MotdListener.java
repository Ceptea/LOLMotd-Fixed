package com.lolmotd;

import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class MotdListener implements Listener {

    @EventHandler
    public void onProxyPing(ProxyPingEvent event) {
        int protocolVersion = event.getConnection().getVersion();
        String motd = protocolVersion < 735 ? LOLMotd.legacyMotd : LOLMotd.modernMotd;
        event.getResponse().setDescription(motd);
    }
}
