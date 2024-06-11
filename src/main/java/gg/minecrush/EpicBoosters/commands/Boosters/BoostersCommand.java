package gg.minecrush.EpicBoosters.commands.Boosters;

import gg.minecrush.EpicBoosters.database.yml.Config;
import gg.minecrush.EpicBoosters.config.MessagesConfig;
import gg.minecrush.EpicBoosters.database.json.playerdata.Json;
import gg.minecrush.EpicBoosters.gui.ListBoosters;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BoostersCommand implements CommandExecutor {

    private final Json json;
    private final ListBoosters listBoosters;
    private final MessagesConfig messagesConfig;
    private final Config config;

    public BoostersCommand(Json json, MessagesConfig messagesConfig, Config config) {
        this.json = json;
        this.messagesConfig = messagesConfig;
        this.config = config;
        this.listBoosters = new ListBoosters(json, messagesConfig, config);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            listBoosters.createBoosterGui(player, 0);
            return true;
        }
        return false;
    }
}
