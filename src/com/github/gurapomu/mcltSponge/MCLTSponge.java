package com.github.gurapomu.mcltSponge;

import org.spongepowered.api.Game;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.args.GenericArguments;
import org.spongepowered.api.util.command.spec.CommandSpec;

import com.github.gurapomu.mcltSponge.Commands.EnterPIN;
import com.github.gurapomu.mcltSponge.Commands.Post;
import com.github.gurapomu.mcltSponge.Commands.TwitterOAuth;
import com.github.gurapomu.mcltSponge.eventListener.PlayerJoinEventListener;
import com.google.inject.Inject;

import org.slf4j.Logger;

@Plugin(id = "mclt4s", name = "MCLT4S", version = "0.1")
public class MCLTSponge {
 	public static Logger logger;
	private Game game;
	EventListener<ClientConnectionEvent.Join> playerJoinEventListener = new PlayerJoinEventListener();
	
	@Inject
	private void setLogger(Logger logger) {
	    MCLTSponge.logger = logger;
	}
	@Inject
	private void setGamge(Game game){
		this.game = game;
	}
	
//	CommandSpec test = CommandSpec.builder()
//							.description(Texts.of("A Test Command"))
//							.permission("mclt.command.test")
//							.executor(new MCLTCommands())
//							.build();
	CommandSpec twitterOAuth = CommandSpec.builder()
										  .description(Texts.of("Authorize twitter account"))
										  .permission("mclt.commands.twitter.authorize")
										  .executor(new TwitterOAuth())
										  .build();
	CommandSpec enterPIN = CommandSpec.builder()
									  .description(Texts.of("Enter PIN code and finish authorize"))
									  .permission("mclt.commands.twitter.authorize")
									  .arguments(GenericArguments.remainingJoinedStrings(Texts.of("pin")))
									  .executor(new EnterPIN())
									  .build();
	CommandSpec post = CommandSpec.builder()
								  .description(Texts.of("Post some string to Twitter"))
								  .permission("mclt.commands.twitter.post")
								  .arguments(GenericArguments.remainingJoinedStrings(Texts.of("str")))
								  .executor(new Post())
								  .build();

	@Listener
	public void onServerInitialization(GameInitializationEvent event){
		logger.info("Enabling MCLogTweet for Sponge v0.1");
	}
	@Listener
	public void onServerStart(GameStartedServerEvent event){
//		game.getCommandDispatcher().register(this, test, "test");
		game.getCommandDispatcher().register(this, twitterOAuth, "oauth");
		game.getCommandDispatcher().register(this, enterPIN, "enterpin");
		game.getCommandDispatcher().register(this, post, "post");
		game.getEventManager().registerListener(this, ClientConnectionEvent.Join.class, playerJoinEventListener);
//		if(Authorization.loadAccessToken() != null){
//			Authorization.tweetString("The server started. (Auth by @" + Authorization.loadAccessToken().getScreenName() + ")");
//		} else{
//			logger.info("It is not yet authenticated for Twitter.");
//			logger.info("Please enter command (/oauth /enterpin [pin])");
//		}
	}
	
	@Listener
	public void onServerStop(GameStoppedServerEvent event){
//		if(Authorization.loadAccessToken() != null){
//			Authorization.tweetString("The server stopped.");
//		}
	}
}