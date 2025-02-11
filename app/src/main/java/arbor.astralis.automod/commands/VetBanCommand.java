package arbor.astralis.automod.commands;

import arbor.astralis.automod.GuildSettings;
import arbor.astralis.automod.Settings;
import discord4j.common.util.Snowflake;
import discord4j.core.event.domain.interaction.ApplicationCommandInteractionEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.PartialMember;
import discord4j.core.spec.BanQuerySpec;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ImmutableApplicationCommandRequest;
import discord4j.rest.util.Permission;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public final class VetBanCommand implements ApplicationCommand {

    private static final String PARAMETER_USER = "member";
    
    @Override
    public String getName() {
        return ApplicationCommand.NAME_PREFIX + "ban";
    }

    @Override
    public String getShortDescription() {
        return "Bans a member who has joined the server in 14 days or less";
    }

    @Override
    public Optional<String> getParametersHelpText() {
        return Optional.of("@username");
    }

    @Override
    public void create(ImmutableApplicationCommandRequest.Builder request) {
        var memberOption = ApplicationCommandOptionData.builder()
            .type(6)
            .name(PARAMETER_USER)
            .description("The new member to be banned (joined less than 14 days ago)")
            .required(true)
            .build();

        request.addOption(memberOption);
    }

    @Override
    public Mono<?> onInteraction(ApplicationCommandInteractionEvent event) {
        Snowflake guildId = event.getInteraction().getGuildId().get();
        GuildSettings settings = Settings.forGuild(guildId.asLong());
        
        if (settings.getModRoleId().isEmpty()) {
            return event.reply("No modRoleId defined for guild").withEphemeral(true);
        }
        
        Member member = event.getInteraction().getUser().asMember(guildId).block();
        boolean isSentinel = Boolean.TRUE.equals(member.getRoles().any(role -> role.getId().asLong() == settings.getModRoleId().get()).block());
        if (!isSentinel) {
            boolean isAdmin = Objects.requireNonNull(member.getBasePermissions().block()).contains(Permission.ADMINISTRATOR);
            if (!isAdmin) {
                return event.reply("This command can only be used by Sentinels").withEphemeral(true);
            }
        }
        
        Map<String, String> optionsAndValues = CommandHelper.marshalOptionValues(event);
        
        long targetMemberId = Long.parseLong(optionsAndValues.get(PARAMETER_USER));
        Optional<Instant> joinTime = event.getClient().getMemberById(guildId, Snowflake.of(targetMemberId)).map(PartialMember::getJoinTime).block();
        
        if (!joinTime.isPresent()) {
            return event.reply("Sorry, something went wrong: member has no join time").withEphemeral(true);
        }
        
        long now = Instant.now().toEpochMilli();
        long then = joinTime.get().toEpochMilli();
        
        if (now - then <= TimeUnit.DAYS.toMillis(14)) {
            Guild guild = event.getClient().getGuildById(guildId).block();
            guild.ban(
                Snowflake.of(targetMemberId), 
                BanQuerySpec.builder()
                    .reason("Banned by <@" + event.getInteraction().getUser().getId().asLong() + "> (via L.U.M.I.)")
                    .deleteMessageSeconds(604800)
                    .build()
            ).block();

            return event.reply("Member is now banned. Thank you for doing your part to keeping the Arbor orderly~").withEphemeral(true);
        } else {
            return event.reply("This member has been in the server for longer than 14 days. Please report this user to staff members instead.").withEphemeral(true);
        }
    }
}
