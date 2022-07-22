package com.jazzkuh.gunshell.common.configuration.lang;

import com.jazzkuh.gunshell.GunshellPlugin;
import com.jazzkuh.gunshell.common.configuration.PlaceHolder;
import com.jazzkuh.gunshell.utils.ChatUtils;
import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.command.CommandSender;

public enum MessagesConfig {
    ERROR_AMMUNITION_NOT_FOUND("error.ammunition-not-found", "&cAmmunition not found for key: <Key>"),
    ERROR_OUT_OF_AMMO("error.out-of-ammo", "&cJe geweer is leeg!"),
    ERROR_SHOT_LAST_BULLET("error.shot-last-bullet", "&cJe hebt je laatste kogel uit je magazijn geschoten."),
    RELOADING_START("common.reloading.start", "&aJe wapen is nu aan het herladen..."),
    RELOADING_FINISHED("common.reloading.finished", "&aJe wapen is herladen."),
    SHOW_AMMO_DURABILITY("common.ammo-durability", "&aDurability: &f<Durability>\n&aAmmo: &7<Ammo>&8/&7<MaxAmmo>");


    private final @Getter String path;
    private final @Getter(AccessLevel.PRIVATE) String message;

    MessagesConfig(String path, String message) {
        this.path = path;
        this.message = message;
    }

    public void get(CommandSender commandSender) {
        String msg = GunshellPlugin.getMessages().getConfig().getString(this.getPath());
        ChatUtils.sendMessage(commandSender, msg);
    }

    public void get(CommandSender commandSender, PlaceHolder... placeholder) {
        String msg = GunshellPlugin.getMessages().getConfig().getString(this.getPath());
        for (PlaceHolder placeHolder : placeholder) {
            if (msg == null) continue;
            msg = msg.replaceAll(placeHolder.getPlaceholder(), placeHolder.getValue());
        }

        ChatUtils.sendMessage(commandSender, msg);
    }

    public static void init() {
        for (MessagesConfig msg : MessagesConfig.values()) {
            if (GunshellPlugin.getMessages().getConfig().getString(msg.getPath()) == null) {
                GunshellPlugin.getMessages().getConfig().set(msg.getPath(), msg.getMessage());
            }
        }

        GunshellPlugin.getMessages().saveConfig();
    }
}