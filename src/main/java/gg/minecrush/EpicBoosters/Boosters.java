package gg.minecrush.EpicBoosters;

import gg.minecrush.EpicBoosters.Bossbar.JoinEvents;
import gg.minecrush.EpicBoosters.Listeners.DamageListener;
import gg.minecrush.EpicBoosters.Listeners.ExperienceListener;
import gg.minecrush.EpicBoosters.commands.BoosterAdmin.BoosterAdminCommand;
import gg.minecrush.EpicBoosters.commands.BoosterAdmin.BoosterTabCompletion;
import gg.minecrush.EpicBoosters.commands.GrantBoosters.GrantTabComplete;
import gg.minecrush.EpicBoosters.database.yml.Config;
import gg.minecrush.EpicBoosters.config.MessagesConfig;
import gg.minecrush.EpicBoosters.gui.ClickEvents;
import gg.minecrush.EpicBoosters.Listeners.MobListener;
import gg.minecrush.EpicBoosters.commands.Boosters.BoostersCommand;
import gg.minecrush.EpicBoosters.commands.GrantBoosters.GrantCommand;
import gg.minecrush.EpicBoosters.database.json.playerdata.Json;
import gg.minecrush.EpicBoosters.gui.ListBoosters;
import gg.minecrush.EpicBoosters.database.yml.FolderCreator;
import gg.minecrush.EpicBoosters.database.json.values.ValuesClass;
import io.github.itzispyder.pdk.PDK;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;
import gg.minecrush.EpicBoosters.booster.BoosterStartListener;

public final class Boosters extends JavaPlugin {

    public static final Logger log = Bukkit.getLogger();
    public static final PluginManager pm = Bukkit.getPluginManager();

    private ValuesClass valuesClass;
    private Json json;

    Json Json = json;
    private MessagesConfig messagesConfig;
    private Config config;
    public Boosters() {

    }




    @Override
    public void onEnable() {
        PDK.init(this);
        createConfigurationFile();
        FolderCreator.createFolder("plugins/" + "EpicBoosters/", "database");

        this.valuesClass = new ValuesClass(this);
        valuesClass.saveValues();
        this.messagesConfig = new MessagesConfig(this);
        this.config = new Config(this);
        Json json = new Json();
        ListBoosters listBoosters = new ListBoosters(json, messagesConfig, config);
        this.getCommand("booster").setExecutor(new BoostersCommand(json, messagesConfig, config));
        this.getCommand("grantbooster").setExecutor(new GrantCommand(json, messagesConfig));
        this.getCommand("grantbooster").setTabCompleter(new GrantTabComplete());
        this.getCommand("boosteradmin").setExecutor(new BoosterAdminCommand(this, valuesClass, messagesConfig, config));
        this.getCommand("boosteradmin").setTabCompleter(new BoosterTabCompletion());
        this.getServer().getPluginManager().registerEvents(new MobListener(valuesClass), this);
        this.getServer().getPluginManager().registerEvents(new DamageListener(valuesClass), this);
        this.getServer().getPluginManager().registerEvents(new ExperienceListener(valuesClass), this);
        this.getServer().getPluginManager().registerEvents(new ClickEvents(json, valuesClass, this, messagesConfig, listBoosters), this);
        this.getServer().getPluginManager().registerEvents(new JoinEvents(valuesClass, this), this);
        getServer().getPluginManager().registerEvents(new BoosterStartListener(this, valuesClass, messagesConfig), this);
        getLogger().info(
                ",---,.     ,---,.                                         ___\n" +
                        " ,'  .' |   ,'  .'  \\                                      ,--.'|_\n" +
                        " ,---.'   | ,---.' .' |    ,---.      ,---.                  |  | :,'               __  ,-.\n" +
                        "|   |   .' |   |  |: |   '   ,\\    '   ,\\    .--.--.      :  : ' :             ,' ,'/ /|   .--.--.\n" +
                        ":   :  |-, :   :  :  /  /   /   |  /   /   |  /  /    '   .;__,'  /      ,---.   '  | |' |  /  /    '\n" +
                        ":   |  ;/| :   |    ;  .   ; ,. : .   ; ,. : |  :  /`./   |  |   |      /     \\  |  |   ,' |  :  /`./\n" +
                        "|   :   .' |   :     \\ '   | |: : '   | |: : |  :  ;_     :__,'| :     /    /  | '  :  /   |  :  ;_\n" +
                        "|   |  |-, |   |   . | '   | .; : '   | .; :  \\  \\    `.    '  : |__  .    ' / | |  | '     \\  \\    `.\n" +
                        "'   :  ;/| '   :  '; | |   :    | |   :    |   `----.   \\   |  | '.'| '   ;   /| ;  : |      `----.   \\\n" +
                        "|   |    \\ |   |  | ;   \\   \\  /   \\   \\  /   /  /`--'  /   ;  :    ; '   |  / | |  , ;     /  /`--'  /\n" +
                        "|   :   .' |   :   /     `----'     `----'   '--'.     /    |  ,   /  |   :    |  ---'     '--'.     /\n" +
                        "|   | ,'   |   | ,'                            `--'---'      ---`-'    \\   \\  /              `--'---'\n" +
                        "`----'     `----'                                                       `----'\n"
        );
    }

    private void createConfigurationFile() {
        File configFile = new File(getDataFolder(), "values.yml");
        saveResource("values.yml", true);


        File msgFile = new File(getDataFolder(), "messages.yml");
        if (!msgFile.exists()) {
            saveResource("messages.yml", false);
        }

        File configFiles = new File(getDataFolder(), "config.yml");
        if (!configFiles.exists()) {
            saveResource("config.yml", false);
        }
    }

    public ValuesClass getValuesClass() {
        return valuesClass;
    }


    @Override
    public void onDisable() {

    }
}
