import discord
from discord import *
import settings


class LumiBot(discord.Client):
    def __init__(self, intents: Intents):
        super().__init__(intents=intents)
        self.__tree = app_commands.CommandTree(self)

        @self.__tree.command(name="lumi-test", description="Lumi test command")
        async def lumi_test(interaction: Interaction):
            await interaction.response.send_message(content="Test")

    async def on_ready(self):
        commands = await self.__tree.sync()
        print(f'Logged on as {self.user}, registered: {len(commands)} commands')


if __name__ == '__main__':
    app_settings = settings.initialize()
    intents = Intents.all()
    client = LumiBot(intents=intents)
    client.run(app_settings.bot_token)