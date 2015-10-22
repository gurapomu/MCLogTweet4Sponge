package com.github.gurapomu.mcltSponge.eventListener;

import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

import com.github.gurapomu.mcltSponge.twitter.Authorization;

public class PlayerDisconnectEventListener implements EventListener<ClientConnectionEvent.Disconnect> {
	@Override
	public void handle(ClientConnectionEvent.Disconnect event) throws Exception {
		String str = event.getTargetEntity().getName() + " has logged out.";
		Authorization.tweetString(str);
	}
}
