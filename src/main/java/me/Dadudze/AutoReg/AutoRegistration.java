package me.Dadudze.AutoReg;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class AutoRegistration extends JavaPlugin {

    private static AutoRegistration instance;

    @Override
    public void onEnable() {
        instance = this;
    }

    /**
     * Registers all Listeners
     * @param packagePrefix Required package of class
     */
    public static void registerListeners(String packagePrefix) {
        try {
            Field classes = Class.forName("org.bukkit.plugin.java.PluginClassLoader").getDeclaredField("classes");
            classes.setAccessible(true);
            for (String aClass : getClasses(FindExecutor.getMyExecutor())) {
                if(aClass.startsWith(packagePrefix)) {
                    try {
                        Bukkit.getPluginManager().registerEvents((Listener) Class.forName(aClass).newInstance(), instance);
                    } catch (Exception exception) {exception.printStackTrace();}
                }
            }
        } catch (Exception e) {e.printStackTrace();}
    }

    /**
     * Registers all AutoRegExecutors
     * @param packagePrefix Required package of class
     */
    public static void registerAutoRegExecutors(String packagePrefix) {
        try {
            Field classes = Class.forName("org.bukkit.plugin.java.PluginClassLoader").getDeclaredField("classes");
            classes.setAccessible(true);
            for (String aClass : getClasses(FindExecutor.getMyExecutor())) {
                if(aClass.startsWith(packagePrefix)) {
                    try {
                        AutoRegExecutor are = (AutoRegExecutor) Class.forName(aClass).newInstance();
                        for (String name : are.getNames()) {
                            System.out.println(name);
                            ((CraftServer) Bukkit.getServer()).getCommandMap().register("", new BukkitCommand(name) {
                                @Override
                                public boolean execute(CommandSender commandSender, String s, String[] strings) {
                                    return are.onCommand(commandSender, this, s, strings);
                                }
                            });
                        }
                    } catch (Exception exception) {exception.printStackTrace();}
                }
            }
        } catch (Exception e) {e.printStackTrace();}
    }

    /***
     * @param oneClass Class from given plugin
     * @return Array list of classes belonging to given plugin
     */
    private static ArrayList<String> getClasses(Class oneClass) {
        for (File file : instance.getDataFolder().getParentFile().listFiles()) {
            if(file.getName().endsWith(".jar")) {
                try {
                    JarFile jf = new JarFile(file);
                    if (jf.getEntry(oneClass.getName().replaceAll("[.]", "/") + ".class") != null) {
                        ArrayList<String> classes = new ArrayList<>();
                        Enumeration<JarEntry> entries = jf.entries();
                        while (true) {
                            if (!entries.hasMoreElements()) break;
                            JarEntry jarEntry = entries.nextElement();
                            if(jarEntry.getName().endsWith(".class") && !jarEntry.getName().contains("$")) {
                                classes.add(jarEntry.getName().replaceFirst("[.]class", "").replaceAll("/", "."));
                            }
                        }
                        return classes;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ArrayList<>();
    }
}
