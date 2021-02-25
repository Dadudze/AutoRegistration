package me.Dadudze.AutoReg;

import org.bukkit.command.CommandExecutor;

public abstract class AutoRegExecutor implements CommandExecutor {

    private String[] names;

    public AutoRegExecutor(String name, String... names) {
        this.names = new String[names.length+1];
        this.names[0] = name;
        for (int i = 0; i < names.length; i++) {
            this.names[i+1] = names[i];
        }
    }

    public String[] getNames() {
        return names;
    }
}
