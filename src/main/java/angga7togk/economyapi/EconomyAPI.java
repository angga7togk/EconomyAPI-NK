package angga7togk.economyapi;

import angga7togk.economyapi.commands.*;
import angga7togk.placeholderapi.PlaceholderAPI;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EconomyAPI extends PluginBase implements Listener {

    private static EconomyAPI instance;
    private Config moneyConfig, config;
    public static String prefix;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        saveResource("money.yml");
        moneyConfig = new Config(this.getDataFolder() + "/money.yml", Config.YAML);
        config = new Config(this.getDataFolder() + "/config.yml", Config.YAML);
        prefix = config.getString("prefix");
        this.getServer().getPluginManager().registerEvents(this, this);

        this.getServer().getCommandMap().registerAll("economyapi", List.of(
                new GiveCommand(this),
                new MyCommand(this),
                new PayCommand(this),
                new ReduceCommand(this),
                new SeeCommand(this),
                new SetCommand(this),
                new TopCommand(this)
        ));

        PlaceholderAPI papi = PlaceholderAPI.getInstance();
        papi.builder("money")
                .visitorLoader(player -> String.valueOf(myMoney(player)))
                .build();
        papi.builder("money_format")
                .visitorLoader(player -> formatMoney(myMoney(player)))
                .build();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!playerExists(player)) {
            moneyConfig.set(getName(player), getStarterMoney());
            moneyConfig.save();
        }
    }


    public String getName(String playerName) {
        return playerName.toLowerCase();
    }

    public String getName(Player player) {
        return player.getName().toLowerCase();
    }

    public boolean playerExists(String playerName) {
        return moneyConfig.exists(getName(playerName));
    }

    public boolean playerExists(Player player) {
        return moneyConfig.exists(getName(player));
    }

    public Map<String, Integer> getAll() {
        Map<String, Integer> map = new HashMap<>();
        for (String name : moneyConfig.getKeys(false)) {
            map.put(name, moneyConfig.getInt(name));
        }
        return map;
    }

    public int getMaxMoney() {
        return config.getInt("max-money");
    }

    public int getStarterMoney() {
        return config.getInt("starter-money");
    }

    public int myMoney(String playerName) {
        return moneyConfig.getInt(getName(playerName));
    }

    public int myMoney(Player player) {
        return moneyConfig.getInt(getName(player));
    }

    public String formatMoney(int money) {
        return formatMoney(money, "id", "id");
    }

    public String formatMoney(int money, String language) {
        return formatMoney(money, language, "id");
    }

    public String formatMoney(int money, String language, String formatCountry) {
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(new Locale(language, formatCountry));
        return removeRupiah(formatRupiah.format(money));
    }

    private String removeRupiah(String input) {
        if (input.length() > 3) {
            return input.substring(0, input.length() - 3);
        } else {
            return input;
        }
    }

    public boolean setMoney(String playerName, int money) {
        playerName = getName(playerName);
        int maxMoney = getMaxMoney();

        if (money > maxMoney) {
            return false;
        }
        if (money < 0) {
            return false;
        }

        moneyConfig.set(playerName, money);
        moneyConfig.save();
        return true;
    }

    public boolean setMoney(Player player, int money) {
        String playerName = getName(player);
        int maxMoney = getMaxMoney();

        if (money > maxMoney) {
            return false;
        }
        if (money < 0) {
            return false;
        }
        moneyConfig.set(playerName, money);
        moneyConfig.save();


        return true;
    }

    public boolean addMoney(String playerName, int money) {
        playerName = getName(playerName);
        int myMoney = myMoney(playerName);
        int maxMoney = getMaxMoney();

        if (money > maxMoney) {
            return false;
        }
        if ((myMoney + money) > maxMoney) {
            return false;
        }
        if (money < 0) {
            return false;
        }

        moneyConfig.set(playerName, myMoney + money);
        moneyConfig.save();


        return true;
    }

    public boolean addMoney(Player player, int money) {
        String playerName = getName(player);
        int myMoney = myMoney(playerName);
        int maxMoney = getMaxMoney();

        if (money > maxMoney) {
            return false;
        }
        if ((myMoney + money) > maxMoney) {
            return false;
        }
        if (money < 0) {
            return false;
        }

        moneyConfig.set(playerName, myMoney + money);
        moneyConfig.save();


        return true;
    }

    public boolean reduceMoney(String playerName, int money) {
        playerName = getName(playerName);
        int myMoney = myMoney(playerName);

        if (money < 0) {
            return false;
        }
        if (myMoney < money) {
            return false;
        }
        moneyConfig.set(playerName, myMoney - money);
        moneyConfig.save();


        return true;
    }

    public boolean reduceMoney(Player player, int money) {
        String playerName = getName(player);
        int myMoney = myMoney(playerName);

        if (money < 0) {
            return false;
        }

        if (myMoney < money) {
            return false;
        }

        moneyConfig.set(playerName, myMoney - money);
        moneyConfig.save();
        return true;
    }

    public static EconomyAPI getInstance() {
        return instance;
    }
}