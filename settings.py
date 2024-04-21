import os
import sys


class AppSettings:
    def __init__(self, bot_token: str):
        self.bot_token: str = bot_token


def read_bot_token(bot_token_file_path: str) -> str:
    if not os.path.isfile(bot_token_file_path):
        with open(bot_token_file_path, 'w') as bot_token_file:
            bot_token_file.write('')
        return ''

    with open(bot_token_file_path, 'r') as bot_token_file:
        bot_token = bot_token_file.readline()
        return bot_token.replace('\n', '')


def initialize() -> AppSettings:
    current_file = os.path.abspath(sys.argv[0])
    current_dir = os.path.dirname(current_file)
    settings_dir = os.path.join(current_dir, 'config')

    if not os.path.isdir(settings_dir):
        os.mkdir(settings_dir)

    bot_token_file = os.path.join(settings_dir, 'bot-token.txt')
    bot_token = read_bot_token(bot_token_file)

    if len(bot_token) == 0:
        print(f'Please configure bot token in: {bot_token_file}')
        exit(1)

    return AppSettings(bot_token)